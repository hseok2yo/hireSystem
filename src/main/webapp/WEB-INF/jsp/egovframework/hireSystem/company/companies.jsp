<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>기업정보 | 개발자 채용포털</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/hireSystem/companies.css' />">
    <script src="/js/hireSystem/companies.js"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/header.jsp"></jsp:include>
	<script>
// 		console.log(JSON.parse('${companiesJson}'));
// 		var a = JSON.parse('${companiesJson}');

// 		console.log(a.dhsOpenEmpHireInfoList.dhsOpenEmpHireInfo);
// 		var dhsOpenEmpHireInfo = a.dhsOpenEmpHireInfoList.dhsOpenEmpHireInfo;

// 		for (var i = 0; i < dhsOpenEmpHireInfo.length; i++) {
// 			console.log(dhsOpenEmpHireInfo[i].busino);
// 			console.log(dhsOpenEmpHireInfo[i].coIntroCont);
// 			console.log(dhsOpenEmpHireInfo[i].coIntroSummaryCont);
// 			console.log(dhsOpenEmpHireInfo[i].coNm);
// 			console.log(dhsOpenEmpHireInfo[i].empCoNo);
// 		}
	</script>
	<main class="companies-page">
        <section class="companies-hero">
            <div class="hero-inner">
                <div class="hero-text">
                    <p class="hero-kicker">PREMIUM PARTNERS</p>
                    <h2>한눈에 보는 기업정보</h2>
                    <p>
                        복지, 성장성, 기술스택, 채용 포지션까지 한 번에 비교하고
                        내 커리어에 딱 맞는 회사를 찾아보세요.
                    </p>
                </div>
                <div class="hero-metrics">
                    <div class="metric-card">
                        <strong>2,500+</strong>
                        <span>등록 기업</span>
                    </div>
                    <div class="metric-card">
                        <strong>4.6/5</strong>
                        <span>평균 기업 평점</span>
                    </div>
                    <div class="metric-card">
                        <strong>780+</strong>
                        <span>실시간 채용</span>
                    </div>
                </div>
            </div>
        </section>

		<section class="companies-controls" aria-label="기업 목록 검색 및 필터">
			<form id="searchForm"
				action="<c:url value='/hireSystem/company/companies.do'/>"
				method="get" class="controls-form">

				<%-- 기업구분만 hidden (칩은 button이라 전송 안 됨). 정렬은 아래 select만 name을 갖게 해 중복 파라미터 방지 — 중복 시 서버는 첫 값만 써서 순서가 어긋남 --%>
				<input type="hidden" name="coClcd" value="${coClcd}" />

				<div class="controls-search-block">
					<span class="controls-field-label" id="controls-search-label">검색</span>
					<div class="search-wrap" role="search" aria-labelledby="controls-search-label">
						<input type="text" name="coNm" value="${coNm}"
							placeholder="기업명으로 검색해보세요">
						<button type="submit">검색</button>
					</div>
				</div>

				<div class="controls-toolbar">
					<div class="controls-filter-block">
						<span class="controls-field-label" id="controls-filter-label">기업 구분</span>
						<div class="filter-chips" role="group" aria-labelledby="controls-filter-label">
							<button class="chip ${empty coClcd ? 'active' : ''}" type="button"
								data-coClcd="">전체</button>
							<button class="chip ${coClcd eq '10' ? 'active' : ''}"
								type="button" data-coClcd="10">대기업</button>
							<button class="chip ${coClcd eq '20' ? 'active' : ''}"
								type="button" data-coClcd="20">공기업</button>
							<button class="chip ${coClcd eq '30' ? 'active' : ''}"
								type="button" data-coClcd="30">공공기관</button>
							<button class="chip ${coClcd eq '40' ? 'active' : ''}"
								type="button" data-coClcd="40">중견기업</button>
							<button class="chip ${coClcd eq '50' ? 'active' : ''}"
								type="button" data-coClcd="50">외국계기업</button>
						</div>
					</div>

					<div class="controls-sort-block">
						<span class="controls-field-label" id="controls-sort-label">정렬</span>
						<div class="sort-wrap" role="group" aria-labelledby="controls-sort-label">
							<div class="sort-select-group">
								<label class="sort-select-label" for="sortFieldSelect">기준</label>
								<select id="sortFieldSelect" name="sortField"
									class="sort-select"
									onchange="filterBySelect()">
									<option value="regDt" ${sortField eq 'regDt' ? 'selected' : ''}>등록일</option>
									<option value="coNm" ${sortField eq 'coNm'  ? 'selected' : ''}>회사명</option>
								</select>
							</div>
							<div class="sort-select-group">
								<label class="sort-select-label" for="sortOrderSelect">순서</label>
								<select id="sortOrderSelect" name="sortOrderBy"
									class="sort-select"
									onchange="filterBySelect()">
									<option value="desc" ${sortOrderBy eq 'desc' ? 'selected' : ''}>내림차순</option>
									<option value="asc" ${sortOrderBy eq 'asc'  ? 'selected' : ''}>오름차순</option>
								</select>
							</div>
						</div>
					</div>
				</div>

			</form>
		</section>

		<%--
          워크넷(고용24) API dhsOpenEmpHireInfoList / dhsOpenEmpHireInfo 필드와 화면 역할 정리
          · 목록(카드): 짧게 보이는 것만 — 높이는 CSS line-clamp로 통일
            - coNm              회사명 (제목)
            - coClcdNm          기업구분명 (배지)  ※ 문서에 coClcdNm이 중복 기재된 경우 있음; 회사명은 coNm
            - empCoNo           채용기업번호 (우측 작은 글씨)
            - coIntroSummaryCont 기업소개요약 (3줄까지)
            - mainBusiCont      주요사업 (2줄까지)
            - busino            사업자등록번호 (메타)
            - mapCoorX/Y        경도·위도 (지도 연동 시 상세에서 쓰기 좋음; 카드에는 짧게만)
            - regLogImgNm       로고 파일명 (실제 URL 규칙은 API/가이드 확인 후 이미지 태그에 연결)
            - homepg            홈페이지 링크
          · 상세(별도 페이지/모달 추천): 길고 변동 큰 본문
            - coIntroCont       기업소개상세 (여기 두면 카드 높이가 들쭉날쭉해짐)
        --%>
        <c:url var="noImageSrc" value="/images/no-image.png"/>
        <section class="companies-grid">
            <c:forEach var="company" items="${companyList}">
                <article class="company-card">
                    <%-- 로고 이미지 (regLogImgNm: 컨트롤러에서 내려주는 값 그대로 src에 사용) --%>
                    <c:choose>
                        <c:when test="${not empty company.regLogImgNm}">
                            <img src="${company.regLogImgNm}" alt="${company.coNm} 로고"
                                 class="company-logo"
                                 onerror="this.onerror=null;this.src='${noImageSrc}';"/>
                            <%-- onerror: 이미지 못 불러오면 기본 이미지로 대체 --%>
                        </c:when>
                        <c:otherwise>
                            <%-- 이미지 없으면 회사명 첫글자 --%>
                            <div class="logo-placeholder">
                                <c:choose>
                                    <c:when test="${not empty company.coNm}">
                                        <c:out value="${fn:substring(company.coNm, 0, 1)}"/>
                                    </c:when>
                                    <c:otherwise>?</c:otherwise>
                                </c:choose>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <div class="card-top">
                        <span class="badge" title="기업구분명 (coClcdNm)"><c:out value="${empty company.coClcdNm ? '기업' : company.coClcdNm}" /></span>
                        <span class="card-id" title="채용기업번호 (empCoNo)">No. <c:out value="${company.empCoNo}" /></span>
                    </div>
                    <h3 title="회사명 (coNm)"><c:out value="${company.coNm}" /></h3>
                    <p class="card-summary" title="기업소개요약 (coIntroSummaryCont)"><c:out value="${company.coIntroSummaryCont}" /></p>
                    <p class="card-mainbusi" title="주요사업 (mainBusiCont)"><c:out value="${company.mainBusiCont}" /></p>
                    <div class="meta">
                        <span title="사업자등록번호 (busino)">사업자 <c:out value="${company.busino}" /></span>
                        <span title="좌표:경도(mapCoorX), 위도(mapCoorY)">위치 <c:out value="${company.mapCoorX}" />, <c:out value="${company.mapCoorY}" /></span>
                    </div>
                    <c:choose>
                        <c:when test="${not empty company.homepg}">
                            <a href="<c:out value='${company.homepg}' />" class="detail-btn" target="_blank" rel="noopener noreferrer">홈페이지</a>
                        </c:when>
                        <c:otherwise>
                            <span class="detail-btn" style="opacity:.55;pointer-events:none;background:#98a2b3;">홈페이지 없음</span>
                        </c:otherwise>
                    </c:choose>
                </article>
            </c:forEach>
        </section>

        <%-- 페이징: 1~10 하드코딩 + 이전/다음 화살표 --%>
        <section class="companies-pagination-wrap">
    <nav class="companies-pagination" aria-label="페이지 번호">

        <%-- 이전 블록 (blockStart가 1보다 클 때만 활성화) --%>
        <c:choose>
            <c:when test="${blockStart le 1}">
                <span class="page-arrow is-disabled">‹</span>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/hireSystem/company/companies.do'>
                            <c:param name='page' value='${blockStart - 1}'/>
                         </c:url>" class="page-arrow">‹</a>
            </c:otherwise>
        </c:choose>

        <%-- 페이지 번호 버튼 --%>
        <div class="pagination-nums">
            <c:forEach var="i" begin="${blockStart}" end="${blockEnd}">
                <a href="<c:url value='/hireSystem/company/companies.do'>
                            <c:param name='page' value='${i}'/>
                         </c:url>"
                   class="page-link ${currentPage eq i ? 'is-active' : ''}">
                    ${i}
                </a>
            </c:forEach>
        </div>

        <%-- 다음 블록 (blockEnd가 totalPages보다 작을 때만 활성화) --%>
        <c:choose>
            <c:when test="${blockEnd ge totalPages}">
                <span class="page-arrow is-disabled">›</span>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/hireSystem/company/companies.do'>
                            <c:param name='page' value='${blockEnd + 1}'/>
                         </c:url>" class="page-arrow">›</a>
            </c:otherwise>
        </c:choose>

    </nav>
</section>
    </main>

    <jsp:include page="/WEB-INF/jsp/egovframework/hireSystem/templete/footer.jsp"></jsp:include>
    <script id="companies-json-data" type="application/json"><c:out value="${companiesJson}" /></script>
    <script>
//         (function () {
//             var raw = document.getElementById("companies-json-data");
//             if (!raw || !raw.textContent) return;
//             var list = JSON.parse(raw.textContent);
//             console.log("연습용 list 객체:", list);
//             if (list.companies && list.companies.length > 0) {
//                 console.log("첫 회사명:", list.companies[0].coNm);
//             }
//         })();
    </script>
</body>
</html>
