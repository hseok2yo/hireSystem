package hireSystem.vo;

import java.util.Date;

import lombok.Data;

/**
 * 채용공고 지원 이력
 * 테이블: HIRE_APPLICATION
 */
@Data
public class HireApplicationVo {

	private Integer applicationId;

	/** 지원한 채용공고 (FK: HIRE_JOB_POSTING) */
	private int jobPostingId;

	/** 지원한 회원 (세션의 loginUserNum) */
	private int userNum;

	/** 지원 시점의 대표이력서 ID (FK: HIRE_RESUME) */
	private int resumeId;

	private Date applyDt;

	/** 지원완료 / 서류심사중 / 합격 / 불합격 등 */
	private String status;
}
