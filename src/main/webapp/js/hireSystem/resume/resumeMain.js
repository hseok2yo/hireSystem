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

            // 대표 이력서 설정
            if (itemBtn.classList.contains("action-representative")) {

                alert("대표 이력서 설정 기능은 백엔드 연동 후 동작합니다.");

            // PDF 다운로드
            } else if (itemBtn.classList.contains("action-pdf")) {

                alert("PDF 다운로드 기능은 백엔드 연동 후 동작합니다.");

            // 이력서 복사
            } else if (itemBtn.classList.contains("action-copy")) {

                alert("이력서 복사 기능은 백엔드 연동 후 동작합니다.");

            // 이력서 삭제
            } else if (itemBtn.classList.contains("action-delete")) {

                alert("이력서 삭제 기능은 백엔드 연동 후 동작합니다.");
            }

            // 메뉴 닫기
            var wrap = itemBtn.closest(".more-wrap");

            if (wrap) {
                wrap.classList.remove("open");
            }
        });
    });
}