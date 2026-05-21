<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- 약관 동의 -->
<div class="form-section">
	<h2>약관 동의</h2>
	<div class="agreement-group">
		<label class="agreement-all"> <input type="checkbox"
			id="agreeAll"> <span class="checkmark"></span> <span>전체
				동의</span>
		</label>
		<div class="agreement-items">
			<label class="agreement-item"> <input type="checkbox"
				name="agreements" value="TERM_SERVICE" class="required"> <span
				class="checkmark"></span> <span>[필수] 이용약관 동의</span>
				<button type="button" class="view-terms">보기</button>
			</label> <label class="agreement-item"> <input type="checkbox"
				name="agreements" value="TERM_PRIVACY_COLLECT" class="required">
				<span class="checkmark"></span> <span>[필수] 개인정보 수집 및 이용 동의</span>
				<button type="button" class="view-terms">보기</button>
			</label> <label class="agreement-item"> <input type="checkbox"
				name="agreements" value="TERM_MARKETING"> <span
				class="checkmark"></span> <span>[선택] 마케팅 정보 수신 동의</span>
				<button type="button" class="view-terms">보기</button>
			</label>
		</div>
	</div>
</div>