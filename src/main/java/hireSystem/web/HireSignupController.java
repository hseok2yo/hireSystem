package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hireSystem.service.HireSignupService;
import hireSystem.vo.HireUserVo;
import hireSystem.vo.KakaoUserVo;

@Controller
@RequestMapping("/hireSystem/signup")
public class HireSignupController {
	
	private final String path = "hireSystem/sign/";
	
	@Resource(name = "hireSignupService")
	private HireSignupService hireSignupService;
	
	@RequestMapping("/signup.do")
	public String signup(HttpSession session, Model model
			,@RequestParam(value = "type", defaultValue = "normal") String type) {
	    KakaoUserVo kakaoUser = (KakaoUserVo) session.getAttribute("kakaoUser");
	    
	    // 일반 회원가입으로 온 경우 카카오 세션 제거
	    if (!"kakao".equals(type)) {
	        session.removeAttribute("kakaoUser");
	        kakaoUser = null;
	    }
	    
	    if(kakaoUser != null) {
	        model.addAttribute("kakaoUser", kakaoUser); //카카오사용자 정보 vo
	        model.addAttribute("isKakao", true);
	        model.addAttribute("loginType", "KAKAO");
	    } else {
	        model.addAttribute("isKakao", false);
	        model.addAttribute("loginType", "NORMAL");
	    }
	    // 현재 연도 추가
	    model.addAttribute("currentYear", java.time.LocalDate.now().getYear());
	    
	    return path + "signup";
	}
	
	/**
	 * ID중복체크
	 * @return 중복여부 0:중복x 1:중복o
	 */
	@RequestMapping("/checkDuplicationID.do")
	@ResponseBody
	public Map<String, Object> checkDuplicationID(@RequestBody Map<String, String> dataMap) {
		
		int resultNum = 0;
		if(dataMap.get("id") != null) {
			resultNum = hireSignupService.checkDuplicationID(dataMap.get("id"));
		}
		
		Map<String, Object> result = new HashMap<>();
		
		if(resultNum > 0) {
			result.put("exists", false);
			result.put("message", "used");
			
		}else {
			result.put("exists", true);
			result.put("message", "unused");
		}
	    
	    return result;  // → JSON: {"exists":false,"message":"성공"}
	}
	
	/**
	 * 회원등록
	 * @param hireUserVo 회원가입할 유저정보
	 * @return 성공여부
	 */
	@RequestMapping("/registMember.do")
	public String registMember(@ModelAttribute HireUserVo hireUserVo, RedirectAttributes redirectAttributes) throws Exception{ 
		Boolean result = false;
		try {
			result = hireSignupService.registMember(hireUserVo);
		}catch(Exception e) {
			System.out.println("@@@@@@@@@@@@@@@error");
			System.out.println(e.getMessage());
		}
		
		 if (result) {
	        redirectAttributes.addFlashAttribute("msg", "회원가입이 완료되었습니다!");
	    } else {
	        redirectAttributes.addFlashAttribute("msg", "회원가입에 실패했습니다.");
	    }
		
		
		 return "redirect:/hireSystem/main.do";
	}
}
