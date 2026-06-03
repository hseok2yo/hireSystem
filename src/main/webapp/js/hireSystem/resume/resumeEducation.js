/**
 * resumeEducation.js
 */

document.addEventListener('DOMContentLoaded', function() {
    initResumeEducation();
});

function initResumeEducation() {

    var educationSection     = document.getElementById('education');
    if (!educationSection) return;

    var educationAddButton   = document.querySelector('.education-add-btn');
    var educationForm        = document.getElementById('educationForm');
    var educationAddForm     = document.getElementById('educationAddForm');
    var educationListWrapper = document.getElementById('educationListWrapper');
    var cancelButton         = document.querySelector('.cancel-education-add');
    var saveButton           = document.querySelector('.save-education-add');
    var currentCheckbox      = educationAddForm ? educationAddForm.querySelector('[name="currentYn"]') : null;
    var endDateInput         = educationAddForm ? educationAddForm.querySelector('[name="endDate"]')    : null;

    // 추가 버튼
    if (educationAddButton) {
        educationAddButton.addEventListener('click', function(e) {
            e.preventDefault();
            educationForm.dataset.educationId = '';  // 추가모드 초기화
            resetEducationForm(educationAddForm);
            showEducationAddForm(educationAddForm, educationListWrapper);
        });
    }

    // 취소 버튼
    if (cancelButton) {
        cancelButton.addEventListener('click', function(e) {
            e.preventDefault();
            hideEducationAddForm(educationAddForm, educationListWrapper);
        });
    }

    // 저장 버튼
    if (saveButton) {
        saveButton.addEventListener('click', function(e) {
            e.preventDefault();
            saveEducationEntry(educationForm, educationAddForm, educationListWrapper);
        });
    }

    // 재학중 체크 → 졸업년월 비활성화
    if (currentCheckbox && endDateInput) {
        currentCheckbox.addEventListener('change', function() {
            endDateInput.disabled = currentCheckbox.checked;
            if (currentCheckbox.checked) endDateInput.value = '';
        });
    }

    // 수정/삭제 버튼 (초기 목록)
    bindEducationListEvents(educationListWrapper, educationForm, educationAddForm);
}


/** 폼 열기 */
function showEducationAddForm(educationAddForm, educationListWrapper) {
    educationAddForm.classList.remove('hidden');
    educationListWrapper.classList.add('hidden');
}

/** 폼 닫기 + 초기화 */
function hideEducationAddForm(educationAddForm, educationListWrapper) {
    educationAddForm.classList.add('hidden');
    educationListWrapper.classList.remove('hidden');
    resetEducationForm(educationAddForm);
}

/** 입력값 초기화 */
function resetEducationForm(educationAddForm) {
    educationAddForm.querySelectorAll('input, select, textarea').forEach(function(el) {
        if (el.type === 'checkbox') el.checked = false;
        else el.value = '';
    });
}


/** 저장 (등록 / 수정 공통) */
function saveEducationEntry(educationForm, educationAddForm, educationListWrapper) {

    var educationId = educationForm.dataset.educationId || '';  // 있으면 수정, 없으면 등록

    var formData = new FormData(educationForm);

    formData.append('resumeId', RESUME_CTX.resumeId);
    formData.append('userNum',  RESUME_CTX.userNum);
    if (educationId) formData.append('educationId', educationId);

    // currentYn 체크박스 → Y/N 변환
    var checkbox = educationAddForm.querySelector('[name="currentYn"]');
    formData.set('currentYn', checkbox && checkbox.checked ? 'Y' : 'N');

    fetch('/hireSystem/resume/educationSave.do', {
        method: 'POST',
        body: formData
    })
    .then(function(res) { return res.json(); })
    .then(function(data) {
        if (data.result) {
            alert(data.message);
            location.href = '/hireSystem/resume/edit.do?resumeId=' + data.resumeId;
        } else {
            alert(data.message || '저장에 실패했습니다.');
        }
    })
    .catch(function(err) {
        console.error(err);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    });
}


/** 수정 버튼 클릭 → 폼에 기존 값 세팅 후 열기 */
function handleEducationEdit(btn, educationForm, educationAddForm, educationListWrapper) {

    var article = btn.closest('.education-entry');

    // educationForm에 educationId 저장 (수정모드 구분)
    educationForm.dataset.educationId = article.dataset.educationId;

    // 기존 값 폼에 채우기
    educationAddForm.querySelector('[name="schoolName"]').value    = article.dataset.schoolName    || '';
    educationAddForm.querySelector('[name="schoolType"]').value    = article.dataset.schoolType    || '';
    educationAddForm.querySelector('[name="major"]').value         = article.dataset.major         || '';
    educationAddForm.querySelector('[name="subMajor"]').value      = article.dataset.subMajor      || '';
    educationAddForm.querySelector('[name="grade"]').value         = article.dataset.grade         || '';
    educationAddForm.querySelector('[name="gradeMax"]').value      = article.dataset.gradeMax      || '';
    educationAddForm.querySelector('[name="location"]').value      = article.dataset.location      || '';
    educationAddForm.querySelector('[name="graduateType"]').value  = article.dataset.graduateType  || '';
    educationAddForm.querySelector('[name="startDate"]').value     = article.dataset.startDate     || '';
    educationAddForm.querySelector('[name="endDate"]').value       = article.dataset.endDate       || '';

    var checkbox     = educationAddForm.querySelector('[name="currentYn"]');
    var endDateInput = educationAddForm.querySelector('[name="endDate"]');
    checkbox.checked      = article.dataset.currentYn === 'Y';
    endDateInput.disabled = checkbox.checked;

    showEducationAddForm(educationAddForm, educationListWrapper);
}


/** 삭제 */
function handleEducationDelete(btn) {

    if (!confirm('학력을 삭제하시겠습니까?')) return;

    var article      = btn.closest('.education-entry');
    var educationId  = article.dataset.educationId;

    var formData = new FormData();
    formData.append('educationId', educationId);
    formData.append('resumeId',    RESUME_CTX.resumeId);
    formData.append('userNum',     RESUME_CTX.userNum);

    fetch('/hireSystem/resume/educationDelete.do', {
        method: 'POST',
        body: formData
    })
    .then(function(res) { return res.json(); })
    .then(function(data) {
        if (data.result) {
            alert(data.message);
            location.reload();
        } else {
            alert(data.message || '삭제에 실패했습니다.');
        }
    })
    .catch(function(err) {
        console.error(err);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    });
}


/** 목록의 수정/삭제 이벤트 바인딩 */
function bindEducationListEvents(educationListWrapper, educationForm, educationAddForm) {
    educationListWrapper.addEventListener('click', function(e) {

        var editBtn   = e.target.closest('.btn-edit-education');
        var deleteBtn = e.target.closest('.btn-delete-education');

        if (editBtn)   handleEducationEdit(editBtn, educationForm, educationAddForm, educationListWrapper);
        if (deleteBtn) handleEducationDelete(deleteBtn);
    });
}