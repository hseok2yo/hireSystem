<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 | 최고의 인재를 만나다</title>
    <link rel="stylesheet" href="/css/hireSystem/signup.css">
    <!-- Font Awesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="/js/hireSystem/signup.js"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>

	<!-- 로그인타입 체크 -->
	<input type="hidden" id="isKakao" value="${isKakao}">
	<input type="hidden" name="loginType" value="${loginType}">
	
	<main class="signup-container">
        <div class="signup-content">
            <div class="signup-header">
                <h1>회원가입</h1>
                <p>나에게 딱 맞는 채용 정보를 받아보세요</p>
            </div>

            <form class="signup-form" id="signupForm" action="/hireSystem/signup/registMember.do" method="POST">
                <!-- 회원 유형 선택 -->
                <div class="form-section">
                    <h2>회원 유형</h2>
                    <div class="member-type-selector">
                        <label class="type-option">
                            <input type="radio" name="userMemberType" value="individual" checked>
                            <span class="type-content">
                                <i class="fas fa-user"></i>
                                <span class="type-label">개인회원</span>
                                <span class="type-desc">구직자용</span>
                            </span>
                        </label>
                        <label class="type-option">
                            <input type="radio" name="userMemberType" value="company">
                            <span class="type-content">
                                <i class="fas fa-building"></i>
                                <span class="type-label">기업회원</span>
                                <span class="type-desc">채용담당자용</span>
                            </span>
                        </label>
                    </div>
                </div>

                <!-- 기본 정보 입력 -->
                <div class="form-section">
                    <h2>기본 정보</h2>

					<c:choose>
						<c:when test="${isKakao}">
							<!-- 카카오로그인 html -->
							<%@ include file="signupFormKakao.jsp"%>
						</c:when>
						<c:otherwise>
							<!-- 일반로그인 html -->
							<%@ include file="signupFormNormal.jsp"%>
						</c:otherwise>
					</c:choose>
                </div>

                <!-- 약관동의항목 -->
				<%@ include file="signupFormAgree.jsp"%>
				
                <button type="submit" class="submit-btn">가입하기</button>
            </form>

            <div class="signup-footer">
                <p>이미 회원이신가요? <a href="/hireSystem/login/login.do">로그인하기</a></p>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>


</body>
</html> 