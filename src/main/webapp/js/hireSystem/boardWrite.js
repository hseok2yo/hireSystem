
validCheck();

function validCheck() {
    var submitbtn = document.querySelector(".btn.btn-primary");
    
    submitbtn.addEventListener("click", function() {
        if (!categoryCheck()) return;
        if (!titleCheck()) return;
        if (!contentCheck()) return;
        
        submitAjax(); // AJAX로 전송
    });
}

function submitAjax() {

    const form = document.querySelector("form");
	const formData = new FormData(form);
	
	// editor 값만 따로 덮어쓰기
	formData.set("content", editorInstance.getData());
	
	// 에디터에 현재 있는 이미지 파일명만 추출 (삭제된 건 자동으로 빠짐)
    const editorData = editorInstance.getData();
    const parser = new DOMParser();
    const doc = parser.parseFromString(editorData, 'text/html');
    const imgs = doc.querySelectorAll('img');
    
    imgs.forEach(img => {
        const src = img.getAttribute('src');
        const filename = src.split('filename=')[1];
        if (filename) formData.append('filenames', filename);
    });

	console.log("5. 최종 formData filenames:");
    for (let [key, value] of formData.entries()) {
        console.log(`   ${key}:`, value);
    }

    apiLoginCommonFetch("/hireSystem/board/boardInsert.do", {
        method: "POST",
        body: formData
    })
    .then(function(data) {
        if (!data) {
            return;
        }
        if (data.result === "success") {
            alert("작성되었습니다");
            location.href = "/hireSystem/board/boardList.do";
        } else {
            alert("에러 발생");
        }
    })
    .catch(function() {
        alert("서버 오류");
    });
}

function categoryCheck() {
    var category = document.getElementById("category").value;
    if (!category) { alert("카테고리를 선택해주세요."); return false; }
    return true;
}

function titleCheck() {
    var title = document.getElementById("title").value.trim();
    if (!title) { alert("제목을 입력해주세요."); return false; }
    return true;
}

function contentCheck() {
    var content = editorInstance.getData();
    if (!content) { alert("내용을 입력해주세요."); return false; }
    return true;
}

