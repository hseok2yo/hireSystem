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

	public int selectJobPostingCount(JobPostingSearchVo searchVo) {
		return selectOne("hireJobPostingDao.selectJobPostingCount", searchVo);
	}

}
