package hireSystem.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import hireSystem.vo.HirePortfolioVo;

public interface HirePortfolioService {

	public List<HirePortfolioVo> selectPortfolioList(int resumeId);

	int savePortfolio(HirePortfolioVo vo, MultipartFile portfolioFile, int loginUserNum) throws Exception;

	int deletePortfolio(Integer portfolioId);

}
