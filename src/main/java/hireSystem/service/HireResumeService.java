package hireSystem.service;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import hireSystem.vo.HireCareerVo;
import hireSystem.vo.HireResumeVo;
import hireSystem.vo.HireUserVo;

public interface HireResumeService {

	/**
	 * 유저정보들
	 * @param loginUserNum 유저번호
	 * @return
	 */
	public EgovMap selectHireUserInfo(int loginUserNum);

	/**
	 * 이력서 없는경우 임시로 생성
	 * @param loginUserNum
	 */
	public int insertEmptyResume(int loginUserNum);

	/**
	 * 이력서 기본정보 저장
	 * @param vo
	 * @return 성공여부 1
	 */
	public int insertBasicResume(HireResumeVo vo);

	public int updateBasicResume(HireResumeVo vo);

	public HireResumeVo selectResumeMainInfo(EgovMap map);

	public Map<String, Object> selectResumeSubInfo(EgovMap map);

	public int saveBasicResume(HireUserVo hireVo, HireResumeVo vo);

	/**
	 * 경력사항
	 * @param resumeId
	 * @return
	 */
	public List<HireCareerVo> selectCareerInfo(int resumeId);

	/**
	 * 총경력 계산
	 * @param careerList
	 * @return
	 */
	public Object calculateTotalCareer(List<HireCareerVo> careerList);

	public int deleteCareer(int careerId);
	/**
	 * 경력사항 저장
	 * @param careerVo
	 * @return
	 */
	public int saveCareer(HireCareerVo careerVo, int loginUserNum);



}
