<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>일반채용정보 | 개발자 채용포털</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/jobs.css' />">
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>

    <main class="jobs-page">
        <section class="jobs-search-bar">
            <div class="search-container">
                <form id="searchForm"
                    action="<c:url value='/hireSystem/job/normalJob.do'/>"
                    method="get" class="search-form">
                    <input type="hidden" name="numOfRows" value="${empty numOfRows ? 10 : numOfRows}" />
                    <div class="search-input-group">
                        <input type="text" name="keyword" value="${keyword}"
                            placeholder="공고 제목/회사명으로 검색해보세요">
                        <button type="submit" class="search-btn">
                            <span class="search-icon">🔍</span>
                            검색
                        </button>
                    </div>
                </form>
            </div>
        </section>

        <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/job/jobSubTabs.jsp">
            <jsp:param name="activeTab" value="normal" />
        </jsp:include>

        <section class="jobs-filters">
            <div class="filter-sidebar">

                <div class="filter-section">
                    <h3 class="filter-title">진행여부</h3>
                    <div class="filter-options">
                        <label class="filter-option">
                            <input type="radio" name="ongoingYn" value="" ${empty ongoingYn ? 'checked' : ''}>
                            <span>전체</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="ongoingYn" value="Y" ${ongoingYn eq 'Y' ? 'checked' : ''}>
                            <span>진행중</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="ongoingYn" value="N" ${ongoingYn eq 'N' ? 'checked' : ''}>
                            <span>마감</span>
                        </label>
                    </div>
                </div>

                <div class="filter-section">
                    <h3 class="filter-title">고용형태</h3>
                    <div class="filter-options">
                        <label class="filter-option">
                            <input type="radio" name="hireType" value="" ${empty hireType ? 'checked' : ''}>
                            <span>전체</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="hireType" value="정규직" ${hireType eq '정규직' ? 'checked' : ''}>
                            <span>정규직</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="hireType" value="계약직" ${hireType eq '계약직' ? 'checked' : ''}>
                            <span>계약직</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="hireType" value="무기계약직" ${hireType eq '무기계약직' ? 'checked' : ''}>
                            <span>무기계약직</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="hireType" value="청년인턴" ${hireType eq '청년인턴' ? 'checked' : ''}>
                            <span>청년인턴</span>
                        </label>
                    </div>
                </div>

                <div class="filter-section">
                    <h3 class="filter-title">채용구분</h3>
                    <div class="filter-options">
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="" ${empty recrutSe ? 'checked' : ''}>
                            <span>전체</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="신입" ${recrutSe eq '신입' ? 'checked' : ''}>
                            <span>신입</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="경력" ${recrutSe eq '경력' ? 'checked' : ''}>
                            <span>경력</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="신입+경력" ${recrutSe eq '신입+경력' ? 'checked' : ''}>
                            <span>신입+경력</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="외국인전형" ${recrutSe eq '외국인전형' ? 'checked' : ''}>
                            <span>외국인 전형</span>
                        </label>
                    </div>
                </div>

                <button type="button" class="filter-apply-btn" onclick="applyNormalJobFilters()">필터 적용</button>
            </div>

            <div class="jobs-content">
                <div class="jobs-header">
                    <h2 class="jobs-title">일반채용정보 <span class="jobs-count">(${totalCount}건)</span></h2>
                </div>

                <c:choose>
                    <c:when test="${empty jobList}">
                        <p style="padding:40px 0;text-align:center;color:#98a2b3;">등록된 채용공고가 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="normaljob-grid">
                            <c:forEach var="job" items="${jobList}">
                                <article class="normaljob-card">
                                    <div class="card-company-row">
                                        <c:choose>
                                            <c:when test="${not empty job.companyLogoUrl}">
                                                <img src="${job.companyLogoUrl}" alt="${job.companyName} 로고" class="card-logo-img">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="card-logo-placeholder">
                                                    <c:out value="${fn:substring(job.companyName, 0, 1)}" />
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <span class="card-company"><c:out value="${job.companyName}" /></span>
                                    </div>

                                    <h3 class="card-title"><c:out value="${job.jobTitle}" /></h3>

                                    <c:if test="${not empty job.tags}">
                                        <div class="card-tags">
                                            <c:forEach var="tag" items="${fn:split(job.tags, ',')}">
                                                <span class="tag"><c:out value="${tag}" /></span>
                                            </c:forEach>
                                        </div>
                                    </c:if>

                                    <div class="card-location">📍 <c:out value="${job.workRegion}" /></div>

                                    <c:choose>
                                        <c:when test="${job.ongoingYn eq 'N'}">
                                            <span class="apply-btn is-closed">마감된 공고</span>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="<c:url value='/hireSystem/job/normalJobDetail.do'>
                                                        <c:param name='jobPostingId' value='${job.jobPostingId}'/>
                                                     </c:url>" class="apply-btn">지원하기</a>
                                        </c:otherwise>
                                    </c:choose>
                                </article>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>

                <section class="jobs-pagination-wrap">
                    <nav class="jobs-pagination" aria-label="페이지 번호">
                        <c:choose>
                            <c:when test="${blockStart le 1}">
                                <span class="page-arrow is-disabled">‹</span>
                            </c:when>
                            <c:otherwise>
                                <a href="javascript:void(0);" onclick="goNormalJobPage(${blockStart - 1})" class="page-arrow">‹</a>
                            </c:otherwise>
                        </c:choose>

                        <div class="pagination-nums">
                            <c:forEach var="i" begin="${blockStart}" end="${blockEnd}">
                                <a href="javascript:void(0);" onclick="goNormalJobPage(${i})"
                                   class="page-link ${currentPage eq i ? 'is-active' : ''}">
                                    ${i}
                                </a>
                            </c:forEach>
                        </div>

                        <c:choose>
                            <c:when test="${blockEnd ge totalPages}">
                                <span class="page-arrow is-disabled">›</span>
                            </c:when>
                            <c:otherwise>
                                <a href="javascript:void(0);" onclick="goNormalJobPage(${blockEnd + 1})" class="page-arrow">›</a>
                            </c:otherwise>
                        </c:choose>
                    </nav>
                </section>
            </div>
        </section>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>

    <script>
        // 필터 라디오 값 + 검색어를 모아서 normalJob.do로 이동 (page=1로 리셋)
        function buildNormalJobParams(page) {
            var form = document.getElementById('searchForm');
            var params = new URLSearchParams();
            params.set('keyword', form.keyword.value || '');
            params.set('numOfRows', form.numOfRows.value || 10);
            params.set('page', page || 1);

            var radioNames = ['ongoingYn', 'hireType', 'recrutSe'];
            radioNames.forEach(function (name) {
                var checked = document.querySelector('input[name="' + name + '"]:checked');
                params.set(name, checked ? checked.value : '');
            });
            return params;
        }

        function applyNormalJobFilters() {
            var params = buildNormalJobParams(1);
            location.href = '<c:url value="/hireSystem/job/normalJob.do"/>?' + params.toString();
        }

        function goNormalJobPage(page) {
            var params = buildNormalJobParams(page);
            location.href = '<c:url value="/hireSystem/job/normalJob.do"/>?' + params.toString();
        }
    </script>
</body>
</html>
