<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>채용공시 | 청년 일자리 올인원</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/jobs.css' />">
    <script src="/js/hireSystem/recruit/recruitList.js"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>

    <main class="jobs-page">
        <section class="jobs-search-bar">
            <div class="search-container">
                <form id="searchForm"
                    action="<c:url value='/hireSystem/recruit/recruitList.do'/>"
                    method="get" class="search-form">
                    <input type="hidden" name="numOfRows" value="${empty numOfRows ? 10 : numOfRows}" />
                    <div class="search-input-group">
                        <input type="text" name="recrutPbancTtl" value="${recrutPbancTtl}"
                            placeholder="공고 제목으로 검색해보세요 (예: 보훈)">
                        <button type="submit" class="search-btn">
                            <span class="search-icon">🔍</span>
                            검색
                        </button>
                    </div>
                </form>
            </div>
        </section>

        <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/job/jobSubTabs.jsp">
            <jsp:param name="activeTab" value="public" />
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

                <%-- 고용형태 코드: 코드정의서 3.1 참조 (R1010 정규직 등) --%>
                <div class="filter-section">
                    <h3 class="filter-title">고용형태</h3>
                    <div class="filter-options">
                        <label class="filter-option">
                            <input type="radio" name="hireTypeLst" value="" ${empty hireTypeLst ? 'checked' : ''}>
                            <span>전체</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="hireTypeLst" value="R1010" ${hireTypeLst eq 'R1010' ? 'checked' : ''}>
                            <span>정규직</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="hireTypeLst" value="R1020" ${hireTypeLst eq 'R1020' ? 'checked' : ''}>
                            <span>계약직</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="hireTypeLst" value="R1030" ${hireTypeLst eq 'R1030' ? 'checked' : ''}>
                            <span>무기계약직</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="hireTypeLst" value="R1050" ${hireTypeLst eq 'R1050' ? 'checked' : ''}>
                            <span>청년인턴</span>
                        </label>
                    </div>
                </div>

                <%-- 채용구분 코드: 코드정의서 3.1 참조 --%>
                <div class="filter-section">
                    <h3 class="filter-title">채용구분</h3>
                    <div class="filter-options">
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="" ${empty recrutSe ? 'checked' : ''}>
                            <span>전체</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="R2010" ${recrutSe eq 'R2010' ? 'checked' : ''}>
                            <span>신입</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="R2020" ${recrutSe eq 'R2020' ? 'checked' : ''}>
                            <span>경력</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="R2030" ${recrutSe eq 'R2030' ? 'checked' : ''}>
                            <span>신입+경력</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="recrutSe" value="R2040" ${recrutSe eq 'R2040' ? 'checked' : ''}>
                            <span>외국인 전형</span>
                        </label>
                    </div>
                </div>

                <%-- 근무지 코드: 코드정의서 3.1 참조 --%>
                <div class="filter-section">
                    <h3 class="filter-title">근무지</h3>
                    <div class="filter-options">
                        <label class="filter-option">
                            <input type="radio" name="workRgnLst" value="" ${empty workRgnLst ? 'checked' : ''}>
                            <span>전체</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="workRgnLst" value="R3010" ${workRgnLst eq 'R3010' ? 'checked' : ''}>
                            <span>서울</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="workRgnLst" value="R3017" ${workRgnLst eq 'R3017' ? 'checked' : ''}>
                            <span>경기</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="workRgnLst" value="R3011" ${workRgnLst eq 'R3011' ? 'checked' : ''}>
                            <span>인천</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="workRgnLst" value="R3012" ${workRgnLst eq 'R3012' ? 'checked' : ''}>
                            <span>대전</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="workRgnLst" value="R3014" ${workRgnLst eq 'R3014' ? 'checked' : ''}>
                            <span>부산</span>
                        </label>
                        <label class="filter-option">
                            <input type="radio" name="workRgnLst" value="R3013" ${workRgnLst eq 'R3013' ? 'checked' : ''}>
                            <span>대구</span>
                        </label>
                    </div>
                </div>

                <button type="button" class="filter-apply-btn" onclick="applyFilters()">필터 적용</button>
            </div>

        <div class="jobs-content">
            <div class="jobs-header">
                <h2 class="jobs-title">채용공시 <span class="jobs-count">(${totalCount}건)</span></h2>
            </div>

            <div class="jobs-list">
                <c:choose>
                    <c:when test="${empty recruitList}">
                        <p style="padding:40px 0;text-align:center;color:#98a2b3;">조회된 채용공시가 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="item" items="${recruitList}">
                            <article class="job-item">
                                <div class="job-item-header">
                                    <div class="job-main-info">
                                        <h3 class="job-position">
                                            <c:choose>
                                                <c:when test="${not empty item.srcUrl && item.srcUrl ne '없음.'}">
                                                    <a href="${item.srcUrl}" target="_blank" rel="noopener noreferrer">
                                                        <c:out value="${item.recrutPbancTtl}"/>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${item.recrutPbancTtl}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </h3>
                                        <div class="company-name-row">
                                            <span class="company-name"><c:out value="${item.instNm}"/></span>
                                            <span class="company-category">
                                                <c:choose>
                                                    <c:when test="${item.ongoingYn eq 'Y'}">
                                                        D-<c:out value="${item.decimalDay}"/>
                                                    </c:when>
                                                    <c:otherwise>마감</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="job-meta">
                                        <span class="meta-item location"><c:out value="${item.workRgnNmLst}"/></span>
                                        <span class="meta-item career"><c:out value="${item.recrutSeNm}"/></span>
                                        <span class="meta-item salary"><c:out value="${item.hireTypeNmLst}"/></span>
                                    </div>
                                </div>
                                <div class="job-item-body">
                                    <div class="job-tags">
                                        <c:if test="${not empty item.ncsCdNmLst}">
                                            <c:forEach var="ncs" items="${fn:split(item.ncsCdNmLst, ',')}">
                                                <span class="tag"><c:out value="${ncs}"/></span>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${not empty item.acbgCondNmLst}">
                                            <span class="tag"><c:out value="${item.acbgCondNmLst}"/></span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="job-item-footer">
                                    <span class="reg-date">
                                        모집인원 <c:out value="${item.recrutNope}"/>명 ·
                                        공고기간
                                        <c:out value="${fn:substring(item.pbancBgngYmd,0,4)}"/>-<c:out value="${fn:substring(item.pbancBgngYmd,4,6)}"/>-<c:out value="${fn:substring(item.pbancBgngYmd,6,8)}"/>
                                        ~
                                        <c:out value="${fn:substring(item.pbancEndYmd,0,4)}"/>-<c:out value="${fn:substring(item.pbancEndYmd,4,6)}"/>-<c:out value="${fn:substring(item.pbancEndYmd,6,8)}"/>
                                    </span>
                                </div>
                            </article>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>

            <%--
                [페이징 리팩토링]
                기존에는 이전/각 페이지번호/다음 링크 3곳마다 c:param으로 검색조건
                (recrutPbancTtl, hireTypeLst, workRgnLst, recrutSe, ongoingYn)을 일일이 나열했음.
                -> 검색조건이 하나 추가될 때마다 3곳을 전부 고쳐야 하고, 하나라도 빠뜨리면
                   페이지 이동 시 그 조건만 조용히 사라지는 버그가 생김 (실제로 numOfRows가 여기서 누락돼있었음).

                지금은 href 대신 goPage(n)만 호출하고, 실제 이동 URL은 JS의 buildParams()가
                "현재 #searchForm에 있는 값 + 필터 라디오 체크값"을 그때그때 긁어서 만들어줌.
                -> 자바/VO/컨트롤러 변경 없이, 검색조건이 늘어나도 페이징 쪽 JSP/JS는 손댈 필요 없음.
            --%>
            <section class="jobs-pagination-wrap">
                <nav class="jobs-pagination" aria-label="페이지 번호">
                    <c:choose>
                        <c:when test="${blockStart le 1}">
                            <span class="page-arrow is-disabled">‹</span>
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:void(0);" onclick="goPage(${blockStart - 1})" class="page-arrow">‹</a>
                        </c:otherwise>
                    </c:choose>

                    <div class="pagination-nums">
                        <c:forEach var="i" begin="${blockStart}" end="${blockEnd}">
                            <a href="javascript:void(0);" onclick="goPage(${i})"
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
                            <a href="javascript:void(0);" onclick="goPage(${blockEnd + 1})" class="page-arrow">›</a>
                        </c:otherwise>
                    </c:choose>
                </nav>
            </section>
        </div>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>

    <%-- 검색/필터/페이징 스크립트는 /js/hireSystem/recruit/recruitList.js 로 분리 (head에서 로드) --%>
</body>
</html>
