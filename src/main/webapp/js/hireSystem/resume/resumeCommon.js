var RESUME_CTX = {}; //공통hidden 전역변수 저장소

document.addEventListener('DOMContentLoaded', function() {
	RESUME_CTX.resumeId = document.getElementById('resumeId').value;
    RESUME_CTX.userNum = document.getElementById('userNum').value;

    initSidebarToggle();
	initCompleteBtn();
	scrollToSavedSection();
});


// =============================================
// 편집중 섹션 감지 (작성완료 체크용)
// =============================================
var DIRTY_SECTION = null;

function setDirty(sectionName) {
    DIRTY_SECTION = sectionName;
}
function clearDirty() {
    DIRTY_SECTION = null;
}
// =============================================
// 작성완료 버튼
// =============================================
function initCompleteBtn() {
    var completeBtn = document.getElementById('completeBtn');
    if (!completeBtn) return;

    completeBtn.addEventListener('click', function() {

        // 1. 편집중 섹션 체크
        if (DIRTY_SECTION) {
            alert(DIRTY_SECTION + ' 항목을 먼저 저장해주세요.');
            return;
        }

        // 2. 필수섹션 체크
        var educationList = document.querySelectorAll('.education-entry:not(.education-add-form)');
        var careerList = document.querySelectorAll('.career-entry:not(.career-add-form)');
        var skillList = document.querySelectorAll('#skillTagWrap .skill-tag');

        if (careerList.length === 0) {
            alert('경력을 입력해주세요.');
            document.getElementById('career').scrollIntoView({ behavior: 'smooth' });
            return;
        }

        if (educationList.length === 0) {
            alert('학력을 입력해주세요.');
            document.getElementById('education').scrollIntoView({ behavior: 'smooth' });
            return;
        }
        if (skillList.length === 0) {
            alert('스킬을 입력해주세요.');
            document.getElementById('skills').scrollIntoView({ behavior: 'smooth' });
            return;
        }

        // 3. 완료처리
		var visibleSections = getVisibleOptionalSections();
		var title = document.getElementById('title').value.trim();

		var formData = new FormData();
		formData.append('resumeId', RESUME_CTX.resumeId);
		formData.append('title',    title);
		formData.append('sectionVisible', visibleSections.join(','));
        fetch('/hireSystem/resume/resumeSave.do', {
		    method: 'POST',
		    body: formData
		})
		.then(function(res) { return res.json(); })
		.then(function(data) {
		    if (data.result) {
				alert("이력서가 저장되었습니다");
		        location.href = '/hireSystem/resume/resumeMain.do';

		    } else {
		        alert('저장에 실패했습니다.');
		    }
		});
    });
}

// =============================================
// 섹션 표시상태 저장 (개별 섹션 저장 성공 시 호출)
// - 옵션 섹션(activity/certification/portfolio/coverLetter) 중
//   현재 화면에서 펼쳐져 있는 섹션 목록을 서버로 보내고,
//   서버는 실제 DB 데이터가 있는 섹션만 필터링해서 저장한다.
// =============================================
function getVisibleOptionalSections() {
    var optionalSections = ['#activity', '#certification', '#portfolio', '#coverLetter'];
    var visibleSections = [];
    optionalSections.forEach(function(targetId) {
        var btn = document.querySelector('.sidebar-toggle[data-target="' + targetId + '"]');
        if (btn && btn.classList.contains('expanded')) {
            visibleSections.push(targetId);
        }
    });
    return visibleSections;
}

// =============================================
// 사이드바 토글
// =============================================
function initSidebarToggle() {
    var requiredSections = ['#career', '#education', '#skills'];

    document.querySelectorAll('.sidebar-toggle').forEach(function(button) {
        var targetId = button.getAttribute('data-target');

        // 필수섹션은 클릭 막기
        if (requiredSections.indexOf(targetId) !== -1) {
            button.style.cursor = 'default';
            var icon = button.querySelector('i');
            if (icon) icon.style.display = 'none';
            return;
        }

        button.addEventListener('click', function(event) {
            event.preventDefault();
            var target = targetId ? document.querySelector(targetId) : null;
            var expanded = button.classList.toggle('expanded');

            var icon = button.querySelector('i');
            if (icon) {
                icon.classList.toggle('fa-minus', expanded);
                icon.classList.toggle('fa-plus', !expanded);
            }
            if (target) {
                if (expanded) {
                    target.classList.remove('hidden');
                    target.scrollIntoView({ behavior: 'smooth', block: 'start' });
                } else {
                    target.classList.add('hidden');
                }
            }
        });
    });
    initSidebarState();
}

function initSidebarState() {
    var sectionVisible = document.getElementById('sectionVisible').value || '';
    var visibleArr = sectionVisible ? sectionVisible.split(',') : [];
    var requiredSections = ['#career', '#education', '#skills'];

	// sectionVisible이 null이면 데이터 있는 섹션 자동으로 열기 (view부분만 해당)
	var sectionDataMap = {
	    '#activity':      '.activity-entry:not(.activity-add-form)',
	    '#certification': '.certification-entry:not(.certification-add-form)',
	    '#portfolio':     '.portfolio-entry:not(.portfolio-add-form)',
	    '#coverLetter':   '.cover-letter-entry:not(.cover-letter-add-form)'
	};

    if (!sectionVisible) {
        Object.keys(sectionDataMap).forEach(function(targetId) {
            var selector = sectionDataMap[targetId];
            if (document.querySelectorAll(selector).length > 0) {
                visibleArr.push(targetId);
            }
        });
    }

    document.querySelectorAll('.sidebar-toggle').forEach(function(button) {
        var targetId = button.getAttribute('data-target');
        if (!targetId) return;
        var target  = document.querySelector(targetId);
        var icon    = button.querySelector('i');
        var isRequired = requiredSections.indexOf(targetId) !== -1;
        var isVisible  = isRequired || visibleArr.indexOf(targetId) !== -1;

        if (isVisible) {
            button.classList.add('expanded');
            button.setAttribute('aria-expanded', true);
            if (icon) { icon.classList.add('fa-minus'); icon.classList.remove('fa-plus'); }
            if (target) target.classList.remove('hidden');
        } else {
            button.classList.remove('expanded');
            button.setAttribute('aria-expanded', false);
            if (icon) { icon.classList.add('fa-plus'); icon.classList.remove('fa-minus'); }
            if (target) target.classList.add('hidden');
        }
    });
}

document.addEventListener('DOMContentLoaded', function () {
    var previewBtn = document.getElementById('previewBtn');
    if (!previewBtn) return;

    previewBtn.addEventListener('click', function () {
        var resumeId = document.getElementById('resumeId').value;
        window.open('/hireSystem/resume/print.do?resumeId=' + resumeId, '_blank');
    });
});

function scrollToSavedSection() {
    var targetId = sessionStorage.getItem('scrollTo');
    if (!targetId) return;
    sessionStorage.removeItem('scrollTo');  // 한 번 쓰고 바로 제거

    setTimeout(function() {
        var target = document.querySelector(targetId);
        if (target) target.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 100);
}