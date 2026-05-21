package hireSystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hireSystem")
public class HireSystemController {
		
	private final String path = "hireSystem/";
	
	/**
	 * @return 메인페이지 이동
	 */
	@RequestMapping(value = "/main.do")
	public String main() {
		return path + "hireSystem";
	}
	
	
}
