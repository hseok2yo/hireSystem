<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="form-group">
	<label for="id">아이디 <span class="required">*</span></label>
	<div class="input-with-button">
		<input type="text" id="userId" name="userId">
		<button type="button" id="idcheck">중복체크</button>
	</div>
	<div id="idcheck-result" style="margin-top: 5px; font-weight: bold;"></div>
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
	<label for="email">이메일 <span class="required">*</span>&nbsp;&nbsp;<span
		id="sendMsg" style="color: green;"></span></label>
	<div class="input-with-button">
		<input type="text" id="userEmail" name="userEmail">
		<button type="button" id="verify-btn" class="verify-btn">인증하기</button>
	</div>
</div>

<!-- 인증번호 입력창 (처음엔 숨김) -->
<div class="form-group" id="codeArea" style="display: none;">
	<label>인증번호 <span id="timer" style="color: red;"></span></label>
	<div class="input-with-button">
		<input type="text" id="authCode" placeholder="인증번호 입력">
		<button type="button" id="verify-btn-check" class="verify-btn">확인</button>
	</div>
</div>