package hireSystem.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hireSystem.service.RecruitApiService;
import hireSystem.vo.RecruitSearchParam;

/**
 * [채용정보 화면단 컨트롤러]
 *
 * 흐름 요약
 * 1) 사용자가 /hireSystem/recruit/recruitList.do 로 검색조건(param)을 담아 요청
 * 2) RecruitApiService(구현체는 RecruitApiServiceImpl)를 통해 공공데이터포털 OpenAPI를 직접 호출
 *    -> 이 컨트롤러는 "가공 없는 원본 JSON 문자열"을 그대로 돌려받는다.
 * 3) 받은 JSON 문자열을 Jackson(ObjectMapper)으로 파싱해서
 *    - 화면에 뿌릴 리스트(recruitList, List<Map<String,Object>>)
 *    - 페이징 계산에 필요한 totalCount
 *    두 가지를 추출한다.
 * 4) 페이징(현재 페이지/전체 페이지/페이지 블록 시작·끝)을 직접 계산해서 Model에 담는다.
 * 5) JSP(recruitList.jsp)로 forward 하면서 Model에 담긴 값들을 화면에서 사용한다.
 */
@Controller
@RequestMapping("/hireSystem/recruit")
public class RecruitController {

    // 컨트롤러 전용 로거. 클래스명 기준으로 찍히므로 로그에서 "RecruitController"로 필터링 가능
    private static final Logger logger = LoggerFactory.getLogger(RecruitController.class);

    private final String path = "hireSystem/job/";

    // eGovFrame 관례: 인터페이스 타입으로 주입, 빈 이름은 ServiceImpl에 등록한 "recruitApiService"
    @Resource(name = "recruitApiService")
    private RecruitApiService recruitApiService;

    /**
     * 채용공시 목록 화면
     *
     * @param param 검색조건 VO. GET 파라미터(recrutPbancTtl, hireTypeLst, workRgnLst, recrutSe,
     *              ongoingYn, pageNo, numOfRows 등)가 @ModelAttribute로 자동 바인딩된다.
     * @param model 뷰(recruitList.jsp)로 전달할 데이터 컨테이너
     * @return "hireSystem/job/recruitList" (View 이름, ViewResolver가 실제 jsp 경로로 변환)
     */
    @RequestMapping("/recruitList.do")
    public String recruitList(@ModelAttribute RecruitSearchParam param, Model model) {

        // 1) 페이징 기본값 보정: 화면에서 pageNo/numOfRows를 안 넘기거나 이상한 값(0, 음수)을 넘긴 경우 대비
        if (param.getPageNo() <= 0) param.setPageNo(1);
        if (param.getNumOfRows() <= 0) param.setNumOfRows(10);

        logger.info("[채용정보 목록 요청] pageNo={}, numOfRows={}, recrutPbancTtl={}, hireTypeLst={}, "
                + "workRgnLst={}, recrutSe={}, ongoingYn={}",
                param.getPageNo(), param.getNumOfRows(), param.getRecrutPbancTtl(),
                param.getHireTypeLst(), param.getWorkRgnLst(), param.getRecrutSe(), param.getOngoingYn());

        // 2) 서비스 계층 호출 -> 내부적으로 공공데이터포털 OpenAPI를 RestTemplate으로 호출한다.
        //    이 시점에는 아직 JSON "문자열"이며, 파싱은 이 컨트롤러가 직접 담당한다.
        long apiStart = System.currentTimeMillis();
        String json = recruitApiService.getRecruitListJson(param);
        long apiElapsed = System.currentTimeMillis() - apiStart;

        logger.info("[채용정보 API 응답 수신] 소요시간={}ms, 응답길이={}자",
                apiElapsed, (json == null ? 0 : json.length()));
        // 응답 원문 전체를 debug 레벨로 남겨서, 실제 운영에서 문제 생기면 로그만 보고도
        // OpenAPI가 어떤 필드를 내려주는지 바로 확인 가능하게 함 (info 레벨에는 안 찍어서 로그량 과다 방지)
        logger.debug("[채용정보 API 원본 응답 JSON] {}", json);

        List<Map<String, Object>> recruitList = new ArrayList<>();
        int totalCount = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            // 실제 응답 구조: { "resultCode":200, "resultMsg":"...", "totalCount":123, "result":[ {...}, ... ] }
            totalCount = root.path("totalCount").asInt(0);

            int resultCode = root.path("resultCode").asInt(-1);
            String resultMsg = root.path("resultMsg").asText("");
            logger.info("[채용정보 API 응답 헤더] resultCode={}, resultMsg={}, totalCount={}",
                    resultCode, resultMsg, totalCount);

            JsonNode resultNode = root.path("result");
            if (resultNode.isArray()) {
                // 정상 케이스: result가 배열 -> 각 원소(공고 1건)를 LinkedHashMap으로 변환해서 담는다.
                // LinkedHashMap을 쓰는 이유: JSON 응답의 필드 순서를 그대로 유지해서
                // 화면(JSP)에서 ${item.recrutPbancTtl} 처럼 키로 바로 접근할 수 있게 하기 위함.
                for (JsonNode node : resultNode) {
                    recruitList.add(mapper.convertValue(node,
                            new TypeReference<LinkedHashMap<String, Object>>() {}));
                }
                logger.info("[채용정보 목록 파싱 완료] 배열 형태, 파싱된 건수={}건", recruitList.size());
            } else if (resultNode.isObject() && resultNode.size() > 0) {
                // 결과가 1건일 때 배열이 아니라 단일 객체로 오는 경우 대비 (공공데이터포털 API 흔한 특성)
                recruitList.add(mapper.convertValue(resultNode,
                        new TypeReference<LinkedHashMap<String, Object>>() {}));
                logger.info("[채용정보 목록 파싱 완료] 단일 객체 형태(결과 1건)를 리스트로 변환함");
            } else {
                // result가 비어있거나 없는 경우 -> 검색조건에 해당하는 채용공고가 없는 정상적인 상황일 수 있음
                logger.info("[채용정보 목록 없음] result 노드가 비어있거나 존재하지 않음 (검색결과 0건으로 처리)");
            }
        } catch (Exception e) {
            // 파싱 실패해도 화면 하단 원본 JSON으로 원인 확인 가능하게 흐름은 계속 진행
            // (예: OpenAPI 서버 점검중 응답이 JSON이 아닌 XML/HTML 에러 페이지로 오는 경우 등)
            logger.error("[채용정보 JSON 파싱 실패] 원인={}, 원본 응답 일부={}",
                    e.toString(),
                    (json != null && json.length() > 300 ? json.substring(0, 300) + "..." : json), e);
        }

        // 3) 페이징 계산
        //    - totalPages: 전체 건수를 페이지당 건수로 나눠서 올림
        //    - pageBlockSize: 한 화면에 보여줄 페이지 번호 묶음 단위 (예: 1~10, 11~20 ...)
        int numOfRows = param.getNumOfRows();
        int currentPage = param.getPageNo();
        int totalPages = (int) Math.ceil((double) totalCount / numOfRows);
        if (totalPages < 1) totalPages = 1;

        int pageBlockSize = 10;
        int blockStart = ((currentPage - 1) / pageBlockSize) * pageBlockSize + 1;
        int blockEnd = Math.min(blockStart + pageBlockSize - 1, totalPages);

        logger.info("[채용정보 페이징 계산] currentPage={}, totalPages={}, numOfRows={}, blockStart={}, blockEnd={}",
                currentPage, totalPages, numOfRows, blockStart, blockEnd);

        // 4) 화면(JSP)에서 사용할 데이터를 Model에 적재
        model.addAttribute("recruitList", recruitList);   // 실제 목록 렌더링용 데이터
        model.addAttribute("recruitJson", json);           // 디버깅/원본 확인용 원문 JSON (화면 하단 등에 표시 가능)
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("blockStart", blockStart);
        model.addAttribute("blockEnd", blockEnd);
        model.addAttribute("numOfRows", numOfRows);

        // 검색 폼 값 유지용 (JSP에서 ${...} 로 다시 채워줌) - 페이지 이동 시에도 검색조건이 유지되도록
        model.addAttribute("recrutPbancTtl", param.getRecrutPbancTtl());
        model.addAttribute("hireTypeLst", param.getHireTypeLst());
        model.addAttribute("workRgnLst", param.getWorkRgnLst());
        model.addAttribute("recrutSe", param.getRecrutSe());
        model.addAttribute("ongoingYn", param.getOngoingYn());

        logger.info("[채용정보 목록 화면 반환] view={}", path + "recruitList");

        return path + "recruitList";
    }
}