package hireSystem.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HirePortfolioVo;

@Repository("hirePortfolioDao")
public class HirePortfolioDao extends HireSystemAbstractMapper {

	 public List<HirePortfolioVo> selectPortfolioList(int resumeId) {
		return selectList("selectPortfolioList", resumeId);
	 };


	public int deletePortfolio(int portfolioId) {

		return delete("hirePortfolioDao.deletePortfolio", portfolioId);
	}

	public int updatePortfolio(HirePortfolioVo vo) {

		return update("hirePortfolioDao.updatePortfolio", vo);
	}

	public int insertPortfolio(HirePortfolioVo vo) {

		return insert("hirePortfolioDao.insertPortfolio", vo);
	}


	public HirePortfolioVo selectPortfolio(Integer portfolioId) {

		return selectOne("selectPortfolio", portfolioId);
	}



}
