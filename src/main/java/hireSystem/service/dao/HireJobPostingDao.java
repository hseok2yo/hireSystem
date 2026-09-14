package hireSystem.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireJobPostingVo;
import hireSystem.vo.JobPostingSearchVo;

@Repository("hireJobPostingDao")
public class HireJobPostingDao extends HireSystemAbstractMapper {

	/** 일반채용정보 목록 조회 */
	public List<HireJobPostingVo> selectJobPostingList(JobPostingSearchVo searchVo) {
		return selectList("hireJobPostingDao.selectJobPostingList", searchVo);
	}

	/** 일반채용정보 총 건수 */
	public int selectJobPostingCount(JobPostingSearchVo searchVo) {
		return selectOne("hireJobPostingDao.selectJobPostingCount", searchVo);
	}

	/** 일반채용정보 상세 조회 */
	public HireJobPostingVo selectJobPostingDetail(int jobPostingId) {
		return selectOne("hireJobPostingDao.selectJobPostingDetail", jobPostingId);
	}

	/** 채용공고 등록 (기업회원) */
	public int insertJobPosting(HireJobPostingVo jobPostingVo) {
		return insert("hireJobPostingDao.insertJobPosting", jobPostingVo);
	}

	/** 조회수 +1 */
	public int updateViewCount(int jobPostingId) {
		return update("hireJobPostingDao.updateViewCount", jobPostingId);
	}
}
