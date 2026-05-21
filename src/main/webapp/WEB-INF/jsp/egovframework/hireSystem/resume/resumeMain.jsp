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
        <!-- 이력서 헤더 섹션 -->
        <section class="resume-header">
            <h1>이력서 관리</h1>
            <p>원하는 포지션에 지원하기 위한 이력서를 관리해보세요.</p>
            <div class="resume-actions">
                <button class="create-resume-btn">
                    <i class="fas fa-plus"></i> 새 이력서 작성
                </button>
            </div>
        </section>

        <!-- 대표 이력서 섹션 -->
        <section class="main-resume">
            <h2 class="section-title">대표 이력서</h2>
            <div class="main-resume-card">
                <div class="resume-preview">
                    <div class="preview-header">
                        <img src="<c:url value='/images/default-profile.png' />" alt="프로필 이미지" class="profile-image">
                        <div class="preview-info">
                            <h3>홍길동</h3>
                            <p class="job-title">프론트엔드 개발자</p>
                            <p class="career">경력 3년</p>
                        </div>
                        <div class="preview-actions">
                            <button class="edit-btn"><i class="fas fa-pen"></i> 수정</button>
                            <button class="preview-btn"><i class="fas fa-eye"></i> 미리보기</button>
                        </div>
                    </div>
                    <div class="preview-body">
                        <div class="status-info">
                            <div class="status-item">
                                <span class="label">작성완료</span>
                                <span class="value complete">100%</span>
                            </div>
                            <div class="status-item">
                                <span class="label">최종 수정일</span>
                                <span class="value">2024.03.21</span>
                            </div>
                            <div class="status-item">
                                <span class="label">열람수</span>
                                <span class="value">38회</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- 저장된 이력서 목록 섹션 -->
        <section class="saved-resumes">
            <div class="section-header">
                <h2 class="section-title">저장된 이력서</h2>
                <div class="list-options">
                    <select class="sort-select">
                        <option value="recent">최신순</option>
                        <option value="name">이름순</option>
                        <option value="view">열람순</option>
                    </select>
                </div>
            </div>
            
            <div class="resume-grid">
                <!-- 이력서 카드 반복 -->
                <div class="resume-card">
                    <div class="card-header">
                        <span class="completion-status">작성중 85%</span>
                        <button class="more-btn"><i class="fas fa-ellipsis-v"></i></button>
                    </div>
                    <div class="card-body">
                        <h3>백엔드 개발자 이력서</h3>
                        <p class="update-date">마지막 수정: 2024.03.20</p>
                        <div class="progress-bar">
                            <div class="progress" style="width: 85%"></div>
                        </div>
                    </div>
                    <div class="card-footer">
                        <span class="view-count"><i class="fas fa-eye"></i> 15회</span>
                        <div class="card-actions">
                            <button class="action-btn edit"><i class="fas fa-pen"></i></button>
                            <button class="action-btn copy"><i class="fas fa-copy"></i></button>
                            <button class="action-btn delete"><i class="fas fa-trash"></i></button>
                        </div>
                    </div>
                </div>
                
                <!-- 추가 이력서 카드들... -->
            </div>

            <!-- 페이지네이션 -->
            <div class="pagination">
                <button class="page-btn prev" disabled><i class="fas fa-chevron-left"></i></button>
                <div class="page-numbers">
                    <button class="page-btn active">1</button>
                    <button class="page-btn">2</button>
                    <button class="page-btn">3</button>
                    <button class="page-btn">4</button>
                    <button class="page-btn">5</button>
                </div>
                <button class="page-btn next"><i class="fas fa-chevron-right"></i></button>
            </div>
        </section>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>

    <script>
        // 정렬 옵션 변경 이벤트
        document.querySelector('.sort-select').addEventListener('change', function(e) {
            // 정렬 로직 구현
            console.log('Sort by:', e.target.value);
        });

        // 페이지네이션 이벤트
        document.querySelectorAll('.page-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                if (!this.classList.contains('prev') && !this.classList.contains('next')) {
                    document.querySelector('.page-btn.active').classList.remove('active');
                    this.classList.add('active');
                }
            });
        });
    </script>
</body>
</html>