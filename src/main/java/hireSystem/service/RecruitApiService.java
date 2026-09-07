package hireSystem.service;

import hireSystem.vo.RecruitSearchParam;

/**
 * [청년 일자리 올인원 지원 서비스] 채용공시 OpenAPI 연동 서비스
 * End Point: https://apis.data.go.kr/1051000/recruitment
 *
 * 이 인터페이스는 "외부 채용정보 OpenAPI를 호출해서 원본 JSON을 그대로 가져오는" 역할만 담당한다.
 * - JSON을 파싱해서 화면용 객체로 변환하는 책임은 여기 없음 (그건 RecruitController 쪽에서 처리)
 * - 실제 HTTP 호출 로직은 구현체인 RecruitApiServiceImpl에 있음
 *
 * 빈(Bean) 등록: 구현체에 @Service("recruitApiService")로 이름이 지정되어 있고,
 * RecruitController에서는 @Resource(name = "recruitApiService")로 이 인터페이스 타입 필드에 주입받는다.
 * (eGovFrame에서 흔히 쓰는 "인터페이스 - 구현체 분리" 패턴)
 */
public interface RecruitApiService {

    /**
     * 채용공시 목록조회 (/list)
     *
     * @param param 조회조건 VO. 페이지번호/페이지당건수(pageNo, numOfRows)와
     *              공고제목/고용형태/근무지역/채용구분/진행여부 등 각종 검색 필터를 담고 있다.
     *              (개별 코드값은 코드정의서 MOEF_NKOD_DB_05 참조)
     * @return OpenAPI가 내려준 원본 응답 JSON 문자열을 가공 없이 그대로 반환.
     *         형태 예: {"resultCode":200,"resultMsg":"OK","totalCount":123,"result":[{...}, {...}]}
     *         호출측(RecruitController)에서 Jackson으로 파싱해서 사용한다.
     */
    String getRecruitListJson(RecruitSearchParam param);

}