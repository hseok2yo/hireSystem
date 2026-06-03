package hireSystem.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HireActivityVo {

    /** 경험/활동/교육 고유 ID */
    private Integer activityId;

    /** 이력서 ID (FK) */
    private Integer resumeId;

    /**
     * 활동구분
     * 예) 교내활동, 인턴, 자원봉사, 해외연수, 기타
     */
    private String activityType;

    /** 기관/장소명 */
    private String orgName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 재학/진행중 여부 (Y/N) */
    private String currentYn;

    /** 활동내용 */
    private String content;

    private Date regDt;
    private Date updDt;
}
