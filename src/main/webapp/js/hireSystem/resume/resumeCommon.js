var RESUME_CTX = {}; //공통hidden 전역변수 저장소

// 사이드바 토글
document.addEventListener('DOMContentLoaded', function() {
	RESUME_CTX.resumeId = document.getElementById('resumeId').value;
    RESUME_CTX.userNum = document.getElementById('userNum').value;

    initSidebarToggle();// 사이드바 토글
	initCompleteBtn(); //작성완료 버튼 필수값 검증
	scrollToSavedSection(); // ← 추가
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
		//섹션상태값 저장
		var optionalSections = ['#activity', '#certification', '#portfolio', '#coverLetter'];
		var visibleSections = [];
		optionalSections.forEach(function(targetId) {
		    var btn = document.querySelector('.sidebar-toggle[data-target="' + targetId + '"]');
		    if (btn && btn.classList.contains('expanded')) {
		        visibleSections.push(targetId);
		    }
		});


		var title = document.getElementById('title').value.trim();
		//if (!resumeTitle) {
		//    alert('이력서 제목을 입력해주세요.');
		//    document.getElementById('resumeTitle').focus();
		//    return;
		//}

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
// 사이드바 토글
// =============================================
function initSidebarToggle() {
    var requiredSections = ['#career', '#education', '#skills'];

    document.querySelectorAll('.sidebar-toggle').forEach(function(button) {
        var targetId = button.getAttribute('data-target');// '#portfolio'

        // 필수섹션은 클릭 막기
        if (requiredSections.indexOf(targetId) !== -1) {
            button.style.cursor = 'default';
            var icon = button.querySelector('i');
            if (icon) icon.style.display = 'none';
            return;
        }

        button.addEventListener('click', function(event) {
            event.preventDefault();
            var target = targetId ? document.querySelector(targetId) : null; /// <section id="portfolio">
            var expanded = button.classList.toggle('expanded'); // 여기서 toggle이 expanded 클래스 없으면 추가, 있으면 제거해요.

            var icon = button.querySelector('i');
            if (icon) {
                icon.classList.toggle('fa-minus', expanded); // fa-plus 제거 → + 아이콘 사라짐
                icon.classList.toggle('fa-plus', !expanded); // fa-minus 추가 → - 아이콘 생김
            }
            if (target) {
                if (expanded) {
                    target.classList.remove('hidden'); // 보이기
                    target.scrollIntoView({ behavior: 'smooth', block: 'start' });
                } else {
                    target.classList.add('hidden'); // 숨기기
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

	// sectionVisible이 null이면 데이터 있는 섹션 자동으로 열기 view부분만 해당
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

function scrollToSavedSection() {
    var targetId = sessionStorage.getItem('scrollTo');
    if (!targetId) return;
    sessionStorage.removeItem('scrollTo');  // 한 번 쓰고 바로 제거

    setTimeout(function() {
        var target = document.querySelector(targetId);
        if (target) target.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 100);
}