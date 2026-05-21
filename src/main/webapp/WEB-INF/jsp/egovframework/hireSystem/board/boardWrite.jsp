<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성 - 커뮤니티 게시판</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
<!-- ckeditor5 -->
<link rel="stylesheet" href="https://cdn.ckeditor.com/ckeditor5/48.0.1/ckeditor5.css" />
<script src="https://cdn.ckeditor.com/ckeditor5/48.0.1/ckeditor5.umd.js"></script>
<link rel="stylesheet" href="/css/hireSystem/boardWrite.css" />
<script defer src="/js/hireSystem/ckeditorcustom.js"></script>

<script defer="defer" src="/js/hireSystem/boardWrite.js"></script>
</head>
<body>
	<div class="write-container">
		<div class="write-header">
			<h2>게시글 작성</h2>
		</div>

		<form action="/hireSystem/board/boardInsert.do" method="post">
			<div class="mb-3">
				<label for="category" class="form-label">카테고리</label> <select
					class="form-select" id="category" name="category">
					<option value="">카테고리 선택</option>
					<option value="JOB_INFO">취업정보</option>
					<option value="FREE">자유게시판</option>
					<option value="QNA">질문답변</option>
				</select>
			</div>
			<div class="mb-3">
				<label for="title" class="form-label">제목</label> <input type="text"
					class="form-control" id="title" name="title">
			</div>

			<%-- 에디터가 붙을 div --%>
			<div id="editor"></div>

			<div class="mb-3">
				<label for="writer" class="form-label">작성자</label> <input
					type="text" class="form-control" id="writer" name="writer">
			</div>

			<div class="d-flex justify-content-between">
				<a href="/board/list" class="btn btn-outline-secondary"> <i
					class="fas fa-arrow-left me-1"></i>취소
				</a>
				<button type="button" class="btn btn-primary">
					<i class="fas fa-paper-plane me-1"></i>등록
				</button>
			</div>
		</form>
	</div>

</body>
</html>
