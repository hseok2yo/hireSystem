package hireSystem.service;

import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import hireSystem.vo.HireResumeVo;
import hireSystem.vo.HireUserVo;

public interface HireResumeService {

    /**
     * 유저 기본 정보 조회
     * @param loginUserNum 유저번호
     */
    public EgovMap selectHireUserInfo(int loginUserNum);

    /**
     * 빈 이력서 생성 (임시)
     */
    public int insertEmptyResume(int loginUserNum);

    /**
     * 이력서 기본정보 등록
     */
    public int insertBasicResume(HireResumeVo vo);

    /**
     * 이력서 기본정보 수정
     */
    public int updateBasicResume(HireResumeVo vo);

    /**
     * 대표 이력서 조회
     */
    public HireResumeVo selectResumeMainInfo(EgovMap map);

    /**
     * 서브 이력서 목록 조회 (페이징)
     */
    public Map<String, Object> selectResumeSubInfo(EgovMap map);

    /**
     * 유저 기본정보 + 이력서 기본정보 저장 (복합 저장)
     */
    public int saveBasicResume(HireUserVo hireVo, HireResumeVo vo);

	public int saveResume(HireResumeVo vo, int loginUserNum);

	public EgovMap selectResume(int resumeId);

	public boolean deleteResume(Integer resumeId, int loginUserNum);

	/**
	 * 이력서 복제 (기본정보 + 모든 서브항목)
	 * @return 새로 생성된 resumeId
	 */
	public int duplicateResume(int resumeId, int loginUserNum);

	/**
	 * 섹션 펼침/접힘 상태만 저장 (제목 미변경)
	 */
	public int updateSectionVisible(int resumeId, String sectionVisible);

	/**
	 * 옵션 섹션(activity, certification, portfolio, coverLetter) 중
	 * 실제 DB 데이터가 존재하는 섹션만 걸러서 반환하는 공통 검증 로직.
	 * updateSectionVisible.do, resumeSave.do 양쪽에서 공통으로 사용한다.
	 *
	 * @param resumeId       이력서ID
	 * @param sectionVisible 클라이언트가 보낸 콤마구분 섹션id 목록 (예: "#activity,#portfolio")
	 * @return 실제 데이터가 있는 섹션만 남긴 콤마구분 문자열
	 */
	public String filterVisibleSections(int resumeId, String sectionVisible);
}