<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 카카오 전용 필드 -->
<input type="hidden" name="kakaoId" value="${kakaoUser.id}">

<div class="form-group">
	<label for="name">이름 <span class="required">*</span></label> <input
		type="text" id="userNm" name="userNm">
</div>

<div class="form-group">
	<label for="phone">휴대폰 번호 <span class="required">*</span></label>
	<div class="input-with-button">
		<input type="tel" id="userPhone" name="userPhone">
		<button type="button" class="verify-btn">인증하기</button>
	</div>
	<span class="form-hint">'-' 없이 숫자만 입력해주세요.</span>
</div>