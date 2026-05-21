package hireSystem.service;

import hireSystem.vo.HireUserVo;

public interface HireLoginService {
	
	/**
	 * 로그인 체크
	 * @param hireUserVo 로그인 정보
	 * @return 성공여부
	 */
	public HireUserVo checkLogin(HireUserVo hireUserVo);

}
