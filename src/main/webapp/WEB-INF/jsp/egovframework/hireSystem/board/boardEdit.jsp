<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 수정 - 커뮤니티 게시판</title>
    <link rel="stylesheet" href="https://cdn.ckeditor.com/ckeditor5/48.0.1/ckeditor5.css" />
    <script src="https://cdn.ckeditor.com/ckeditor5/48.0.1/ckeditor5.umd.js"></script>
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/boardEdit.css' />" />
    <script defer src="/js/hireSystem/ckeditorcustom.js"></script>
    <script defer src="/js/hireSystem/board/boardEdit.js"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>

    <main class="edit-container">
        <article class="edit-card">
            <header class="edit-header">
                <h2 class="edit-title">게시글 수정</h2>
            </header>

            <form id="editForm" action="<c:url value='/hireSystem/board/boardUpdate.do' />" method="post">
                <input type="hidden" name="boardNum" value="${board.boardNum}" />

                <div class="form-group">
                    <label for="category" class="form-label">카테고리</label>
                    <select class="form-select" id="category" name="category" required>
                        <option value="">카테고리 선택</option>
                        <option value="JOB_INFO" ${board.category == '취업정보' ? 'selected' : ''}>취업정보</option>
                        <option value="FREE" ${board.category == '자유게시판' ? 'selected' : ''}>자유게시판</option>
                        <option value="QNA" ${board.category == '질문답변' ? 'selected' : ''}>질문답변</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="title" class="form-label">제목</label>
                    <input type="text" class="form-control" id="title" name="title"
                        value="${board.title}" required />
                </div>

                <div class="form-group">
                    <label class="form-label">내용</label>
                    <div id="editor"></div>
                    <div id="editor-initial-content" class="editor-initial-content" hidden>
                        <c:out value="${board.content}" escapeXml="false" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="writer" class="form-label">작성자</label>
                    <input type="text" class="form-control" id="writer" name="writer"
                        value="${board.writer}" readonly />
                </div>

                <footer class="edit-actions">
                    <div class="action-buttons">
                        <a href="<c:url value='/hireSystem/board/boardDetail.do' />?boardNum=${board.boardNum}"
                            class="btn btn-secondary">취소</a>
                        <button type="button" class="btn btn-primary" id="btnUpdate">수정</button>
                    </div>
                </footer>
            </form>
        </article>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>
</body>
</html>
