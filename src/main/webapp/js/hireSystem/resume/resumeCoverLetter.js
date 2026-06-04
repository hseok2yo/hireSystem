/**
 * resumeCoverLetter.js
 * 자기소개서 항목 - 인라인 폼 방식 (경력사항 패턴 동일)
 */

var CL_MAX_LENGTH = 2000; // 자기소개서 최대 글자수

document.addEventListener('DOMContentLoaded', function() {
    initResumeCoverLetter();
});

function initResumeCoverLetter() {

    var section             = document.getElementById('coverLetter');
    if (!section) return;

    var coverLetterForm        = document.getElementById('coverLetterForm');
    var coverLetterAddForm     = document.getElementById('coverLetterAddForm');
    var coverLetterListWrapper = document.getElementById('coverLetterListWrapper');
    var addButton              = section.querySelector('.cover-letter-add-btn');
    var cancelButton           = coverLetterAddForm.querySelector('.cancel-cover-letter-add');
    var saveButton             = coverLetterAddForm.querySelector('.save-cover-letter-add');
    var textarea               = coverLetterAddForm.querySelector('[name="clContent"]');
    var charCount              = coverLetterAddForm.querySelector('.cover-letter-char-count');

    // ── 항목 추가 버튼 ──────────────────────────
    addButton.addEventListener('click', function(e) {
        e.preventDefault();
        coverLetterForm.dataset.clId = ''; // 추가 모드 초기화
        resetCoverLetterForm(coverLetterAddForm);
        showCoverLetterForm(coverLetterAddForm, coverLetterListWrapper);
    });

    // ── 취소 버튼 ────────────────────────────────
    cancelButton.addEventListener('click', function(e) {
        e.preventDefault();
        hideCoverLetterForm(coverLetterAddForm, coverLetterListWrapper);
    });

    // ── 저장 버튼 ────────────────────────────────
    saveButton.addEventListener('click', function(e) {
        e.preventDefault();
        saveCoverLetterEntry(coverLetterForm, coverLetterAddForm, coverLetterListWrapper);
    });

    // ── 글자수 카운트 ─────────────────────────────
    textarea.addEventListener('input', function() {
        updateCharCount(textarea, charCount);
    });

    // ── 목록 수정/삭제/더보기 이벤트 위임 ──────────
    bindCoverLetterListEvents(coverLetterListWrapper, coverLetterForm, coverLetterAddForm);
}


/* 폼 열기 */
function showCoverLetterForm(coverLetterAddForm, coverLetterListWrapper) {
	setDirty('자기소개서');
    coverLetterAddForm.classList.remove('hidden');
    coverLetterListWrapper.classList.add('hidden');
}

/* 폼 닫기 + 초기화 */
function hideCoverLetterForm(coverLetterAddForm, coverLetterListWrapper) {
	clearDirty(); //섹션 열린거 체크초기화
    coverLetterAddForm.classList.add('hidden');
    coverLetterListWrapper.classList.remove('hidden');
    resetCoverLetterForm(coverLetterAddForm);
}

/* 폼 입력값 초기화 */
function resetCoverLetterForm(coverLetterAddForm) {
    coverLetterAddForm.querySelectorAll('input, textarea').forEach(function(el) {
        el.value = '';
    });
    // 글자수 카운트 초기화
    var charCount = coverLetterAddForm.querySelector('.cover-letter-char-count');
    var current   = coverLetterAddForm.querySelector('.cl-current-count');
    if (current) current.textContent = '0';
    if (charCount) charCount.classList.remove('over-limit');
}

/* 글자수 카운트 업데이트 */
function updateCharCount(textarea, charCountEl) {
    var len     = textarea.value.length;
    var current = charCountEl.querySelector('.cl-current-count');
    current.textContent = len;

    if (len > CL_MAX_LENGTH) {
        charCountEl.classList.add('over-limit');
    } else {
        charCountEl.classList.remove('over-limit');
    }
}


/* 저장 (신규 / 수정 공통) */
function saveCoverLetterEntry(coverLetterForm, coverLetterAddForm, coverLetterListWrapper) {

    var clId      = coverLetterForm.dataset.clId || '';
    var clTitle   = coverLetterAddForm.querySelector('[name="clTitle"]').value.trim();
    var clContent = coverLetterAddForm.querySelector('[name="clContent"]').value.trim();

    if (!clTitle)   { alert('항목 제목을 입력해주세요.'); return; }
    if (!clContent) { alert('내용을 입력해주세요.');      return; }
    if (clContent.length > CL_MAX_LENGTH) {
        alert('내용은 ' + CL_MAX_LENGTH + '자 이내로 입력해주세요.');
        return;
    }

    var formData = new FormData();
    formData.append('resumeId',  RESUME_CTX.resumeId);
    formData.append('userNum',   RESUME_CTX.userNum);
    formData.append('clTitle',   clTitle);
    formData.append('clContent', clContent);
    if (clId) formData.append('clId', clId);

    var url = '/hireSystem/resume/coverLetterSave.do';

    fetch(url, { method: 'POST', body: formData })
        .then(function(res) { return res.json(); })
        .then(function(data) {
            if (data.result) {
                alert(data.message);
				sessionStorage.setItem('scrollTo', '#coverLetter');
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


/* 수정 버튼 클릭 → 폼에 기존 값 세팅 후 열기 */
function handleCoverLetterEdit(btn, coverLetterForm, coverLetterAddForm, coverLetterListWrapper) {

    var article = btn.closest('.cover-letter-entry');

    // 수정 모드 - clId 저장
    coverLetterForm.dataset.clId = article.dataset.clId;

    coverLetterAddForm.querySelector('[name="clTitle"]').value   = article.dataset.clTitle   || '';
    coverLetterAddForm.querySelector('[name="clContent"]').value = article.dataset.clContent || '';

    // 글자수 카운트 반영
    var textarea  = coverLetterAddForm.querySelector('[name="clContent"]');
    var charCount = coverLetterAddForm.querySelector('.cover-letter-char-count');
    updateCharCount(textarea, charCount);

    showCoverLetterForm(coverLetterAddForm, coverLetterListWrapper);
}


/* 삭제 */
function handleCoverLetterDelete(btn) {

    if (!confirm('자기소개서 항목을 삭제하시겠습니까?')) return;

    var article = btn.closest('.cover-letter-entry');
    var clId    = article.dataset.clId;

    var formData = new FormData();
    formData.append('clId',     clId);
    formData.append('resumeId', RESUME_CTX.resumeId);
    formData.append('userNum',  RESUME_CTX.userNum);

    fetch('/hireSystem/resume/coverLetterDelete.do', { method: 'POST', body: formData })
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


/* 더보기 / 접기 토글 */
function handleCoverLetterToggle(btn) {
    var article = btn.closest('.cover-letter-entry');
    var isExpanded = article.classList.toggle('expanded');
    btn.textContent = isExpanded ? '접기 ▲' : '더보기 ▼';
}


/* 목록 이벤트 위임 바인딩 */
function bindCoverLetterListEvents(coverLetterListWrapper, coverLetterForm, coverLetterAddForm) {
    coverLetterListWrapper.addEventListener('click', function(e) {

        var editBtn   = e.target.closest('.btn-edit-cover-letter');
        var deleteBtn = e.target.closest('.btn-delete-cover-letter');
        var toggleBtn = e.target.closest('.cover-letter-toggle-btn');

        if (editBtn)   handleCoverLetterEdit(editBtn, coverLetterForm, coverLetterAddForm, coverLetterListWrapper);
        if (deleteBtn) handleCoverLetterDelete(deleteBtn);
        if (toggleBtn) handleCoverLetterToggle(toggleBtn);
    });

    // 3줄 초과 항목에 더보기 버튼 동적 추가
    coverLetterListWrapper.querySelectorAll('.cover-letter-entry').forEach(function(article) {
        var p = article.querySelector('.cover-letter-body p');
        if (p && p.scrollHeight > p.clientHeight) {
            var toggleBtn = document.createElement('button');
            toggleBtn.type = 'button';
            toggleBtn.className = 'cover-letter-toggle-btn';
            toggleBtn.textContent = '더보기 ▼';
            article.querySelector('.cover-letter-body').appendChild(toggleBtn);
        }
    });
}
