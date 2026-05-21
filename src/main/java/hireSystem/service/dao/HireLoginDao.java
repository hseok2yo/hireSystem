package hireSystem.service.dao;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireUserVo;

@Repository("hireLoginDao")
public class HireLoginDao extends HireSystemAbstractMapper{
	
	/**
	 * 로그인 체크
	 * @param hireUserVo 유저정보
	 * @return 성공여부
	 */
	public HireUserVo checkLogin(HireUserVo hireUserVo) {
		return selectOne("hireLoginDao.checkLogin", hireUserVo);
	}
	
}
