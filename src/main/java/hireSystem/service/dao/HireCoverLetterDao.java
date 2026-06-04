package hireSystem.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireCoverLetterVo;

/**
 * HireCoverLetterDao
 *
 * <p>자기소개서 항목 MyBatis DAO</p>
 */
@Repository("hireCoverLetterDao")
public class HireCoverLetterDao extends HireSystemAbstractMapper {

    /** 자기소개서 항목 목록 조회 */
    public List<HireCoverLetterVo> selectCoverLetterList(int resumeId) {
        return selectList("hireCoverLetterDao.selectCoverLetterList", resumeId);
    }

    /** 자기소개서 항목 등록 */
    public int insertCoverLetter(HireCoverLetterVo coverLetterVo) {
        return insert("hireCoverLetterDao.insertCoverLetter", coverLetterVo);
    }

    /** 자기소개서 항목 수정 */
    public int updateCoverLetter(HireCoverLetterVo coverLetterVo) {
        return update("hireCoverLetterDao.updateCoverLetter", coverLetterVo);
    }

    /** 자기소개서 항목 삭제 */
    public int deleteCoverLetter(int clId) {
        return delete("hireCoverLetterDao.deleteCoverLetter", clId);
    }
}
