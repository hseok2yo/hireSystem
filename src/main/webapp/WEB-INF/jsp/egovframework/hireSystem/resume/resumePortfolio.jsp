<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"   prefix="fmt" %>

<%-- ===================== 포트폴리오 모달 ===================== --%>
<div id="portfolioModal" class="portfolio-modal-overlay hidden">
    <div class="portfolio-modal-box">

        <button type="button" class="portfolio-modal-close" id="portfolioModalClose">×</button>
        <h3>포트폴리오/기타문서 추가</h3>

        <%-- 파일구분 --%>
        <div class="pm-row">
            <label class="pm-label">파일구분 <span class="pm-star">*</span></label>
            <select id="pmFileCategory">
                <option value="">구분선택</option>
                <option value="포트폴리오">포트폴리오</option>
                <option value="기타문서">기타문서</option>
                <option value="증빙자료">증빙자료</option>
                <option value="수료증">수료증</option>
                <option value="기타">기타</option>
            </select>
        </div>

        <%-- 파일찾기 --%>
        <div class="pm-row">
            <label class="pm-label">파일찾기 <span class="pm-star">*</span></label>
            <div class="pm-file-type-row">
                <label class="pm-radio-label">
                    <input type="radio" name="pmFileType" value="file" checked> 파일
                </label>
                <label class="pm-radio-label">
                    <input type="radio" name="pmFileType" value="url"> URL
                </label>
            </div>

            <%-- 파일 선택 --%>
            <div id="pmFileWrap" class="pm-file-wrap">
                <button type="button" id="pmFileBtn" class="pm-file-btn">파일 선택</button>
                <span id="pmFileName" class="pm-file-name">선택된 파일 없음</span>
                <input type="file" id="pmFileInput"
                       accept=".hwp,.doc,.docx,.ppt,.pptx,.pdf,.xls,.xlsx,.rtf,.gul,.zip,.alz,.rar,.lzh,.arj,.egg,.tar,.sit,.jpg,.gif,.png,.psd,.fla,.ai,.ipe,.jpeg,.tif,.tiff,.pcx,.swf"
                       style="display:none;">
                <p class="pm-size-guide">*파일은 50MB 이하의 파일을 등록할 수 있습니다.</p>
            </div>

            <%-- URL 입력 --%>
            <div id="pmUrlWrap" class="pm-url-wrap hidden">
                <input type="text" id="pmUrlInput" placeholder="URL을 입력하세요 (예: https://github.com/...)">
            </div>

            <%-- 확장자 안내 --%>
            <div class="pm-ext-guide">
                <p>등록가능한 파일 형식 및 확장자</p>
                <p>- 문서파일 : .hwp .doc .docx .ppt .pptx .pdf .xls .xlsx .rtf .gul</p>
                <p>- 압축파일 : .zip .alz .rar .lzh .arj .egg .tar .sit</p>
                <p>- 이미지 및 플래시 파일 : .jpg .gif .png .psd .fla .ai .ipe .jpeg .tif .tiff .pcx .swf</p>
            </div>
        </div>

        <%-- 버튼 --%>
        <div class="pm-btns">
            <button type="button" class="btn-outline" id="pmCancelBtn">취소</button>
            <button type="button" class="btn-primary" id="pmSaveBtn">등록</button>
        </div>

    </div>
</div>


<%-- ===================== 포트폴리오/기타문서 섹션 ===================== --%>
<section class="section-card section-portfolio hidden" id="portfolio">

    <div class="section-header">
        <div>
            <h2>포트폴리오/기타문서</h2>
            <p>포트폴리오나 추가 서류가 있다면 첨부해 보세요.</p>
        </div>
        <div class="section-actions">
            <button type="button" class="btn-text" id="portfolioAddBtn">+ 포트폴리오/기타문서 추가</button>
        </div>
    </div>

    <%-- 목록 --%>
    <div class="portfolio-list-wrapper" id="portfolioListWrapper">
        <c:forEach items="${portfolioList}" var="pf">
            <article class="portfolio-entry"
                data-portfolio-id="${pf.portfolioId}"
                data-file-category="${pf.fileCategory}"
                data-file-type="${pf.fileType}"
                data-original-name="${pf.originalName}"
                data-portfolio-url="${pf.portfolioUrl}">
                <div class="portfolio-item-inner">
                    <span class="portfolio-icon"><i class="fas fa-file-alt"></i></span>
                    <c:choose>
					    <c:when test="${pf.fileType == 'file'}">
					        <span class="portfolio-name" style="cursor:pointer;"
					              onclick="location.href='/hireSystem/common/download.do?type=portfolio&id=${pf.portfolioId}'">
					            ${pf.originalName}
					        </span>
					    </c:when>
					    <c:otherwise>
					        <span class="portfolio-name">${pf.portfolioUrl}</span>
					    </c:otherwise>
					</c:choose>
                    <c:if test="${not empty pf.fileCategory}">
                        <span class="portfolio-badge">${pf.fileCategory}</span>
                    </c:if>
                </div>
                <div class="portfolio-actions">
                    <button type="button" class="icon-btn btn-edit-portfolio" aria-label="수정">
                        <i class="fas fa-pencil-alt"></i>
                    </button>
                    <button type="button" class="icon-btn btn-delete-portfolio" aria-label="삭제">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </div>
            </article>
        </c:forEach>
    </div>

</section>
