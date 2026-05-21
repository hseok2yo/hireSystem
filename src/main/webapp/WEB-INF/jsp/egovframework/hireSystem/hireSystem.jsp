<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>채용포털 | 최고의 인재를 만나다</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 기본 폰트 및 외부 리소스 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/@lottiefiles/lottie-player@latest/dist/lottie-player.js"></script>
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
                    <p class="animate-text delay-1">AI 매칭 시스템으로 나에게 딱 맞는 기업을 찾아보세요</p>
                </div>
                <div class="search-container animate-up delay-2">
                    <div class="search-box">
                        <form action="search.jsp" method="get" class="advanced-search">
                            <div class="search-row">
                                <div class="search-group">
                                    <label>직무</label>
                                    <input type="text" name="position" placeholder="직무 또는 직종">
                                </div>
                                <div class="search-group">
                                    <label>지역</label>
                                    <input type="text" name="location" placeholder="근무지역">
                                </div>
                                <div class="search-group">
                                    <label>연봉</label>
                                    <select name="salary">
                                        <option value="">연봉대 선택</option>
                                        <option value="2000">2,000만원 이상</option>
                                        <option value="3000">3,000만원 이상</option>
                                        <option value="4000">4,000만원 이상</option>
                                        <option value="5000">5,000만원 이상</option>
                                    </select>
                                </div>
                                <button type="submit" class="search-button">맞춤 공고 찾기</button>
                            </div>
                        </form>
                        <div class="trending-keywords">
                            <span class="trend-label">트렌드 키워드</span>
                            <div class="keyword-chips">
                                <a href="#" class="chip">데이터 사이언티스트</a>
                                <a href="#" class="chip">프론트엔드</a>
                                <a href="#" class="chip">AI 엔지니어</a>
                                <a href="#" class="chip">DevOps</a>
                            </div>
                        </div>
                    </div>
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
                    <div class="category-tabs">
                        <button class="tab active">전체</button>
                        <button class="tab">개발</button>
                        <button class="tab">디자인</button>
                        <button class="tab">마케팅</button>
                    </div>
                    <a href="jobs.jsp" class="view-all">전체보기</a>
                </div>
            </div>
            
            <div class="job-slider swiper">
                <div class="swiper-wrapper">
                    <div class="swiper-slide">
                        <div class="premium-job-card">
                            <div class="card-header">
                                <img src="<c:url value='/images/company-logos/default-company.png' />" alt="네이버" class="company-logo">
                                <div class="job-badge hot">인기</div>
                            </div>
                            <div class="card-body">
                                <h3>시니어 백엔드 개발자</h3>
                                <h4>네이버</h4>
                                <div class="job-highlights">
                                    <span class="highlight">연봉 7,000~9,000만원</span>
                                    <span class="highlight">재택근무</span>
                                    <span class="highlight">스톡옵션</span>
                                </div>
                                <div class="tech-stack">
                                    <span class="tech">Java</span>
                                    <span class="tech">Spring</span>
                                    <span class="tech">MSA</span>
                                    <span class="tech">AWS</span>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="job-info">
                                    <span>서울 강남구</span>
                                    <span>경력 5년↑</span>
                                </div>
                                <button class="apply-btn">바로지원</button>
                            </div>
                        </div>
                    </div>
                    
                    <div class="swiper-slide">
                        <div class="premium-job-card">
                            <div class="card-header">
                                <img src="<c:url value='/images/company-logos/default-company.png' />" alt="카카오" class="company-logo">
                                <div class="job-badge new">신규</div>
                            </div>
                            <div class="card-body">
                                <h3>프로덕트 디자이너</h3>
                                <h4>카카오</h4>
                                <div class="job-highlights">
                                    <span class="highlight">연봉 6,000~8,000만원</span>
                                    <span class="highlight">유연근무</span>
                                    <span class="highlight">퇴직연금</span>
                                </div>
                                <div class="tech-stack">
                                    <span class="tech">Figma</span>
                                    <span class="tech">UI/UX</span>
                                    <span class="tech">Design System</span>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="job-info">
                                    <span>성남 분당구</span>
                                    <span>경력 3년↑</span>
                                </div>
                                <button class="apply-btn">바로지원</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="swiper-pagination"></div>
                <div class="swiper-button-prev"></div>
                <div class="swiper-button-next"></div>
            </div>
        </section>

        <section class="ai-matching">
            <div class="section-content">
                <div class="ai-text">
                    <h2>AI 매칭 시스템</h2>
                    <p>당신의 경력과 스킬을 분석하여<br>최적의 기업을 추천해드립니다</p>
                    <a href="ai-match.jsp" class="cta-button">
                        AI 매칭 시작하기
                        <span class="arrow">→</span>
                    </a>
                </div>
                <div class="ai-visual">
<!--                     <lottie-player  -->
<!--                         src="https://assets2.lottiefiles.com/packages/lf20_xyadoh9h.json" -->
<!--                         background="transparent" -->
<!--                         speed="1" -->
<!--                         style="width: 400px; height: 400px;" -->
<!--                         loop -->
<!--                         autoplay> -->
<!--                     </lottie-player> -->
                </div>
            </div>
        </section>

        <section class="top-companies">
            <div class="section-header">
                <div class="header-main">
                    <h2>트렌딩 기업</h2>
                    <p>실시간으로 가장 주목받는 기업들을 만나보세요</p>
                </div>
            </div>
            <div class="company-grid">
                <div class="premium-company-card">
                    <div class="company-banner">
                        <img src="<c:url value='/images/company-banners/default-banner.jpg' />" alt="회사 배너">
                    </div>
                    <div class="company-info">
                        <img src="<c:url value='/images/company-logos/default-company.png' />" alt="삼성전자" class="company-logo">
                        <h3>삼성전자</h3>
                        <p class="company-brief">세계를 선도하는 IT 기업</p>
                        <div class="company-stats">
                            <div class="stat">
                                <span class="label">평균연봉</span>
                                <span class="value">6,500만원</span>
                            </div>
                            <div class="stat">
                                <span class="label">채용중</span>
                                <span class="value">25개</span>
                            </div>
                            <div class="stat">
                                <span class="label">기업리뷰</span>
                                <span class="value">4.5/5</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="premium-company-card">
                    <div class="company-banner">
                        <img src="<c:url value='/images/company-banners/default-banner.jpg' />" alt="회사 배너">
                    </div>
                    <div class="company-info">
                        <img src="<c:url value='/images/company-logos/default-company.png' />" alt="네이버" class="company-logo">
                        <h3>네이버</h3>
                        <p class="company-brief">대한민국 대표 IT 플랫폼</p>
                        <div class="company-stats">
                            <div class="stat">
                                <span class="label">평균연봉</span>
                                <span class="value">7,000만원</span>
                            </div>
                            <div class="stat">
                                <span class="label">채용중</span>
                                <span class="value">18개</span>
                            </div>
                            <div class="stat">
                                <span class="label">기업리뷰</span>
                                <span class="value">4.7/5</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="career-insights">
            <div class="section-header">
                <div class="header-main">
                    <h2>커리어 인사이트</h2>
                    <p>업계 전문가들의 인사이트를 만나보세요</p>
                </div>
            </div>
            <div class="insights-grid">
                <article class="insight-card">
                    <div class="insight-image">
                        <img src="<c:url value='/images/insights/default-insight.jpg' />" alt="커리어 인사이트">
                    </div>
                    <div class="insight-content">
                        <span class="category">트렌드</span>
                        <h3>2024년 개발자 채용 트렌드</h3>
                        <p>AI, 빅데이터 시대의 개발자 역량과 전망</p>
                        <div class="author">
                            <img src="<c:url value='/images/authors/default-author.jpg' />" alt="저자">
                            <span>김전문 대표</span>
                        </div>
                    </div>
                </article>

                <article class="insight-card">
                    <div class="insight-image">
                        <img src="<c:url value='/images/insights/default-insight.jpg' />" alt="커리어 인사이트">
                    </div>
                    <div class="insight-content">
                        <span class="category">인터뷰</span>
                        <h3>실리콘밸리 현직자 인터뷰</h3>
                        <p>글로벌 테크 기업의 채용 프로세스</p>
                        <div class="author">
                            <img src="<c:url value='/images/authors/default-author.jpg' />" alt="저자">
                            <span>이글로벌 님</span>
                        </div>
                    </div>
                </article>
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
