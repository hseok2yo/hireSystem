<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
<!--     <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet"> -->
<!--     <script src="https://unpkg.com/@lottiefiles/lottie-player@latest/dist/lottie-player.js"></script> -->
<!--     <script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.js"></script> -->
<!--     <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css" /> -->
    <title>커뮤니티 게시판</title>
    
    <!-- CSS 순서 지정 -->
<%--     <link rel="stylesheet" href="<c:url value='/css/hireSystem/header.css?v=1' />"> --%>
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/boardList.css' />">
    
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>

	<form id="searchForm" method="get" action="/hireSystem/board/boardList.do">
		<input type="hidden" name="page" id="pageInput" value="1">
		<input type="hidden" name="category" value="${param.category}">
		<input type="hidden" name="searchType" value="${param.searchType}">
		<input type="hidden" name="searchKeyword" value="${param.searchKeyword}">
	</form>

	<main class="board-container">
		<div class="board-header">
			<h2>커뮤니티 게시판</h2>
			<div class="board-utils">
				<div class="search-area">
					<select class="category-select" id="searchType">
						<option value="all"
							${param.searchType == 'all' || empty param.searchType ? 'selected' : ''}>전체</option>
						<option value="title"
							${param.searchType == 'title' ? 'selected' : ''}>제목</option>
						<option value="writer"
							${param.searchType == 'writer' ? 'selected' : ''}>작성자</option>
					</select> 
					<input type="text" class="search-input" id="searchKeyword" value="${param.searchKeyword}" placeholder="검색어를 입력하세요">
					<button type="button" class="search-btn" onclick="goSearch()">검색</button>
				</div>
			</div>
		</div>
		<script>
			function goSearch() {
				document.querySelector("[name=category]").value = '';
			    document.querySelector("[name=searchType]").value = document.querySelector(".category-select").value;
			    document.querySelector("[name=searchKeyword]").value = document.querySelector(".search-input").value;
			    document.getElementById("pageInput").value = 1;
			    document.getElementById("searchForm").submit();
			}
		</script>

		<ul class="category-menu">
			<li class="category-item"><a href="#"
				onclick="goCategory(''); return false;"
				class="category-link ${empty param.category ? 'active' : ''}">전체</a>
			</li>
			<li class="category-item"><a href="#"
				onclick="goCategory('JOB_INFO'); return false;"
				class="category-link ${param.category == 'JOB_INFO' ? 'active' : ''}">취업정보</a>
			</li>
			<li class="category-item"><a href="#"
				onclick="goCategory('FREE'); return false;"
				class="category-link ${param.category == 'FREE' ? 'active' : ''}">자유게시판</a>
			</li>
			<li class="category-item"><a href="#"
				onclick="goCategory('QNA'); return false;"
				class="category-link ${param.category == 'QNA' ? 'active' : ''}">질문답변</a>
			</li>
		</ul>
		<script>
			function goCategory(category) {
			    document.querySelector("[name=category]").value = category;
			    document.getElementById("pageInput").value = 1;
			    document.getElementById("searchForm").submit();
			}
		</script>
		<div class="write-btn-area">
			<button type="button" class="write-btn">글쓰기</button>
		</div>
		<script defer src="/js/hireSystem/boardList.js"></script>

		<div class="board-content">
			<table class="board-table">
				<thead>
					<tr>
						<th>번호</th>
						<th>카테고리</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회</th>
<!-- 						<th>좋아요</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="board" varStatus="status">
						<tr>
							<td>${displayNo - status.index}</td>
							<td><span class="category-badge">${board.category }</span></td>
							<td class="title">
								<a href="/hireSystem/board/boardDetail.do?boardNum=${board.boardNum }">${board.title }</a>
								<span class="comment-count">[0]</span>
							</td>
							<td>${board.writer }</td>
							<td><fmt:formatDate value="${board.regDt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${board.viewCnt }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="board-footer">
			<div class="pagination">
				<c:if test="${currentPage > 1}">
					<a href="#" onclick="goPage(1); return false;" class="page-btn">◁◁</a>
				</c:if>

				<c:if test="${startPage > 1}">
					<a href="#" onclick="goPage(${startPage - 1}); return false;"
						class="page-btn">◁</a>
				</c:if>

				<c:forEach begin="${startPage}" end="${endPage}" var="i">
					<c:choose>
						<c:when test="${i == currentPage}">
							<a href="#" onclick="goPage(${i}); return false;"
								class="page-num active">${i}</a>
						</c:when>
						<c:otherwise>
							<a href="#" onclick="goPage(${i}); return false;"
								class="page-btn">${i}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:if test="${endPage < totalPage}">
					<a href="#" onclick="goPage(${endPage + 1}); return false;"
						class="page-btn">▷</a>
				</c:if>
				<c:if test="${currentPage < totalPage}">
					<a href="#" onclick="goPage(${totalPage}); return false;"
						class="page-btn">▷▷</a>
				</c:if>
			</div>
			<script>
				function goPage(page) {
				    document.getElementById("pageInput").value = page;
				    document.getElementById("searchForm").submit();
				}
			</script>
			
		</div>
		
		
	</main>
	<jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>
</body>
</html> 