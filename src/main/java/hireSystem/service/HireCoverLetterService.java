package hireSystem.service;

import java.util.List;

import hireSystem.vo.HireCoverLetterVo;

/**
 * HireCoverLetterService
 *
 * <p>이력서 자기소개서 항목 관련 비즈니스 로직 인터페이스</p>
 */
public interface HireCoverLetterService {

    /**
     * 자기소개서 항목 목록 조회
     * @param resumeId 이력서 ID
     * @return 자기소개서 항목 목록 (sortOrder 오름차순)
     */
    public List<HireCoverLetterVo> selectCoverLetterList(int resumeId);

    /**
     * 자기소개서 항목 저장 (신규/수정 공통)
     * resumeId 가 없으면 resume 을 먼저 생성 후 저장
     * @param coverLetterVo 자기소개서 VO
     * @param loginUserNum  로그인 유저번호
     * @return 처리 결과 건수
     */
    public int saveCoverLetter(HireCoverLetterVo coverLetterVo, int loginUserNum);

    /**
     * 자기소개서 항목 삭제
     * @param clId 자기소개서 항목 ID
     * @return 처리 결과 건수
     */
    public int deleteCoverLetter(int clId);
}
