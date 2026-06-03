package hireSystem.service;

import java.util.List;

import hireSystem.vo.HireEducationVo;

public interface HireEducationService {

    /**
     * 학력 목록 조회
     * @param resumeId 이력서 ID
     * @return 학력 목록 (재학중 우선, 종료일 내림차순)
     */
    public List<HireEducationVo> selectEducationInfo(int resumeId);

    /**
     * 학력 저장 (신규/수정 공통)
     * resumeId 가 없으면 resume 을 먼저 생성 후 저장
     * @param vo           학력 VO
     * @param loginUserNum 로그인 유저번호
     * @return 처리 결과 건수
     */
    public int saveEducation(HireEducationVo vo, int loginUserNum);

    /**
     * 학력 삭제
     * @param educationId 학력 ID
     * @return 처리 결과 건수
     */
    public int deleteEducation(int educationId);
}
