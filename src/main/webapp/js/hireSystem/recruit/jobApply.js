/* =======================================================================
   jobApply.js – 채용공고 지원하기 (모달 버전)
   - 로그인 체크는 LoginInterceptor + common.js의 apiLoginCommonFetch가 처리
     (비로그인 상태면 401 → 로그인 페이지로 자동 이동)
   - "이력서 변경"은 로그인 유저의 전체 이력서 중에서 고를 수 있다.
   - "첨부파일"은 선택된 이력서에 실제로 첨부된 파일(RESUME_PORTFOLIO)이다.
     여기서 삭제하면 이력서 자체에서도 삭제되고, 파일추가도 이력서에 그대로 저장된다.
     (지원 건마다 별도로 보관되는 임시 첨부가 아니라, 이력서의 포트폴리오를 그대로 재사용)
======================================================================= */

var applyState = {
    jobPostingId: null,
    resumeList: [],
    selectedResumeId: null,
    fileList: [],
    submitting: false
};

/* ── 진입점: normalJobDetail.jsp의 지원하기 버튼에서 호출 ── */
function applyToJob(jobPostingId) {
    applyState.jobPostingId = jobPostingId;
    applyState.resumeList = [];
    applyState.selectedResumeId = null;
    applyState.fileList = [];

    openApplyModal();
    loadResumeOptions();
}

document.addEventListener('DOMContentLoaded', function () {
    var modal = document.getElementById('applyModal');
    if (!modal) return; // 지원하기 버튼이 없는 페이지에서는 초기화하지 않음

    document.getElementById('applyModalClose').addEventListener('click', closeApplyModal);
    modal.addEventListener('click', function (e) {
        if (e.target === modal) closeApplyModal();
    });

    document.getElementById('applyResumeChangeBtn').addEventListener('click', toggleResumeList);

    document.getElementById('applyFileAddBtn').addEventListener('click', toggleFileAddRow);
    document.getElementById('applyFilePickBtn').addEventListener('click', function () {
        document.getElementById('applyFileInput').click();
    });
    document.getElementById('applyFileInput').addEventListener('change', onFileInputChange);
    document.getElementById('applyFileUploadBtn').addEventListener('click', uploadApplyFile);

    document.getElementById('applyFileList').addEventListener('click', function (e) {
        var delBtn = e.target.closest('.apply-file-delete-btn');
        var nameEl = e.target.closest('.apply-file-name');
        if (delBtn) {
            deleteApplyFile(delBtn.dataset.portfolioId);
        } else if (nameEl && nameEl.dataset.portfolioId) {
            location.href = '/hireSystem/common/download.do?type=portfolio&id=' + nameEl.dataset.portfolioId;
        }
    });

    document.getElementById('applySubmitBtn').addEventListener('click', submitApply);
});

/* ── 모달 열기/닫기 ───────────────────────────────────── */
function openApplyModal() {
    document.getElementById('applyModal').classList.remove('hidden');
}

function closeApplyModal() {
    document.getElementById('applyModal').classList.add('hidden');
    document.getElementById('applyResumeList').classList.add('hidden');
    document.getElementById('applyFileAddRow').classList.add('hidden');
    resetFileAddRow();
}

/* ── 이력서 목록 조회 ─────────────────────────────────── */
function loadResumeOptions() {
    var card = document.getElementById('applyResumeCard');
    card.innerHTML = '<p class="apply-empty-text">이력서 정보를 불러오는 중...</p>';

    apiLoginCommonFetch('/hireSystem/apply/resumeOptions.do')
        .then(function (data) {
            if (!data) return; // 401 → 이미 로그인 페이지로 이동됨

            if (!data.result || !data.list || data.list.length === 0) {
                card.innerHTML = '<p class="apply-empty-text">등록된 이력서가 없습니다. 이력서를 먼저 작성해주세요.</p>';
                document.getElementById('applySubmitBtn').disabled = true;
                return;
            }

            applyState.resumeList = data.list;

            // 대표이력서(IS_MAIN='Y')를 기본 선택, 없으면 첫 번째
            var mainResume = data.list.find(function (r) { return r.isMain === 'Y'; });
            applyState.selectedResumeId = (mainResume || data.list[0]).resumeId;

            renderResumeCard();
            renderResumeList();
            loadPortfolioList(applyState.selectedResumeId);
        })
        .catch(function (err) {
            console.error(err);
            card.innerHTML = '<p class="apply-empty-text">이력서 정보를 불러오지 못했습니다.</p>';
        });
}

function getSelectedResume() {
    return applyState.resumeList.find(function (r) { return r.resumeId === applyState.selectedResumeId; });
}

function renderResumeCard() {
    var resume = getSelectedResume();
    var card = document.getElementById('applyResumeCard');
    if (!resume) {
        card.innerHTML = '<p class="apply-empty-text">선택된 이력서가 없습니다.</p>';
        return;
    }

    var badge = resume.isMain === 'Y' ? '<span class="apply-resume-card-badge">대표</span>' : '';

    card.innerHTML =
        '<p class="apply-resume-card-date">' + formatDate(resume.updatedAt) + ' 수정</p>' +
        '<p class="apply-resume-card-title">' + escapeHtml(resume.title || '제목 없는 이력서') + badge + '</p>';
}

function renderResumeList() {
    var listEl = document.getElementById('applyResumeList');
    listEl.innerHTML = '';   // 기존 목록 비우기 (다시 그리기 전 초기화)

    applyState.resumeList.forEach(function (resume) {
        // resumeList 배열의 이력서 하나하나(resume)마다 반복 실행됨

        var isSelected = resume.resumeId === applyState.selectedResumeId;
        var option = document.createElement('div');  // 이력서 한 줄을 담을 div 새로 생성
        option.className = 'apply-resume-option' + (isSelected ? ' is-selected' : '');
        // 지금 선택된 이력서면 is-selected 클래스 추가 (선택 표시용 스타일)

        option.innerHTML =
            '<span class="apply-resume-option-title">' + escapeHtml(resume.title || '제목 없는 이력서') +
            (resume.isMain === 'Y' ? ' <span class="apply-resume-card-badge">대표</span>' : '') + '</span>' +
            '<span class="apply-resume-option-date">' + formatDate(resume.updatedAt) + '</span>';
        // 이 div 안에 이력서 제목 + (대표면 뱃지) + 수정일 텍스트 채움

        option.addEventListener('click', function () {
		    applyState.selectedResumeId = resume.resumeId;  // 선택값 변경
		    renderResumeCard();       // 카드 다시 그림 (새로 선택한 이력서로)
		    renderResumeList();       // 목록도 다시 그림 (선택표시 is-selected 갱신용)
		    document.getElementById('applyResumeList').classList.add('hidden'); // 목록 닫기
		    loadPortfolioList(resume.resumeId);  // ★ 새 이력서 기준으로 포폴 다시 fetch
		});
        // 이 div(한 줄)를 클릭했을 때 실행될 함수를 미리 등록

        listEl.appendChild(option);
        // 만들어진 div를 실제 화면(applyResumeList)에 자식으로 추가
    });
}

function toggleResumeList() {
    document.getElementById('applyResumeList').classList.toggle('hidden');
}

/* ── 첨부파일(이력서 포트폴리오) 목록 조회 ───────────────── */
function loadPortfolioList(resumeId) {
    var listEl = document.getElementById('applyFileList');
    listEl.innerHTML = '<p class="apply-empty-text">첨부파일을 불러오는 중...</p>';

    apiLoginCommonFetch('/hireSystem/apply/portfolioList.do?resumeId=' + resumeId)
        .then(function (data) {
            if (!data) return;

            if (!data.result) {
                listEl.innerHTML = '<p class="apply-empty-text">' + (data.message || '첨부파일을 불러오지 못했습니다.') + '</p>';
                return;
            }

            applyState.fileList = data.list || [];
            renderFileList();
        })
        .catch(function (err) {
            console.error(err);
            listEl.innerHTML = '<p class="apply-empty-text">첨부파일을 불러오지 못했습니다.</p>';
        });
}

function renderFileList() {
    var listEl = document.getElementById('applyFileList');
    var countEl = document.getElementById('applyFileCount');
    countEl.textContent = applyState.fileList.length;

    if (applyState.fileList.length === 0) {
        listEl.innerHTML = '<p class="apply-empty-text" id="applyFileEmpty">이력서에 첨부된 파일이 없습니다.</p>';
        return;
    }

    listEl.innerHTML = '';
    applyState.fileList.forEach(function (file) {
        var row = document.createElement('div');
        row.className = 'apply-file-entry';

        var nameHtml = file.fileType === 'file'
            ? '<span class="apply-file-name" data-portfolio-id="' + file.portfolioId + '">' + escapeHtml(file.originalName) + '</span>'
            : '<span class="apply-file-name" data-portfolio-id="' + file.portfolioId + '">' + escapeHtml(file.portfolioUrl) + '</span>';

        row.innerHTML =
            '<div class="apply-file-info">' +
                '<span class="apply-file-icon">📄</span>' +
                nameHtml +
                (file.fileCategory ? '<span class="apply-file-badge">' + escapeHtml(file.fileCategory) + '</span>' : '') +
            '</div>' +
            '<button type="button" class="apply-file-delete-btn" data-portfolio-id="' + file.portfolioId + '">삭제</button>';

        listEl.appendChild(row);
    });
}

/* ── 파일추가 미니 폼 ─────────────────────────────────── */
function toggleFileAddRow() {
    var row = document.getElementById('applyFileAddRow');
    row.classList.toggle('hidden');
    if (!row.classList.contains('hidden')) resetFileAddRow();
}

function resetFileAddRow() {
    document.getElementById('applyFileCategory').value = '';
    document.getElementById('applyFileInput').value = '';
    var nameEl = document.getElementById('applyFilePickName');
    nameEl.textContent = '선택된 파일 없음';
    nameEl.classList.remove('has-file');
}

function onFileInputChange() {
    var fileInput = document.getElementById('applyFileInput');
    var nameEl = document.getElementById('applyFilePickName');
    if (fileInput.files && fileInput.files.length > 0) {
        nameEl.textContent = fileInput.files[0].name;
        nameEl.classList.add('has-file');
    } else {
        nameEl.textContent = '선택된 파일 없음';
        nameEl.classList.remove('has-file');
    }
}

function uploadApplyFile() {
    var fileCategory = document.getElementById('applyFileCategory').value;
    var fileInput = document.getElementById('applyFileInput');

    if (!fileCategory) { alert('파일구분을 선택해주세요.'); return; }
    if (!fileInput.files || fileInput.files.length === 0) { alert('파일을 선택해주세요.'); return; }
    if (!applyState.selectedResumeId) { alert('이력서를 먼저 선택해주세요.'); return; }

    var formData = new FormData();
    formData.append('resumeId', applyState.selectedResumeId);
    formData.append('fileCategory', fileCategory);
    formData.append('fileType', 'file');
    formData.append('portfolioFile', fileInput.files[0]);

    apiLoginCommonFetch('/hireSystem/resume/portfolioSave.do', {
        method: 'POST',
        body: formData
    })
    .then(function (data) {
        if (!data) return;
        if (data.result) {
            document.getElementById('applyFileAddRow').classList.add('hidden');
            resetFileAddRow();
            loadPortfolioList(applyState.selectedResumeId);
        } else {
            alert(data.message || '파일 등록에 실패했습니다.');
        }
    })
    .catch(function (err) {
        console.error(err);
        alert('파일 등록 중 오류가 발생했습니다.');
    });
}

/* ── 첨부파일 삭제 ────────────────────────────────────── */
function deleteApplyFile(portfolioId) {
    if (!confirm('이 파일을 삭제하시겠습니까?\n(이력서에서도 함께 삭제됩니다)')) return;

    var formData = new FormData();
    formData.append('portfolioId', portfolioId);
    formData.append('resumeId', applyState.selectedResumeId);

    apiLoginCommonFetch('/hireSystem/resume/portfolioDelete.do', {
        method: 'POST',
        body: formData
    })
    .then(function (data) {
        if (!data) return;
        if (data.result) {
            loadPortfolioList(applyState.selectedResumeId);
        } else {
            alert(data.message || '삭제에 실패했습니다.');
        }
    })
    .catch(function (err) {
        console.error(err);
        alert('삭제 중 오류가 발생했습니다.');
    });
}

/* ── 최종 지원하기 제출 ───────────────────────────────── */
function submitApply() {
    if (applyState.submitting) return;
    if (!applyState.selectedResumeId) { alert('이력서를 선택해주세요.'); return; }
    if (!confirm('이 공고에 지원하시겠습니까?')) return;

    applyState.submitting = true;
    var submitBtn = document.getElementById('applySubmitBtn');
    submitBtn.disabled = true;

    var formData = new FormData();
    formData.append('jobPostingId', applyState.jobPostingId);
    formData.append('resumeId', applyState.selectedResumeId);

    apiLoginCommonFetch('/hireSystem/apply/applyJob.do', {
        method: 'POST',
        body: formData
    })
    .then(function (data) {
        if (!data) return; // 401 → apiLoginCommonFetch가 로그인페이지로 이미 이동시킴

        if (data.result) {
            alert(data.message);
            location.reload(); // 지원완료 상태/지원자수 반영을 위해 새로고침
        } else {
            alert(data.message || '지원에 실패했습니다.');
            submitBtn.disabled = false;
        }
    })
    .catch(function (err) {
        console.error(err);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
        submitBtn.disabled = false;
    })
    .finally(function () {
        applyState.submitting = false;
    });
}

/* ── 유틸 ─────────────────────────────────────────────── */
function formatDate(value) {
    if (!value) return '';
    var d = new Date(value);
    if (isNaN(d.getTime())) return '';
    var pad = function (n) { return n < 10 ? '0' + n : n; };
    return d.getFullYear() + '.' + pad(d.getMonth() + 1) + '.' + pad(d.getDate());
}

function escapeHtml(str) {
    if (str === null || str === undefined) return '';
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;');
}
