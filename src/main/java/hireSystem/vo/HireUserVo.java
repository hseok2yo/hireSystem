package hireSystem.vo;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HireUserVo {

    /** 유저넘버 */
    private int userNum;

    /** 유저아이디 */
    private String userId;

    /** 유저이메일 */
    private String userEmail;
    
    /** 유저비번*/
    private String userPw;

    /** 유저이름 */
    private String userNm;

    /** 유저폰번 */
    private String userPhone;

    /** 유저타입 */
    private String userMemberType;

    /** 등록일 */
    private Date regDt;
    
    /** 동의약관 리스트값 */
    private List<String> agreements;
    
    /** 현재 페이지 url값*/
	private String redirectUrl;
	
	/** 로그인 상태유지값 */
	private String remember;
	
	/** 카카오 id토큰값*/
	private String kakaoId;
	
	/** 회원가입 경로 null (NORMAL): 일반 / kakao : 카카오*/
	private String loginType;
	
	/** 생년월일 */
    private String birthDate; 
	
	/** 만나이*/
	private int age;
	
	/** 대표이미지 파일명*/
	private String userPhotoName;
	
	/** 대표이미지 경로*/
	private String userPhotoPath;
	
}
