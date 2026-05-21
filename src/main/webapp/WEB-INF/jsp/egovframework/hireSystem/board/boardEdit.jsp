<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 수정 - 커뮤니티 게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <style>
        .edit-container {
            max-width: 900px;
            margin: 2rem auto;
            padding: 0 1rem;
        }
        .edit-header {
            border-bottom: 2px solid #0d6efd;
            padding-bottom: 1rem;
            margin-bottom: 2rem;
        }
        .note-editor {
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <div class="edit-container">
        <div class="edit-header">
            <h2>게시글 수정</h2>
        </div>

        <form action="/board/edit" method="post">
            <input type="hidden" name="boardId" value="${board.boardId}">
            
            <div class="mb-3">
                <label for="category" class="form-label">카테고리</label>
                <select class="form-select" id="category" name="category" required>
                    <option value="">카테고리 선택</option>
                    <option value="취업정보" ${board.category == '취업정보' ? 'selected' : ''}>취업정보</option>
                    <option value="자유게시판" ${board.category == '자유게시판' ? 'selected' : ''}>자유게시판</option>
                    <option value="질문답변" ${board.category == '질문답변' ? 'selected' : ''}>질문답변</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="title" class="form-label">제목</label>
                <input type="text" class="form-control" id="title" name="title" value="${board.title}" required>
            </div>

            <div class="mb-3">
                <label for="content" class="form-label">내용</label>
                <textarea id="content" name="content" required>${board.content}</textarea>
            </div>

            <div class="mb-3">
                <label for="writer" class="form-label">작성자</label>
                <input type="text" class="form-control" id="writer" name="writer" value="${board.writer}" required readonly>
            </div>

            <div class="d-flex justify-content-between">
                <a href="/board/detail?boardId=${board.boardId}" class="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-1"></i>취소
                </a>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save me-1"></i>수정
                </button>
            </div>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#content').summernote({
                height: 300,
                minHeight: null,
                maxHeight: null,
                focus: true,
                lang: 'ko-KR',
                toolbar: [
                    ['style', ['style']],
                    ['font', ['bold', 'underline', 'clear']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['table', ['table']],
                    ['insert', ['link', 'picture']],
                    ['view', ['fullscreen', 'codeview', 'help']]
                ]
            });
        });
    </script>
</body>
</html> 