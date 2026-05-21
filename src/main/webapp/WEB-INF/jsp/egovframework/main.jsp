<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script>
		window.onload = function () {
			var btn = document.querySelectorAll("button[type='button']");
			
			for(let i = 0; i < btn.length; i++) {
				btn[i].addEventListener("click", function() {
					var text = btn[i].innerText;
					if (text === "hireSystem") {
		                location.href = "hireSystem/main.do";
		            } else {
		                location.href = text + "/" + text + ".do";
		            }
					
				})
			}
		}
		
	</script>
</head>
<body>
	<h1>main page</h1>
	<hr>
	
	<button type="button">fileDivideDown</button>
	<button type="button">file</button>
	<button type="button">json</button>
	<button type="button">postPractice</button>
	<button type="button">hireSystem</button>
	
</body>
</html>