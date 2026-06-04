package hireSystem.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * HireCoverLetterVo
 *
 * <p>이력서 자기소개서 항목 정보를 담는 Value Object</p>
 * <p>대응 테이블: hire_cover_letter</p>
 */
@Data
public class HireCoverLetterVo {

    /** 자기소개서 항목 고유 식별자 (PK, AUTO_INCREMENT) */
    private Integer clId;

    /** 이력서 ID (FK → hire_resume.resume_id) */
    private Integer resumeId;

    /** 자기소개서 항목 제목 (예: 지원 동기, 성장 과정, 장단점 등) */
    private String clTitle;

    /** 자기소개서 항목 내용 (최대 2000자) */
    private String clContent;

    /** 항목 정렬 순서 (낮을수록 먼저 표시) */
    private Integer sortOrder;

    /** 최초 등록일시 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDt;

    /** 최종 수정일시 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updDt;
}
