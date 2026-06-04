package hireSystem.vo;

import java.util.Date;

import lombok.Data;

@Data
public class HirePortfolioVo {

    /** 포트폴리오 고유 식별자 (PK, SEQ_RESUME_PORTFOLIO 사용) */
    private Integer portfolioId;

    /** 이력서 ID (FK → RESUME.RESUME_ID) */
    private Integer resumeId;

    /**
     * 파일 구분 코드
     * <ul>
     *   <li>포트폴리오</li>
     *   <li>기타문서</li>
     *   <li>증빙자료</li>
     *   <li>수료증</li>
     *   <li>기타</li>
     * </ul>
     */
    private String fileCategory;

    /**
     * 파일 등록 유형
     * <ul>
     *   <li>file : 파일 직접 업로드</li>
     *   <li>url  : URL 직접 입력</li>
     * </ul>
     */
    private String fileType;

    /** 업로드 파일 원본명 (fileType = "file" 일 때 사용) */
    private String originalName;

    /** 서버 저장 파일명 (UUID 기반으로 rename 된 이름, fileType = "file" 일 때 사용) */
    private String savedName;

    /** 서버 저장 디렉토리 경로 (fileType = "file" 일 때 사용) */
    private String savedPath;

    /** 파일 크기 (단위: Byte, 최대 50MB / fileType = "file" 일 때 사용) */
    private long fileSize;

    /** 포트폴리오 URL (fileType = "url" 일 때 사용) */
    private String portfolioUrl;

    /** 최초 등록일시 */
    private Date regDate;

    /** 최종 수정일시 */
    private Date modDate;

}
