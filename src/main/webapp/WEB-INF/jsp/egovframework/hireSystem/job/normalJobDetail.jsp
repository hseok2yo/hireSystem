<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${detailList.jobTitle}" default="채용공고"/> | 개발자 채용포털</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/jobs.css' />">
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/applyModal.css' />">
    <script src="<c:url value='/js/hireSystem/recruit/jobApply.js' />"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>

    <main class="jobs-page">
        <div class="jobs-content" style="max-width:900px;margin:32px auto;">
            <c:choose>
                <c:when test="${empty detailList}">
                    <p style="padding:60px 0;text-align:center;color:#98a2b3;">존재하지 않는 공고입니다.</p>
                </c:when>
                <c:otherwise>
                    <div class="normaljob-card" style="max-width:100%;">
                        <div class="card-company-row">
                            <c:choose>
                                <c:when test="${not empty detailList.companyLogoUrl}">
                                    <img src="${detailList.companyLogoUrl}" alt="${detailList.companyName} 로고" class="card-logo-img">
                                </c:when>
                                <c:otherwise>
                                    <div class="card-logo-placeholder">
                                        <c:out value="${fn:substring(detailList.companyName, 0, 1)}" />
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <span class="card-company"><c:out value="${detailList.companyName}" /></span>
                        </div>

                        <h2 style="margin:16px 0 8px;"><c:out value="${detailList.jobTitle}" /></h2>

                        <c:if test="${not empty detailList.tags}">
                            <div class="card-tags">
                                <c:forEach var="tag" items="${fn:split(detailList.tags, ',')}">
                                    <span class="tag"><c:out value="${tag}" /></span>
                                </c:forEach>
                            </div>
                        </c:if>

                        <p style="margin:12px 0;color:#475467;">
                            📍 <c:out value="${detailList.workRegion}" />
                            &nbsp;·&nbsp; <c:out value="${detailList.hireType}" />
                            &nbsp;·&nbsp; <c:out value="${detailList.recrutSe}" />
                            &nbsp;·&nbsp; 마감일 <c:out value="${detailList.deadlineDt}" />
                        </p>

                        <c:if test="${not empty detailList.jobDesc}">
                            <div style="white-space:pre-line;line-height:1.7;margin:20px 0;">
                                <c:out value="${detailList.jobDesc}" />
                            </div>
                        </c:if>

                        <c:choose>
                            <c:when test="${detailList.ongoingYn eq 'N'}">
                                <span class="apply-btn is-closed" style="max-width:240px;">마감된 공고</span>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="apply-btn" style="max-width:240px;border:none;cursor:pointer;"
                                    onclick="applyToJob(${detailList.jobPostingId})">
                                    지원하기
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <%-- 지원하기 모달 (위 버튼 클릭 시 jobApply.js가 열어줌) --%>
                    <jsp:include page="applyModal.jsp" />
                </c:otherwise>
            </c:choose>
        </div>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>


</body>
</html>
