package hireSystem.service.impl;

import hireSystem.service.RecruitApiService;
import hireSystem.vo.RecruitSearchParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * RecruitApiService 구현체
 *
 * 실제로 공공데이터포털(data.go.kr) "청년 일자리 올인원 지원 서비스" OpenAPI를
 * RestTemplate으로 직접 호출하는 클래스. 데이터가 나오기까지의 흐름은 다음과 같다.
 *
 *   RecruitController.recruitList()
 *        -> RecruitApiServiceImpl.getRecruitListJson(param)   [본 클래스]
 *             1) UriComponentsBuilder로 쿼리파라미터(serviceKey, pageNo, numOfRows,
 *                검색조건들)를 조립해서 최종 요청 URL(uri)을 만든다.
 *             2) callApi(uri) 에서 RestTemplate.exchange(GET)로 실제 HTTP 요청을 보낸다.
 *             3) 응답 바디(JSON 문자열)를 가공 없이 그대로 반환한다.
 *        -> RecruitController가 그 JSON 문자열을 Jackson으로 파싱해서 화면에 뿌린다.
 *
 * 주의: 요청대로 서비스키를 소스코드에 직접 넣었음.
 * 실제 운영 배포 시에는 반드시 properties/환경변수/Vault 등으로 분리하는 게 안전함.
 */
@Service("recruitApiService")
public class RecruitApiServiceImpl implements RecruitApiService {

    // 이 클래스 전용 로거. RecruitController 로그와 구분해서 "실제 외부 API 호출 구간"만 추적 가능
    private static final Logger logger = LoggerFactory.getLogger(RecruitApiServiceImpl.class);

    // 공공데이터포털에서 발급받은 인증키 (Encoding 된 값 그대로 사용)
    private static final String SERVICE_KEY =
            "%2Bt74wBuvAszqEeE9BKcwwW4MceBu4icZ6TP2u6KGnnIjxm%2FxHVRAl1CKQb5OKgW932n5357ZBdP78FrQnHOwAQ%3D%3D";

    private static final String LIST_URL = "https://apis.data.go.kr/1051000/recruitment/list";
    // 상세조회 필요하면 아래 URL도 같은 패턴으로 추가하면 됨
    private static final String DETAIL_URL = "https://apis.data.go.kr/1051000/recruitment/detail";

    private final RestTemplate restTemplate = new RestTemplate();

    // 로그 미리보기 전용 ObjectMapper. RecruitController가 실제로 쓰는 파싱과는 완전히 별개이며,
    // 오직 "응답 첫번째 데이터 1건만 로그로 예쁘게 찍기" 위한 용도임. (ObjectMapper는 read 시 스레드세이프)
    private final ObjectMapper previewMapper = new ObjectMapper();

    // 필드명(영문) -> 한글 설명 매핑. 로그 가독성용. 실제 파싱 로직에는 영향 없음.
    // 값의 출처: 위 스웨거 응답 모델(Model)에 나온 필드 전체.
    // buildFieldLabels()가 클래스 로드 시 한 번만 호출되어 그 결과로 초기화됨 (static 블록 대신 메서드로 분리)
    private static final Map<String, String> FIELD_LABELS = buildFieldLabels();

    /**
     * FIELD_LABELS 맵을 만들어서 반환하는 static 팩토리 메서드.
     * static 초기화 블록(static { ... }) 대신 이 방식을 쓴 이유:
     *  - 초기화 로직에 이름(buildFieldLabels)이 붙어서 뭘 하는 코드인지 바로 파악됨
     *  - 클래스 상단이 "필드 선언부"와 "초기화 로직"으로 명확히 분리되어 더 읽기 편함
     * 실행 시점은 static 블록과 동일하게 "클래스가 처음 로드될 때 딱 1번"임.
     */
    private static Map<String, String> buildFieldLabels() {
        Map<String, String> labels = new LinkedHashMap<>();
        labels.put("recrutPblntSn", "공고일련번호");
        labels.put("pblntInstCd", "기관코드");
        labels.put("pbadmsStdInstCd", "행정표준기관코드");
        labels.put("instNm", "기관명");
        labels.put("ncsCdLst", "NCS코드목록");
        labels.put("ncsCdNmLst", "NCS분류명목록");
        labels.put("hireTypeLst", "고용형태코드목록");
        labels.put("hireTypeNmLst", "고용형태명목록");
        labels.put("workRgnLst", "근무지역코드목록");
        labels.put("workRgnNmLst", "근무지역명목록");
        labels.put("recrutSe", "채용구분코드");
        labels.put("recrutSeNm", "채용구분명");
        labels.put("prefCondCn", "우대조건내용");
        labels.put("recrutNope", "모집인원");
        labels.put("pbancBgngYmd", "공고시작일");
        labels.put("pbancEndYmd", "공고종료일");
        labels.put("recrutPbancTtl", "공고제목");
        labels.put("srcUrl", "원문링크");
        labels.put("replmprYn", "대체인력여부");
        labels.put("aplyQlfcCn", "지원자격내용");
        labels.put("disqlfcRsn", "결격사유");
        labels.put("scrnprcdrMthdExpln", "전형절차 설명");
        labels.put("prefCn", "우대사항");
        labels.put("acbgCondLst", "학력조건코드목록");
        labels.put("acbgCondNmLst", "학력조건명목록");
        labels.put("nonatchRsn", "입사지원서 미첨부 사유");
        labels.put("ongoingYn", "진행여부(Y/N)");
        labels.put("decimalDay", "마감까지 남은 일수");
        labels.put("files", "첨부파일목록");
        labels.put("steps", "전형단계목록");
        return labels;
    }

    /**
     * 채용공시 목록조회 API 호출
     *
     * 동작 순서:
     *  1) LIST_URL을 베이스로 UriComponentsBuilder를 생성하고, 필수 파라미터(serviceKey, resultType,
     *     pageNo, numOfRows)를 먼저 세팅한다.
     *  2) addIfNotEmpty()로 선택적 검색조건들(제목/고용형태/근무지역 등)을 값이 있을 때만 추가한다.
     *     -> 값이 없는 파라미터를 그냥 넣으면 API가 "빈 문자열로 필터링"해서 결과가 0건이 될 수 있으므로
     *        반드시 비어있지 않은 값만 붙인다.
     *  3) build(true)로 URI를 완성한 뒤 callApi()로 실제 호출을 위임한다.
     */
    @Override
    public String getRecruitListJson(RecruitSearchParam param) {

        logger.info("[채용정보 API 요청 파라미터 조립 시작] pageNo={}, numOfRows={}",
                param.getPageNo(), param.getNumOfRows());

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(LIST_URL)
                .queryParam("serviceKey", SERVICE_KEY)
                .queryParam("resultType", "json")
                .queryParam("pageNo", param.getPageNo())
                .queryParam("numOfRows", param.getNumOfRows());

        addIfNotEmpty(builder, "ongoingYn", param.getOngoingYn());
        addIfNotEmpty(builder, "recrutPbancTtl", param.getRecrutPbancTtl());
        addIfNotEmpty(builder, "recrutSe", param.getRecrutSe());
        addIfNotEmpty(builder, "hireTypeLst", param.getHireTypeLst());
        addIfNotEmpty(builder, "acbgCondLst", param.getAcbgCondLst());
        addIfNotEmpty(builder, "workRgnLst", param.getWorkRgnLst());
        addIfNotEmpty(builder, "ncsCdLst", param.getNcsCdLst());
        addIfNotEmpty(builder, "instClsf", param.getInstClsf());
        addIfNotEmpty(builder, "instType", param.getInstType());
        addIfNotEmpty(builder, "pblntInstCd", param.getPblntInstCd());
        addIfNotEmpty(builder, "pbancBgngYmd", param.getPbancBgngYmd());
        addIfNotEmpty(builder, "pbancEndYmd", param.getPbancEndYmd());
        addIfNotEmpty(builder, "replmprYn", param.getReplmprYn());

        // serviceKey가 이미 URL 인코딩된 값이므로 build(true)로 재인코딩 방지
        // (build() 그냥 쓰면 %2B 같은 값이 %252B 로 이중 인코딩되어 인증 실패남)
        URI uri = builder.build(true).toUri();

        // serviceKey는 인증정보이므로 로그에는 마스킹해서 남긴다 (원본 그대로 찍지 않음)
        String maskedUrl = uri.toString().replace(SERVICE_KEY, "****MASKED****");
        logger.info("[채용정보 API 최종 요청 URL] {}", maskedUrl);

        return callApi(uri);
    }

    /**
     * 실제 HTTP GET 호출을 수행하는 부분.
     * Accept 헤더를 MediaType.ALL로 열어둔 이유: 이 API가 상황에 따라
     * Content-Type을 application/json이 아닌 다른 값으로 내려주는 경우가 있어서,
     * Spring의 MediaType 불일치로 인한 예외를 피하기 위함.
     *
     * 참고: restTemplate.exchange(..., String.class)에서 마지막 인자 String.class는
     * "이 응답을 String으로 변환해달라"는 뜻일 뿐, JSON을 해석(파싱)하는 게 아니다.
     * Spring이 내부적으로 StringHttpMessageConverter를 골라서, 응답 바이트를
     * (Content-Type의 charset 기준으로) 문자로 디코딩만 해줄 뿐이다.
     * 즉 response.getBody()로 나오는 body는 "JSON 모양을 한 순수 텍스트"이고,
     * 실제 파싱(JsonNode로 쪼개기)은 이 body를 Jackson(ObjectMapper)에 넘기는 시점에 비로소 일어난다.
     * (여기서는 로그 미리보기용으로 아래 previewFirstResultItem()에서, 화면용으로는
     *  RecruitController.recruitList()에서 각각 별도로 파싱한다.)
     *
     * @param uri 조립이 끝난 최종 요청 URI (쿼리파라미터 포함)
     * @return 응답 바디 원문(JSON 문자열). 실패 시 예외를 로깅하고 그대로 던진다.
     */
    private String callApi(URI uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        long start = System.currentTimeMillis();
        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            long elapsed = System.currentTimeMillis() - start;
            String body = response.getBody();

            logger.info("[채용정보 API 호출 성공] httpStatus={}, 소요시간={}ms, 응답바디길이={}자",
                    response.getStatusCode(), elapsed, (body == null ? 0 : body.length()));

            // 응답이 10건이든 100건이든, 로그에는 "첫 번째 데이터 1건"만 필드명(한글설명 포함)과
            // 함께 보기 좋게 남긴다. 원문 전체를 그대로 찍던 기존 방식은 항목이 많을 때 로그가
            // 너무 길어져서 가독성이 떨어졌기 때문에 이걸로 대체함.
            previewFirstResultItem(body);

            return body;
        } catch (RestClientException e) {
            long elapsed = System.currentTimeMillis() - start;
            // 네트워크 오류, 타임아웃, 4xx/5xx 등 HTTP 호출 자체가 실패한 경우
            logger.error("[채용정보 API 호출 실패] 소요시간={}ms, 원인={}", elapsed, e.toString(), e);
            throw e;
        }
    }

    /**
     * 응답 바디(JSON) 중 result 배열의 "첫 번째 데이터 1건만" 필드명(한글설명 포함) : 값
     * 형태로 로그에 남기기 위한 미리보기 전용 파서.
     *
     * - RecruitController가 실제 화면 렌더링에 쓰는 파싱 로직과는 완전히 별개. 여기서 파싱 실패해도
     *   실제 서비스 흐름(return body)에는 전혀 영향 없음 (예외를 잡아서 warn 로그만 남기고 끝냄).
     * - Swagger 문서상 예시는 result[0].item.필드 구조이지만, 실제 API 응답은 item 래핑 없이
     *   result[0].필드 로 바로 온다. 두 구조 모두 대응하도록 item이 있으면 한 겹 벗겨내고 쓴다.
     *
     * @param body callApi()가 받은 원문 JSON 문자열 (아직 파싱 전)
     */
    private void previewFirstResultItem(String body) {
        try {
            JsonNode root = previewMapper.readTree(body);
            int totalCount = root.path("totalCount").asInt(0);

            JsonNode resultNode = root.path("result");
            JsonNode firstItem = null;

            if (resultNode.isArray() && resultNode.size() > 0) {
                firstItem = resultNode.get(0);
            } else if (resultNode.isObject() && resultNode.size() > 0) {
                // 결과가 1건일 때 배열이 아니라 단일 객체로 오는 경우 대비
                firstItem = resultNode;
            }

            if (firstItem == null) {
                logger.debug("[채용정보 API 응답 미리보기] result 데이터 없음 (totalCount={})", totalCount);
                return;
            }

            // Swagger 문서 구조(result[0].item.필드) 대응: item으로 한 번 더 감싸져 있으면 벗겨냄
            if (firstItem.has("item")) {
                firstItem = firstItem.path("item");
            }

            StringBuilder sb = new StringBuilder();
            sb.append("\n========== [채용정보 API 응답 미리보기] 전체 ").append(totalCount)
                    .append("건 중 1번째 데이터만 표시 ==========\n");

            Iterator<Map.Entry<String, JsonNode>> fields = firstItem.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String fieldName = entry.getKey();
                String label = FIELD_LABELS.getOrDefault(fieldName, "(설명없음)");
                // files, steps처럼 배열/객체인 필드는 asText()가 아니라 toString()으로 그대로 보여줌
                String value = entry.getValue().isValueNode()
                        ? entry.getValue().asText()
                        : entry.getValue().toString();

                sb.append(String.format("  %-20s [%s] : %s%n", fieldName, label, value));
            }
            sb.append("=====================================================");

            logger.debug(sb.toString());
        } catch (Exception e) {
            // 미리보기 로그 생성 실패는 실제 서비스 흐름과 무관하므로 warn으로만 남기고 조용히 넘어감
            logger.warn("[채용정보 API 응답 미리보기 로그 생성 실패] 원인={}", e.toString());
        }
    }

    /**
     * 검색조건 값이 비어있지 않을 때만 쿼리파라미터로 추가하는 헬퍼.
     * (null 또는 공백만 있는 문자열은 API에 아예 전달하지 않음 -> 불필요한 필터링 방지)
     *
     * 주의: getRecruitListJson()에서 builder.build(true)로 URI를 만드는데,
     * build(true)는 "여기 넣은 값은 이미 인코딩된 값"이라고 간주하고 재인코딩을 안 한다
     * (serviceKey가 이미 인코딩된 값이라 이중 인코딩을 막으려고 이렇게 씀).
     * 그래서 한글/공백이 그대로 들어있는 검색조건 값(recrutPbancTtl 등)을 인코딩 없이 넣으면
     * URI 표준상 허용 안 되는 문자라 IllegalArgumentException이 난다.
     * -> 여기서 직접 URLEncoder로 인코딩해서 넣어줘야 build(true)와 앞뒤가 맞는다.
     */
    private void addIfNotEmpty(UriComponentsBuilder builder, String name, String value) {
        if (value != null && !value.trim().isEmpty()) {
            builder.queryParam(name, encode(value));
            logger.debug("[검색조건 파라미터 추가] {}={}", name, value);
        }
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // UTF-8은 JVM이 항상 지원하는 표준 인코딩이라 실제로는 발생하지 않는 예외
            throw new IllegalStateException(e);
        }
    }
}