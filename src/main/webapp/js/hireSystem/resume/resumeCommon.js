// 사이드바 토글
document.addEventListener('DOMContentLoaded', function() {
	
    initSidebarToggle();// 사이드바 토글
});


// 섹션 안의 input 긁어서 FormData 만들기
function buildFormData(sectionId) {
    var formData = new FormData();
    var hidden   = getResumeHidden();

    // 공통 hidden 자동으로 추가
    formData.append('resumeId', hidden.resumeId);
    formData.append('userNum',  hidden.userNum);

    // 섹션 안 input/select/textarea 자동 수집
    var section = document.getElementById(sectionId);
    section.querySelectorAll('input, select, textarea').forEach(function(el) {
        if (el.name) formData.append(el.name, el.value);
    });

    return formData;
}


// resumeId, userNum 공통으로 읽어오기
function getResumeHidden() {
    return {
        resumeId : document.querySelector('input[name="resumeId"]').value,
        userNum  : document.querySelector('input[name="userNum"]').value
    };
}


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