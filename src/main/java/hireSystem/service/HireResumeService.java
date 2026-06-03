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
}
