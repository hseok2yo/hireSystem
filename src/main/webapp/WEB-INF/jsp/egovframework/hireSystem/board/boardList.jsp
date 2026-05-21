<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	
	<main class="board-container">
		<div class="board-header">
			<h2>커뮤니티 게시판</h2>
			<div class="board-utils">
				<div class="search-area">
					<select class="category-select">
						<option value="">전체 카테고리</option>
						<option value="취업정보">취업정보</option>
						<option value="자유게시판">자유게시판</option>
						<option value="질문답변">질문답변</option>
					</select>
					<input type="text" class="search-input" placeholder="검색어를 입력하세요">
					<button type="button" class="search-btn">검색</button>
				</div>
			</div>
		</div>

		<ul class="category-menu">
			<li class="category-item"><a href="#" class="category-link active">전체</a></li>
			<li class="category-item"><a href="#" class="category-link">취업정보</a></li>
			<li class="category-item"><a href="#" class="category-link">자유게시판</a></li>
			<li class="category-item"><a href="#" class="category-link">질문답변</a></li>
		</ul>

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
						<th>좋아요</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>10</td>
						<td><span class="category-badge">취업정보</span></td>
						<td class="title">
							<a href="#">2024 상반기 신입 개발자 채용 정보</a>
							<span class="comment-count">[5]</span>
						</td>
						<td>취업멘토</td>
						<td>2024-01-10</td>
						<td>128</td>
						<td>23</td>
					</tr>
					<tr>
						<td>9</td>
						<td><span class="category-badge">자유게시판</span></td>
						<td class="title">
							<a href="#">개발자 스터디 모집합니다</a>
							<span class="comment-count">[3]</span>
						</td>
						<td>코딩왕</td>
						<td>2024-01-09</td>
						<td>95</td>
						<td>12</td>
					</tr>
					<tr>
						<td>8</td>
						<td><span class="category-badge">질문답변</span></td>
						<td class="title">
							<a href="#">Spring Boot vs Node.js 고민입니다</a>
							<span class="comment-count">[8]</span>
						</td>
						<td>초보개발자</td>
						<td>2024-01-08</td>
						<td>156</td>
						<td>18</td>
					</tr>
					<tr>
						<td>7</td>
						<td><span class="category-badge">취업정보</span></td>
						<td class="title">
							<a href="#">백엔드 개발자 포트폴리오 팁</a>
							<span class="comment-count">[12]</span>
						</td>
						<td>경력쌓기</td>
						<td>2024-01-07</td>
						<td>245</td>
						<td>45</td>
					</tr>
					<tr>
						<td>6</td>
						<td><span class="category-badge">자유게시판</span></td>
						<td class="title">
							<a href="#">개발자 연봉 이야기</a>
							<span class="comment-count">[15]</span>
						</td>
						<td>연봉킹</td>
						<td>2024-01-06</td>
						<td>312</td>
						<td>67</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="board-footer">
			<div class="pagination">
				<a href="#" class="page-btn">이전</a>
				<a href="#" class="page-num active">1</a>
				<a href="#" class="page-num">2</a>
				<a href="#" class="page-num">3</a>
				<a href="#" class="page-num">4</a>
				<a href="#" class="page-num">5</a>
				<a href="#" class="page-btn">다음</a>
			</div>
		</div>
	</main>
</body>
</html> 