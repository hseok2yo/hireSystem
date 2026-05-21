<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${board.title} - 커뮤니티 게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        .detail-container {
            max-width: 900px;
            margin: 2rem auto;
            padding: 0 1rem;
        }
        .board-header {
            border-bottom: 2px solid #0d6efd;
            padding-bottom: 1rem;
            margin-bottom: 2rem;
        }
        .content-area {
            min-height: 300px;
            padding: 2rem;
            background-color: #f8f9fa;
            border-radius: 0.5rem;
            margin-bottom: 2rem;
        }
        .action-buttons {
            gap: 0.5rem;
        }
        .like-btn {
            cursor: pointer;
            transition: transform 0.2s;
        }
        .like-btn:hover {
            transform: scale(1.1);
        }
        .stats-info {
            color: #6c757d;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
    <div class="detail-container">
        <div class="board-header">
            <div class="d-flex justify-content-between align-items-start mb-3">
                <div>
                    <span class="badge bg-primary mb-2">${board.category}</span>
                    <h2>${board.title}</h2>
                </div>
                <div class="stats-info text-end">
                    <div><i class="far fa-user me-1"></i>${board.writer}</div>
                    <div><i class="far fa-clock me-1"></i><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd HH:mm"/></div>
                    <div><i class="far fa-eye me-1"></i>조회 ${board.viewCount}</div>
                    <div><i class="far fa-heart me-1"></i>좋아요 ${board.likeCount}</div>
                </div>
            </div>
        </div>

        <div class="content-area">
            ${board.content}
        </div>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div class="action-buttons d-flex">
                <a href="/board/list" class="btn btn-outline-secondary">
                    <i class="fas fa-list me-1"></i>목록
                </a>
                <a href="/board/edit?boardId=${board.boardId}" class="btn btn-outline-primary ms-2">
                    <i class="fas fa-edit me-1"></i>수정
                </a>
                <button type="button" class="btn btn-outline-danger ms-2" onclick="deleteBoard()">
                    <i class="fas fa-trash me-1"></i>삭제
                </button>
            </div>
            <div class="like-btn text-primary" onclick="updateLike()">
                <i class="far fa-heart fa-2x"></i>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function deleteBoard() {
            if(confirm('정말 삭제하시겠습니까?')) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = '/board/delete';
                
                const input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'boardId';
                input.value = '${board.boardId}';
                
                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            }
        }

        function updateLike() {
            fetch('/board/like?boardId=${board.boardId}', {
                method: 'POST'
            })
            .then(response => {
                if(response.ok) {
                    location.reload();
                }
            })
            .catch(error => console.error('Error:', error));
        }
    </script>
</body>
</html> 