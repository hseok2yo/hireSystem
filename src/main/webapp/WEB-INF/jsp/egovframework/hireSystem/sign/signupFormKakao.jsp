<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 카카오 전용 필드 -->
<input type="hidden" name="kakaoId" value="${kakaoUser.id}">

<div class="form-group">
	<label for="name">이름 <span class="required">*</span></label> <input
		type="text" id="userNm" name="userNm">
</div>


<div class="form-group">
    <label for="birthDate">생년월일 <span class="required">*</span></label>
    <input type="date" id="birthDate" name="birthDate"
           max="${currentYear}-12-31"
           min="1924-01-01">
</div>

<div class="form-group">
	<label for="phone">휴대폰 번호 <span class="required">*</span></label>
	<div class="input-with-button">
		<input type="tel" id="userPhone" name="userPhone">
		<button type="button" class="verify-btn">인증하기</button>
	</div>
	<span class="form-hint">'-' 없이 숫자만 입력해주세요.</span>
</div>

<div class="form-group">
	<label for="email">이메일 <span class="required">*</span>&nbsp;&nbsp;
		<span id="sendMsg" style="color: green;"> ${not empty kakaoUser.email ? '✔ 카카오 이메일 인증 완료' : ''}
	</span>
	</label>
	<div class="input-with-button">
		<input type="text" id="userEmail" name="userEmail"
			value="${kakaoUser.email}"
			${not empty kakaoUser.email ? 'readonly' : ''}>
		<%-- 카카오 이메일 없을 때만 인증버튼 노출 --%>
		<c:if test="${empty kakaoUser.email}">
			<button type="button" id="verify-btn" class="verify-btn">인증하기</button>
		</c:if>
	</div>

	<%-- 카카오 이메일 없을 때만 인증번호 영역 노출 --%>
	<c:if test="${empty kakaoUser.email}">
		<div id="codeArea" style="display: none;">
			<input type="text" id="authCode" placeholder="인증번호 입력"> <span
				id="timer"></span>
			<button type="button" id="verify-btn-check">확인</button>
		</div>
	</c:if>

	<%-- 카카오 이메일 있으면 true, 없으면 false --%>
	<input type="hidden" id="kakaoEmailVerified"
		value="${not empty kakaoUser.email ? 'true' : 'false'}">
</div>

