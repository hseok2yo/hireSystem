package hireSystem.service.dao;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireApplicationVo;

@Repository("hireApplicationDao")
public class HireApplicationDao extends HireSystemAbstractMapper {

	/** 중복 지원 여부 확인 (해당 회원 + 해당 공고 조합이 이미 존재하는지) */
	public int countApplication(HireApplicationVo vo) {
		return selectOne("hireApplicationDao.countApplication", vo);
	}

	/** 지원내역 저장 */
	public int insertApplication(HireApplicationVo vo) {
		return insert("hireApplicationDao.insertApplication", vo);
	}
}
