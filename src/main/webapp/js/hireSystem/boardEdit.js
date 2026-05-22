
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

        var form = document.getElementById("editForm");
        var hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "content";
        hiddenInput.value = editorInstance.getData();
        form.appendChild(hiddenInput);
        form.submit();
    });
}
