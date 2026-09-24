/**
 * 채용정보 목록(recruitList.jsp) 전용 스크립트
 *
 * 역할: 검색폼(#searchForm) 값 + 상세조건 패널의 라디오 필터값을 모아서
 *       recruitList.do로 재요청(location.href 이동)하는 것.
 * - buildSearchParams(pageNo): 현재 화면의 검색조건을 전부 모아 URLSearchParams로 반환
 * - applyFilters(): "필터 적용" 버튼 클릭 시 호출 (항상 1페이지부터 다시 조회)
 * - goPage(pageNo): 페이징 링크 클릭 시 호출 (현재 조건 유지 + 지정 페이지로 이동)
 *
 * 원래 recruitList.jsp 안에 인라인 <script>로 있던 것을 별도 js 파일로 분리한 것.
 * (JSP 안에서는 <c:url value="/hireSystem/recruit/recruitList.do"/> 로 컨텍스트패스를
 *  자동 보정해줬지만, 이 파일은 정적 리소스라 JSTL이 동작하지 않으므로
 *  프로젝트 내 다른 js 파일들(boardList.js, resumeMain.js 등)과 동일하게
 *  절대경로 문자열을 그대로 하드코딩함)
 */

var RECRUIT_LIST_URL = '/hireSystem/recruit/recruitList.do';

/**
 * 현재 화면의 검색조건을 전부 모아서 URLSearchParams로 반환.
 * - #searchForm 안의 값(공고제목 텍스트, numOfRows 히든필드)은 FormData로 수집
 * - 상세조건 패널의 라디오버튼(진행여부/고용형태/채용구분/근무지)은 <form> 밖에 있으므로 별도로 담아준다.
 *   ("전체" 선택 시 value=""인 경우는 파라미터 자체를 안 보냄 -> 서버(addIfNotEmpty)에서
 *    빈 값과 파라미터 없음을 동일하게 처리하므로 결과는 같고 URL만 깔끔해짐)
 *
 * @param {number} [pageNo] 이동할 페이지 번호. 생략하면 1페이지로 취급.
 *                          -> 검색조건을 바꾸면 항상 1페이지부터 다시 보는 게 자연스러운 UX라 기본값을 1로 둠.
 */
function buildSearchParams(pageNo) {
    var form = document.getElementById('searchForm');
    var formData = new FormData(form);
    var params = new URLSearchParams(formData);

    document.querySelectorAll('input[type="radio"]:checked').forEach(function (radio) {
        if (radio.value !== '') {
            params.set(radio.name, radio.value);
        }
    });

    params.set('pageNo', pageNo != null ? pageNo : 1);

    return params;
}

function navigateWithParams(params) {
    window.location.href = RECRUIT_LIST_URL + '?' + params.toString();
}

// "필터 적용" 버튼 클릭 시 호출 -> 1페이지부터 다시 조회
function applyFilters() {
    navigateWithParams(buildSearchParams());
}

// 페이징 링크 클릭 시 호출 -> 현재 검색조건은 유지한 채 지정한 페이지로 이동
function goPage(pageNo) {
    navigateWithParams(buildSearchParams(pageNo));
}

// 선택된 필터 칩(사람인 스타일) 관련.
// ※ 여기 두 함수는 페이지 이동(재조회)을 하지 않는다 - 화면에 "적용 예정" 상태만 바꿔주고,
//   실제 조회는 사람인처럼 검색바의 "검색" 버튼(또는 상세조건 패널의 "필터 적용")을
//   눌러야만 일어난다. (라디오 change 시 자동조회도 이미 꺼둔 상태라 동일한 흐름)
var FILTER_PARAM_NAMES = ['ongoingYn', 'hireTypeLst', 'recrutSe', 'workRgnLst'];

// 칩 하나의 × 클릭 -> 해당 라디오를 "전체"로 되돌리고 칩만 화면에서 제거 (재조회 없음)
function removeFilter(paramName) {
    if (paramName === 'recrutPbancTtl') {
        var form = document.getElementById('searchForm');
        if (form && form.recrutPbancTtl) {
            form.recrutPbancTtl.value = '';
        }
    } else {
        var emptyRadio = document.querySelector('input[name="' + paramName + '"][value=""]');
        if (emptyRadio) {
            emptyRadio.checked = true;
        }
    }

    var chip = document.querySelector('.filter-tag[data-param="' + paramName + '"]');
    if (chip) {
        chip.remove();
    }

    removeFilterBarIfEmpty();
}

// "선택초기화" 클릭 -> 검색어 + 모든 필터 라디오를 전부 초기화하고 칩 바 자체를 제거 (재조회 없음)
function resetFilters() {
    var form = document.getElementById('searchForm');
    if (form && form.recrutPbancTtl) {
        form.recrutPbancTtl.value = '';
    }

    FILTER_PARAM_NAMES.forEach(function (name) {
        var emptyRadio = document.querySelector('input[name="' + name + '"][value=""]');
        if (emptyRadio) {
            emptyRadio.checked = true;
        }
    });

    var bar = document.querySelector('.selected-filters-bar');
    if (bar) {
        bar.remove();
    }
}

// 칩을 하나씩 지우다가 마지막 칩까지 없어지면 "선택초기화" 버튼만 덩그러니 남지 않도록 바 자체를 제거
function removeFilterBarIfEmpty() {
    var bar = document.querySelector('.selected-filters-bar');
    if (bar && bar.querySelectorAll('.filter-tag').length === 0) {
        bar.remove();
    }
}

// 상세조건 패널 토글 (열림/닫힘)
function toggleDetailFilter() {
    var panel = document.getElementById('detailFilterPanel');
    var btn = document.getElementById('detailToggleBtn');
    var isOpen = panel.classList.contains('is-open');
    panel.classList.toggle('is-open', !isOpen);
    btn.classList.toggle('is-open', !isOpen);
}


document.addEventListener('DOMContentLoaded', function () {
    var searchForm = document.getElementById('searchForm');

    // 공고제목 검색폼을 "검색" 버튼으로 제출할 때도 상세조건 필터값이 같이 실리도록
    // 폼의 기본 GET 제출을 막고 applyFilters()로 대체
    searchForm.addEventListener('submit', function (e) {
        e.preventDefault();
        applyFilters();
    });

    // ※ 라디오 버튼 change 시 자동제출 하던 부분은 제거함.
    //    이제 상세조건 패널 안 "필터 적용" 버튼을 눌러야만 applyFilters()가 호출됨.
});
