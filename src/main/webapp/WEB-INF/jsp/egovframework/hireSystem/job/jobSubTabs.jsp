<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    채용정보 서브탭 공통 파트 (jobSubTabs.jsp)

    사용법: 각 페이지(recruitList.jsp / normalJob.jsp)의 header include 바로 아래에서
    activeTab 파라미터만 넘겨서 include 한다. 값은 normal 또는 public.

    예)
    jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/jobSubTabs.jsp"
        jsp:param name="activeTab" value="normal"
    /jsp:include

    두 탭은 완전히 다른 데이터 소스(자체 DB / 공공데이터 API)라서
    한 페이지 안에서 JS로 내용만 바꿔치기하지 않고, 실제로는 서로 다른
    URL(페이지)로 이동한다. 이 공통 바 덕분에 화면상으로는
    "하나의 채용정보 화면 안에 탭이 있는 것"처럼 보인다.
--%>
<section class="job-subtabs">
    <div class="job-subtabs-inner">
        <a href="<c:url value='/hireSystem/job/normalJob.do'/>"
           class="job-subtab ${param.activeTab eq 'normal' ? 'is-active' : ''}">
            일반채용정보
        </a>
        <a href="<c:url value='/hireSystem/recruit/recruitList.do'/>"
           class="job-subtab ${param.activeTab eq 'public' ? 'is-active' : ''}">
            공공기관채용정보
        </a>
    </div>
</section>
