<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
  ===================== 지원하기 모달 =====================
  normalJobDetail.jsp의 c:otherwise(공고 존재) 블록 안에서만 include 되므로
  ${detailList}를 그대로 사용할 수 있다.
  실제 동작은 jobApply.js가 담당하고, 이 파일은 마크업만 담당한다.
--%>
<div id="applyModal" class="apply-modal-overlay hidden">
    <div class="apply-modal-box">

        <button type="button" class="apply-modal-close" id="applyModalClose">&times;</button>

        <p class="apply-modal-company"><c:out value="${detailList.companyName}" /></p>
        <h3 class="apply-modal-title"><c:out value="${detailList.jobTitle}" /></h3>

        <!-- ── 선택된 이력서 ─────────────────────────── -->
        <div class="apply-section">
            <div class="apply-section-header">
                <span class="apply-section-label">선택된 이력서</span>
                <button type="button" class="apply-change-btn" id="applyResumeChangeBtn">이력서 변경 &rsaquo;</button>
            </div>

            <div class="apply-resume-card" id="applyResumeCard">
                <p class="apply-empty-text">이력서 정보를 불러오는 중...</p>
            </div>

            <!-- 이력서 변경 목록 (토글 시 표시) -->
            <div class="apply-resume-list hidden" id="applyResumeList"></div>
        </div>

        <!-- ── 첨부파일 ─────────────────────────────── -->
        <div class="apply-section">
            <div class="apply-section-header">
                <span class="apply-section-label">첨부파일 <span id="applyFileCount">0</span>건</span>
                <button type="button" class="apply-change-btn" id="applyFileAddBtn">파일추가 +</button>
            </div>

            <!-- 파일추가 미니 폼 (평소엔 숨김) -->
            <div class="apply-file-add-row hidden" id="applyFileAddRow">
                <select id="applyFileCategory">
                    <option value="">구분선택</option>
                    <option value="포트폴리오">포트폴리오</option>
                    <option value="기타문서">기타문서</option>
                    <option value="증빙자료">증빙자료</option>
                    <option value="수료증">수료증</option>
                    <option value="기타">기타</option>
                </select>
                <button type="button" class="apply-file-pick-btn" id="applyFilePickBtn">파일 선택</button>
                <span class="apply-file-pick-name" id="applyFilePickName">선택된 파일 없음</span>
                <input type="file" id="applyFileInput"
                       accept=".hwp,.doc,.docx,.ppt,.pptx,.pdf,.xls,.xlsx,.jpg,.jpeg,.png,.zip"
                       style="display:none;">
                <button type="button" class="apply-file-upload-btn" id="applyFileUploadBtn">등록</button>
            </div>

            <div class="apply-file-list" id="applyFileList">
                <p class="apply-empty-text" id="applyFileEmpty">이력서에 첨부된 파일이 없습니다.</p>
            </div>
        </div>

        <button type="button" class="apply-submit-btn" id="applySubmitBtn">입사지원</button>
    </div>
</div>
