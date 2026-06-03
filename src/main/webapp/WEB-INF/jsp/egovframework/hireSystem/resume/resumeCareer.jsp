<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<section class="section-card section-career" id="career">
	<div class="section-header">
		<div>
			<h2>
				경력 <span class="required">필수</span>
			</h2>
			<p>총 경력 ${totalCareer }</p>
		</div>
		<div class="section-actions">
			<button type="button" class="btn-text career-add-btn">+ 추가</button>
		</div>
	</div>
	<!-- 경력 추가form -->
	<form id="careerForm">
		<article class="career-entry career-add-form hidden"
			id="careerAddForm">
			<div class="career-add-grid">
				<div class="form-row form-row-full company-row">
					<label>회사명 <span>*</span></label>
					<div class="company-input-wrap">
						<input type="text" name="companyName" placeholder="회사명을 입력하세요">
						<button type="button" class="icon-btn search-company"
							aria-label="회사 검색">
							<i class="fas fa-search"></i>
						</button>
					</div>
				</div>
				<div class="form-row form-row-quarter">
					<label>입사년월 <span>*</span></label> <input type="date"
						name="startDate">
				</div>
				<div class="form-row form-row-quarter">
					<label>퇴사년월</label> <input type="date" name="endDate">
				</div>
				<div class="form-row form-row-quarter current-job-row">
					<label>재직중</label> <label class="switch"><input
						type="checkbox" name="currentYn"><span class="slider"></span></label>
				</div>
				<div class="form-row form-row-full">
					<label>직무 <span>*</span></label> <input type="text" name="jobTitle"
						placeholder="직무를 입력하세요">
				</div>
				<div class="form-row form-row-half">
					<label>근무부서</label> <input type="text" name="department"
						placeholder="근무부서를 입력하세요">
				</div>
				<div class="form-row form-row-half">
					<label>직급/직책</label> <input type="text" name="positionName"
						placeholder="직급/직책을 입력하세요">
				</div>
				<div class="form-row form-row-full">
					<label>담당업무</label>
					<textarea name="duties" rows="4"
						placeholder="담당업무를 입력해주세요.\n- 진행한 업무를 더 적기 보다는 경력사항 별로 중요한 내용만 엄선해서 작성하는 것이 중요합니다!\n- 담당한 업무 내용을 요약해서 작성해보세요!\n- 경력별 프로젝트 내용을 적을 경우, 역할/팀구성/기여도/성과를 기준으로 요약해서 작성해보세요!"></textarea>
				</div>
				<div class="form-actions career-add-actions">
					<button type="button" class="btn-outline cancel-career-add">취소</button>
					<button type="button" class="btn-primary save-career-add">저장</button>
				</div>
			</div>
		</article>
	</form>
	<!-- 백에서 조회 후 foreach -->
	<div class="career-list-wrapper" id="careerListWrapper">
		<c:forEach items="${careerInfo}" var="career">
			<article class="career-entry" data-career-id="${career.careerId}"
				data-company-name="${career.companyName}"
				data-start-date="<fmt:formatDate value='${career.startDate}' pattern='yyyy-MM-dd'/>"
				data-end-date="<fmt:formatDate value='${career.endDate}' pattern='yyyy-MM-dd'/>"
				data-current-yn="${career.currentYn}"
				data-job-title="${career.jobTitle}"
				data-department="${career.department}"
				data-position-name="${career.positionName}"
				data-duties="${career.duties}">
				<div class="career-top">
					<div class="career-info">
						<div class="career-header">
							<strong>${career.companyName}</strong> <span
								class="career-period"> <fmt:formatDate
									value="${career.startDate}" pattern="yyyy-MM-dd" /> ~ <c:choose>
									<c:when test="${career.currentYn eq 'Y'}">재직중</c:when>
									<c:otherwise>
										<fmt:formatDate value="${career.endDate}" pattern="yyyy-MM-dd" />
									</c:otherwise>
								</c:choose> · ${career.duration}
							</span>
						</div>
						<p>${career.jobTitle}· ${career.department}
							${career.positionName}</p>
					</div>
					<!-- career-info 닫고 career-top 안에 나란히 -->
					<div class="career-actions">
						<button type="button" class="icon-btn btn-edit-career"
							aria-label="수정">
							<i class="fas fa-pencil-alt"></i>
						</button>
						<button type="button" class="icon-btn btn-delete-career"
							aria-label="삭제">
							<i class="fas fa-trash-alt"></i>
						</button>
					</div>
				</div>
				<ul class="career-list">
					<li>${career.duties}</li>
				</ul>
			</article>
		</c:forEach>
	</div>
</section>