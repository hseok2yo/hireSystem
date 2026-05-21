package hireSystem.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class RestLoginInterceptor implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 캐시 차단
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, private");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");

        System.out.println("@@@@@@@@@@@@@@@@@@@");
        System.out.println("🔥 REST 인터셉터 실행됨: " + request.getRequestURI());

        if (loginUser == null) {
        	String redirectUrl = request.getRequestURI();
            System.out.println("@@@@@@@@@@@@@@@redirectUrl");
            System.out.println(redirectUrl);
            
            response.setStatus(401);
            return false;
        }

        return true; // 로그인 되어있으면 통과
    }
}
