<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>이력서 관리 | 최고의 인재를 만나다</title>
    <link rel="stylesheet" href="/css/hireSystem/resumeMain.css">
    <!-- Font Awesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <script src="/js/hireSystem/resume/resumeMain.js"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>

    <main class="resume-container">
        <div class="resume-topbar">
            <div>
                <h1>이력서 관리</h1>
                <p>대표 이력서를 설정하고 내 이력서를 관리해보세요.</p>
            </div>
            <button type="button" class="create-resume-btn">
                <i class="fas fa-plus"></i> 새 이력서
            </button>
        </div>

        <section class="representative-section">
            <div class="section-header">
                <h2 class="section-title">대표 이력서</h2>
                <span class="rep-guide"></span>
            </div>

            <article class="representative-card" id="representativeCard">
                <div class="rep-left">
                    <span class="rep-badge">대표</span>
                    <h3 id="repTitle">${mainResume.title }</h3>

                    <p id="repUpdated">마지막 수정: <fmt:formatDate value="${mainResume.updatedAt}" pattern="yyyy-MM-dd HH:mm"/></p>
                </div>
                <div class="rep-right">
                    <span><i class="fas fa-eye"></i> <span id="repViews"></span></span>
					<%-- 점3개 메뉴 --%>
					<div class="more-wrap">
						<button type="button" class="more-btn" aria-label="더보기">
							<i class="fas fa-ellipsis-v"></i>
						</button>
						<div class="more-menu">
							<button type="button" class="menu-item action-edit"
								data-num="${mainResume.resumeId}">이력서 수정</button>
							<button type="button" class="menu-item action-pdf"
								data-num="${mainResume.resumeId}">PDF 다운로드</button>
							<button type="button" class="menu-item action-copy"
								data-num="${mainResume.resumeId}">이력서 복사</button>
							<button type="button" class="menu-item action-delete"
								data-num="${mainResume.resumeId}">이력서 삭제</button>
						</div>
					</div>
				</div>
            </article>
        </section>

		<%-- 서브이력서 목록 --%>
        <section class="saved-resumes">
            <div class="section-header">
                <h2 class="section-title">내 이력서</h2>
                <div class="list-options">
					<select class="sort-select" id="sortSelect">
						<option value="recent"
							${param.searchSort == 'recent' ? 'selected' : ''}>최신순</option>

						<option value="name"
							${param.searchSort == 'name' ? 'selected' : ''}>이름순</option>
					</select>
				</div>
            </div>

            <div class="resume-grid">
                <c:choose>
                    <c:when test="${not empty subResume}">
                        <c:forEach var="resume" items="${subResume.list}">
                            <article class="resume-card" data-resume-num="${resume.resumeId}">
                                <div class="card-header">
                                    <span class="completion-status">%</span>
                                    <div class="more-wrap">
                                        <button type="button" class="more-btn" aria-label="더보기">
                                            <i class="fas fa-ellipsis-v"></i>
                                        </button>
                                        <div class="more-menu">
                                            <button type="button" class="menu-item action-representative" data-num="${resume.resumeId}">대표이력서 설정</button>
											<button type="button" class="menu-item action-edit" data-num="${resume.resumeId}">이력서 수정</button>
											<button type="button" class="menu-item action-pdf" data-num="${resume.resumeId}">PDF 다운로드</button>
                                            <button type="button" class="menu-item action-copy" data-num="${resume.resumeId}">이력서 복사</button>
                                            <button type="button" class="menu-item action-delete" data-num="${resume.resumeId}">이력서 삭제</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <h3>${resume.title}</h3>
                                    <p class="update-date">마지막 수정: <fmt:formatDate value="${resume.updatedAt}" pattern="yyyy-MM-dd"/></p>
                                    <div class="progress-bar">
                                        <div class="progress" style="width: %"></div>
                                    </div>
                                </div>
                                <div class="card-footer">
                                    <span class="view-count"><i class="fas fa-eye"></i>  회</span>
                                </div>
                            </article>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-list">
                            <p>등록된 이력서가 없습니다.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

			<form id="searchForm" action="/hireSystem/resume/resumeMain.do">
				<input id="page" type="hidden" name="page">
				<input id="searchSort" type="hidden" name="searchSort" value="${param.searchSort }">
			</form>
			<div class="pagination">
				<c:if test="${subResume.currentPage > 1}">
					<a href="#" onclick="goPage(1); return false;">◁◁</a>
				</c:if>
				<c:if test="${subResume.startPage > 1}">
					<a href="#"
						onclick="goPage(${subResume.startPage-1}); return false;">◁</a>
				</c:if>

				<c:forEach begin="${subResume.startPage}" end="${subResume.endPage}"
					var="i">
					<c:choose>
						<c:when test="${i == subResume.currentPage}">
							<span class="current-page">${i}</span>
						</c:when>
						<c:otherwise>
							<a href="#" onclick="goPage(${i}); return false;">${i}</a>
						</c:otherwise>
					</c:choose>

				</c:forEach>

				<c:if test="${subResume.endPage < subResume.totalPage}">
					<a href="#"
						onclick="goPage(${subResume.endPage + 1}); return false;">▶</a>
				</c:if>

				<c:if test="${subResume.currentPage < subResume.totalPage}">
					<a href="#" onclick="goPage(${subResume.totalPage}); return false;">▶▶</a>
				</c:if>

			</div>
		</section>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>

</body>
</html>