<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"   prefix="fmt" %>

<section class="section-card section-activity" id="activity">

    <%-- 헤더 --%>
    <div class="section-header">
        <div>
            <h2>경험/활동/교육</h2>
        </div>
        <div class="section-actions">
            <button type="button" class="btn-text activity-add-btn">+ 추가</button>
        </div>
    </div>

    <%-- 추가/수정 입력 폼 (기본 hidden) --%>
    <form id="activityForm">
        <article class="activity-entry activity-add-form hidden" id="activityAddForm">
            <div class="activity-add-grid">

                <%-- 활동구분 + 기관/장소명 --%>
                <div class="form-row form-row-half">
                    <label>활동구분 <span>*</span></label>
                    <select name="activityType">
                        <option value="">활동구분 선택 *</option>
                        <option value="교내활동">교내활동</option>
                        <option value="인턴">인턴</option>
                        <option value="자원봉사">자원봉사</option>
                        <option value="해외연수">해외연수</option>
                        <option value="어학연수">어학연수</option>
                        <option value="직무교육">직무교육</option>
                        <option value="기타">기타</option>
                    </select>
                </div>
                <div class="form-row form-row-half">
                    <label>기관/장소명 <span>*</span></label>
                    <input type="text" name="orgName" placeholder="기관/장소명 *">
                </div>

                <%-- 월입력(진행중 토글) + 시작년월 + 종료년월 --%>
                <div class="form-row current-job-row">
                    <label>진행중</label>
                    <label class="switch">
                        <input type="checkbox" name="currentYn">
                        <span class="slider"></span>
                    </label>
                </div>
                <div class="form-row form-row-quarter">
                    <label>시작년월 <span>*</span></label>
                    <input type="date" name="startDate">
                </div>
                <div class="form-row form-row-quarter">
                    <label>종료년월</label>
                    <input type="date" name="endDate">
                </div>

                <%-- 활동내용 --%>
                <div class="form-row form-row-full">
                    <label>활동내용</label>
                    <textarea name="content"
                              id="activityContent"
                              rows="5"
                              placeholder="경험/활동 상세내용 입력"></textarea>
                </div>

                <%-- 글자수 카운터 --%>
                <div class="activity-char-count">
                    <span>총 글자수 <b id="activityTotalCount">0</b>자 / <b id="activityTotalByte">0</b> byte</span>
                    <span>공백제외 <b id="activityNoBlanksCount">0</b>자 / <b id="activityNoBlanksBytes">0</b> byte</span>
                </div>

                <%-- 취소 / 저장 버튼 --%>
                <div class="activity-add-actions">
                    <button type="button" class="btn-outline cancel-activity-add">취소</button>
                    <button type="button" class="btn-primary save-activity-add">저장</button>
                </div>

            </div>
        </article>
    </form>

    <%-- 목록 (서버 조회 결과) --%>
    <div class="activity-list-wrapper" id="activityListWrapper">
        <c:forEach items="${activityList}" var="act">
            <article
                class="activity-entry"
                data-activity-id="${act.activityId}"
                data-activity-type="${act.activityType}"
                data-org-name="${act.orgName}"
                data-start-date="<fmt:formatDate value='${act.startDate}' pattern='yyyy-MM-dd'/>"
                data-end-date="<fmt:formatDate value='${act.endDate}'   pattern='yyyy-MM-dd'/>"
                data-current-yn="${act.currentYn}"
                data-content="${act.content}">

                <div class="activity-top">
                    <div class="activity-info">
                        <div class="activity-header">
                            <strong class="activity-org">${act.orgName}</strong>
                            <c:if test="${not empty act.activityType}">
                                <span class="activity-type-badge">${act.activityType}</span>
                            </c:if>
                            <span class="activity-period">
                                <fmt:formatDate value="${act.startDate}" pattern="yyyy.MM"/>
                                ~
                                <c:choose>
                                    <c:when test="${act.currentYn eq 'Y'}">진행중</c:when>
                                    <c:otherwise>
                                        <fmt:formatDate value="${act.endDate}" pattern="yyyy.MM"/>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <p class="activity-content">${act.content}</p>
                    </div>

                    <div class="activity-actions">
                        <button type="button" class="icon-btn btn-edit-activity"   aria-label="수정">
                            <i class="fas fa-pencil-alt"></i>
                        </button>
                        <button type="button" class="icon-btn btn-delete-activity" aria-label="삭제">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </div>
                </div>

            </article>
        </c:forEach>
    </div>

</section>
