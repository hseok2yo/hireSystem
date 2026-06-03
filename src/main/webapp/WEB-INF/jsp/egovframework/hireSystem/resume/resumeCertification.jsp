<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<section class="section-card section-certification" id="certification">
    <div class="section-header">
        <div>
            <h2>자격/어학/수상</h2>
        </div>
        <div class="section-actions">
            <button type="button" class="btn-text certification-add-btn">+ 추가</button>
        </div>
    </div>

    <%-- 자격사항 입력 폼 --%>
    <form id="certificationForm">
        <article class="certification-entry certification-add-form hidden"
            id="certificationAddForm">
            <div class="career-add-grid">
                <div class="form-row form-row-full">
                    <label>자격증명 <span>*</span></label>
                    <input type="text" name="certName" placeholder="자격증명을 입력하세요">
                </div>
                <div class="form-row form-row-half">
                    <label>발행기관</label>
                    <input type="text" name="issuer" placeholder="발행기관을 입력하세요">
                </div>
                <div class="form-row form-row-quarter">
                    <label>취득일</label>
                    <input type="date" name="acquiredDate">
                </div>
                <div class="form-row form-row-half">
                    <label>자격증 번호</label>
                    <input type="text" name="certNumber" placeholder="자격증 번호를 입력하세요">
                </div>
                <div class="form-row form-row-quarter">
                    <label>점수/등급</label>
                    <input type="text" name="score" placeholder="예) 900점, 1급">
                </div>
                <div class="form-actions career-add-actions">
                    <button type="button" class="btn-outline cancel-certification-add">취소</button>
                    <button type="button" class="btn-primary save-certification-add">저장</button>
                </div>
            </div>
        </article>
    </form>

    <%-- 자격사항 목록 (백에서 조회 후 forEach) --%>
    <div class="certification-list-wrapper" id="certificationListWrapper">
        <c:forEach items="${certificationInfo}" var="cert">
            <article class="certification-entry"
                data-certification-id="${cert.certificationId}"
                data-cert-name="${cert.certName}"
                data-issuer="${cert.issuer}"
                data-acquired-date="<fmt:formatDate value='${cert.acquiredDate}' pattern='yyyy-MM-dd'/>"
                data-cert-number="${cert.certNumber}"
                data-score="${cert.score}">

                <div class="certification-top">
                    <div class="certification-info">
                        <div class="certification-header">
                            <strong>${cert.certName}</strong>
                            <c:if test="${not empty cert.acquiredDate}">
                                <span class="career-period">
                                    <fmt:formatDate value="${cert.acquiredDate}" pattern="yyyy.MM.dd" /> 취득
                                </span>
                            </c:if>
                        </div>
                        <p class="certification-detail">
                            <c:if test="${not empty cert.issuer}">
                                <span>${cert.issuer}</span>
                            </c:if>
                            <c:if test="${not empty cert.certNumber}">
                                <span class="cert-divider"> · </span>
                                <span>No. ${cert.certNumber}</span>
                            </c:if>
                            <c:if test="${not empty cert.score}">
                                <span class="cert-divider"> · </span>
                                <span>${cert.score}</span>
                            </c:if>
                        </p>
                    </div>
                    <div class="career-actions">
                        <button type="button" class="icon-btn btn-edit-certification"
                            aria-label="수정">
                            <i class="fas fa-pencil-alt"></i>
                        </button>
                        <button type="button" class="icon-btn btn-delete-certification"
                            aria-label="삭제">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </div>
                </div>
            </article>
        </c:forEach>

        <%-- 등록된 자격사항이 없을 때 안내 문구 --%>
        <c:if test="${empty certificationInfo}">
            <div class="section-empty">
                <p>등록된 자격/어학/수상 내역이 없습니다.</p>
            </div>
        </c:if>
    </div>
</section>
