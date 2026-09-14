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

	/**
	 * 일반채용정보 상세 조회 (조회수 +1 포함)
	 */
	HireJobPostingVo selectJobPostingDetail(int jobPostingId);

	/**
	 * 채용공고 등록
	 */
	int insertJobPosting(HireJobPostingVo jobPostingVo);
}
