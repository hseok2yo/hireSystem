<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <form action="fileSingleMultidown.do" method="post" enctype="multipart/form-data">
    <label for="text">제거할 텍스트:</label>
    <input type="text" id="text" name="removeText"  value="y2mate.com - ">

    <label for="files">파일을 선택하세요:</label>
    <input type="file" id="files" name="files" multiple>

    <button type="submit">파일 처리</button>
</form>
</body>
</html>