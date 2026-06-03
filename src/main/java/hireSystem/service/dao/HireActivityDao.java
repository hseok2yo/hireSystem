package hireSystem.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireActivityVo;

@Repository("hireActivityDao")
public class HireActivityDao extends HireSystemAbstractMapper {

    /** 경험/활동/교육 목록 조회 */
    public List<HireActivityVo> selectActivityList(int resumeId) {
        return selectList("hireActivityDao.selectActivityList", resumeId);
    }

    /** 경험/활동/교육 등록 */
    public int insertActivity(HireActivityVo vo) {
        return insert("hireActivityDao.insertActivity", vo);
    }

    /** 경험/활동/교육 수정 */
    public int updateActivity(HireActivityVo vo) {
        return update("hireActivityDao.updateActivity", vo);
    }

    /** 경험/활동/교육 삭제 */
    public int deleteActivity(int activityId) {
        return delete("hireActivityDao.deleteActivity", activityId);
    }
}
