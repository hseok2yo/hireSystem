package hireSystem.vo;

import java.util.Date;

import lombok.Data;
/**
 * 필수 약관정의 테이블
 * @author hyung
 *
 */
@Data
public class UserAgreeDefVo {

    /** 약관코드 */
    private String agreeCd;

    /** 약관명 */
    private String agreeNm;

    /** 필수약관여부 */
    private String requiredYn;

    /** 사용여부 */
    private String useYn;
    
    /** 등록일 */
    private Date regDt;
    
    /** 사용할 페이지 */
    private String pages;

}
