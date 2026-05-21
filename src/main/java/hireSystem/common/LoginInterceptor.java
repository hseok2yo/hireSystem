package hireSystem.common;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

		// 모든 페이지에 캐시 차단 헤더(로그아웃 후 뒤로가기 방지)
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, private");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		HttpSession session = request.getSession();
		Object loginUser = session.getAttribute("loginUser");
        
        System.out.println("@@@@@@@@@@@@@@@@@@@");
        System.out.println("🔥 인터셉터 실행됨: " + request.getRequestURI());
        
        // 로그인 체크만
        if (loginUser == null) {
            String redirectUrl = request.getRequestURI();
            String queryString = request.getQueryString();
            
            if (queryString != null) {
                redirectUrl += "?" + queryString;
            }
            
            response.sendRedirect(
                request.getContextPath() + 
                "/hireSystem/login/login.do?redirectUrl=" + 
                java.net.URLEncoder.encode(redirectUrl, "UTF-8")
            );
            
            return false;
        }

        return true; // 로그인 되어있으면 통과
    }
}