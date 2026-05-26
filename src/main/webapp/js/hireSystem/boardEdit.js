
document.addEventListener("DOMContentLoaded", function() {
    loadInitialEditorContent();
    bindUpdateButton();
});

function loadInitialEditorContent() {
    var initialEl = document.getElementById("editor-initial-content");
    if (!initialEl) {
        return;
    }
    var initialHtml = initialEl.innerHTML;

    var timer = setInterval(function() {
        if (typeof editorInstance !== "undefined" && editorInstance) {
            clearInterval(timer);
            editorInstance.setData(initialHtml);
        }
    }, 100);
}

function bindUpdateButton() {
    var btn = document.getElementById("btnUpdate");
    if (!btn) {
        return;
    }

    btn.addEventListener("click", function() {
        if (!document.getElementById("category").value) {
            alert("카테고리를 선택해주세요.");
            return;
        }
        if (!document.getElementById("title").value.trim()) {
            alert("제목을 입력해주세요.");
            return;
        }
        if (!editorInstance || !editorInstance.getData().trim()) {
            alert("내용을 입력해주세요.");
            return;
        }

		const form = document.querySelector("#editForm");
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
		
        fetch("/hireSystem/board/boardUpdate.do", {
		    method: "POST",
		    body: formData
		})
		.then(function(res) {
		    return res.json(); // ← 이게 없으면 data가 Response 객체라 result 못 꺼냄
		})
		.then(function(data) {
		    if (!data) return;
		    if (data.result === "success") {
		        alert("수정되었습니다.");
		        location.href = "/hireSystem/board/boardDetail.do?boardNum=" + formData.get("boardNum");
		    } else {
		        alert("에러 발생");
		    }
		})
		.catch(function() {
		    alert("서버 오류");
		});
    });
}
