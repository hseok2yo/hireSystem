// HTML 문서가 전부 로드된 후 실행
document.addEventListener("DOMContentLoaded", function() {

    // 새 이력서 버튼 이벤트 실행
    resumeForm();

    // 더보기 버튼 이벤트 실행
    initMoreButton();

    // 바깥 클릭 시 메뉴 닫기 이벤트 실행
    closeMoreMenu();

    // 메뉴 항목 이벤트 실행
    initMenuItem();

	//검색조건
	searchSort();

	//이력서 수정
	editResumeForm();

});


// =========================
// 새 이력서 작성 버튼
// =========================
function resumeForm() {

    // 새 이력서 버튼 가져오기
    var writeBtn = document.querySelector(".create-resume-btn");

    // 버튼 없으면 종료
    if (!writeBtn) {
        return;
    }

    // 클릭 이벤트 등록
    writeBtn.addEventListener("click", function() {

        // 작성 페이지 이동
        location.href = "/hireSystem/resume/resumeForm.do";
    });
}

//이력서 수정버튼
function editResumeForm() {
	document.querySelectorAll('.action-edit').forEach(function(btn) {

		btn.addEventListener('click', function() {

			const resumeId = this.dataset.num;

			location.href =
				'/hireSystem/resume/edit.do?resumeId=' + resumeId;

		});

	});
}


function goPage(page) {
	document.querySelector('input[name="page"]').value = page;
    document.getElementById('searchForm').submit();

}

function searchSort() {
	document.querySelector('#sortSelect').addEventListener('change', function() {

		document.querySelector('[name="searchSort"]').value = this.value;
		document.querySelector('[name="page"]').value = 1;

		document.getElementById('searchForm').submit();
	});
}

// =========================
// 더보기 버튼 이벤트
// =========================
function initMoreButton() {

    // 점 세개 버튼 목록 가져오기
    var moreButtons = document.querySelectorAll(".more-btn");

    // 버튼 반복
    moreButtons.forEach(function(btn) {

        // 클릭 이벤트 등록
        btn.addEventListener("click", function(e) {

            // 이벤트 버블링 방지
            e.stopPropagation();

            // 현재 more-wrap 찾기
            var wrap = btn.closest(".more-wrap");

            // 이미 열린 메뉴 닫기
            document.querySelectorAll(".more-wrap.open").forEach(function(opened) {

                // 현재 메뉴 제외
                if (opened !== wrap) {
                    opened.classList.remove("open");
                }
            });

            // 메뉴 열기 / 닫기
            wrap.classList.toggle("open");
        });
    });
}


// =========================
// 바깥 클릭 시 메뉴 닫기
// =========================
function closeMoreMenu() {

    // 문서 전체 클릭 이벤트
    document.addEventListener("click", function(e) {

        // more-wrap 밖 클릭 시
        if (!e.target.closest(".more-wrap")) {

            // 열린 메뉴 모두 닫기
            document.querySelectorAll(".more-wrap.open").forEach(function(opened) {

                opened.classList.remove("open");
            });
        }
    });
}


// =========================
// 메뉴 항목 이벤트
// =========================
function initMenuItem() {

    // 메뉴 항목 목록 가져오기
    document.querySelectorAll(".menu-item").forEach(function(itemBtn) {


        // 클릭 이벤트 등록
        itemBtn.addEventListener("click", function(e) {

            // 이벤트 버블링 방지
            e.stopPropagation();

			const resumeId = itemBtn.dataset.num;

			console.log("resumeId:", resumeId);

            // 대표 이력서 설정
			if (itemBtn.classList.contains("action-representative")) {

				if (confirm("이 이력서를 대표 이력서로 설정하시겠습니까?")) {
					alert("대표 이력서 설정 기능은 백엔드 연동 후 동작합니다.");
				}

				// PDF 다운로드
			} else if (itemBtn.classList.contains("action-pdf")) {

				if (confirm("이 이력서를 PDF로 다운로드하시겠습니까?")) {
					window.open('/hireSystem/resume/print.do?resumeId=' + resumeId, '_blank');
				}

				// 이력서 복사
			} else if (itemBtn.classList.contains("action-copy")) {

				if (confirm("이 이력서를 복사하시겠습니까?")) {
					fetch("/hireSystem/resume/duplicate.do", {
						method: "POST",
						headers: {
							"Content-Type": "application/x-www-form-urlencoded"
						},
						body: `resumeId=${encodeURIComponent(resumeId)}`
					})
						.then(response => response.json())
						.then(data => {

							if (data.result) {
								alert("이력서가 복사되었습니다.");
								location.reload();
							} else {
								alert(data.message || "이력서 복사에 실패했습니다.");
							}

						})
						.catch(error => {
							console.error("복사 오류:", error);
							alert("복사 중 오류가 발생했습니다.");
						});
				}

				// 이력서 삭제
			} else if (itemBtn.classList.contains("action-delete")) {

				if (confirm("이 이력서를 삭제하시겠습니까?")) {
					fetch("/hireSystem/resume/resumeDelete.do", {
						method: "POST",
						headers: {
							"Content-Type": "application/x-www-form-urlencoded"
						},
						body: `resumeId=${encodeURIComponent(resumeId)}`
					})
						.then(response => response.json())
						.then(data => {

							if (data.result) {
								alert("이력서가 삭제되었습니다.");
								location.reload();
							} else {
								alert("이력서 삭제에 실패했습니다.");
							}

						})
						.catch(error => {
							console.error("삭제 오류:", error);
							alert("삭제 중 오류가 발생했습니다.");
						});
				}
			}

            // 메뉴 닫기
            var wrap = itemBtn.closest(".more-wrap");

            if (wrap) {
                wrap.classList.remove("open");
            }
        });
    });
}