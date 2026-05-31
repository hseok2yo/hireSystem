<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>이력서 상세 | 사람인 스타일</title>
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/resumeForm.css' />">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/resume/resumeCropModal.jsp"></jsp:include>

	<script src="/js/hireSystem/resume/resumeCommon.js"></script>
	<script src="/js/hireSystem/resume/resumeBasic.js"></script>

	<form id="resumeForm">
		<!-- 공통 hidden -->
	    <input type="hidden" id="resumeId" name="resumeId" value="${userInfo.resumeId}">
	    <input type="hidden" id="userNum" name="userNum" value="${userInfo.userNum}">

	    <div class="resume-page">
	        <div class="resume-layout">
	            <div class="resume-left">
	                <div class="section-card section-basic-info">
	                    <div class="basic-top">
	                        <div>
	                            <h2>기본정보 <span class="required">필수</span></h2>
	                        </div>
	                    </div>
	                    <div class="basic-info-grid">
	                        <div class="basic-info-view" id="basicInfoView">
	                            <div class="basic-info-view-header">
	                                <div>
	                                    <h3>${userInfo.userNm }</h3>
	                                    <c:if test="${not empty userInfo.birthDate}">
										    <p>${userInfo.birthDate.substring(0, 4)}년 (${userInfo.age}세)</p>
										</c:if>
	                                </div>
	                                <button type="button" class="icon-btn edit-basic-info" aria-label="수정">
	                                    <i class="fas fa-pencil-alt"></i>
	                                </button>
	                            </div>
	                            <div class="basic-info-summary">
	                                <div class="summary-item">
	                                    <i class="fas fa-eye-slash"></i>
	                                    <span>가려진 정보 보기</span>
	                                </div>
	                                <div class="summary-item">
	                                    <i class="fas fa-envelope"></i>
	                                    <span>${userInfo.userEmail }</span>
	                                </div>
	                                <div class="summary-item">
	                                    <i class="fas fa-mobile-alt"></i>
	                                    <span>${userInfo.userPhone }</span>
	                                </div>
	                                <div class="summary-item">
	                                    <i class="fas fa-map-marker-alt"></i>
	                                    <span>${userInfo.addressFirst} ${userInfo.addressSecond}</span>
	                                </div>
	                            </div>
	                        </div>

							<!-- 기본정보 숨겨진부분(수정페이지) -->
	                        <div class="basic-info-edit hidden" id="basicInfoEdit">
	                            <div class="basic-info-form">
	                                <div class="form-row form-row-wide">
	                                    <label>이름 <span>*</span></label>
	                                    <input type="text" name="userNm" value="${userInfo.userNm}">
	                                </div>
	                                <div class="form-row-group">
	                                    <div class="form-row">
	                                        <label>성별</label>
	                                        <select name="gender">
											    <option value="">선택</option>
											    <option value="M" ${userInfo.gender == 'M' ? 'selected' : ''}>남</option>
											    <option value="F" ${userInfo.gender == 'F' ? 'selected' : ''}>여</option>
											</select>
	                                    </div>
	                                    <div class="form-row">
	                                        <label>생년월일 <span>*</span></label>
	                                        <input type="text" name="birthDate" value="${userInfo.birthDate }">
	                                    </div>
	                                </div>
	                                <div class="form-row form-row-with-button">
	                                    <label>이메일 <span>*</span></label>
	                                    <div class="contact-row">
	                                        <input type="text" name="userEmail" value="${userInfo.userEmail }" readonly>
	                                        <button type="button" class="btn-secondary">인증</button>
	                                        <button type="button" class="btn-outline">수정</button>
	                                    </div>
	                                </div>
	                                <div class="form-row form-row-with-button">
	                                    <label>휴대폰 <span>*</span></label>
	                                    <div class="contact-row">
	                                        <input type="text" name="userPhone" value="${userInfo.userPhone }">
	                                        <button type="button" class="btn-secondary">인증</button>
	                                        <button type="button" class="btn-outline">수정</button>
	                                    </div>
	                                </div>
	                                <div class="form-row-group">
	                                    <div class="form-row">
	                                        <label>주소 <span>*</span></label>
	                                        <input type="text" name="addressFirst" value="${userInfo.addressFirst }">
	                                    </div>
	                                    <div class="form-row">
	                                        <label>상세주소</label>
	                                        <input type="text" name="addressSecond"  value="${userInfo.addressSecond }">
	                                    </div>
	                                </div>
	                                <div class="form-actions">
	                                    <button type="button" class="btn-outline cancel-basic-info">취소</button>
	                                    <button type="button" class="btn-outline save-basic-info">저장</button>
	                                </div>
	                            </div>
	                        </div>

	                        <div class="basic-info-photo">
	                            <div class="photo-box">
	                                <img src="${userInfo.userPhotoUrl }">
	                            </div>
	                            <button type="button" class="btn-outline photo-edit-btn hidden">사진 수정</button>
	                        </div>
	                    </div>
	                </div>

	                <div class="resume-body">
	                    <main class="resume-content">
	                        <section class="section-card section-summary">
	                            <div class="section-header">
	                                <div>
	                                    <h2>MY Career</h2>
	                                    <p>마이커리어란?</p>
	                                </div>
	                                <button type="button" class="btn-text">+ 추가</button>
	                            </div>
	                            <div class="section-note">ChatGPT API 기반으로 커리어소개 생성하고 나의 이력서를 어필해보세요!</div>
	                        </section>

	                        <section class="section-card section-career" id="career">
	                            <div class="section-header">
	                                <div>
	                                    <h2>경력 <span class="required">필수</span></h2>
	                                    <p>총 경력 1년 6개월</p>
	                                </div>
	                                <div class="section-actions">
	                                    <button type="button" class="btn-text">+ 인증 경력 불러오기</button>
	                                    <button type="button" class="btn-text">+ 추가</button>
	                                </div>
	                            </div>
	                            <article class="career-entry">
	                                <div class="career-top">
	                                    <div>
	                                        <strong>(주)유시스</strong>
	                                        <p>웹개발 · 시스템개발SI 사원/매니저 2년차</p>
	                                    </div>
	                                    <span class="career-period">2023.10 ~ 2025.03 · 1년 6개월</span>
	                                </div>
	                                <ul class="career-list">
	                                    <li>부산교통공사 인사시스템 개발 (2023.12 ~ 2024.11)</li>
	                                    <li>[기술스택] : Java, Spring MVC, eGovFramework, Nexacro, Oracle, MyBatis</li>
	                                </ul>
	                                <div class="career-footer">
	                                    <button type="button" class="coach-btn"><i class="fas fa-magic"></i> AI 코칭 결과 확인</button>
	                                </div>
	                            </article>
	                        </section>

	                        <section class="section-card section-education" id="education">
	                            <div class="section-header">
	                                <div>
	                                    <h2>학력 <span class="required">필수</span></h2>
	                                </div>
	                                <button type="button" class="btn-text">+ 추가</button>
	                            </div>
	                            <article class="education-entry">
	                                <div class="education-top">
	                                    <strong>부산외국어대학교(부산) (4년제)</strong>
	                                    <span>2014.03 ~ 2020.02 (졸업)</span>
	                                </div>
	                                <p class="education-major">영어통번역학과</p>
	                                <div class="education-grid">
	                                    <div><strong>부전공</strong><span>파이데이아창의인재학과</span></div>
	                                    <div><strong>학점</strong><span>3/4.5</span></div>
	                                    <div><strong>지역</strong><span>부산</span></div>
	                                </div>
	                            </article>
	                            <article class="education-entry">
	                                <div class="education-top">
	                                    <strong>사직고등학교</strong>
	                                    <span>2011.03 ~ 2013.11 (졸업)</span>
	                                </div>
	                                <p class="education-major">문과계열</p>
	                            </article>
	                        </section>

	                        <section class="section-card section-skill" id="skills">
	                            <div class="section-header">
	                                <div>
	                                    <h2>스킬</h2>
	                                </div>
	                                <div class="section-actions">
	                                    <button type="button" class="btn-text"><i class="fas fa-magic"></i> AI 코칭 결과 확인</button>
	                                    <button type="button" class="btn-text">+ 인성검사 소프트스킬 불러오기</button>
	                                    <button type="button" class="btn-text">+ 추가</button>
	                                </div>
	                            </div>
	                            <div class="skill-tags">
	                                <span>Java</span>
	                                <span>Spring Framework</span>
	                                <span>JSP</span>
	                                <span>jQuery</span>
	                                <span>JavaScript</span>
	                                <span>HTML5</span>
	                                <span>CSS 3</span>
	                                <span>MySQL</span>
	                                <span>Oracle</span>
	                                <span>MyBatis</span>
	                                <span>eGovFrame</span>
	                                <span>문서작성</span>
	                                <span>Linux</span>
	                                <span>PHP</span>
	                                <span>Nexacro</span>
	                            </div>
	                        </section>

	                        <section class="section-card section-experience" id="experience">
	                            <div class="section-header">
	                                <div>
	                                    <h2>경험/활동/교육</h2>
	                                </div>
	                                <div class="section-actions">
	                                    <button type="button" class="btn-text">+ 직무캠프 수료 내역 불러오기</button>
	                                    <button type="button" class="btn-text">+ 추가</button>
	                                </div>
	                            </div>

	                            <article class="experience-entry">
	                                <div class="experience-top">
	                                    <strong>그린컴퓨터아카데미</strong>
	                                    <span>2023.03 ~ 2023.08</span>
	                                </div>
	                                <ul class="experience-list">
	                                    <li>디지털 기술을 기반으로 다양한 기기의 융합, 네트워크의 융합, 콘텐츠의 융합을 통해 새로운 형태의 제품이나 융합 서비스를 창출하기 위한 설계 및 테스트 수행</li>
	                                    <li>컴퓨터 프로그래밍 언어로 각 업무에 맞는 소프트웨어 기능 설계, 구현 및 테스트 수행</li>
	                                    <li>관계형 데이터베이스에서 SQL을 활용하여 응용 SW 데이터 정의, 조작, 제어</li>
	                                    <li>공공데이터를 활용한 창의적 서비스 적용 및 데이터 융합</li>
	                                </ul>
	                            </article>

	                            <article class="experience-entry">
	                                <div class="experience-top">
	                                    <strong>지아이티아카데미 부산</strong>
	                                    <span>2021.05 ~ 2021.12</span>
	                                </div>
	                                <ul class="experience-list">
	                                    <li>수업 평가자료 관리</li>
	                                    <li>수업 OT자료 및 설문지 작성 및 취합</li>
	                                    <li>훈련생 출결관리 및 전산처리</li>
	                                    <li>서류 및 교재정리</li>
	                                </ul>
	                            </article>

	                            <article class="experience-entry">
	                                <div class="experience-top">
	                                    <strong>부산양정인력개발센터</strong>
	                                    <span>2019.12 ~ 2020.05</span>
	                                </div>
	                                <ul class="experience-list">
	                                    <li>정보구조 설계 및 워크플로우 제작</li>
	                                    <li>UI/UX 디자인 기반 개발 계획 수립</li>
	                                    <li>프로그램 계획 수립 및 멀티미디어 연동 산출물 작성</li>
	                                </ul>
	                            </article>
	                        </section>
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
	                    <p>윤형석 회원님의 이력서가 완성됐어요!</p>
	                    <button type="button" class="coach-btn primary">AI 이력서 코칭 결과 보기</button>
	                </section>

	                <section class="sidebar-menu">
	                    <h3>My Career</h3>
	                    <ul class="sidebar-menu-list">
	                        <li><button type="button" class="sidebar-toggle expanded" data-target="#career"><span>경력</span><i class="fas fa-minus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle expanded" data-target="#education"><span>학력</span><i class="fas fa-minus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle expanded" data-target="#skills"><span>스킬</span><i class="fas fa-minus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle expanded" data-target="#experience"><span>경험/활동/교육</span><i class="fas fa-minus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle" data-target=""><span>자격/어학/수상</span><i class="fas fa-plus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle" data-target=""><span>취업우대사항</span><i class="fas fa-plus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle" data-target=""><span>포트폴리오 및 기타문서</span><i class="fas fa-plus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle" data-target=""><span>경력기술서</span><i class="fas fa-plus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle" data-target=""><span>자기소개서</span><i class="fas fa-plus"></i></button></li>
	                        <li><button type="button" class="sidebar-toggle" data-target=""><span>사람인 인·적성검사</span><i class="fas fa-plus"></i></button></li>
	                    </ul>
	                </section>
	            </aside>
	        </div>

	        <div class="resume-actions">
	            <button type="button" class="btn-outline">취소</button>
	            <button type="button" class="btn-primary submit-complete">작성완료</button>
	        </div>
	    </div>
    </form>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>
</body>
</html>
