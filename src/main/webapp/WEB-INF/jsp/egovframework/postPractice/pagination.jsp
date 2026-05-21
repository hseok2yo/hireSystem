<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<nav aria-label="Page navigation" class="mt-4">
    <ul class="pagination justify-content-center">
        <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>">
            <a class="page-link" href="/postPractice/postPractice.do?pageNum=1">처음</a>
        </li>
        <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>">
            <a class="page-link" href="/postPractice/postPractice.do?pageNum=${currentPage - 1}">이전</a>
        </li>
        <c:forEach var="i" begin="${startPageInGroup}" end="${endPageInGroup}" step="1">
            <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                <a class="page-link" href="/postPractice/postPractice.do?pageNum=${i}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>">
            <a class="page-link" href="/postPractice/postPractice.do?pageNum=${currentPage + 1}">다음</a>
        </li>
        <li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>">
            <a class="page-link" href="/postPractice/postPractice.do?pageNum=${totalPages}">마지막</a>
        </li>
    </ul>
</nav>
