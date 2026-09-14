package hireSystem.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 일반채용정보(자체 DB) - 기업회원이 직접 등록하는 채용공고
 * 테이블: HIRE_JOB_POSTING
 */
@Data
public class HireJobPostingVo {

	private Integer jobPostingId;

	/** 등록한 기업회원 USER_NUM (기업 회원가입/로그인 붙기 전까지는 NULL 허용) */
	private Integer companyUserNum;

	private String companyName;
	private String companyLogoUrl;

	/** 채용 직무명 (예: Backend Developer) */
	private String jobTitle;

	/** 화면에 칩 형태로 보여줄 태그 (콤마구분, 예: "Senior,Experienced") */
	private String tags;

	/** 근무지역 (예: 서울·강남) */
	private String workRegion;

	/** 고용형태 (정규직/계약직/무기계약직/청년인턴) */
	private String hireType;

	/** 채용구분 (신입/경력/신입+경력/외국인전형) */
	private String recrutSe;

	/** 진행여부 [Y/N] */
	private String ongoingYn;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadlineDt;

	private Integer viewCnt;

	private String jobDesc;

	private Date regDt;
	private Date updDt;
}
