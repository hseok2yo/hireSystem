package hireSystem.vo;

import java.util.Date;

import lombok.Data;

/**
 * 채용공시 목록조회(/list) 요청 파라미터
 * 코드값(고용형태, 근무지 등)은 MOEF_NKOD_DB_05_코드 정의서_v1.2 참조
 */
@Data
public class RecruitSearchParam {

    private int pageNo = 1;
    private int numOfRows = 10;

    private String ongoingYn;        // 진행여부 [Y/N]
    private String recrutPbancTtl;   // 공시제목 (문자열포함조건)
    private String recrutSe;         // 채용구분 (R2010 신입, R2020 경력, R2030 신입+경력, R2040 외국인전형)
    private String hireTypeLst;      // 고용형태목록 (R1010 정규직 등, 여러개 콤마구분)
    private String acbgCondLst;      // 학력조건목록 (R7010 학력무관 등, 여러개 콤마구분)
    private String workRgnLst;       // 근무지역목록 (R3010 서울 등, 여러개 콤마구분)
    private String ncsCdLst;         // NCS코드목록 (R600001 사업관리 등, 여러개 콤마구분)
    private String instClsf;         // 기관분류 (01~09, 99)
    private String instType;         // 기관유형 (A2001~A2005)
    private String pblntInstCd;      // 기관코드
    private String pbancBgngYmd;     // 채용공시 조회시작일 (YYYY-MM-DD)
    private String pbancEndYmd;      // 채용공시 조회종료일 (YYYY-MM-DD)
    private String replmprYn;        // 대체인력여부 [Y/N]

}
