<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <!-- 검색 및 버튼 영역 -->
        <div class="row mb-3">
            <div class="col-md-6">
                <div class="input-group">
                    <select class="form-select" style="max-width: 130px;">
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                        <option value="writer">작성자</option>
                    </select>
                    <input type="text" class="form-control" placeholder="검색어를 입력하세요">
                    <button class="btn btn-primary">검색</button>
                </div>
            </div>
        </div>
        총 게시글 수 : ${maxPostCnt }<br>

        <!-- 게시판 테이블 -->
        <div class="table-responsive">
            <table class="table table-hover">
                <thead class="table-light">
                    <tr>
                        <th style="width: 10%">번호</th>
                        <th style="width: 45%">제목</th>
                        <th style="width: 15%">작성자</th>
                        <th style="width: 15%">작성일</th>
                        <th style="width: 15%">조회수</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="list" items="${list}">
	                    <tr>
	                        <td>${list.num }</td>
	                        <td><a href="#" class="text-decoration-none">${list.title }</a></td>
	                        <td>${list.writer }</td>
	                        <td>${list.regdate }</td>
	                        <td>${list.viewcount }</td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <div class="col-md-6 text-end">
             <button type="button" class="btn btn-primary" onclick="write()">글쓰기</button>
         </div>
            
        <!-- 페이지네이션 포함 -->
    	<jsp:include page="pagination.jsp" />
    </div>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>