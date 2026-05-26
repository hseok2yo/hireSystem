<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${board.title} - 커뮤니티 게시판</title>
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/boardDetail.css' />">
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>

    <main class="detail-container">
        <article class="detail-card">
            <header class="detail-header">
                <div class="detail-title-area">
                    <span class="category-badge">${board.category}</span>
                    <h2 class="detail-title">${board.title}</h2>
                </div>
                <ul class="detail-meta">
                    <li><span class="meta-label">작성자</span>${board.writer}</li>
                    <li><span class="meta-label">작성일</span><fmt:formatDate value="${board.regDt}" pattern="yyyy-MM-dd HH:mm"/></li>
                    <li><span class="meta-label">조회</span>${board.viewCnt}</li>
                </ul>
            </header>

            <div class="content-area">
                ${board.content}
            </div>

            <footer class="detail-actions">
                <div class="action-buttons">
                    <a href="<c:url value='/hireSystem/board/boardList.do' />" class="btn btn-secondary">목록</a>
                    <a href="<c:url value='/hireSystem/board/boardEdit.do' />?boardNum=${board.boardNum}" class="btn btn-primary">수정</a>
                    <button type="button" class="btn btn-danger" onclick="deleteBoard()">삭제</button>
                </div>
            </footer>
        </article>
    </main>
	
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>

    <script>
        function deleteBoard() {
            if (!confirm('정말 삭제하시겠습니까?')) {
                return;
            }
            fetch('<c:url value="/hireSystem/board/boardDelete.do" />', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ boardNum: ${board.boardNum} })
            })
            .then(function(response) {
                return response.json();
            })
            .then(function(result) {
                if (result.result) {
                    alert('삭제되었습니다.');
                    location.href = '<c:url value="/hireSystem/board/boardList.do" />';
                } else {
                    alert('삭제에 실패했습니다.');
                }
            })
            .catch(function() {
                alert('오류가 발생했습니다.');
            });
        }
    </script>
</body>
</html>
