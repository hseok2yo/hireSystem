<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<section class="section-card section-education" id="education">
	<div class="section-header">
		<div>
			<h2>
				학력 <span class="required">필수</span>
			</h2>
		</div>
		<button type="button" class="btn-text education-add-btn">+ 추가</button>
	</div>

	<%-- 학력 입력 폼 --%>
	<form id="educationForm">
		<article class="education-entry education-add-form hidden"
			id="educationAddForm">
			<div class="career-add-grid">
				<div class="form-row form-row-full">
					<label>학교명 <span>*</span></label> <input type="text"
						name="schoolName" placeholder="학교명을 입력하세요">
				</div>
				<div class="form-row form-row-half">
					<label>학교구분 <span>*</span></label> <select name="schoolType">
						<option value="">선택</option>
						<option value="고등학교">고등학교</option>
						<option value="전문대">전문대(2~3년)</option>
						<option value="4년제">대학교(4년제)</option>
						<option value="대학원">대학원</option>
					</select>
				</div>
				<div class="form-row form-row-half">
					<label>졸업구분 <span>*</span></label> <select name="graduateType">
						<option value="">선택</option>
						<option value="졸업">졸업</option>
						<option value="재학중">재학중</option>
						<option value="중퇴">중퇴</option>
						<option value="휴학">휴학</option>
					</select>
				</div>
				<div class="form-row form-row-quarter">
					<label>입학년월 <span>*</span></label> <input type="date"
						name="startDate">
				</div>
				<div class="form-row form-row-quarter">
					<label>졸업년월</label> <input type="date" name="endDate">
				</div>
				<div class="form-row form-row-quarter current-job-row">
					<label>재학중</label> <label class="switch"> <input
						type="checkbox" name="currentYn"> <span class="slider"></span>
					</label>
				</div>
				<div class="form-row form-row-half">
					<label>전공</label> <input type="text" name="major"
						placeholder="전공을 입력하세요">
				</div>
				<div class="form-row form-row-half">
					<label>부전공</label> <input type="text" name="subMajor"
						placeholder="부전공을 입력하세요">
				</div>
				<div class="form-row form-row-quarter">
					<label>학점</label> <input type="text" name="grade"
						placeholder="예) 3.8">
				</div>
				<div class="form-row form-row-quarter">
					<label>기준학점</label> <select name="gradeMax">
						<option value="">선택</option>
						<option value="4.5">4.5</option>
						<option value="4.0">4.0</option>
					</select>
				</div>
				<div class="form-row form-row-half">
					<label>지역</label> <input type="text" name="location"
						placeholder="예) 부산">
				</div>
				<div class="form-actions career-add-actions">
					<button type="button" class="btn-outline cancel-education-add">취소</button>
					<button type="button" class="btn-primary save-education-add">저장</button>
				</div>
			</div>
		</article>
	</form>

	<%-- 학력 목록 (백에서 조회 후 forEach) --%>
	<div class="education-list-wrapper" id="educationListWrapper">
		<c:forEach items="${educationInfo}" var="edu">
			<article class="education-entry"
				data-education-id="${edu.educationId}"
				data-school-name="${edu.schoolName}"
				data-school-type="${edu.schoolType}" data-major="${edu.major}"
				data-sub-major="${edu.subMajor}" data-grade="${edu.grade}"
				data-grade-max="${edu.gradeMax}" data-location="${edu.location}"
				data-graduate-type="${edu.graduateType}"
				data-start-date="<fmt:formatDate value='${edu.startDate}' pattern='yyyy-MM-dd'/>"
				data-end-date="<fmt:formatDate value='${edu.endDate}' pattern='yyyy-MM-dd'/>"
				data-current-yn="${edu.currentYn}">

				<div class="education-top">
					<div class="education-info">
						<div class="education-header">
							<strong>${edu.schoolName} <c:if
									test="${not empty edu.schoolType}">(${edu.schoolType})</c:if>
							</strong> <span class="career-period"> <fmt:formatDate
									value="${edu.startDate}" pattern="yyyy.MM" /> ~ <c:choose>
									<c:when test="${edu.currentYn eq 'Y'}">재학중</c:when>
									<c:otherwise>
										<fmt:formatDate value="${edu.endDate}" pattern="yyyy.MM" />
										<c:if test="${not empty edu.graduateType}"> (${edu.graduateType})</c:if>
									</c:otherwise>
								</c:choose>
							</span>
						</div>
						<p class="education-major">
							<strong>전공 </strong>${edu.major}</p>
						<div class="education-grid">
							<c:if test="${not empty edu.subMajor}">
								<div>
									<strong>부전공</strong><span>${edu.subMajor}</span>
								</div>
							</c:if>
							<c:if test="${not empty edu.grade}">
								<div>
									<strong>학점</strong><span>${edu.grade}/${edu.gradeMax}</span>
								</div>
							</c:if>
							<c:if test="${not empty edu.location}">
								<div>
									<strong>지역</strong><span>${edu.location}</span>
								</div>
							</c:if>
						</div>
					</div>
					<div class="career-actions">
						<button type="button" class="icon-btn btn-edit-education"
							aria-label="수정">
							<i class="fas fa-pencil-alt"></i>
						</button>
						<button type="button" class="icon-btn btn-delete-education"
							aria-label="삭제">
							<i class="fas fa-trash-alt"></i>
						</button>
					</div>
				</div>
			</article>
		</c:forEach>
	</div>
</section>