package hireSystem.vo;

import java.util.Date;

import lombok.Data;

@Data
public class HireResumeVo {

    /** 이력서 고유 ID */
    private Integer resumeId;

    /** 회원 고유 NUM (FK) */
    private int userNum;

    /** 이력서 제목 */
    private String title;

    /** 이름 */
    private String userNm;

    /** 생년월일 */
    private String birthDate;

    /** 성별 (M/F) */
    private String gender;

    /** 연락처 */
    private String userPhone;

    /** 이메일 */
    private String userEmail;

    /** 주소 */
    private String addressFirst;

    /** 상세주소 */
    private String addressSecond;

    /** 등록일시 */
    private Date createdAt;

    /** 수정일시 */
    private Date updatedAt;

    /** 대표이력서 설정*/
    private String isMain;

    private int offset;
    private int page;
	private int pageSize;
	private String searchSort;



}
