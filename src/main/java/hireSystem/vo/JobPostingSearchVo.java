package hireSystem.vo;

import lombok.Data;

/**
 * 일반채용정보(자체 DB) 목록조회 검색조건
 */
@Data
public class JobPostingSearchVo {

	private int page = 1;
	private int numOfRows = 10;

	private String keyword;    // 직무명/회사명 검색어
	private String ongoingYn;  // 진행여부 [Y/N]
	private String hireType;   // 고용형태 (정규직/계약직/무기계약직/청년인턴)
	private String recrutSe;   // 채용구분 (신입/경력/신입+경력/외국인전형)
	private String workRegion; // 근무지역

	private int offset;        // 서비스단에서 계산해서 세팅 (page, numOfRows 기반)
}
