<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"   prefix="fmt"%>

<section class="section-card section-coverLetter hidden" id="coverLetter">
    <div class="section-header">
        <div>
            <h2>자기소개서</h2>
            <p>나를 잘 표현할 수 있는 자기소개서를 작성해보세요.</p>
        </div>
        <div class="section-actions">
            <button type="button" class="btn-text cover-letter-add-btn">+ 항목 추가</button>
        </div>
    </div>

    <%-- 자기소개서 항목 추가/수정 폼 --%>
    <form id="coverLetterForm">
        <article class="cover-letter-entry cover-letter-add-form hidden" id="coverLetterAddForm">
            <div class="cover-letter-add-grid">
                <div class="form-row form-row-full">
                    <label>항목 제목 <span>*</span></label>
                    <input type="text" name="clTitle" placeholder="예) 지원 동기, 성장 과정, 장단점 등">
                </div>
                <div class="form-row form-row-full">
                    <label>내용 <span>*</span></label>
                    <div class="cover-letter-textarea-wrap">
                        <textarea name="clContent" rows="8"
                            placeholder="자기소개서 내용을 입력해주세요.&#10;- 구체적인 경험과 사례를 중심으로 작성해보세요!&#10;- 지원 직무와 연관된 역량을 강조해서 작성해보세요!&#10;- 진실되고 솔직하게 자신만의 이야기를 담아보세요!"></textarea>
                        <div class="cover-letter-char-count">
                            <span class="cl-current-count">0</span> / <span class="cl-max-count">2000</span>자
                        </div>
                    </div>
                </div>
                <div class="form-actions cover-letter-add-actions">
                    <button type="button" class="btn-outline cancel-cover-letter-add">취소</button>
                    <button type="button" class="btn-primary save-cover-letter-add">저장</button>
                </div>
            </div>
        </article>
    </form>

    <%-- 자기소개서 항목 목록 --%>
    <div class="cover-letter-list-wrapper" id="coverLetterListWrapper">
        <c:forEach items="${coverLetterList}" var="cl" varStatus="status">
            <article class="cover-letter-entry"
                data-cl-id="${cl.clId}"
                data-cl-title="${cl.clTitle}"
                data-cl-content="${cl.clContent}"
                data-sort-order="${cl.sortOrder}">
                <div class="cover-letter-top">
                    <div class="cover-letter-info">
                        <div class="cover-letter-header">
                            <span class="cover-letter-num">${status.index + 1}</span>
                            <strong>${cl.clTitle}</strong>
                        </div>
                    </div>
                    <div class="cover-letter-actions">
                        <button type="button" class="icon-btn btn-edit-cover-letter" aria-label="수정">
                            <i class="fas fa-pencil-alt"></i>
                        </button>
                        <button type="button" class="icon-btn btn-delete-cover-letter" aria-label="삭제">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </div>
                </div>
                <div class="cover-letter-body">
                    <p>${cl.clContent}</p>
                </div>
            </article>
        </c:forEach>
    </div>
</section>
