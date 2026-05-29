package hireSystem.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hireSystem.service.HireResumeService;
import hireSystem.service.HireSignupService;

@Controller
@RequestMapping("/hireSystem/resume")
public class HireResumeController {
	
	private final String path = "hireSystem/resume/";
	
	@Resource(name = "hireSignupService")
	private HireSignupService hireSignupService;
	
	@Resource(name = "hireResumeService")
	private HireResumeService hireResumeService;
	
	@RequestMapping("/resumeMain.do")
	public String resumeMain() {
		return path + "resumeMain";
	}
	
	@RequestMapping("/resumeForm.do")
	public String resumeForm(HttpSession session, Model model) {
		// 세션에서 로그인 회원번호 가져오기
	    int loginUserNum = (int) session.getAttribute("loginUserNum");
	    
	    //유저정보
	    model.addAttribute("userInfo", hireResumeService.selectResumeUserInfo(loginUserNum));
	    
	    
		return path + "resumeForm";
	}
	
	/**
	 * 프로필 이미지 변경
	 * @param file 변경할 파일
	 * @param session 유저세션정보
	 * @return 사진url
	 * @throws IOException
	 */
	@PostMapping("/image/uploadPhoto.do")
	@ResponseBody
	public Map<String, Object> uploadPhoto(
	        @RequestParam("upload") MultipartFile file,
	        HttpSession session) throws IOException {

	    int loginUserNum = (int) session.getAttribute("loginUserNum");
	    
	    Map<String, Object> result = new HashMap<>();
	    try {
	        String url = hireSignupService.updateUserPhoto(file, loginUserNum);
	        result.put("success", true);
	        result.put("url", url);
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", "파일 업로드에 실패했습니다.");
	    }

	    return result;
	}
	
	@PostMapping("/saveBasicResume.do")
	@ResponseBody
	public Map<String, Object> saveBasicResume(@RequestParam Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<>();
		
		System.out.println("@@@@@@@@@@@@@@@");
		System.out.println(map);
		resultMap.put("success", true);
		resultMap.put("message", "저장완료");
		return resultMap;
		
	}
}
