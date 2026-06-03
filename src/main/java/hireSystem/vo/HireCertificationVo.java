package hireSystem.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HireCertificationVo {
    private Integer certificationId;
    private Integer resumeId;

    /** 자격증명 */
    private String certName;

    /** 발행기관 */
    private String issuer;

    /** 취득일 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acquiredDate;

    /** 자격증 번호 */
    private String certNumber;

    /** 점수/등급 */
    private String score;

    /** 등록일 */
    private Date regDt;

    /** 수정일 */
    private Date updDt;
}
