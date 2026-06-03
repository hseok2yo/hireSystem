package hireSystem.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireEducationVo;

@Repository("hireEducationDao")
public class HireEducationDao extends HireSystemAbstractMapper {

    /** 학력 목록 조회 */
    public List<HireEducationVo> selectEducationInfo(int resumeId) {
        return selectList("hireEducationDao.selectEducationInfo", resumeId);
    }

    /** 학력 등록 */
    public int insertEducation(HireEducationVo vo) {
        return insert("hireEducationDao.insertEducation", vo);
    }

    /** 학력 수정 */
    public int updateEducation(HireEducationVo vo) {
        return update("hireEducationDao.updateEducation", vo);
    }

    /** 학력 삭제 */
    public int deleteEducation(int educationId) {
        return delete("hireEducationDao.deleteEducation", educationId);
    }
}
