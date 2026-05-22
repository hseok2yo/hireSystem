

buttonWrite(); //글쓰기

//글쓰기 
function buttonWrite() {
	const writeBtn = document.querySelector(".write-btn");

	writeBtn.addEventListener("click", function() {
		location.href = "/hireSystem/board/boardWrite.do";
	});

}
