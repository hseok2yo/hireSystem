<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <link rel="stylesheet" href="<c:url value='/css/hireSystem/hireSystem.css' />"> --%>
<link rel="stylesheet" href="<c:url value='/css/hireSystem/header.css' />">
<script defer src="/js/hireSystem/common.js"></script>

<div class="site-header">
	<header class="main-header">
		<div class="header-container">
			<div class="logo">
				<h1><a href="/hireSystem/main.do">개발자 채용포털</a></h1>
			</div>
			<nav class="main-nav">
				<a href="jobs.jsp" class="nav-item">채용정보</a>
				<a href="<c:url value='/hireSystem/company/companies.do' />" class="nav-item">기업정보</a>
				<a href="/hireSystem/resume/resumeMain.do" class="nav-item">이력서관리</a>
				<a href="/hireSystem/board/boardList.do" class="nav-item">커뮤니티</a>
				<a href="ai-match.jsp" class="nav-item premium">AI 매칭 <span class="badge">NEW</span></a>
			</nav>
			<div class="auth-buttons" style="font-size : 13px;">
				<c:choose>
					<c:when test="${not empty sessionScope.loginUser}">
						📺 ${sessionScope.loginNm}님 환영합니다
						<a href="<c:url value='/hireSystem/login/logout.do'/>" class="btn-login">로그아웃</a>
					</c:when>
					<c:otherwise>
					<a class="btn-login" href="#" onclick="goToLogin();">
						로그인</a>
					</c:otherwise>
				</c:choose>
				<script>
					//머물렀던 당시 페이지 url저장 후 로그인 성공 시 그 페이지로 이동
					function goToLogin() {
						
						const currentUrl = window.location.pathname
								+ window.location.search;
						const a = encodeURIComponent(currentUrl);
						location.href = "/hireSystem/login/login.do?redirectUrl="
								+ encodeURIComponent(currentUrl);
					}
				</script>
				<c:if test="${empty sessionScope.loginUser}">
					<a href="/hireSystem/signup/signup.do" class="btn-register">회원가입</a>
				</c:if>
			</div>
		</div>
	</header>
</div>