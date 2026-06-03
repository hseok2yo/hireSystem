/**
 * resumeSkill.js
 * - 스킬 태그 추가(입력 후 Enter/버튼) / X 클릭 삭제
 * - 추가 즉시 서버 insert → 응답받은 skillId를 태그 data에 세팅
 * - X 클릭 즉시 서버 delete → DOM에서 제거
 * - location.href 이동 없이 현재 페이지에서 바로 반영
 */

document.addEventListener('DOMContentLoaded', function() {
    initResumeSkill();
});

function initResumeSkill() {

    var skillSection = document.getElementById('skills');
    if (!skillSection) return;

    var skillInput   = document.getElementById('skillInput');
    var skillAddBtn  = document.getElementById('skillAddBtn');
    var skillTagWrap = document.getElementById('skillTagWrap');

    // Enter 키 → 추가
    if (skillInput) {
        skillInput.addEventListener('keydown', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                addSkillTag(skillInput.value.trim(), skillTagWrap, skillInput);
            }
        });
    }

    // + 버튼 → 추가
    if (skillAddBtn) {
        skillAddBtn.addEventListener('click', function() {
            addSkillTag(skillInput.value.trim(), skillTagWrap, skillInput);
        });
    }

    // 삭제 버튼 이벤트 위임
    if (skillTagWrap) {
        skillTagWrap.addEventListener('click', function(e) {
            var deleteBtn = e.target.closest('.skill-tag-delete');
            if (deleteBtn) {
                handleSkillDelete(deleteBtn);
            }
        });
    }
}


/** 태그 추가 */
function addSkillTag(skillName, skillTagWrap, skillInput) {

    if (!skillName) return;

    // 중복 체크
    var existing = skillTagWrap.querySelectorAll('.skill-tag');
    for (var i = 0; i < existing.length; i++) {
        if (existing[i].dataset.skillName === skillName) {
            alert('이미 추가된 스킬입니다.');
            return;
        }
    }

    var formData = new FormData();
    formData.append('resumeId',  RESUME_CTX.resumeId);
    formData.append('userNum',   RESUME_CTX.userNum);
    formData.append('skillName', skillName);

    fetch('/hireSystem/resume/skillAdd.do', {
        method: 'POST',
        body: formData
    })
    .then(function(res) { return res.json(); })
    .then(function(data) {
        if (data.result) {
            // resumeId 없었던 경우 hidden에 세팅
            if (!RESUME_CTX.resumeId && data.resumeId) {
                RESUME_CTX.resumeId = data.resumeId;
                document.getElementById('resumeId').value = data.resumeId;
            }
            appendSkillTag(skillTagWrap, data.skillId, skillName);
            skillInput.value = '';
            skillInput.focus();
        } else {
            alert(data.message || '추가에 실패했습니다.');
        }
    })
    .catch(function(err) {
        console.error(err);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    });
}


/** 태그 DOM 생성 후 삽입 */
function appendSkillTag(skillTagWrap, skillId, skillName) {
    var tag = document.createElement('span');
    tag.className       = 'skill-tag';
    tag.dataset.skillId   = skillId;
    tag.dataset.skillName = skillName;
    tag.innerHTML =
        skillName +
        '<button type="button" class="skill-tag-delete" aria-label="삭제">' +
        '<i class="fas fa-times"></i>' +
        '</button>';
    skillTagWrap.appendChild(tag);
}


/** 태그 삭제 */
function handleSkillDelete(btn) {

    var tag     = btn.closest('.skill-tag');
    var skillId = tag.dataset.skillId;

    var formData = new FormData();
    formData.append('skillId', skillId);

    fetch('/hireSystem/resume/skillDelete.do', {
        method: 'POST',
        body: formData
    })
    .then(function(res) { return res.json(); })
    .then(function(data) {
        if (data.result) {
            tag.remove();
        } else {
            alert(data.message || '삭제에 실패했습니다.');
        }
    })
    .catch(function(err) {
        console.error(err);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    });
}