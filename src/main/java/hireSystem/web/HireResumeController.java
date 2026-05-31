package hireSystem.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hireSystem.service.HireResumeService;
import hireSystem.service.HireSignupService;
import hireSystem.vo.HireResumeVo;

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
	    model.addAttribute("userInfo", hireResumeService.selectHireUserInfo(loginUserNum));


		return path + "resumeForm";
	}

	/**
	 * 프로필 이미지 변경
	 * @param file 크롭된 이미지 파일
	 * @param original 원본 이미지 파일 (새 파일 선택 시에만 전달, 없으면 기존 원본 유지)
	 * @param session 유저 세션 정보
	 * @return 크롭본 URL, 원본 URL
	 * @throws IOException
	 */
	@PostMapping("/image/uploadPhoto.do")
	@ResponseBody
	public Map<String, Object> uploadPhoto(
	        @RequestParam("upload") MultipartFile file,
	        @RequestParam(value="original", required=false) MultipartFile original,
	        HttpSession session) throws IOException {

	    int loginUserNum = (int) session.getAttribute("loginUserNum");

	    Map<String, Object> result = new HashMap<>();
	    try {
	    	Map<String, Object> photoResult = hireSignupService.updateUserPhoto(file, original, loginUserNum);
	        result.put("success", true);
	        result.put("url", photoResult.get("url"));
	        result.put("originalUrl", photoResult.get("originalUrl"));
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", "파일 업로드에 실패했습니다.");
	    }

	    return result;
	}

	@PostMapping("/saveBasicResume.do")
	@ResponseBody
	public Map<String, Object> saveBasicResume(@ModelAttribute HireResumeVo vo) {
		Map<String, Object> resultMap = new HashMap<>();

		System.out.println("@@@@@@@@@@@@@@@");
		System.out.println(vo);
		int result;
		//resumeid없는경우 insert
		if(vo.getResumeId() == null) {
			result = hireResumeService.insertBasicResume(vo);
		}else {
			result = hireResumeService.updateBasicResume(vo);
		}
		if(result > 0) {
			resultMap.put("result", true);
			resultMap.put("message", "저장완료");
			resultMap.put("resumeId", vo.getResumeId()); // insert는 생성된 id 반환
		}else {
			resultMap.put("result", false);
			resultMap.put("message", "저장실패");
		}


		return resultMap;

	}
}
