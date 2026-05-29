package hireSystem.service;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

public interface HireResumeService {
	
	/**
	 * 이력서 기본정보
	 * @param loginUserNum 유저번호
	 * @return
	 */
	public EgovMap selectResumeUserInfo(int loginUserNum);
	
	/**
	 * 이력서 없는경우 임시로 생성
	 * @param loginUserNum
	 */
	public int insertEmptyResume(int loginUserNum);
		

}
