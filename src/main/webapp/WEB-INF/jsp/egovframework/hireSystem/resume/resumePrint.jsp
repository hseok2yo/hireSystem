<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${resume.title} - 이력서</title>
    <link rel="stylesheet" href="/css/hireSystem/resumePrint.css">
</head>
<body>

    <div class="print-toolbar">
        <button type="button" id="pdfDownloadBtn">PDF 다운로드</button>
    </div>

    <div class="resume-print-page">

        <!-- 기본정보 -->
        <section class="p-basic">
            <c:if test="${not empty userInfo.userPhotoUrl}">
                <img class="p-photo" src="${userInfo.userPhotoUrl}" alt="프로필 사진">
            </c:if>
            <div class="p-basic-info">
                <h1>${userInfo.userNm}</h1>
                <p>${fn:substring(userInfo.birthDate, 0, 4)}년(${userInfo.age}세) · ${userInfo.gender eq 'M' ? '남' : '여'}</p>
                <p>${userInfo.userPhone} | ${userInfo.userEmail}</p>
                <p>${userInfo.addressFirst} ${userInfo.addressSecond}</p>
            </div>
        </section>

        <!-- 경력 -->
        <c:if test="${not empty careerInfo}">
        <section class="p-section">
            <h2>경력 <span class="p-sub">(총 ${totalCareer})</span></h2>
            <c:forEach items="${careerInfo}" var="c">
                <div class="p-item">
                    <div class="p-item-head">
                        <strong>${c.companyName}</strong>
                        <span>
                            <fmt:formatDate value="${c.startDate}" pattern="yyyy.MM"/> ~
                            <c:choose>
                                <c:when test="${c.currentYn eq 'Y'}">재직중</c:when>
                                <c:otherwise><fmt:formatDate value="${c.endDate}" pattern="yyyy.MM"/></c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    <div class="p-item-sub">${c.department} · ${c.positionName} · ${c.jobTitle}</div>
                    <div class="p-item-body">${c.duties}</div>
                </div>
            </c:forEach>
        </section>
        </c:if>

        <!-- 학력 -->
        <c:if test="${not empty educationInfo}">
        <section class="p-section">
            <h2>학력</h2>
            <c:forEach items="${educationInfo}" var="e">
                <div class="p-item">
                    <div class="p-item-head">
                        <strong>${e.schoolName}</strong>
                        <span>
                            <fmt:formatDate value="${e.startDate}" pattern="yyyy.MM"/> ~
                            <c:choose>
                                <c:when test="${e.currentYn eq 'Y'}">재학중</c:when>
                                <c:otherwise><fmt:formatDate value="${e.endDate}" pattern="yyyy.MM"/></c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    <div class="p-item-sub">${e.major} ${not empty e.subMajor ? '/ '.concat(e.subMajor) : ''} · ${e.graduateType}</div>
                </div>
            </c:forEach>
        </section>
        </c:if>

        <!-- 스킬 -->
        <c:if test="${not empty skillInfo}">
        <section class="p-section">
            <h2>스킬</h2>
            <div class="p-skill-list">
                <c:forEach items="${skillInfo}" var="s">
                    <span class="p-skill">${s.skillName}</span>
                </c:forEach>
            </div>
        </section>
        </c:if>

        <!-- 경험/활동/교육 -->
        <c:if test="${not empty activityList}">
        <section class="p-section">
            <h2>경험/활동/교육</h2>
            <c:forEach items="${activityList}" var="a">
                <div class="p-item">
                    <div class="p-item-head">
                        <strong>${a.orgName}</strong>
                        <span>
                            <fmt:formatDate value="${a.startDate}" pattern="yyyy.MM"/> ~
                            <c:choose>
                                <c:when test="${a.currentYn eq 'Y'}">진행중</c:when>
                                <c:otherwise><fmt:formatDate value="${a.endDate}" pattern="yyyy.MM"/></c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    <div class="p-item-sub">${a.activityType}</div>
                    <div class="p-item-body">${a.content}</div>
                </div>
            </c:forEach>
        </section>
        </c:if>

        <!-- 자격/어학/수상 -->
        <c:if test="${not empty certificationInfo}">
        <section class="p-section">
            <h2>자격/어학/수상</h2>
            <c:forEach items="${certificationInfo}" var="cert">
                <div class="p-item">
                    <div class="p-item-head">
                        <strong>${cert.certName}</strong>
                        <span><fmt:formatDate value="${cert.acquiredDate}" pattern="yyyy.MM.dd"/></span>
                    </div>
                    <div class="p-item-sub">${cert.issuer} <c:if test="${not empty cert.score}">· ${cert.score}</c:if></div>
                </div>
            </c:forEach>
        </section>
        </c:if>

        <!-- 자기소개서 -->
        <c:if test="${not empty coverLetterList}">
        <section class="p-section">
            <h2>자기소개서</h2>
            <c:forEach items="${coverLetterList}" var="cl">
                <div class="p-item">
                    <div class="p-item-head"><strong>${cl.clTitle}</strong></div>
                    <div class="p-item-body p-coverletter">${cl.clContent}</div>
                </div>
            </c:forEach>
        </section>
        </c:if>

    </div>

    <script>
        document.getElementById('pdfDownloadBtn').addEventListener('click', function () {
            window.print();
        });
    </script>
</body>
</html>
