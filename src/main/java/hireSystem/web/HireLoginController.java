package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hireSystem.service.HireLoginService;
import hireSystem.vo.HireUserVo;

@Controller
@RequestMapping("/hireSystem/login")
public class HireLoginController {
		
	private final String path = "hireSystem/login/";
	private static final Logger logger = LoggerFactory.getLogger(HireLoginController.class);
	
	@Resource(name = "hireLoginService")
	private HireLoginService hireLoginService;
	
	@RequestMapping("/login.do")
	public String login(@RequestParam(required = false) String redirectUrl
			, Model model
			, HttpSession session) {
	    model.addAttribute("redirectUrl", redirectUrl);
	    return path + "login";
	}
	
	
	@PostMapping("/checkLogin.do")
	@ResponseBody
	public Map<String, Object> checkLogin(@RequestBody HireUserVo hireUserVo
            , HttpServletResponse response
			, HttpSession session) {
	    
		HireUserVo resultVo = hireLoginService.checkLogin(hireUserVo);
		
		Map<String, Object> map = new HashMap<>();
		
		if(resultVo != null) {
			map.put("result", "success");
			// 로그인 성공 → 세션에 저장
	        session.setAttribute("loginUser", resultVo.getUserId());
	        session.setAttribute("loginNm", resultVo.getUserNm());
			// ✅ 로그인 상태 유지 체크 여부 확인
			if ("true".equals(hireUserVo.getRemember())) {
				// ✅ 체크 O : 1일 유지
				session.setMaxInactiveInterval(60 * 60 * 24);
			} else {
				// ✅ 체크 X : 30분 유지
				session.setMaxInactiveInterval(60 * 30);
			}

		}else {
			map.put("result", "fail");
		}
		
		return map;
		
	}
	
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session, HttpServletRequest request) {

	    session.invalidate();

	    // 현재 요청 URL, 쿼리 로깅
	    String currentUrl = request.getRequestURL().toString();
	    String currentQuery = request.getQueryString();
	    logger.info("[LOGOUT] currentUrl={}, currentQuery={}", currentUrl, currentQuery);

	    // 머물렀던 페이지로 이동
	    String referer = request.getHeader("Referer"); // http://localhost:8080/hireSystem/board/boardList.do
	    logger.info("[LOGOUT] Referer={}", referer);
	    
	    if (referer != null && !referer.isEmpty()) {
	        try {
	            // URI로 파싱
	            java.net.URI uri = new java.net.URI(referer);
	            String path = uri.getPath(); // /hireSystem/board/boardList.do
	            String query = uri.getRawQuery();

	            // Referer는 조작될 수 있으니, 우리 서비스 경로만 허용
	            String allowedPrefix = request.getContextPath() + "/hireSystem/";
	            String logoutPath = request.getContextPath() + "/hireSystem/login/logout.do";
	            logger.info("[LOGOUT] parsedPath={}, parsedQuery={}, allowedPrefix={}, logoutPath={}",
	            		path, query, allowedPrefix, logoutPath);
	            
	            if (path != null
	            		&& path.startsWith(allowedPrefix)
	            		&& !path.equals(logoutPath)) {
	            	String target = path + (query != null && !query.isEmpty() ? "?" + query : "");
	            	logger.info("[LOGOUT] redirect to previous page: {}", target);
	                return "redirect:" + target;
	            } else {
	            	logger.info("[LOGOUT] previous page is not allowed. Fallback to main.");
	            }
	            
	        } catch (Exception e) {
	        	logger.error("[LOGOUT] error while parsing referer", e);
	            e.printStackTrace();
	        }
	    }

	    return "redirect:/hireSystem/main.do"; // 예외 대비
	}
	
}
