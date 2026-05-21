document.addEventListener("DOMContentLoaded", function() {

    // 칩 버튼들에 이벤트 등록
    document.querySelectorAll(".chip").forEach(function(chip) {
        chip.addEventListener("click", function() {
            var coClcd = this.getAttribute("data-coClcd");
            filterByChip("coClcd", coClcd);
        });
    });

    // 정렬은 JSP select의 onchange에서 setFilter 호출 (중복 리스너 방지)
});


// 칩 전용
function filterByChip(name, value) {
    var form = document.getElementById("searchForm");
    if (!form) return;
    var field = form.querySelector('[name="' + name + '"]');
    if (field) field.value = value;
    form.submit();
}


// select 전용
function filterBySelect() {
    var form = document.getElementById("searchForm");
    if (!form) return;
    form.submit();
}