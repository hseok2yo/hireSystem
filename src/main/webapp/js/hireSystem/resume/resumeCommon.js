var RESUME_CTX = {}; //공통hidden 전역변수 저장소

// 사이드바 토글
document.addEventListener('DOMContentLoaded', function() {
	RESUME_CTX.resumeId = document.getElementById('resumeId').value;
    RESUME_CTX.userNum = document.getElementById('userNum').value;

    initSidebarToggle();// 사이드바 토글
});


// =============================================
// 사이드바 토글
// =============================================
function initSidebarToggle() {
    document.querySelectorAll('.sidebar-toggle').forEach(function(button) {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            var targetId = button.getAttribute('data-target');
            var expanded = button.classList.toggle('expanded');
            button.setAttribute('aria-expanded', expanded);
            var icon = button.querySelector('i');
            if (icon) {
                icon.classList.toggle('fa-minus', expanded);
                icon.classList.toggle('fa-plus', !expanded);
            }
            if (targetId) {
                var target = document.querySelector(targetId);
                if (target) target.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    });
}