package hireSystem.service;

import java.util.List;

import hireSystem.vo.HireSkillVo;

public interface HireResumeSkillService {

	 /** 스킬 목록 조회 */
    public List<HireSkillVo> selectSkillInfo(int resumeId);

    /** 스킬 단건 추가 */
    public int addSkill(HireSkillVo vo, int userNum);

    /** 스킬 단건 삭제 */
    public int deleteSkill(int skillId);
}
