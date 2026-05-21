

buttonWrite(); //글쓰기

//글쓰기 
function buttonWrite() {
	const writeBtn = document.querySelector(".write-btn");

	writeBtn.addEventListener("click", function() {

		apiLoginCommonFetch("/hireSystem/board/boardWriteCheck.do")
			.then(function(data) {
				
				if (!data) {
					alert("로그인이 필요합니다");
					return;
				} else {
					location.href =
						"/hireSystem/board/boardWrite.do";
				}
			});
	});

}
