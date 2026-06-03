package hireSystem.service;

import java.util.List;

import hireSystem.vo.HireCertificationVo;

public interface HireCertificationService {

    /**
     * 자격사항 목록 조회
     * @param resumeId 이력서 ID
     * @return 자격사항 목록 (취득일 내림차순)
     */
    public List<HireCertificationVo> selectCertificationInfo(int resumeId);

    /**
     * 자격사항 저장 (신규/수정 공통)
     * resumeId 가 없으면 resume 을 먼저 생성 후 저장
     * @param certVo 자격사항 VO
     * @param loginUserNum 로그인 유저번호
     * @return 처리 결과 건수
     */
    public int saveCertification(HireCertificationVo certVo, int loginUserNum);

    /**
     * 자격사항 삭제
     * @param certificationId 자격사항 ID
     * @return 처리 결과 건수
     */
    public int deleteCertification(int certificationId);
}
