package hireSystem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import hireSystem.common.CommonUtil;
import hireSystem.service.HireCertificationService;
import hireSystem.service.dao.HireCertificationDao;
import hireSystem.service.dao.HireResumeDao;
import hireSystem.vo.HireCertificationVo;
import hireSystem.vo.HireResumeVo;

@Service("hireCertificationService")
public class HireCertificationServiceImpl extends EgovAbstractServiceImpl implements HireCertificationService {

    @Resource(name = "hireCertificationDao")
    private HireCertificationDao hireCertificationDao;

    /** resume 미생성 시 resume 먼저 생성하기 위해 주입 */
    @Resource(name = "hireResumeDao")
    private HireResumeDao hireResumeDao;

    @Resource(name = "commonUtil")
	private CommonUtil commonUtil;
    // ---------------------------------------------------------------
    // 조회
    // ---------------------------------------------------------------

    @Override
    public List<HireCertificationVo> selectCertificationInfo(int resumeId) {
        return hireCertificationDao.selectCertificationInfo(resumeId);
    }

    // ---------------------------------------------------------------
    // 저장 / 수정
    // ---------------------------------------------------------------

    @Override
    public int saveCertification(HireCertificationVo certVo, int loginUserNum) {
        // resumeId 없으면 resume 먼저 생성
        int resumeId = commonUtil.getOrCreateResumeId(certVo.getResumeId(), loginUserNum);
        certVo.setResumeId(resumeId);

        if (certVo.getCertificationId() != null) {
            return hireCertificationDao.updateCertification(certVo);
        } else {
            return hireCertificationDao.insertCertification(certVo);
        }
    }

    // ---------------------------------------------------------------
    // 삭제
    // ---------------------------------------------------------------

    @Override
    public int deleteCertification(int certificationId) {
        return hireCertificationDao.deleteCertification(certificationId);
    }

}
