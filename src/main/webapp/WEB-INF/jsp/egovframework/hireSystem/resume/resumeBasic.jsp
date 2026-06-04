<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"   prefix="fmt" %>

<div class="section-card section-basic-info">
	<div class="basic-top">
		<div>
			<h2>
				기본정보 <span class="required">필수</span>
			</h2>
		</div>
	</div>
	<div class="basic-info-grid">
		<div class="basic-info-view" id="basicInfoView">
			<div class="basic-info-view-header">
				<div>
					<h3>${userInfo.userNm }</h3>
					<c:if test="${not empty userInfo.birthDate}">
						<p>${userInfo.birthDate.substring(0, 4)}년(${userInfo.age}세)</p>
					</c:if>
				</div>
				<button type="button" class="icon-btn edit-basic-info"
					aria-label="수정">
					<i class="fas fa-pencil-alt"></i>
				</button>
			</div>
			<div class="basic-info-summary">
				<div class="summary-item">
				    <i class="fas fa-eye-slash"></i> <span>가려진 정보 보기</span>
				</div>
				<div class="summary-item" data-field="userEmail">       <%-- 추가 --%>
				    <i class="fas fa-envelope"></i> <span>${userInfo.userEmail}</span>
				</div>
				<div class="summary-item" data-field="userPhone">       <%-- 추가 --%>
				    <i class="fas fa-mobile-alt"></i> <span>${userInfo.userPhone}</span>
				</div>
				<div class="summary-item" data-field="userAddress">     <%-- 추가 --%>
				    <i class="fas fa-map-marker-alt"></i> <span>${userInfo.addressFirst} ${userInfo.addressSecond}</span>
				</div>
			</div>
		</div>

		<!-- 기본정보 숨겨진부분(수정페이지) -->
		<form id="basicFormElem">
			<div class="basic-info-edit hidden" id="basicForm">
				<div class="basic-info-form">
					<div class="form-row form-row-wide">
						<label>이름 <span>*</span></label> <input type="text" name="userNm"
							value="${userInfo.userNm}">
					</div>
					<div class="form-row-group">
						<div class="form-row">
							<label>성별</label> <select name="gender">
								<option value="">선택</option>
								<option value="M" ${userInfo.gender == 'M' ? 'selected' : ''}>남</option>
								<option value="F" ${userInfo.gender == 'F' ? 'selected' : ''}>여</option>
							</select>
						</div>
						<div class="form-row">
							<label>생년월일 <span>*</span></label> <input type="text"
								name="birthDate" value="${userInfo.birthDate }">
						</div>
					</div>
					<div class="form-row form-row-with-button">
						<label>이메일 <span>*</span></label>
						<div class="contact-row">
							<input type="text" name="userEmail"
								value="${userInfo.userEmail }" readonly>
							<button type="button" class="btn-secondary">인증</button>
							<button type="button" class="btn-outline">수정</button>
						</div>
					</div>
					<div class="form-row form-row-with-button">
						<label>휴대폰 <span>*</span></label>
						<div class="contact-row">
							<input type="text" name="userPhone"
								value="${userInfo.userPhone }">
							<button type="button" class="btn-secondary">인증</button>
							<button type="button" class="btn-outline">수정</button>
						</div>
					</div>
					<div class="form-row-group">
						<div class="form-row">
							<label>주소 <span>*</span></label> <input type="text"
								name="addressFirst" value="${userInfo.addressFirst }">
						</div>
						<div class="form-row">
							<label>상세주소</label> <input type="text" name="addressSecond"
								value="${userInfo.addressSecond }">
						</div>
					</div>
					<div class="form-actions">
						<button type="button" class="btn-outline cancel-basic-info">취소</button>
						<button type="button" class="btn-outline save-basic-info">저장</button>
					</div>
				</div>
			</div>
		</form>
		<div class="basic-info-photo">
			<div class="photo-box">
				<img src="${userInfo.userPhotoUrl }">
			</div>
			<button type="button" class="btn-outline photo-edit-btn hidden">사진
				수정</button>
		</div>
	</div>
</div>