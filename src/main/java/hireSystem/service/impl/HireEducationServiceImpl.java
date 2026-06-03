package hireSystem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import hireSystem.common.CommonUtil;
import hireSystem.service.HireEducationService;
import hireSystem.service.dao.HireEducationDao;
import hireSystem.service.dao.HireResumeDao;
import hireSystem.vo.HireEducationVo;
import hireSystem.vo.HireResumeVo;

@Service("hireEducationService")
public class HireEducationServiceImpl extends EgovAbstractServiceImpl implements HireEducationService {

    @Resource(name = "hireEducationDao")
    private HireEducationDao hireEducationDao;

    /** resume 미생성 시 resume 먼저 생성하기 위해 주입 */
    @Resource(name = "hireResumeDao")
    private HireResumeDao hireResumeDao;

    @Resource(name = "commonUtil")
	private CommonUtil commonUtil;

    // ---------------------------------------------------------------
    // 조회
    // ---------------------------------------------------------------

    @Override
    public List<HireEducationVo> selectEducationInfo(int resumeId) {
        return hireEducationDao.selectEducationInfo(resumeId);
    }

    // ---------------------------------------------------------------
    // 저장 / 수정
    // ---------------------------------------------------------------

    @Override
    public int saveEducation(HireEducationVo vo, int loginUserNum) {
        // resumeId 없으면 resume 먼저 생성
        int resumeId = commonUtil.getOrCreateResumeId(vo.getResumeId(), loginUserNum);

        vo.setResumeId(resumeId);

        if (vo.getEducationId() != null) {
            return hireEducationDao.updateEducation(vo);
        } else {
            return hireEducationDao.insertEducation(vo);
        }
    }

    // ---------------------------------------------------------------
    // 삭제
    // ---------------------------------------------------------------

    @Override
    public int deleteEducation(int educationId) {
        return hireEducationDao.deleteEducation(educationId);
    }

}
