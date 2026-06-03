package hireSystem.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import hireSystem.service.dao.HireResumeDao;
import hireSystem.vo.HireResumeVo;

@Component
public class CommonUtil {

	@Resource(name = "hireResumeDao")
	private HireResumeDao hireResumeDao;

	public int getOrCreateResumeId(Integer resumeId, int userNum) {
        if (resumeId != null && resumeId != 0) return resumeId;
        HireResumeVo resumeVo = new HireResumeVo();
        resumeVo.setUserNum(userNum);
        hireResumeDao.insertBasicResume(resumeVo);
        return resumeVo.getResumeId();
    }
}
