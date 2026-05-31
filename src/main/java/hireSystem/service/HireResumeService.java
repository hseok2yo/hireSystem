package hireSystem.service;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import hireSystem.vo.HireResumeVo;

public interface HireResumeService {
	
	/**
	 * 유저정보들
	 * @param loginUserNum 유저번호
	 * @return
	 */
	public EgovMap selectHireUserInfo(int loginUserNum);
	
	/**
	 * 이력서 없는경우 임시로 생성
	 * @param loginUserNum
	 */
	public int insertEmptyResume(int loginUserNum);
	
	/**
	 * 이력서 기본정보 저장
	 * @param vo
	 * @return 성공여부 1
	 */
	public int insertBasicResume(HireResumeVo vo);

	public int updateBasicResume(HireResumeVo vo);
		

}
