package hireSystem.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireCertificationVo;

@Repository("hireCertificationDao")
public class HireCertificationDao extends HireSystemAbstractMapper {

    /** 자격사항 목록 조회 */
    public List<HireCertificationVo> selectCertificationInfo(int resumeId) {
        return selectList("hireCertificationDao.selectCertificationInfo", resumeId);
    }

    /** 자격사항 등록 */
    public int insertCertification(HireCertificationVo certVo) {
        return insert("hireCertificationDao.insertCertification", certVo);
    }

    /** 자격사항 수정 */
    public int updateCertification(HireCertificationVo certVo) {
        return update("hireCertificationDao.updateCertification", certVo);
    }

    /** 자격사항 삭제 */
    public int deleteCertification(int certificationId) {
        return delete("hireCertificationDao.deleteCertification", certificationId);
    }
}
