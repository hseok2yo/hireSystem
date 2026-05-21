package music.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import music.vo.UserVo;

@Controller
@RequestMapping("/json")
public class JSONController {
	
	@RequestMapping("/json.do")
	public String json() {
		return "/musicpractice/jsonPractice";
	}
	
	
	
	@RequestMapping("/jsonPractice.do")
	@ResponseBody
	public List<UserVo> jsonzz(@RequestBody List<UserVo> voList) {
		return voList;
	}
}
