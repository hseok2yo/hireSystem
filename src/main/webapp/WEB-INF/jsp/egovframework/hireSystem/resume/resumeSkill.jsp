<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<section class="section-card section-skill" id="skills">
	<div class="section-header">
		<div>
			<h2>스킬</h2>
		</div>
	</div>

	<%-- 태그 목록 (서버에서 내려온 스킬 + 동적 추가분) --%>
	<div class="skill-tag-wrap" id="skillTagWrap">
		<c:forEach items="${skillInfo}" var="skill">
			<span class="skill-tag" data-skill-id="${skill.skillId}"
				data-skill-name="${skill.skillName}"> ${skill.skillName}
				<button type="button" class="skill-tag-delete" aria-label="삭제">
					<i class="fas fa-times"></i>
				</button>
			</span>
		</c:forEach>
	</div>

	<%-- 입력 행 --%>
	<div class="skill-input-row">
		<input type="text" id="skillInput"
			placeholder="스킬을 입력하고 Enter 또는 + 버튼을 누르세요" maxlength="50">
		<button type="button" id="skillAddBtn" class="btn-skill-add">+
			추가</button>
	</div>
</section>