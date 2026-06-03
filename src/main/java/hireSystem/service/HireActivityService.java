package hireSystem.service;

import java.util.List;

import hireSystem.vo.HireActivityVo;

public interface HireActivityService {

    /**
     * 경험/활동/교육 목록 조회
     * @param resumeId 이력서 ID
     * @return 목록 (등록일 내림차순)
     */
    public List<HireActivityVo> selectActivityList(int resumeId);

    /**
     * 경험/활동/교육 저장 (신규/수정 공통)
     * resumeId 가 없으면 RESUME 을 먼저 생성 후 저장
     * @param vo           경험 VO
     * @param loginUserNum 로그인 유저번호
     * @return 처리 결과 건수
     */
    public int saveActivity(HireActivityVo vo, int loginUserNum);

    /**
     * 경험/활동/교육 삭제
     * @param activityId 경험 ID
     * @return 처리 결과 건수
     */
    public int deleteActivity(int activityId);
}
