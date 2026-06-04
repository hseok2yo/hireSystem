package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hireSystem.service.HirePortfolioService;
import hireSystem.vo.HirePortfolioVo;

@Controller
@RequestMapping("/hireSystem/resume")
public class HirePortfolioController {

	@Resource(name = "hirePortfolioService")
	private HirePortfolioService hirePortfolioService;

	@RequestMapping("/portfolioSave.do")
	@ResponseBody
	public Map<String, Object> portfolioSave(
	        HirePortfolioVo vo,
	        @RequestParam(value = "portfolioFile", required = false) MultipartFile portfolioFile,
	        HttpSession session) {

	    Map<String, Object> result = new HashMap<>();
	    try {
	        int loginUserNum = (int) session.getAttribute("loginUserNum");
	        hirePortfolioService.savePortfolio(vo, portfolioFile, loginUserNum);
	        result.put("resumeId", vo.getResumeId());
	        result.put("result",   true);
	        result.put("message",  vo.getPortfolioId() != null ? "수정완료" : "저장완료");
	    } catch (Exception e) {
	        result.put("result",  false);
	        result.put("message", e.getMessage());
	    }
	    return result;
	}

	@RequestMapping("/portfolioDelete.do")
	@ResponseBody
	public Map<String, Object> portfolioDelete(HirePortfolioVo vo) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        hirePortfolioService.deletePortfolio(vo.getPortfolioId());
	        result.put("resumeId", vo.getResumeId());
	        result.put("result",   true);
	        result.put("message",  "삭제완료");
	    } catch (Exception e) {
	        result.put("result",  false);
	        result.put("message", e.getMessage());
	    }
	    return result;
	}
}
