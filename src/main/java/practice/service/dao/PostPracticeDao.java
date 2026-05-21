package practice.service.dao;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;

import practice.vo.PostPracticeVo;

@Repository("PostPracticeDao")
public class PostPracticeDao extends EgovAbstractMapper{

	public List<EgovMap> selectList(PostPracticeVo vo) {
		return selectList("PostPracticeDao.selectPostList", vo);
	}

	public int selectPostListCnt() {
		return selectOne("PostPracticeDao.selectPostListCnt");
	}
	
	
}
