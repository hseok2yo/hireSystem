package hireSystem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import hireSystem.common.CommonUtil;
import hireSystem.service.HireCoverLetterService;
import hireSystem.service.dao.HireCoverLetterDao;
import hireSystem.vo.HireCoverLetterVo;

/**
 * HireCoverLetterServiceImpl
 *
 * <p>자기소개서 서비스 구현체</p>
 */
@Service("hireCoverLetterService")
public class HireCoverLetterServiceImpl extends EgovAbstractServiceImpl implements HireCoverLetterService {

    @Resource(name = "hireCoverLetterDao")
    private HireCoverLetterDao hireCoverLetterDao;

    /** resume 미생성 시 resume 먼저 생성하기 위해 주입 */
    @Resource(name = "commonUtil")
    private CommonUtil commonUtil;

    // ---------------------------------------------------------------
    // 조회
    // ---------------------------------------------------------------

    @Override
    public List<HireCoverLetterVo> selectCoverLetterList(int resumeId) {
        return hireCoverLetterDao.selectCoverLetterList(resumeId);
    }

    // ---------------------------------------------------------------
    // 저장 / 수정
    // ---------------------------------------------------------------

    @Override
    public int saveCoverLetter(HireCoverLetterVo coverLetterVo, int loginUserNum) {
        // resumeId 없으면 resume 먼저 생성
        int resumeId = commonUtil.getOrCreateResumeId(coverLetterVo.getResumeId(), loginUserNum);
        coverLetterVo.setResumeId(resumeId);

        if (coverLetterVo.getClId() != null) {
            return hireCoverLetterDao.updateCoverLetter(coverLetterVo);
        } else {
            return hireCoverLetterDao.insertCoverLetter(coverLetterVo);
        }
    }

    // ---------------------------------------------------------------
    // 삭제
    // ---------------------------------------------------------------

    @Override
    public int deleteCoverLetter(int clId) {
        return hireCoverLetterDao.deleteCoverLetter(clId);
    }
}
