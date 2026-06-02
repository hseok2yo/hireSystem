/**
 * resumeCareer.js
 */

document.addEventListener('DOMContentLoaded', function() {
    initResumeCareer();
});

function initResumeCareer() {

    var careerSection     = document.getElementById('career');
    if (!careerSection) return;

    var careerAddButton   = document.querySelector('.career-add-btn');
    var careerForm        = document.getElementById('careerForm');
    var careerAddForm     = document.getElementById('careerAddForm');
    var careerListWrapper = document.getElementById('careerListWrapper');
    var cancelButton      = document.querySelector('.cancel-career-add');
    var saveButton        = document.querySelector('.save-career-add');
    var currentCheckbox   = careerAddForm ? careerAddForm.querySelector('[name="currentYn"]') : null;
    var endDateInput      = careerAddForm ? careerAddForm.querySelector('[name="endDate"]')    : null;

    // 추가 버튼
    if (careerAddButton) {
        careerAddButton.addEventListener('click', function(e) {
            e.preventDefault();
            careerForm.dataset.careerId = '';   // 추가모드 초기화
            resetCareerForm(careerAddForm);
            showCareerAddForm(careerAddForm, careerListWrapper);
        });
    }

    // 취소 버튼
    if (cancelButton) {
        cancelButton.addEventListener('click', function(e) {
            e.preventDefault();
            hideCareerAddForm(careerAddForm, careerListWrapper);
        });
    }

    // 저장 버튼
    if (saveButton) {
        saveButton.addEventListener('click', function(e) {
            e.preventDefault();
            saveCareerEntry(careerForm, careerAddForm, careerListWrapper);
        });
    }

    // 재직중 체크 → 퇴사년월 비활성화
    if (currentCheckbox && endDateInput) {
        currentCheckbox.addEventListener('change', function() {
            endDateInput.disabled = currentCheckbox.checked;
            if (currentCheckbox.checked) endDateInput.value = '';
        });
    }

    // 수정/삭제 버튼 (초기 목록)
    bindCareerListEvents(careerListWrapper, careerForm, careerAddForm);
}


/** 폼 열기 */
function showCareerAddForm(careerAddForm, careerListWrapper) {
    careerAddForm.classList.remove('hidden');
    careerListWrapper.classList.add('hidden');
}

/** 폼 닫기 + 초기화 */
function hideCareerAddForm(careerAddForm, careerListWrapper) {
    careerAddForm.classList.add('hidden');
    careerListWrapper.classList.remove('hidden');
    resetCareerForm(careerAddForm);
}

/** 입력값 초기화 */
function resetCareerForm(careerAddForm) {
    careerAddForm.querySelectorAll('input, textarea').forEach(function(el) {
        if (el.type === 'checkbox') el.checked = false;
        else el.value = '';
    });
}


/** 저장 (등록 / 수정 공통) */
function saveCareerEntry(careerForm, careerAddForm, careerListWrapper) {

    var careerId = careerForm.dataset.careerId || '';   // 있으면 수정, 없으면 등록

    var formData = new FormData(careerForm);

    formData.append('resumeId', RESUME_CTX.resumeId);
    formData.append('userNum',  RESUME_CTX.userNum);
    if (careerId) formData.append('careerId', careerId);

    // currentYn 체크박스 → Y/N 변환
    var checkbox = careerAddForm.querySelector('[name="currentYn"]');
    formData.set('currentYn', checkbox && checkbox.checked ? 'Y' : 'N');

    fetch('/hireSystem/resume/careerSave.do', {
        method: 'POST',
        body: formData
    })
    .then(function(res) { return res.json(); })
    .then(function(data) {
        if (data.result) {
            alert(data.message);
            location.href = '/hireSystem/resume/edit.do?resumeId=' + data.resumeId;
			//location.reload();
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
function handleCareerEdit(btn, careerForm, careerAddForm, careerListWrapper) {

    var article = btn.closest('.career-entry');

    // careerForm에 careerId 저장 (수정모드 구분)
    careerForm.dataset.careerId = article.dataset.careerId;

    // 기존 값 폼에 채우기
    careerAddForm.querySelector('[name="companyName"]').value  = article.dataset.companyName  || '';
    careerAddForm.querySelector('[name="startDate"]').value    = article.dataset.startDate    || '';
    careerAddForm.querySelector('[name="endDate"]').value      = article.dataset.endDate      || '';
    careerAddForm.querySelector('[name="jobTitle"]').value     = article.dataset.jobTitle     || '';
    careerAddForm.querySelector('[name="department"]').value   = article.dataset.department   || '';
    careerAddForm.querySelector('[name="positionName"]').value = article.dataset.positionName || '';
    careerAddForm.querySelector('[name="duties"]').value       = article.dataset.duties       || '';

    var checkbox = careerAddForm.querySelector('[name="currentYn"]');
    var endDateInput = careerAddForm.querySelector('[name="endDate"]');
    checkbox.checked     = article.dataset.currentYn === 'Y';
    endDateInput.disabled = checkbox.checked;

    showCareerAddForm(careerAddForm, careerListWrapper);
}


/** 삭제 */
function handleCareerDelete(btn) {

    if (!confirm('경력을 삭제하시겠습니까?')) return;

    var article  = btn.closest('.career-entry');
    var careerId = article.dataset.careerId;

    var formData = new FormData();
    formData.append('careerId',  careerId);
    formData.append('resumeId',  RESUME_CTX.resumeId);
    formData.append('userNum',   RESUME_CTX.userNum);

    fetch('/hireSystem/resume/careerDelete.do', {
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


/** 목록의 수정/삭제 이벤트 바인딩 (동적 추가 대비 wrapper에 위임) */
function bindCareerListEvents(careerListWrapper, careerForm, careerAddForm) {
    careerListWrapper.addEventListener('click', function(e) {

        var editBtn   = e.target.closest('.btn-edit-career');
        var deleteBtn = e.target.closest('.btn-delete-career');

        if (editBtn)   handleCareerEdit(editBtn, careerForm, careerAddForm, careerListWrapper);
        if (deleteBtn) handleCareerDelete(deleteBtn);
    });
}