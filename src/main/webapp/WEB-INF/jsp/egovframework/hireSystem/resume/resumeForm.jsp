<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>이력서 상세 | 사람인 스타일</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <%-- 섹션별 CSS (각 include 파일에 대응) --%>
    <link rel="stylesheet" href="/css/hireSystem/resumeForm.css">
    <link rel="stylesheet" href="/css/hireSystem/resumeActivity.css">
    <link rel="stylesheet" href="/css/hireSystem/resumeCertification.css">
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/resume/resumeCropModal.jsp"></jsp:include>

	<%-- 섹션별 js --%>
	<script src="/js/hireSystem/resume/resumeCommon.js"></script>
	<script src="/js/hireSystem/resume/resumeBasic.js"></script>
	<script src="/js/hireSystem/resume/resumeCareer.js"></script>
	<script src="/js/hireSystem/resume/resumeEducation.js"></script>
	<script src="/js/hireSystem/resume/resumeSkill.js"></script>
	<script src="/js/hireSystem/resume/resumeActivity.js"></script>
	<script src="/js/hireSystem/resume/resumeCertification.js"></script>


		<!-- 공통 hidden -->
	    <input type="hidden" id="resumeId" name="resumeId" value="${commonResumeId}">
		<input type="hidden" id="userNum"  name="userNum"  value="${commonUserNum}">

	    <div class="resume-page">
	        <div class="resume-layout">
	            <div class="resume-left">

	            	<%-- ===================== 기본정보 섹션  ===================== --%>
					<jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/resume/resumeBasic.jsp"/>

	                <div class="resume-body">
	                    <main class="resume-content">

	                    	<!-- 커리어 -->
<!-- 	                        <section class="section-card section-summary" style="display:none;"> -->
<!-- 	                            <div class="section-header"> -->
<!-- 	                                <div> -->
<!-- 	                                    <h2>MY Career</h2> -->
<!-- 	                                    <p>마이커리어란?</p> -->
<!-- 	                                </div> -->
<!-- 	                                <button type="button" class="btn-text">+ 추가</button> -->
<!-- 	                            </div> -->
<!-- 	                            <div class="section-note">ChatGPT API 기반으로 커리어소개 생성하고 나의 이력서를 어필해보세요!</div> -->
<!-- 	                        </section> -->

							<%-- ===================== 경력사항 섹션  ===================== --%>
	                        <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/resume/resumeCareer.jsp"/>

	                        <%-- ===================== 학력사항 섹션  ===================== --%>
							<jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/resume/resumeEducation.jsp"/>

	                        <%-- ===================== 스킬 섹션===================== --%>
							<jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/resume/resumeSkill.jsp"/>

							<%-- ===================== 경험/활동/교육 섹션 ===================== --%>
	                        <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/resume/resumeActivity.jsp"/>

							<%-- ===================== 자격/어학/수상 섹션 ===================== --%>
							 <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/resume/resumeCertification.jsp"/>

	                    </main>
	                </div>
	            </div>

	            <aside class="resume-sidebar">
	                <section class="completion-card">
	                    <div class="completion-header">
	                        <span>이력서 완성도</span>
	                        <strong>100%</strong>
	                    </div>
	                    <div class="completion-progress">
	                        <span></span>
	                    </div>
	                    <p>회원님의 이력서가 완성됐어요!</p>
	                    <button type="button" class="coach-btn primary" disabled>AI 이력서 코칭 결과 보기</button>
	                </section>

	                <section class="sidebar-menu">
	                    <h3>My Career</h3>
	                    <ul class="sidebar-menu-list">
	                        <li><button type="button" class="sidebar-toggle expanded" data-target="#career"><span>경력</span><i class="fas fa-minus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle expanded" data-target="#education"><span>학력</span><i class="fas fa-minus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle expanded" data-target="#skills"><span>스킬</span><i class="fas fa-minus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle expanded" data-target="#activity"><span>경험/활동/교육</span><i class="fas fa-minus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle expanded" data-target="#certification"><span>자격/어학/수상</span><i class="fas fa-minus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle" data-target=""><span>포트폴리오 및 기타문서</span><i class="fas fa-plus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle" data-target=""><span>자기소개서</span><i class="fas fa-plus"></i></button></li>
	                    </ul>
	                </section>
	            </aside>
	        </div>

	        <div class="resume-actions">
	            <button type="button" class="btn-outline">취소</button>
	            <button type="button" class="btn-primary submit-complete">작성완료</button>
	        </div>
	    </div>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>
</body>
</html>
