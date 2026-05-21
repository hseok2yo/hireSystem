package hireSystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hireSystem/resume")
public class HireResumeController {
	
	private final String path = "hireSystem/resume/";
	
	
	@RequestMapping("/resume.do")
	public String resume() {
		return path + "resumeMain";
	}
}
