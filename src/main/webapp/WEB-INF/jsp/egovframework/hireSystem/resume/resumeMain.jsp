<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>이력서 관리 | 최고의 인재를 만나다</title>
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/resumeMain.css' />">
    <!-- Font Awesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
                <span class="rep-guide">사람인처럼 최상단 고정 노출</span>
            </div>

            <article class="representative-card" id="representativeCard">
                <div class="rep-left">
                    <span class="rep-badge">대표</span>
                    <h3 id="repTitle">백엔드 개발자 이력서</h3>
                    <p id="repUpdated">마지막 수정: 2026.05.28</p>
                </div>
                <div class="rep-right">
                    <span><i class="fas fa-eye"></i> <span id="repViews">15회</span></span>
                </div>
            </article>
        </section>

        <section class="saved-resumes">
            <div class="section-header">
                <h2 class="section-title">내 이력서</h2>
                <div class="list-options">
                    <select class="sort-select" id="sortSelect">
                        <option value="recent">최신순</option>
                        <option value="name">이름순</option>
                        <option value="view">열람순</option>
                    </select>
                </div>
            </div>

            <div class="resume-grid">
                <article class="resume-card is-representative" data-resume-id="1" data-title="백엔드 개발자 이력서" data-updated="2026.05.28" data-views="15회">
                    <div class="card-header">
                        <span class="completion-status">작성중 85%</span>
                        <div class="more-wrap">
                            <button type="button" class="more-btn" aria-label="더보기">
                                <i class="fas fa-ellipsis-v"></i>
                            </button>
                            <div class="more-menu">
                                <button type="button" class="menu-item action-representative">대표이력서 설정</button>
                                <button type="button" class="menu-item action-pdf">PDF 다운로드</button>
                                <button type="button" class="menu-item action-copy">이력서 복사</button>
                                <button type="button" class="menu-item action-delete">이력서 삭제</button>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <h3>백엔드 개발자 이력서</h3>
                        <p class="update-date">마지막 수정: 2026.05.28</p>
                        <div class="progress-bar"><div class="progress" style="width: 85%"></div></div>
                    </div>
                    <div class="card-footer">
                        <span class="view-count"><i class="fas fa-eye"></i> 15회</span>
                    </div>
                </article>

                <article class="resume-card" data-resume-id="2" data-title="프론트엔드 포트폴리오 이력서" data-updated="2026.05.20" data-views="33회">
                    <div class="card-header">
                        <span class="completion-status">완성 100%</span>
                        <div class="more-wrap">
                            <button type="button" class="more-btn" aria-label="더보기">
                                <i class="fas fa-ellipsis-v"></i>
                            </button>
                            <div class="more-menu">
                                <button type="button" class="menu-item action-representative">대표이력서 설정</button>
                                <button type="button" class="menu-item action-pdf">PDF 다운로드</button>
                                <button type="button" class="menu-item action-copy">이력서 복사</button>
                                <button type="button" class="menu-item action-delete">이력서 삭제</button>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <h3>프론트엔드 포트폴리오 이력서</h3>
                        <p class="update-date">마지막 수정: 2026.05.20</p>
                        <div class="progress-bar"><div class="progress" style="width: 100%"></div></div>
                    </div>
                    <div class="card-footer">
                        <span class="view-count"><i class="fas fa-eye"></i> 33회</span>
                    </div>
                </article>
            </div>

            <div class="pagination">
                <button type="button" class="page-btn prev" disabled><i class="fas fa-chevron-left"></i></button>
                <div class="page-numbers">
                    <button type="button" class="page-btn active">1</button>
                    <button type="button" class="page-btn">2</button>
                    <button type="button" class="page-btn">3</button>
                    <button type="button" class="page-btn">4</button>
                </div>
                <button type="button" class="page-btn next"><i class="fas fa-chevron-right"></i></button>
            </div>
        </section>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>
    <script defer src="/js/hireSystem/resume/resumeMain.js"></script>
</body>
</html>