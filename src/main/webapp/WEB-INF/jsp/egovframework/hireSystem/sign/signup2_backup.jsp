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
                    
                    <div class="form-group">
                        <label for="id">아이디 <span class="required">*</span></label>
                        <div class="input-with-button">
                            <input type="text" id="userId" name="userId">
                            <button type="button" id="idcheck">중복체크</button>
                        </div>
                        <div id="idcheck-result" style="margin-top: 5px; font-weight: bold;"></div>
                    </div>
                    
                    
                    <div class="form-group">
                        <label for="email">이메일 <span class="required">*</span>&nbsp;&nbsp;<span id="sendMsg" style="color:green;"></span></label>
                        <div class="input-with-button">
                            <input type="text" id="userEmail" name="userEmail">
                            <button type="button" id="verify-btn" class="verify-btn">인증하기</button>
                        </div>
                    </div>
						
					<!-- 인증번호 입력창 (처음엔 숨김) -->
					<div class="form-group" id="codeArea" style="display: none;">
						<label>인증번호 <span id="timer" style="color:red;"></span></label>
						<div class="input-with-button">
							<input type="text" id="authCode" placeholder="인증번호 입력">
							<button type="button" id="verify-btn-check" class="verify-btn">확인</button>
						</div>
					</div>
					
					<div class="form-group">
                        <label for="password">비밀번호 <span class="required">*</span></label>
                        <div class="password-input">
                            <input type="password" id="userPw" name="userPw">
                            <button type="button" class="toggle-password">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                        <span class="form-hint">영문, 숫자, 특수문자 조합 8-20자</span>
                    </div>

                    <div class="form-group">
                        <label for="passwordConfirm">비밀번호 확인 <span class="required">*</span></label>
                        <div class="password-input">
                            <input type="password" id="passwordConfirm" name="passwordConfirm">
                            <button type="button" class="toggle-password">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name">이름 <span class="required">*</span></label>
                        <input type="text" id="userNm" name="userNm">
                    </div>

                    <div class="form-group">
                        <label for="phone">휴대폰 번호 <span class="required">*</span></label>
                        <div class="input-with-button">
                            <input type="tel" id="userPhone" name="userPhone">
                            <button type="button" class="verify-btn">인증하기</button>
                        </div>
                        <span class="form-hint">'-' 없이 숫자만 입력해주세요.</span>
                    </div>
                </div>

                <!-- 약관 동의 -->
                <div class="form-section">
                    <h2>약관 동의</h2>
                    <div class="agreement-group">
                        <label class="agreement-all">
                            <input type="checkbox" id="agreeAll">
                            <span class="checkmark"></span>
                            <span>전체 동의</span>
                        </label>
                        <div class="agreement-items">
                            <label class="agreement-item">
                                <input type="checkbox" name="agreements" value="TERM_SERVICE" class="required">
                                <span class="checkmark"></span>
                                <span>[필수] 이용약관 동의</span>
                                <button type="button" class="view-terms">보기</button>
                            </label>
                            <label class="agreement-item">
                                <input type="checkbox" name="agreements" value="TERM_PRIVACY_COLLECT" class="required">
                                <span class="checkmark"></span>
                                <span>[필수] 개인정보 수집 및 이용 동의</span>
                                <button type="button" class="view-terms">보기</button>
                            </label>
                            <label class="agreement-item">
                                <input type="checkbox" name="agreements" value="TERM_MARKETING">
                                <span class="checkmark"></span>
                                <span>[선택] 마케팅 정보 수신 동의</span>
                                <button type="button" class="view-terms">보기</button>
                            </label>
                        </div>
                    </div>
                </div>
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