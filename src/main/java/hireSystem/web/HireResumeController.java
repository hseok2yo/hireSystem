package hireSystem.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
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
import hireSystem.vo.HireCareerVo;
import hireSystem.vo.HireResumeVo;
import hireSystem.vo.HireUserVo;

@Controller
@RequestMapping("/hireSystem/resume")
public class HireResumeController {

	private final String path = "hireSystem/resume/";

	@Resource(name = "hireSignupService")
	private HireSignupService hireSignupService;

	@Resource(name = "hireResumeService")
	private HireResumeService hireResumeService;


	@RequestMapping("/resumeMain.do")
	public String resumeMain(@RequestParam(defaultValue = "1") int page
			,@RequestParam(defaultValue = "recent") String searchSort
			,HttpSession session, Model model) {
		// 세션에서 로그인 회원번호 가져오기
	    int loginUserNum = (int) session.getAttribute("loginUserNum");
	    EgovMap map = new EgovMap();
	    map.put("page", page);
	    map.put("searchSort", searchSort);
	    map.put("loginUserNum", loginUserNum);
	    //대표이력서
	    model.addAttribute("mainResume", hireResumeService.selectResumeMainInfo(map));
	    //서브이력서
	    model.addAttribute("subResume", hireResumeService.selectResumeSubInfo(map));

		return path + "resumeMain";
	}

	//이력서 신규등록
	@RequestMapping("/resumeForm.do")
	public String resumeForm(HttpSession session, Model model) {
		// 세션에서 로그인 회원번호 가져오기
	    int loginUserNum = (int) session.getAttribute("loginUserNum");

	    //공통사항
	    model.addAttribute("commonUserNum", loginUserNum);

	    //유저정보(기본정보)
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
	public Map<String, Object> saveBasicResume(
			@ModelAttribute HireUserVo hireVo
			,@ModelAttribute HireResumeVo vo) {
		Map<String, Object> resultMap = new HashMap<>();

		int result = hireResumeService.saveBasicResume(hireVo, vo);

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
	/**
	 * 수정페이지
	 * @param resumeId
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit.do")
	public String edit(@RequestParam int resumeId
			,HttpSession session
			, Model model
			) {
		int loginUserNum = (int) session.getAttribute("loginUserNum");

		//공통사항
		model.addAttribute("commonUserNum", loginUserNum);
		model.addAttribute("commonResumeId", resumeId);

		//1.(기본정보)
		model.addAttribute("userInfo", hireResumeService.selectHireUserInfo(loginUserNum));

		//2.경력사항
		List<HireCareerVo> careerList = hireResumeService.selectCareerInfo(resumeId);
		model.addAttribute("careerInfo", careerList); //경력리스트
		//총경력
		model.addAttribute("totalCareer",hireResumeService.calculateTotalCareer(careerList));
		return path + "resumeForm";
	}

	@RequestMapping("/careerSave.do")
	@ResponseBody
	public Map<String, Object> careerSave(HireCareerVo careerVo, HttpSession session) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        int loginUserNum = (int) session.getAttribute("loginUserNum");
	        hireResumeService.saveCareer(careerVo, loginUserNum);
	        result.put("resumeId", careerVo.getResumeId());
	        result.put("result", true);
	        result.put("message", "저장완료~");
	    } catch (Exception e) {
	        result.put("result", false);
	        result.put("message", e.getMessage());
	    }
	    return result;
	}

	@RequestMapping("/careerDelete.do")
	@ResponseBody
	public Map<String, Object> careerDelete(@RequestParam int careerId) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        hireResumeService.deleteCareer(careerId);
	        result.put("result", true);
	        result.put("message", "삭제되었습니다.");
	    } catch (Exception e) {
	        result.put("result", false);
	        result.put("message", e.getMessage());
	    }
	    return result;
	}

}
