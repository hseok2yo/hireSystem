package hireSystem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import hireSystem.common.CommonUtil;
import hireSystem.service.HireActivityService;
import hireSystem.service.dao.HireActivityDao;
import hireSystem.service.dao.HireResumeDao;
import hireSystem.vo.HireActivityVo;

@Service("hireActivityService")
public class HireActivityServiceImpl extends EgovAbstractServiceImpl implements HireActivityService {

    @Resource(name = "hireActivityDao")
    private HireActivityDao hireActivityDao;

    /** resume 미생성 시 resume 먼저 생성하기 위해 주입 */
    @Resource(name = "hireResumeDao")
    private HireResumeDao hireResumeDao;

    @Resource(name = "commonUtil")
	private CommonUtil commonUtil;

    // ---------------------------------------------------------------
    // 조회
    // ---------------------------------------------------------------

    @Override
    public List<HireActivityVo> selectActivityList(int resumeId) {
        return hireActivityDao.selectActivityList(resumeId);
    }

    // ---------------------------------------------------------------
    // 저장 / 수정
    // ---------------------------------------------------------------

    @Override
    public int saveActivity(HireActivityVo vo, int loginUserNum) {
        // resumeId 없으면 resume 먼저 생성
        int resumeId = commonUtil.getOrCreateResumeId(vo.getResumeId(), loginUserNum);
        vo.setResumeId(resumeId);

        if (vo.getActivityId() != null) {
            return hireActivityDao.updateActivity(vo);
        } else {
            return hireActivityDao.insertActivity(vo);
        }
    }

    // ---------------------------------------------------------------
    // 삭제
    // ---------------------------------------------------------------

    @Override
    public int deleteActivity(int activityId) {
        return hireActivityDao.deleteActivity(activityId);
    }
}
