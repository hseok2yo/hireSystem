package hireSystem.service;

import java.util.Map;

import hireSystem.vo.HireJobPostingVo;
import hireSystem.vo.JobPostingSearchVo;

public interface HireJobPostingService {

	/**
	 * 일반채용정보 목록 + 페이징 정보 조회
	 * @param searchVo 검색조건
	 * @return list(HireJobPostingVo), totalCount, currentPage, totalPages, blockStart, blockEnd, numOfRows
	 */
	Map<String, Object> selectJobPostingList(JobPostingSearchVo searchVo);

}
