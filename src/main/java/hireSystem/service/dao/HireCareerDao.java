package hireSystem.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireCareerVo;

@Repository("hireCareerDao")
public class HireCareerDao extends HireSystemAbstractMapper {

    /** 경력 목록 조회 */
    public List<HireCareerVo> selectCareerInfo(int resumeId) {
        return selectList("hireCareerDao.selectCareerInfo", resumeId);
    }

    /** 경력 등록 */
    public int insertCareer(HireCareerVo careerVo) {
        return insert("hireCareerDao.insertCareer", careerVo);
    }

    /** 경력 수정 */
    public int updateCareer(HireCareerVo careerVo) {
        return update("hireCareerDao.updateCareer", careerVo);
    }

    /** 경력 삭제 */
    public int deleteCareer(int careerId) {
        return delete("hireCareerDao.deleteCareer", careerId);
    }
}
