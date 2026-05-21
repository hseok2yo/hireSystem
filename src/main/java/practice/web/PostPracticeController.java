package practice.web;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import practice.service.PostPracticeService;
import practice.vo.PostPracticeVo;

@Controller
@RequestMapping("/postPractice")
public class PostPracticeController {
	
	private final String path = "/postPractice/";
	
	@Resource(name = "PostPracticeService")
	private PostPracticeService service;
		
	
	@RequestMapping("/postPractice.do")
	private String postPractice(ModelMap model, @ModelAttribute("postPracticeVo") PostPracticeVo vo) {
		
		List<EgovMap> returnList = service.selectList(vo, model);
		model.addAttribute("list", returnList);
		
		return path + "postPractice";
	}

}
