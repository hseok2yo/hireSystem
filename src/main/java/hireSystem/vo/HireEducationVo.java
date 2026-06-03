package hireSystem.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HireEducationVo {
    private Integer educationId;
    private Integer resumeId;

    /** 학교명 */
    private String schoolName;

    /** 학교구분 (고등학교/전문대/4년제/대학원 등) */
    private String schoolType;

    /** 전공 */
    private String major;

    /** 부전공 */
    private String subMajor;

    /** 학점 */
    private String grade;

    /** 최대학점 */
    private String gradeMax;

    /** 지역 */
    private String location;

    /** 졸업구분 (졸업/재학중/중퇴/휴학) */
    private String graduateType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 재학중 여부 */
    private String currentYn;

    private Date regDt;
    private Date updDt;
}