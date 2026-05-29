package hireSystem.service.dao;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;

@Repository("hireResumeDao")
public class HireResumeDao extends HireSystemAbstractMapper{

	public EgovMap selectResumeUserInfo(int loginUserNum) {
		return selectOne("hireResumeDao.selectResumeUserInfo", loginUserNum);
	}

	public int insertEmptyResume(int loginUserNum) {
		return insert("hireResumeDao.insertEmptyResume", loginUserNum);
	}
	
	
}
