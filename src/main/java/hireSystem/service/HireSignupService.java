package hireSystem.service;

import hireSystem.vo.HireUserVo;

public interface HireSignupService {
	
	/**
	 * 아이디 중복체크
	 * @param id 중복체크할 아이디
	 * @return 0 : 없음 1 : 있음
	 */
	public int checkDuplicationID(String id);
	
	/**
	 * 유저정보 등록(회원가입)
	 * @param hireUserVo 유저정보vo
	 * @return 성공여부
	 */
	public boolean registMember(HireUserVo hireUserVo);
}
