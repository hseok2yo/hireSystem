<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>채용포털 | 최고의 인재를 만나다</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 기본 폰트 및 외부 리소스 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css" />
    
    <!-- CSS 순서 지정 -->
<%--     <link rel="stylesheet" href="<c:url value='/css/hireSystem/header.css?v=1' />"> --%>
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/maingraph.css' />">
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/hireSystem.css' />">
    
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>
	<c:if test="${not empty msg}">
	    <script>
	        alert("${msg}");
	    </script>
	</c:if>
    <main class="hire-container">
        <section class="hero-section">
            <div class="hero-content">
                <div class="hero-text">
                    <h2 class="animate-text">당신의 꿈을 실현할<br>최고의 기회를 만나보세요</h2>
                    <p class="animate-text delay-1">지금 진행중인 채용공고를 확인해보세요</p>
                </div>
            </div>
            <div class="hero-stats animate-up delay-3">
                <div class="stat-item">
                    <div class="stat-icon">📊</div>
                    <span class="stat-number">15,000+</span>
                    <span class="stat-label">채용 공고</span>
                </div>
                <div class="stat-item">
                    <div class="stat-icon">🏢</div>
                    <span class="stat-number">2,500+</span>
                    <span class="stat-label">기업 파트너</span>
                </div>
                <div class="stat-item">
                    <div class="stat-icon">🎯</div>
                    <span class="stat-number">98%</span>
                    <span class="stat-label">취업 성공률</span>
                </div>
            </div>
        </section>

        <section class="featured-jobs">
            <div class="section-header">
                <div class="header-main">
                    <h2>주목할만한 채용공고</h2>
                    <p>실시간 업데이트되는 프리미엄 채용정보를 만나보세요</p>
                </div>
                <div class="header-actions">
                    <a href="<c:url value='/hireSystem/recruit/recruitList.do' />" class="view-all">전체보기</a>
                </div>
            </div>
            
            <div class="job-slider swiper">
                <c:choose>
                    <c:when test="${empty featuredJobs}">
                        <%-- API 조회 실패했거나 진행중인 공고가 없을 때: 빈 슬라이더 대신 안내 문구만 표시 --%>
                        <p style="padding:40px 0;text-align:center;color:#98a2b3;">현재 표시할 채용공고가 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="swiper-wrapper">
                            <c:forEach var="job" items="${featuredJobs}">
                                <div class="swiper-slide">
                                    <div class="premium-job-card">
                                        <div class="card-header">
                                            <img src="<c:url value='/images/company-logos/default-company.png' />" alt="${job.instNm}" class="company-logo">
                                            <div class="job-badge hot">D-<c:out value="${job.decimalDay}"/></div>
                                        </div>
                                        <div class="card-body">
                                            <h3><c:out value="${job.recrutPbancTtl}"/></h3>
                                            <h4><c:out value="${job.instNm}"/></h4>
                                            <div class="job-highlights">
                                                <span class="highlight"><c:out value="${job.recrutSeNm}"/></span>
                                                <span class="highlight">모집 <c:out value="${job.recrutNope}"/>명</span>
                                            </div>
                                            <div class="tech-stack">
                                                <span class="tech"><c:out value="${job.hireTypeNmLst}"/></span>
                                            </div>
                                        </div>
                                        <div class="card-footer">
                                            <div class="job-info">
                                                <span><c:out value="${job.workRgnNmLst}"/></span>
                                            </div>
                                            <c:choose>
                                                <c:when test="${not empty job.srcUrl && job.srcUrl ne '없음.'}">
                                                    <a href="${job.srcUrl}" target="_blank" rel="noopener noreferrer" class="apply-btn">바로지원</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="apply-btn" disabled>바로지원</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="swiper-pagination"></div>
                        <div class="swiper-button-prev"></div>
                        <div class="swiper-button-next"></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>

    </main>
	
	<jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>

    <script>
        // Swiper 초기화
        const swiper = new Swiper('.swiper', {
            slidesPerView: 'auto',
            spaceBetween: 30,
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
            breakpoints: {
                640: {
                    slidesPerView: 1,
                },
                768: {
                    slidesPerView: 2,
                },
                1024: {
                    slidesPerView: 3,
                },
            }
        });

        // 스크롤 애니메이션
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('visible');
                }
            });
        });

        document.querySelectorAll('.animate-up, .animate-text').forEach((el) => observer.observe(el));
    </script>

</body>
</html>
