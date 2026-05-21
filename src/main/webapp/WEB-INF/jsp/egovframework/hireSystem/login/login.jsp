<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%	
// 	String beforeUrl = request.getParameter("redirectUrl"); // 받아온 redirectUrl(로그인하고 나면 이동하려고)

//     String kakaoClientId = "f2c3fc45323982fd05f128f3b8f8f0e9";
//     String redirectUri = "http://localhost:8080/auth/kakaoHireSystem/callback.do";
//     String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
//         + "?client_id=" + kakaoClientId
//         + "&redirect_uri=" + redirectUri
//         + "&response_type=code"
//     	+ "&prompt=login";
%>


<!DOCTYPE html>
<html>
<head>
    <title>로그인 | 개발자 채용포털</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/hireSystem/login.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
</head>
<body class="login-page">
    <script>
<%--         var kakaoAuthUrl = "<%= kakaoAuthUrl %>"; ① 변수 먼저 --%>
    </script>
    <script src="/js/hireSystem/login.js"></script>
    
    
    <div class="login-container">
        <div class="login-content">
            <div class="login-header">
                <a href="<c:url value='/hireSystem/main.do' />" class="logo">
                    <h1>개발자 채용포털</h1>
                </a>
                <p>환영합니다! 로그인하여 다양한 서비스를 이용해보세요.</p>
            </div>
            <input type="hidden" id="redirectUrl" value="${redirectUrl}">
            
            
            <form action="/hireSystem/login/checkLogin.do" method="post" class="login-form" id="loginForm">
                <c:if test="${not empty error}">
                    <div class="error-message">
                        ${error}
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="username">아이디</label>
                    <input type="text" id="userId" name="userId" required 
                           placeholder="아이디를 입력하세요">
                </div>
                
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="userPw" name="userPw" required 
                           placeholder="비밀번호를 입력하세요">
                </div>
                
                <div class="form-options">
                    <label class="remember-me">
                        <input type="checkbox" name="remember" id="remember">
                        <span>로그인 상태 유지</span>
                    </label>
                    <a href="#" class="forgot-password">비밀번호 찾기</a>
                </div>
                
                <button type="submit" class="login-button">로그인</button>
                
                <div class="social-login">
                    <p>다른 방법으로 로그인</p>
                    <div class="social-buttons">
                        <button type="button" id="kakaoLogin" class="social-button kakao">
                            카카오 로그인
                        </button>
						<button type="button" class="social-button naver">
                            네이버 로그인
                        </button>
                    </div>
                </div>
            </form>
            
            <div class="register-link">
                <p>아직 회원이 아니신가요? <a href="/hireSystem/signup/signup.do">회원가입</a></p>
            </div>
        </div>
        
        <div class="login-image">
            <div class="image-overlay"></div>
        </div>
    </div>
</body>
</html> 