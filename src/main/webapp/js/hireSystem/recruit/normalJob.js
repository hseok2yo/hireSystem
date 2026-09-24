/* =======================================================================
   normalJob.js  –  일반채용정보 리스트
   백엔드 연동 시:
     - applyNormalJobFilters() / goNormalJobPage() 는 location.href 방식으로
       서버에 파라미터를 넘기면 됨 (recruitList.js 방식과 동일)
     - renderFilterBar() 는 그대로 사용 가능 (파라미터 기반)
     - toggleDetailFilter() 는 그대로 사용 가능
======================================================================= */

document.addEventListener('DOMContentLoaded', function () {
    // 선택된 필터 칩 표시
    renderFilterBar();

    // 검색 폼 submit → 서버로 GET 요청 (기본 동작 그대로)
    // form action 이 normalJob.do 로 잡혀 있으므로 preventDefault 불필요
 	var form = document.getElementById('searchForm');
    if (form) {
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            goNormalJobPage(1);
        });
    }
});

/* -----------------------------------------------------------------------
   상세조건 패널 토글
----------------------------------------------------------------------- */
function toggleDetailFilter() {
    var panel  = document.getElementById('detailFilterPanel');
    var btn    = document.getElementById('detailToggleBtn');
    var isOpen = panel.classList.contains('is-open');
    panel.classList.toggle('is-open', !isOpen);
    btn.classList.toggle('is-open', !isOpen);
}

/* -----------------------------------------------------------------------
   필터 적용 → normalJob.do 로 GET 이동
----------------------------------------------------------------------- */
function applyNormalJobFilters() {
    var params = buildParams(1);
    location.href = '/hireSystem/job/normalJob.do?' + params.toString();
}

/* -----------------------------------------------------------------------
   페이지 이동
----------------------------------------------------------------------- */
function goNormalJobPage(page) {
    var params = buildParams(page);
    location.href = '/hireSystem/job/normalJob.do?' + params.toString();
}

/* -----------------------------------------------------------------------
   현재 폼 + 라디오 값으로 URLSearchParams 생성
----------------------------------------------------------------------- */
function buildParams(page) {
    var form   = document.getElementById('searchForm');
    var params = new URLSearchParams();

    params.set('keyword',    form.querySelector('input[name="keyword"]').value.trim());
    params.set('page',       page || 1);
    params.set('ongoingYn',  getRadio('ongoingYn'));
    params.set('hireType',   getRadio('hireType'));
    params.set('recrutSe',   getRadio('recrutSe'));
    return params;
}

/* -----------------------------------------------------------------------
   선택된 필터 칩 렌더링
   - 필터가 하나도 없으면 bar 자체를 숨김 (recruitList.jsp 방식과 동일)
----------------------------------------------------------------------- */
function renderFilterBar() {
    var bar    = document.getElementById('selectedFiltersBar');
    var chips  = document.getElementById('filterChips');
    if (!bar || !chips) return;

    var params   = new URLSearchParams(location.search);
    var keyword  = params.get('keyword')   || '';
    var ongoingYn = params.get('ongoingYn') || '';
    var hireType  = params.get('hireType')  || '';
    var recrutSe  = params.get('recrutSe')  || '';

    var hasFilter = keyword || ongoingYn || hireType || recrutSe;
    if (!hasFilter) {
        bar.style.display = 'none';
        return;
    }

    bar.style.display = '';
    var html = '';

    if (keyword) {
        html += makeChip('keyword', '"' + escHtml(keyword) + '"');
    }
    if (ongoingYn) {
        html += makeChip('ongoingYn', ongoingYn === 'Y' ? '진행중' : '마감');
    }
    if (hireType) {
        var hireTypeLabel = { '정규직':'정규직', '계약직':'계약직', '무기계약직':'무기계약직', '청년인턴':'청년인턴' };
        html += makeChip('hireType', hireTypeLabel[hireType] || hireType);
    }
    if (recrutSe) {
        var recrutSeLabel = { '신입':'신입', '경력':'경력', '신입+경력':'신입+경력', '외국인전형':'외국인 전형' };
        html += makeChip('recrutSe', recrutSeLabel[recrutSe] || recrutSe);
    }

    html += '<button type="button" class="filter-reset-btn" onclick="resetFilters()">'
          + '<span class="reset-icon">↻</span> 선택초기화</button>';

    chips.innerHTML = html;
}

function makeChip(paramName, label) {
    return '<span class="filter-tag" data-param="' + paramName + '">'
         + label
         + '<button type="button" onclick="removeFilter(\'' + paramName + '\')">×</button>'
         + '</span>';
}

/* -----------------------------------------------------------------------
   필터 칩 개별 제거
----------------------------------------------------------------------- */
function removeFilter(paramName) {
    var params = new URLSearchParams(location.search);
    params.delete(paramName);
    params.set('page', 1);
    location.href = '/hireSystem/job/normalJob.do?' + params.toString();
}

/* -----------------------------------------------------------------------
   전체 초기화
----------------------------------------------------------------------- */
function resetFilters() {
    location.href = '/hireSystem/job/normalJob.do';
}

/* -----------------------------------------------------------------------
   유틸
----------------------------------------------------------------------- */
function getRadio(name) {
    var el = document.querySelector('input[name="' + name + '"]:checked');
    return el ? el.value : '';
}

function setRadio(name, value) {
    var els = document.querySelectorAll('input[name="' + name + '"]');
    els.forEach(function (el) { el.checked = (el.value === value); });
    if (!value) {
        var all = document.querySelector('input[name="' + name + '"][value=""]');
        if (all) all.checked = true;
    }
}

function escHtml(str) {
    if (!str) return '';
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;');
}
