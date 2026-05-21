<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script>
		window.onload = function() {
			const btns = document.querySelector("#btns");
			btns.addEventListener("click", f);

			let array = [{
				name: "흰둥이",
				age: 3
			},
			{
				name: "짱구",
				age: 7
			},
			{
				name: "짱아",
				age: 4
			}];

			let jstr = JSON.stringify(array);

			function f() {
				const xhttp = new XMLHttpRequest();
				xhttp.onload = function() {

					let jobj = JSON.parse(this.responseText);
					for(let i =0;i<jobj.length; i++) {
						document.querySelector("#divs").innerHTML += "이름:" + jobj[i].name +"<br>나이:" + jobj[i].age+"<br>";
					}
					//document.querySelector("#divs").innerHTML = "이름:" + jobj.name +"<br>나이:" + jobj.age;
				}
				xhttp.open("Post","jsonPractice.do",true);
				xhttp.setRequestHeader("Content-type", "application/json");
				xhttp.send(jstr);
			}
		}
	</script>
</head>
<body>
	<button id="btns">버튼동작</button>
	<div id="divs"></div>
</body>
</html>