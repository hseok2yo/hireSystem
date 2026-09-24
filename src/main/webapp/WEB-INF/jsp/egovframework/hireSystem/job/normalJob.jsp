<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>일반채용정보 | 개발자 채용포털</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/jobs.css' />">
    <script src="<c:url value='/js/hireSystem/recruit/normalJob.js' />"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>

    <main class="jobs-page">
        <section class="jobs-search-bar">
            <div class="search-container">
                <form id="searchForm"
                    action="<c:url value='/hireSystem/job/normalJob.do'/>"
                    method="get" class="search-form">
                    <div class="search-input-group">
                        <input type="text" name="keyword" value="${keyword}"
                            placeholder="공고 제목/회사명으로 검색해보세요">
                        <button type="button" id="detailToggleBtn" class="detail-toggle-btn" onclick="toggleDetailFilter()">
                            <span class="plus-icon">+</span>
                            <span>상세조건</span>
                        </button>
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

        <section class="detail-filter-panel" id="detailFilterPanel">
            <div class="detail-filter-inner">

                <div class="filter-row">
                    <span class="filter-row-label">진행여부</span>
                    <div class="filter-row-options">
                        <label class="filter-chip">
                            <input type="radio" name="ongoingYn" value="" ${empty ongoingYn ? 'checked' : ''}>
                            <span>전체</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="ongoingYn" value="Y" ${ongoingYn eq 'Y' ? 'checked' : ''}>
                            <span>진행중</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="ongoingYn" value="N" ${ongoingYn eq 'N' ? 'checked' : ''}>
                            <span>마감</span>
                        </label>
                    </div>
                </div>

                <div class="filter-row">
                    <span class="filter-row-label">고용형태</span>
                    <div class="filter-row-options">
                        <label class="filter-chip">
                            <input type="radio" name="hireType" value="" ${empty hireType ? 'checked' : ''}>
                            <span>전체</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="hireType" value="정규직" ${hireType eq '정규직' ? 'checked' : ''}>
                            <span>정규직</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="hireType" value="계약직" ${hireType eq '계약직' ? 'checked' : ''}>
                            <span>계약직</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="hireType" value="무기계약직" ${hireType eq '무기계약직' ? 'checked' : ''}>
                            <span>무기계약직</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="hireType" value="청년인턴" ${hireType eq '청년인턴' ? 'checked' : ''}>
                            <span>청년인턴</span>
                        </label>
                    </div>
                </div>

                <div class="filter-row">
                    <span class="filter-row-label">채용구분</span>
                    <div class="filter-row-options">
                        <label class="filter-chip">
                            <input type="radio" name="recrutSe" value="" ${empty recrutSe ? 'checked' : ''}>
                            <span>전체</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="recrutSe" value="신입" ${recrutSe eq '신입' ? 'checked' : ''}>
                            <span>신입</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="recrutSe" value="경력" ${recrutSe eq '경력' ? 'checked' : ''}>
                            <span>경력</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="recrutSe" value="신입+경력" ${recrutSe eq '신입+경력' ? 'checked' : ''}>
                            <span>신입+경력</span>
                        </label>
                        <label class="filter-chip">
                            <input type="radio" name="recrutSe" value="외국인전형" ${recrutSe eq '외국인전형' ? 'checked' : ''}>
                            <span>외국인 전형</span>
                        </label>
                    </div>
                </div>

                <div class="filter-row-actions">
                    <button type="button" class="filter-apply-btn" onclick="applyNormalJobFilters()">필터 적용</button>
                    <button type="button" class="filter-close-btn" onclick="toggleDetailFilter()">닫기</button>
                </div>
            </div>
        </section>

        <section class="jobs-filters">
            <div class="jobs-content">
                <div class="jobs-header">
                    <h2 class="jobs-title">일반채용정보 <span class="jobs-count">(${totalCount}건)</span></h2>
                </div>

                <%-- 선택된 필터 칩 표시 (사람인 스타일) - 필터가 하나도 없으면 아예 렌더링 안 함 --%>
                <c:if test="${not empty keyword or not empty ongoingYn or not empty hireType or not empty recrutSe}">
                    <div class="selected-filters-bar" id="selectedFiltersBar">
                        <div id="filterChips">
                            <c:if test="${not empty keyword}">
                                <span class="filter-tag" data-param="keyword">
                                    "<c:out value="${keyword}"/>"
                                    <button type="button" onclick="removeFilter('keyword')">×</button>
                                </span>
                            </c:if>
                            <c:if test="${not empty ongoingYn}">
                                <span class="filter-tag" data-param="ongoingYn">
                                    <c:choose>
                                        <c:when test="${ongoingYn eq 'Y'}">진행중</c:when>
                                        <c:otherwise>마감</c:otherwise>
                                    </c:choose>
                                    <button type="button" onclick="removeFilter('ongoingYn')">×</button>
                                </span>
                            </c:if>
                            <c:if test="${not empty hireType}">
                                <span class="filter-tag" data-param="hireType">
                                    <c:out value="${hireType}"/>
                                    <button type="button" onclick="removeFilter('hireType')">×</button>
                                </span>
                            </c:if>
                            <c:if test="${not empty recrutSe}">
                                <span class="filter-tag" data-param="recrutSe">
                                    <c:out value="${recrutSe}"/>
                                    <button type="button" onclick="removeFilter('recrutSe')">×</button>
                                </span>
                            </c:if>
                            <button type="button" class="filter-reset-btn" onclick="resetFilters()">
                                <span class="reset-icon">↻</span> 선택초기화
                            </button>
                        </div>
                    </div>
                </c:if>

                <%-- 공고 리스트 --%>
                <div class="jobs-list">
                    <c:choose>
                        <c:when test="${empty jobList}">
                            <p style="padding:40px 0;text-align:center;color:#98a2b3;">조회된 채용공고가 없습니다.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="job" items="${jobList}">
                                <article class="job-item">
                                    <div class="job-item-header">
                                        <div class="job-main-info">
                                            <h3 class="job-position">
                                                <a href="<c:url value='/hireSystem/job/normalJobDetail.do'/>?jobPostingId=${job.jobPostingId}">
                                                    <c:out value="${job.jobTitle}"/>
                                                </a>
                                            </h3>
                                            <div class="company-name-row">
                                                <span class="company-name"><c:out value="${job.companyName}"/></span>
                                                <span class="company-category">
                                                    <c:choose>
                                                        <c:when test="${job.ongoingYn eq 'Y'}">진행중</c:when>
                                                        <c:otherwise>마감</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="job-meta">
                                            <span class="meta-item location"><c:out value="${job.workRegion}"/></span>
                                            <span class="meta-item career"><c:out value="${job.recrutSe}"/></span>
                                            <span class="meta-item salary"><c:out value="${job.hireType}"/></span>
                                        </div>
                                    </div>
                                    <div class="job-item-body">
                                        <div class="job-tags">
                                            <c:if test="${not empty job.tags}">
                                                <c:forEach var="tag" items="${fn:split(job.tags, ',')}">
                                                    <span class="tag"><c:out value="${fn:trim(tag)}"/></span>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${not empty job.eduLevel}">
                                                <span class="tag"><c:out value="${job.eduLevel}"/></span>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="job-item-footer">
                                        <span class="reg-date">
                                            모집인원 <c:out value="${job.recruitCnt}"/> ·
                                            마감일 <fmt:formatDate value="${job.deadlineDt}" pattern="yyyy-MM-dd"/>
                                        </span>
                                    </div>
                                </article>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>

                <%-- 페이징 --%>
				<section class="jobs-pagination-wrap">
					<nav class="jobs-pagination" aria-label="페이지 번호">
						<c:choose>
							<c:when test="${startPage le 1}">
								<span class="page-arrow is-disabled">‹</span>
							</c:when>
							<c:otherwise>
								<a href="javascript:void(0);"
									onclick="goNormalJobPage(${startPage - 1})" class="page-arrow">‹</a>
							</c:otherwise>
						</c:choose>

						<div class="pagination-nums">
							<c:forEach var="i" begin="${startPage}" end="${endPage}">
								<a href="javascript:void(0);" onclick="goNormalJobPage(${i})"
									class="page-link ${currentPage eq i ? 'is-active' : ''}">
									${i} </a>
							</c:forEach>
						</div>

						<c:choose>
							<c:when test="${endPage ge totalPage}">
								<span class="page-arrow is-disabled">›</span>
							</c:when>
							<c:otherwise>
								<a href="javascript:void(0);"
									onclick="goNormalJobPage(${endPage + 1})" class="page-arrow">›</a>
							</c:otherwise>
						</c:choose>
					</nav>
				</section>
			</div>
        </section>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>
</body>
</html>
