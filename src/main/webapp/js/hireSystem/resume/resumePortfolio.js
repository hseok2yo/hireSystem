/**
 * resumePortfolio.js
 * 포트폴리오/기타문서 - 모달 방식
 */

// 현재 수정 중인 portfolioId (null = 신규 등록)
var currentPortfolioId = null;

document.addEventListener('DOMContentLoaded', function() {
    initResumePortfolio();
});

function initResumePortfolio() {

    var section = document.getElementById('portfolio');
    if (!section) return;

    var modal       = document.getElementById('portfolioModal');
    var addBtn      = document.getElementById('portfolioAddBtn');
    var closeBtn    = document.getElementById('portfolioModalClose');
    var cancelBtn   = document.getElementById('pmCancelBtn');
    var saveBtn     = document.getElementById('pmSaveBtn');

    var fileBtn     = document.getElementById('pmFileBtn');
    var fileInput   = document.getElementById('pmFileInput');
    var fileName    = document.getElementById('pmFileName');
    var fileWrap    = document.getElementById('pmFileWrap');
    var urlWrap     = document.getElementById('pmUrlWrap');

    var listWrapper = document.getElementById('portfolioListWrapper');

    // ── 모달 열기 (신규) ──────────────────────────
    addBtn.addEventListener('click', function() {
        currentPortfolioId = null;
        resetModal();
        openModal();
    });

    // ── 모달 닫기 ────────────────────────────────
    closeBtn.addEventListener('click', closeModal);
    cancelBtn.addEventListener('click', closeModal);

    // 오버레이 클릭 시 닫기
    modal.addEventListener('click', function(e) {
        if (e.target === modal) closeModal();
    });

    // ── 파일 / URL 라디오 전환 ───────────────────
    document.querySelectorAll('[name="pmFileType"]').forEach(function(radio) {
        radio.addEventListener('change', function() {
            if (this.value === 'file') {
                fileWrap.classList.remove('hidden');
                urlWrap.classList.add('hidden');
            } else {
                fileWrap.classList.add('hidden');
                urlWrap.classList.remove('hidden');
            }
        });
    });

    // ── 파일 선택 버튼 ───────────────────────────
    fileBtn.addEventListener('click', function() {
        fileInput.click();
    });

    fileInput.addEventListener('change', function() {
        if (fileInput.files && fileInput.files.length > 0) {
            fileName.textContent = fileInput.files[0].name;
            fileName.classList.add('has-file');
        } else {
            fileName.textContent = '선택된 파일 없음';
            fileName.classList.remove('has-file');
        }
    });

    // ── 등록 버튼 ────────────────────────────────
    saveBtn.addEventListener('click', function() {
        savePortfolio();
    });

    // ── 목록 수정/삭제 이벤트 위임 ───────────────
    listWrapper.addEventListener('click', function(e) {
        var editBtn   = e.target.closest('.btn-edit-portfolio');
        var deleteBtn = e.target.closest('.btn-delete-portfolio');
        if (editBtn)   openEditModal(editBtn.closest('.portfolio-entry'));
        if (deleteBtn) deletePortfolio(deleteBtn.closest('.portfolio-entry'));
    });
}


/* 모달 열기 */
function openModal() {
    document.getElementById('portfolioModal').classList.remove('hidden');
}

/* 모달 닫기 */
function closeModal() {
    document.getElementById('portfolioModal').classList.add('hidden');
    resetModal();
}

/* 모달 초기화 */
function resetModal() {
    currentPortfolioId = null;

    document.getElementById('pmFileCategory').value = ''; //파일구분

    // 파일 라디오 초기화
    var fileRadio = document.querySelector('[name="pmFileType"][value="file"]');
    if (fileRadio) fileRadio.checked = true;

    // 파일 영역 표시, URL 숨김
    document.getElementById('pmFileWrap').classList.remove('hidden');
    document.getElementById('pmUrlWrap').classList.add('hidden');

    // 파일 초기화
    var fileInput = document.getElementById('pmFileInput');
    var fileName  = document.getElementById('pmFileName');
    fileInput.value = '';
    fileName.textContent = '선택된 파일 없음';
    fileName.classList.remove('has-file');

    // URL 초기화
    document.getElementById('pmUrlInput').value = '';

    // 제목 원복
    document.querySelector('.portfolio-modal-box h3').textContent = '포트폴리오/기타문서 추가';
}

/* 수정 버튼 → 모달에 기존 값 세팅 후 열기 */
function openEditModal(article) {
    currentPortfolioId = article.dataset.portfolioId;

    var fileCategory = article.dataset.fileCategory || '';
    var fileType     = article.dataset.fileType     || 'file';
    var originalName = article.dataset.originalName || '';
    var portfolioUrl = article.dataset.portfolioUrl || '';

    document.querySelector('.portfolio-modal-box h3').textContent = '포트폴리오/기타문서 수정';
    document.getElementById('pmFileCategory').value = fileCategory;

    // 파일/URL 라디오 세팅
    var fileTypeRadio = document.querySelector('[name="pmFileType"][value="' + fileType + '"]');
    if (fileTypeRadio) fileTypeRadio.checked = true;

    var fileWrap = document.getElementById('pmFileWrap');
    var urlWrap  = document.getElementById('pmUrlWrap');
    var fileName = document.getElementById('pmFileName');
    var urlInput = document.getElementById('pmUrlInput');

    if (fileType === 'url') {
        fileWrap.classList.add('hidden');
        urlWrap.classList.remove('hidden');
        urlInput.value = portfolioUrl;
    } else {
        fileWrap.classList.remove('hidden');
        urlWrap.classList.add('hidden');
        if (originalName) {
            fileName.textContent = originalName;
            fileName.classList.add('has-file');
        }
    }

    openModal();
}


/* 저장 (신규 / 수정 공통) */
function savePortfolio() {

    var fileCategory = document.getElementById('pmFileCategory').value;
    if (!fileCategory) { alert('파일구분을 선택해주세요.'); return; }

    var fileType  = document.querySelector('[name="pmFileType"]:checked').value;
    var fileInput = document.getElementById('pmFileInput');
    var urlInput  = document.getElementById('pmUrlInput');

    if (fileType === 'file') {
        // 신규일 때만 파일 필수
        if (!currentPortfolioId && (!fileInput.files || fileInput.files.length === 0)) {
            alert('파일을 선택해주세요.');
            return;
        }
    } else {
        if (!urlInput.value.trim()) { alert('URL을 입력해주세요.'); return; }
    }

    var formData = new FormData();
    formData.append('resumeId',     RESUME_CTX.resumeId);
    formData.append('userNum',      RESUME_CTX.userNum);
    formData.append('fileCategory', fileCategory);
    formData.append('fileType',     fileType);

	//파일일때만 파일보내고 url이면 url보냄
    if (fileType === 'file' && fileInput.files && fileInput.files.length > 0) {
        formData.append('portfolioFile', fileInput.files[0]);
    } else if (fileType === 'url') {
        formData.append('portfolioUrl', urlInput.value.trim());
    }

    if (currentPortfolioId) formData.append('portfolioId', currentPortfolioId);

    var url = '/hireSystem/resume/portfolioSave.do';

    fetch(url, { method: 'POST', body: formData })
        .then(function(res) { return res.json(); })
        .then(function(data) {
            if (data.result) {
                alert(data.message);
				sessionStorage.setItem('scrollTo', '#portfolio');
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


/* 삭제 */
function deletePortfolio(article) {
    if (!confirm('포트폴리오를 삭제하시겠습니까?')) return;

    var formData = new FormData();
    formData.append('portfolioId', article.dataset.portfolioId);
    formData.append('resumeId',    RESUME_CTX.resumeId);
    formData.append('userNum',     RESUME_CTX.userNum);

    fetch('/hireSystem/resume/portfolioDelete.do', { method: 'POST', body: formData })
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
