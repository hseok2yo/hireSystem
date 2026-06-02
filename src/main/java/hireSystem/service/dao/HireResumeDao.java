package hireSystem.service.dao;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireCareerVo;
import hireSystem.vo.HireResumeVo;

@Repository("hireResumeDao")
public class HireResumeDao extends HireSystemAbstractMapper{

	public int insertEmptyResume(int loginUserNum) {
		return insert("hireResumeDao.insertEmptyResume", loginUserNum);
	}

	public int insertBasicResume(HireResumeVo vo) {
		return insert("hireResumeDao.insertBasicResume", vo);
	}

	public int updateBasicResume(HireResumeVo vo) {
		return update("hireResumeDao.updateBasicResume", vo);
	}

	public HireResumeVo selectResumeMainInfo(int loginUserNum) {

		return selectOne("hireResumeDao.selectResumeMainInfo", loginUserNum);
	}

	public List<HireResumeVo> selectResumeSubInfo(HireResumeVo vo) {

		return selectList("hireResumeDao.selectResumeSubInfo", vo);
	}

	public int selectSubTotalCount(HireResumeVo vo) {

		return selectOne("hireResumeDao.selectSubTotalCount", vo);
	}

	public List<HireCareerVo> selectCareerInfo(int resumeId) {
		return selectList("hireResumeDao.selectCareerInfo", resumeId);
	}

	public int updateCareer(HireCareerVo careerVo) {
		return update("hireResumeDao.updateCareer", careerVo);
	}

	public int insertCareer(HireCareerVo careerVo) {
		return insert("hireResumeDao.insertCareer", careerVo);
	}

	public int deleteCareer(int careerId) {
		return insert("hireResumeDao.deleteCareer", careerId);
	}



}
