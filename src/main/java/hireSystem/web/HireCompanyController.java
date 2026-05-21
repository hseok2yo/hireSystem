package hireSystem.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/hireSystem/company")
public class HireCompanyController {

	private final String path = "hireSystem/company/";

	/**
	 * @return 기업정보 페이지 이동
	 */
	@RequestMapping("/companies.do")
	public String companies(
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "") String coNm,       // 회사명 검색
	        @RequestParam(defaultValue = "") String coClcd,     // 기업구분코드 (10,20,30,40,50)
	        @RequestParam(defaultValue = "regDt") String sortField,    // 정렬필드
	        @RequestParam(defaultValue = "desc") String sortOrderBy,   // 정렬방식
	        Model model) {
		String authKey = "6e94a9a9-a08e-433d-ba91-bd9c113f3ff4";
		int display = 6;
		log.info("[companies.do] START page={}, display={}", page, display);

		String url = "https://www.work24.go.kr/cm/openApi/call/wk/callOpenApiSvcInfo210L31.do"
		        + "?authKey=" + authKey
		        + "&callTp=L"
		        + "&returnType=XML"
		        + "&startPage=" + page
		        + "&display=" + display
		        + "&sortField=" + sortField
		        + "&sortOrderBy=" + sortOrderBy
		        + "&coNm=" + coNm
		        + "&coClcd=" + coClcd;
	    log.info("[companies.do] API URL={}", url);
	    
		// ✅ 1. API 호출
	    RestTemplate restTemplate = new RestTemplate();
	    String response = restTemplate.getForObject(url, String.class);
	    log.info("[companies.do] XML 응답 수신 완료" + response);

		// ✅ 2. XML → JSONObject 변환
	    Map<String, Object> parsed = parseCompaniesXml(response);

	    // ✅ 3. Model에 담기(API값)
	    model.addAttribute("companyList", parsed.get("companyList"));
	    model.addAttribute("total",       parsed.get("total"));
	    model.addAttribute("startPage",   parsed.get("startPage"));
	    model.addAttribute("display",     parsed.get("display"));
	    model.addAttribute("companiesJson", parsed.get("companiesJson")); //jsp에서 json연습용
	    log.info("[companies.do] Model 세팅 완료 → companies 뷰로 이동");
	    
	    
	    int total = Integer.parseInt(parsed.get("total").toString());
	    int totalPages = (int) Math.ceil((double) total / display); // 전체 페이지 수

	    int blockSize = 10; // 페이징 버튼 10개씩
	    int blockStart = ((page - 1) / blockSize) * blockSize + 1; // ex) 1, 11, 21...
	    int blockEnd   = Math.min(blockStart + blockSize - 1, totalPages); // ex) 10, 20...
	    //페이징
	    model.addAttribute("currentPage",   page);
	    model.addAttribute("totalPages",    totalPages);
	    model.addAttribute("blockStart",    blockStart);
	    model.addAttribute("blockEnd",      blockEnd);
	    
	    //검색조건
	    model.addAttribute("coNm",       coNm);
	    model.addAttribute("coClcd",     coClcd);
	    model.addAttribute("sortField",  sortField);
	    model.addAttribute("sortOrderBy", sortOrderBy);
	    
		return path + "companies";
	}

	// ✅ XML → Map 파싱 메서드
	private Map<String, Object> parseCompaniesXml(String response) {
	    log.info("[parseCompaniesXml] 파싱 시작");

	    // XML → JSONObject 변환
	    JSONObject jsonObject = XML.toJSONObject(response);
//	    String jsonStr = jsonObject.toString();
//	    log.info("[parseCompaniesXml] JSON 전체 구조:\n{}", jsonStr);

	    // dhsOpenEmpHireInfoList 꺼내기
		// ✅ 실제 데이터 꺼내기
		// { } = JSONObject → 키:값 형태, 이름으로 꺼냄 { } → .으로 접근
		// [ ] = JSONArray → 순서있는 목록, 번호(인덱스)로 꺼냄 [ ] → [숫자]로 접근
	    JSONObject listRoot = jsonObject.getJSONObject("dhsOpenEmpHireInfoList");
	    log.info("[parseCompaniesXml] listRoot 키 목록: {}", listRoot.keySet());

	 // JSON 객체에서 "total"이라는 값을 문자열로 꺼내는 함수 (optString)
	    String total     = listRoot.optString("total");
	    String startPage = listRoot.optString("startPage");
	    String dispCnt   = listRoot.optString("display");
	    log.info("[parseCompaniesXml] total={}, startPage={}, display={}", total, startPage, dispCnt);

	    // 업체 목록 파싱
	    List<Map<String, Object>> companyList = parseCompanyList(listRoot);
	    log.info("[parseCompaniesXml] 파싱된 업체수={}", companyList.size());

	    // 결과 Map에 담기
	    Map<String, Object> result = new LinkedHashMap<>();
	    result.put("total",        total);
	    result.put("startPage",    startPage);
	    result.put("display",      dispCnt);
	    result.put("companyList",  companyList);
	    
//	    //json데이터 jsp에서 연습용임
//	    result.put("companiesJson", jsonStr
//	    	    .replace("\\r\\n", " ")   // JSON 안의 \r\n 문자열
//	    	    .replace("\\n", " ")      // JSON 안의 \n 문자열  
//	    	    .replace("\\r", " ")      // JSON 안의 \r 문자열
//	    	    .replaceAll("[\\x00-\\x1F\\x7F]", " "));
	    return result;
	}
	
	// ✅ 업체 목록 파싱 메서드 (1개/여러개 분기처리)
	private List<Map<String, Object>> parseCompanyList(JSONObject listRoot) {
	    log.info("[parseCompanyList] 업체목록 파싱 시작");

	    
		// ✅ 업체 목록 꺼내기 (1개면 Object, 여러개면 Array) - 오류방지
		Object raw = listRoot.opt("dhsOpenEmpHireInfo");
		
		// ✅ 결과 없을 때 (total=0) 빈 리스트 반환
	    if (raw == null) {
	        log.info("[parseCompanyList] 검색 결과 없음 (dhsOpenEmpHireInfo 없음)");
	        return new ArrayList<>();
	    }
	    
		// 로그 예시:
		// dhsOpenEmpHireInfo 타입: JSONArray (여러개일때)
		// dhsOpenEmpHireInfo 타입: JSONObject (1개일때)
		log.info("[companies.do] dhsOpenEmpHireInfo 타입: {}", raw.getClass().getSimpleName());

		// ✅ JSP가 읽을 수 있게 하기  (List<JSONObject> companyList // ❌ JSP가 못읽음)
	    List<Map<String, Object>> companyList = new ArrayList<>();

	    if (raw instanceof JSONArray) {
	        JSONArray arr = (JSONArray) raw;
	        log.info("[parseCompanyList] 배열 형태 - 업체 {}개", arr.length());
	        for (int i = 0; i < arr.length(); i++) {
	            Map<String, Object> map = toMap(arr.getJSONObject(i));
	            companyList.add(map);
	            
            	// 로그 예시:
				// 1번째 업체 - coNm=에이치디씨폴리올, coClcdNm=, coIntroSummaryCont=운송장비 조립용 플라스틱제품 제조업
				// 2번째 업체 - coNm=넥스트로, coClcdNm=중견기업, coIntroSummaryCont=자동차부품물류 전문기업
				// 3번째 업체 - coNm=서울특별시120다산콜재단, coClcdNm=공공기관, coIntroSummaryCont=시민이 120%
	            log.info("[parseCompanyList] {}번째 업체 - coNm={}, coClcdNm={}",
	                    i + 1, map.get("coNm"), map.get("coClcdNm"));
	        }
	    } else if (raw instanceof JSONObject) {
	        log.info("[parseCompanyList] 객체 형태 - 업체 1개");
	        Map<String, Object> map = toMap((JSONObject) raw);
	        companyList.add(map);
	        log.info("[parseCompanyList] 1번째 업체 - coNm={}, coClcdNm={}",
	                map.get("coNm"), map.get("coClcdNm"));
	    }

	    return companyList;
	}
	
	// ✅ JSONObject → Map 변환 메서드 (빈값 "" 보장)
	private Map<String, Object> toMap(JSONObject company) {
	    Map<String, Object> map = new HashMap<>();
	    for (String key : company.keySet()) {
	        map.put(key, company.optString(key, "")); // 없으면 "" 보장
	    }
	    return map;
	}
	
	
	
	
	
	
	
	/**
	 * 연습용 JSON 직접 확인 엔드포인트
	 */
	@ResponseBody
	@RequestMapping(value = "/companies/json.do", produces = "application/json; charset=UTF-8")
	public Map<String, Object> companiesJson(@RequestParam(defaultValue = "1") int page) {
		String authKey = "6e94a9a9-a08e-433d-ba91-bd9c113f3ff4";
		int display = 6;
		log.info("[companies/json.do] START page={}, display={}", page, display);

		String url = "https://www.work24.go.kr/cm/openApi/call/wk/callOpenApiSvcInfo210L31.do" + "?authKey=" + authKey
				+ "&callTp=L" + "&returnType=XML" + "&startPage=" + page + "&display=" + display;
		log.info("[companies/json.do] API URL={}", maskAuthKey(url));

		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		log.info("[companies/json.do] API response length={}", response == null ? 0 : response.length());

		Map<String, Object> result = parseCompaniesXmlToMap(response);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> companyList = (List<Map<String, Object>>) result.get("companies");
		log.info("[companies/json.do] DONE companyCount={}", companyList.size());
		return result;
	}

	private Map<String, Object> parseCompaniesXmlToMap(String xml) {
		if (xml == null || xml.trim().isEmpty()) {
			log.warn("[parseCompaniesXmlToMap] empty XML response");
			Map<String, Object> emptyResult = new LinkedHashMap<String, Object>();
			emptyResult.put("total", "0");
			emptyResult.put("startPage", "1");
			emptyResult.put("display", "0");
			emptyResult.put("companies", new ArrayList<Map<String, Object>>());
			return emptyResult;
		}

		JSONObject root = XML.toJSONObject(xml);
		JSONObject listRoot = root.getJSONObject("dhsOpenEmpHireInfoList");

		Object raw = listRoot.opt("dhsOpenEmpHireInfo");
		List<Map<String, Object>> companies = new ArrayList<Map<String, Object>>();

		if (raw instanceof JSONArray) {
			JSONArray arr = (JSONArray) raw;
			for (int i = 0; i < arr.length(); i++) {
				companies.add(toCompanyMap(arr.getJSONObject(i)));
			}
		} else if (raw instanceof JSONObject) {
			companies.add(toCompanyMap((JSONObject) raw));
		}

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", listRoot.optString("total", "0"));
		result.put("startPage", listRoot.optString("startPage", "1"));
		result.put("display", listRoot.optString("display", String.valueOf(companies.size())));
		result.put("companies", companies);
		log.debug("[parseCompaniesXmlToMap] parsed companies={}", companies.size());
		return result;
	}

	private Map<String, Object> toCompanyMap(JSONObject itemJson) {
		Map<String, Object> item = new LinkedHashMap<String, Object>();
		item.put("coClcdNm", itemJson.optString("coClcdNm", ""));
		item.put("regLogImgNm", itemJson.optString("regLogImgNm", ""));
		item.put("empCoNo", itemJson.optString("empCoNo", ""));
		item.put("coNm", itemJson.optString("coNm", ""));
		item.put("busino", itemJson.optString("busino", ""));
		item.put("mapCoorY", itemJson.optString("mapCoorY", ""));
		item.put("mapCoorX", itemJson.optString("mapCoorX", ""));
		item.put("coIntroSummaryCont", itemJson.optString("coIntroSummaryCont", ""));
		item.put("coIntroCont", itemJson.optString("coIntroCont", ""));
		item.put("homepg", itemJson.optString("homepg", ""));
		item.put("mainBusiCont", itemJson.optString("mainBusiCont", ""));
		return item;
	}

	private String maskAuthKey(String url) {
		return url.replaceAll("authKey=[^&]+", "authKey=****");
	}
}
