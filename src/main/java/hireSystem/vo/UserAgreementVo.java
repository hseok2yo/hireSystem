package hireSystem.vo;

import java.util.Date;

import lombok.Data;

@Data
public class UserAgreementVo {

    /** 회원 번호 */
    private int userNum;

    /** 약관 코드 */
    private String agreeCd;

    /** 약관동의여부 */
    private String agreeYn;

    /** 날짜 */
    private Date agreeDt;

    /**동의한 페이지 */
    private String pages;
    

}
