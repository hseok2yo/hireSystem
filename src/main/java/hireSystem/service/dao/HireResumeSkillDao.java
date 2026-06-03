package hireSystem.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireSkillVo;

@Repository("hireResumeSkillDao")
public class HireResumeSkillDao extends HireSystemAbstractMapper{
	public List<HireSkillVo> selectSkillInfo(int resumeId) {
        return selectList("hireResumeSkillDao.selectSkillInfo", resumeId);
    }

    public int insertSkill(HireSkillVo vo) {
        return insert("hireResumeSkillDao.insertSkill", vo);
    }

    public int deleteSkill(int skillId) {
        return delete("hireResumeSkillDao.deleteSkill", skillId);
    }

    public int deleteAllSkill(int resumeId) {
        return delete("hireResumeSkillDao.deleteAllSkill", resumeId);
    }
}
