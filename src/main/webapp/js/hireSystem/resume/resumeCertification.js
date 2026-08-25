/**
 * resumeCertification.js
 * 자격/어학/수상 섹션 처리
 */

document.addEventListener('DOMContentLoaded', function() {
    initResumeCertification();
});

function initResumeCertification() {

    var certSection      = document.getElementById('certification');
    if (!certSection) return;

    var certAddButton    = document.querySelector('.certification-add-btn');
    var certForm         = document.getElementById('certificationForm');
    var certAddForm      = document.getElementById('certificationAddForm');
    var certListWrapper  = document.getElementById('certificationListWrapper');
    var cancelButton     = document.querySelector('.cancel-certification-add');
    var saveButton       = document.querySelector('.save-certification-add');

    // 추가 버튼
    if (certAddButton) {
        certAddButton.addEventListener('click', function(e) {
            e.preventDefault();
            certForm.dataset.certificationId = '';  // 추가모드 초기화
            resetCertificationForm(certAddForm);
            showCertificationAddForm(certAddForm, certListWrapper);
        });
    }

    // 취소 버튼
    if (cancelButton) {
        cancelButton.addEventListener('click', function(e) {
            e.preventDefault();
            hideCertificationAddForm(certAddForm, certListWrapper);
        });
    }

    // 저장 버튼
    if (saveButton) {
        saveButton.addEventListener('click', function(e) {
            e.preventDefault();
            saveCertificationEntry(certForm, certAddForm, certListWrapper);
        });
    }

    // 수정/삭제 버튼 (초기 목록)
    bindCertificationListEvents(certListWrapper, certForm, certAddForm);
}


/** 폼 열기 */
function showCertificationAddForm(certAddForm, certListWrapper) {
	setDirty('자격/어학/수상');
    certAddForm.classList.remove('hidden');
    certListWrapper.classList.add('hidden');
}

/** 폼 닫기 + 초기화 */
function hideCertificationAddForm(certAddForm, certListWrapper) {
	clearDirty(); //섹션 열린거 체크초기화
    certAddForm.classList.add('hidden');
    certListWrapper.classList.remove('hidden');
    resetCertificationForm(certAddForm);
}

/** 입력값 초기화 */
function resetCertificationForm(certAddForm) {
    certAddForm.querySelectorAll('input, select, textarea').forEach(function(el) {
        if (el.type === 'checkbox') el.checked = false;
        else el.value = '';
    });
}


/** 저장 (등록 / 수정 공통) */
function saveCertificationEntry(certForm, certAddForm, certListWrapper) {

    var certificationId = certForm.dataset.certificationId || '';  // 있으면 수정, 없으면 등록

    // 유효성 검사 - 자격증명 필수
    var certNameEl = certAddForm.querySelector('[name="certName"]');
    if (!certNameEl || !certNameEl.value.trim()) {
        alert('자격증명을 입력해주세요.');
        certNameEl && certNameEl.focus();
        return;
    }

    var formData = new FormData(certForm);

    // 신규 이력서 생성 중이면 RESUME_CTX.resumeId가 빈 값일 수 있음.
    // 그래도 그대로 보내면 백엔드(getOrCreateResumeId)가 resume를 새로 만들어서 처리해줌.
    formData.append('resumeId', RESUME_CTX.resumeId);
    formData.append('userNum',  RESUME_CTX.userNum);
    if (certificationId) formData.append('certificationId', certificationId);
    formData.append('sectionVisible', getVisibleOptionalSections().join(','));

    fetch('/hireSystem/resume/certificationSave.do', {
        method: 'POST',
        body: formData
    })
    .then(function(res) { return res.json(); })
    .then(function(data) {
        if (data.result) {
            alert(data.message);
			sessionStorage.setItem('scrollTo', '#certification');
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
function handleCertificationEdit(btn, certForm, certAddForm, certListWrapper) {

    var article = btn.closest('.certification-entry');

    // certForm 에 certificationId 저장 (수정모드 구분)
    certForm.dataset.certificationId = article.dataset.certificationId;

    // 기존 값 폼에 채우기
    certAddForm.querySelector('[name="certName"]').value      = article.dataset.certName      || '';
    certAddForm.querySelector('[name="issuer"]').value        = article.dataset.issuer        || '';
    certAddForm.querySelector('[name="acquiredDate"]').value  = article.dataset.acquiredDate  || '';
    certAddForm.querySelector('[name="certNumber"]').value    = article.dataset.certNumber    || '';
    certAddForm.querySelector('[name="score"]').value         = article.dataset.score         || '';

    showCertificationAddForm(certAddForm, certListWrapper);
}


/** 삭제 */
function handleCertificationDelete(btn) {

    if (!confirm('자격사항을 삭제하시겠습니까?')) return;

    var article         = btn.closest('.certification-entry');
    var certificationId = article.dataset.certificationId;

    var formData = new FormData();
    formData.append('certificationId', certificationId);
    formData.append('resumeId',        RESUME_CTX.resumeId);
    formData.append('userNum',         RESUME_CTX.userNum);
    formData.append('sectionVisible', getVisibleOptionalSections().join(','));

    fetch('/hireSystem/resume/certificationDelete.do', {
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
function bindCertificationListEvents(certListWrapper, certForm, certAddForm) {
    certListWrapper.addEventListener('click', function(e) {

        var editBtn   = e.target.closest('.btn-edit-certification');
        var deleteBtn = e.target.closest('.btn-delete-certification');

        if (editBtn)   handleCertificationEdit(editBtn, certForm, certAddForm, certListWrapper);
        if (deleteBtn) handleCertificationDelete(deleteBtn);
    });
}