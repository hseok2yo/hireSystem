<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        * {
            padding: 0;
            margin: 0;
        }
        #wrap {
            width: 100%;
            /* height: 100vh; */
            display: grid;
            justify-content: center;
            border: 3px solid black;

        }
        #container {
            width: 400px;
            height: 400px;
            background-color: palevioletred;
            display: flex;
            justify-content: center;
            align-items: center;
            border: 1px solid black;
        }
        #lv1 {
            width: 300px;
            height: 300px;
            background-color: rgb(179, 94, 94);
            border: 1px solid black;
            display: flex;
            justify-content: center;
            align-items: center;

        }
        #lv2 {
            width: 200px;
            height: 200px;
            background-color: rgba(47, 136, 114, 0.555);
            border: 1px solid black;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        #lv3 {
            width: 100px;
            height: 100px;
            background-color: black;
            border: 1px solid black;
        }
    </style>
<script>
		window.onload = function() {

		    const container = document.querySelector("#container");
            const lv1 = document.querySelector("#lv1");
            const lv2 = document.querySelector("#lv2");
            const lv3 = document.querySelector("#lv3");

             container.addEventListener("click", f);
            //lv1.addEventListener("click", f1, true);
            //lv2.addEventListener("click", f1, true);
            //lv3.addEventListener("click", f1, true);
            lv1.addEventListener("click", f);
            lv2.addEventListener("click", f);
            lv3.addEventListener("click", f);

            function f(event) {
                // 이벤트가 발생한 지점 찍어주는 곳(event.target)
                //그 id는?
                // console.log(event.target.id);
                // console.log(event.currentTarget.id);
                if(this.id != event.target.id) {
                	return;
                }
                console.log("버블링단계" + this.id + " | 이벤트발생 id-" + event.target.id);
            }

            function f1 (event) {
                console.log("캡처링" + this.id + " | 이벤트발생 id-" + event.target.id);
            }
		}
	</script>
</head>

<body>
	<script>
		function main() {
			location.href = "index.do";
		}
	</script>
	<button type="button" onclick="main()">main</button>

	<div id="wrap">
		<div id="container">
			<div id="lv1">
				<div id="lv2">
					<div id="lv3">

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>