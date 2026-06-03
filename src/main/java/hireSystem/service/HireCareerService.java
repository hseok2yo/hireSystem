package hireSystem.service;

import java.util.List;

import hireSystem.vo.HireCareerVo;

public interface HireCareerService {

    /**
     * 경력 목록 조회
     * @param resumeId 이력서 ID
     * @return 경력 목록 (재직중 우선, 종료일 내림차순)
     */
    public List<HireCareerVo> selectCareerInfo(int resumeId);

    /**
     * 총 경력 계산 (예: "3년 2개월")
     * @param careerList 경력 목록
     * @return 총 경력 문자열
     */
    public String calculateTotalCareer(List<HireCareerVo> careerList);

    /**
     * 경력 저장 (신규/수정 공통)
     * resumeId 가 없으면 resume 을 먼저 생성 후 저장
     * @param careerVo 경력 VO
     * @param loginUserNum 로그인 유저번호
     * @return 처리 결과 건수
     */
    public int saveCareer(HireCareerVo careerVo, int loginUserNum);

    /**
     * 경력 삭제
     * @param careerId 경력 ID
     * @return 처리 결과 건수
     */
    public int deleteCareer(int careerId);
}
