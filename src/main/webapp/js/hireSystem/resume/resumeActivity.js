/**
 * resumeActivity.js
 * 경험/활동/교육 섹션 CRUD
 */

document.addEventListener('DOMContentLoaded', function () {
    initResumeActivity();
});

function initResumeActivity() {

    var activitySection     = document.getElementById('activity');
    if (!activitySection) return;

    var activityAddButton   = activitySection.querySelector('.activity-add-btn');
    var activityForm        = document.getElementById('activityForm');
    var activityAddForm     = document.getElementById('activityAddForm');
    var activityListWrapper = document.getElementById('activityListWrapper');
    var cancelButton        = activitySection.querySelector('.cancel-activity-add');
    var saveButton          = activitySection.querySelector('.save-activity-add');
    var currentCheckbox     = activityAddForm ? activityAddForm.querySelector('[name="currentYn"]') : null;
    var endDateInput        = activityAddForm ? activityAddForm.querySelector('[name="endDate"]')   : null;

    // 추가 버튼
    if (activityAddButton) {
        activityAddButton.addEventListener('click', function (e) {
            e.preventDefault();
            activityForm.dataset.activityId = '';   // 추가모드 초기화
            resetActivityForm(activityAddForm);
            showActivityAddForm(activityAddForm, activityListWrapper);
        });
    }

    // 취소 버튼
    if (cancelButton) {
        cancelButton.addEventListener('click', function (e) {
            e.preventDefault();
            hideActivityAddForm(activityAddForm, activityListWrapper);
        });
    }

    // 저장 버튼
    if (saveButton) {
        saveButton.addEventListener('click', function (e) {
            e.preventDefault();
            saveActivityEntry(activityForm, activityAddForm, activityListWrapper);
        });
    }

    // 진행중 체크 → 종료년월 비활성화
    if (currentCheckbox && endDateInput) {
        currentCheckbox.addEventListener('change', function () {
            endDateInput.disabled = currentCheckbox.checked;
            if (currentCheckbox.checked) endDateInput.value = '';
        });
    }

    // 수정/삭제 이벤트 위임
    bindActivityListEvents(activityListWrapper, activityForm, activityAddForm);
}


/* ── 폼 열기 / 닫기 ──────────────────────────────────────── */

function showActivityAddForm(activityAddForm, activityListWrapper) {
	setDirty('경험/활동/교육');
    activityAddForm.classList.remove('hidden');
    activityListWrapper.classList.add('hidden');
}

function hideActivityAddForm(activityAddForm, activityListWrapper) {
	clearDirty(); //섹션 열린거 체크초기화
    activityAddForm.classList.add('hidden');
    activityListWrapper.classList.remove('hidden');
    resetActivityForm(activityAddForm);
}

function resetActivityForm(activityAddForm) {
    activityAddForm.querySelectorAll('input, select, textarea').forEach(function (el) {
        if (el.type === 'checkbox') el.checked = false;
        else el.value = '';
    });
    // 종료년월 재활성화
    var endDate = activityAddForm.querySelector('[name="endDate"]');
    if (endDate) endDate.disabled = false;
}


/* ── 저장 (등록 / 수정 공통) ──────────────────────────────── */

function saveActivityEntry(activityForm, activityAddForm, activityListWrapper) {

    var activityId = activityForm.dataset.activityId || '';

    var formData = new FormData(activityForm);
    // 신규 이력서 생성 중이면 RESUME_CTX.resumeId가 빈 값일 수 있음.
    // 그래도 그대로 보내면 백엔드(getOrCreateResumeId)가 resume를 새로 만들어서 처리해줌.
    formData.append('resumeId', RESUME_CTX.resumeId);
    formData.append('userNum',  RESUME_CTX.userNum);
    if (activityId) formData.append('activityId', activityId);
    formData.append('sectionVisible', getVisibleOptionalSections().join(','));

    // currentYn 체크박스 → Y/N 변환
    var checkbox = activityAddForm.querySelector('[name="currentYn"]');
    formData.set('currentYn', checkbox && checkbox.checked ? 'Y' : 'N');

    fetch('/hireSystem/resume/activitySave.do', {
        method: 'POST',
        body: formData
    })
    .then(function (res) { return res.json(); })
    .then(function (data) {
        if (data.result) {
            alert(data.message);
			sessionStorage.setItem('scrollTo', '#activity');  // ← 추가
            location.href = '/hireSystem/resume/edit.do?resumeId=' + data.resumeId;
        } else {
            alert(data.message || '저장에 실패했습니다.');
        }
    })
    .catch(function (err) {
        console.error(err);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    });
}


/* ── 수정 버튼 ────────────────────────────────────────────── */

function handleActivityEdit(btn, activityForm, activityAddForm, activityListWrapper) {

    var article = btn.closest('.activity-entry');

    activityForm.dataset.activityId = article.dataset.activityId;

    activityAddForm.querySelector('[name="activityType"]').value = article.dataset.activityType || '';
    activityAddForm.querySelector('[name="orgName"]').value      = article.dataset.orgName      || '';
    activityAddForm.querySelector('[name="startDate"]').value    = article.dataset.startDate    || '';
    activityAddForm.querySelector('[name="endDate"]').value      = article.dataset.endDate      || '';
    activityAddForm.querySelector('[name="content"]').value      = article.dataset.content      || '';

    var checkbox     = activityAddForm.querySelector('[name="currentYn"]');
    var endDateInput = activityAddForm.querySelector('[name="endDate"]');
    checkbox.checked      = article.dataset.currentYn === 'Y';
    endDateInput.disabled = checkbox.checked;

    showActivityAddForm(activityAddForm, activityListWrapper);
}


/* ── 삭제 버튼 ────────────────────────────────────────────── */

function handleActivityDelete(btn) {

    if (!confirm('경험/활동/교육을 삭제하시겠습니까?')) return;

    var article    = btn.closest('.activity-entry');
    var activityId = article.dataset.activityId;

    var formData = new FormData();
    formData.append('activityId', activityId);
    formData.append('resumeId',   RESUME_CTX.resumeId);
    formData.append('userNum',    RESUME_CTX.userNum);
    formData.append('sectionVisible', getVisibleOptionalSections().join(','));

    fetch('/hireSystem/resume/activityDelete.do', {
        method: 'POST',
        body: formData
    })
    .then(function (res) { return res.json(); })
    .then(function (data) {
        if (data.result) {
            alert(data.message);
            location.reload();
        } else {
            alert(data.message || '삭제에 실패했습니다.');
        }
    })
    .catch(function (err) {
        console.error(err);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    });
}


/* ── 이벤트 위임 바인딩 ───────────────────────────────────── */

function bindActivityListEvents(activityListWrapper, activityForm, activityAddForm) {
    activityListWrapper.addEventListener('click', function (e) {
        var editBtn   = e.target.closest('.btn-edit-activity');
        var deleteBtn = e.target.closest('.btn-delete-activity');

        if (editBtn)   handleActivityEdit(editBtn, activityForm, activityAddForm, activityListWrapper);
        if (deleteBtn) handleActivityDelete(deleteBtn);
    });
}