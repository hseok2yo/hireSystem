package hireSystem.service.dao;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
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

	public int selectJobPostingCount(JobPostingSearchVo searchVo) {
		return selectOne("hireJobPostingDao.selectJobPostingCount", searchVo);
	}

	public EgovMap normalJobDetail(int jobPostingId) {
		return selectOne("hireJobPostingDao.normalJobDetail", jobPostingId);
	}

	public int increaseViewCnt(int jobPostingId) {
		return update("hireJobPostingDao.increaseViewCnt", jobPostingId);
	}

	/** 지원하기 완료 시 누적 지원자수 +1 */
	public int increaseApplyCnt(int jobPostingId) {
		return update("hireJobPostingDao.increaseApplyCnt", jobPostingId);
	}


}
