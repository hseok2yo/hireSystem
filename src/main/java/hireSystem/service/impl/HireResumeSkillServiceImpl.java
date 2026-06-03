package hireSystem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hireSystem.common.CommonUtil;
import hireSystem.service.HireResumeSkillService;
import hireSystem.service.dao.HireResumeSkillDao;
import hireSystem.vo.HireSkillVo;

@Service("hireResumeSkillService")
public class HireResumeSkillServiceImpl implements HireResumeSkillService{

	@Resource(name = "hireResumeSkillDao")
	HireResumeSkillDao hireResumeSkillDao;

	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;

	@Override
    public List<HireSkillVo> selectSkillInfo(int resumeId) {
        return hireResumeSkillDao.selectSkillInfo(resumeId);
    }

    @Override
    public int addSkill(HireSkillVo vo, int userNum) {
        // resumeId 없으면 resume 먼저 생성 (경력/학력과 동일)
        int resumeId = commonUtil.getOrCreateResumeId(vo.getResumeId(), userNum);
        vo.setResumeId(resumeId);
        return hireResumeSkillDao.insertSkill(vo);
    }

    @Override
    public int deleteSkill(int skillId) {
        return hireResumeSkillDao.deleteSkill(skillId);
    }
}
