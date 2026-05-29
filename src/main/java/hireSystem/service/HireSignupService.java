package hireSystem.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

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
	
	/**
	 * 유저 1명 전체정보 가져오기
	 * @param userNum 조회할 유저pk번호
	 * @return 유저정보 단건조회
	 */
	public HireUserVo selectHireUserInfo(int userNum);

	public String updateUserPhoto(MultipartFile file, int userNum) throws IOException;
}
