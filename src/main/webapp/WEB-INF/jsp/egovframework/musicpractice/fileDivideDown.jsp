<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <label for="text">제거할 텍스트:</label>
    <input type="text" id="removeText" value="y2mate.com - ">

    <label for="files">파일을 선택하세요:</label>
    <input type="file" id="files" multiple>

    <button id="processFiles">파일 처리</button>

    <script>
        document.getElementById('processFiles').addEventListener('click', function(event) {
            event.preventDefault();

            let files = document.getElementById('files').files;
            let removeText = document.getElementById('removeText').value;

            // 각 파일에 대해 처리
            for (let i = 0; i < files.length; i++) {
                let formData = new FormData();
                formData.append("files", files[i]);  // 파일을 formData에 추가
                formData.append("removeText", removeText);  // 제거할 텍스트 추가

                // XMLHttpRequest를 사용한 순수 자바스크립트 AJAX 요청
                let xhr = new XMLHttpRequest();
                xhr.open("POST", "fileSingledown.do", true);
                xhr.responseType = "blob"; // 서버 응답을 파일(blob)로 받기

                // 요청이 완료되면 실행
                xhr.onload = function() {
                    if (xhr.status === 200) {
                        let blob = xhr.response;
                        let downloadLink = document.createElement('a');
                        downloadLink.href = window.URL.createObjectURL(blob);
                        downloadLink.download = files[i].name.replace(removeText, '').trim();  // 파일명에서 제거 텍스트 제거
                        downloadLink.click();  // 다운로드 실행
                    } else {
                        console.error('파일 다운로드 실패:', xhr.statusText);
                    }
                };

                // 요청 오류 발생 시 실행
                xhr.onerror = function() {
                    console.error('파일 처리 중 오류 발생');
                };

                // 요청 전송
                xhr.send(formData);
            }
        });
    </script>
</body>
</html>