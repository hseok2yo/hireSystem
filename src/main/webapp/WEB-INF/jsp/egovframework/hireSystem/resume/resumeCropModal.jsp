<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.13/cropper.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.13/cropper.min.js"></script>
<link rel="stylesheet" href="/css/hireSystem/cropModal.css">
<script src="/js/hireSystem/resume/cropModal.js"></script>
	

<div id="cropModal" class="hidden">
    <div class="crop-modal-box">
        <button class="crop-modal-close" onclick="cancelCrop()">×</button>
        <h3>사진 올리기</h3>
        <ol class="crop-guide">
            <li>[파일 선택]을 클릭, 등록할 사진을 선택한 후, 이력서 및 회원정보에 첨부할 영역을 마우스로 드래그하여 선택해 주십시오.</li>
            <li>이력서용 사진 칸에 원하는 사진 영역 선택이 완료되면 [등록]을 클릭해주십시오.</li>
            <li>사진을 새로 등록 또는 변경하려면 작성한 이력서에도 사진이 변경됩니다.</li>
        </ol>
        <div class="crop-preview-area">
			<div class="crop-left">
				<p>원본사진</p>
				<div id="cropPlaceholder"
					${not empty userInfo.userPhotoUrl ? 'style="display:none"' : ''}>
					편집할 사진을 등록해주세요</div>
				<img id="cropImage" src="${userInfo.userPhotoOriginalName}"
					style="${not empty userInfo.userPhotoOriginalName ? '' : 'display:none'}">
			</div>
			<span class="crop-arrow">›</span>
            <div class="crop-right">
                <p>이력서용 사진</p>
                <div id="preview" class="preview-box"></div>
            </div>
        </div>
        <div class="crop-file-area">
            <label class="crop-file-label">
                <input type="file" id="photoFile" accept=".jpg,.jpeg,.png,.gif">
                <span>파일 선택</span>
            </label>
            <span class="crop-file-name">선택된 파일 없음</span>
        </div>
        <p class="crop-file-info">• 사진 파일은 10MB 미만의 JPG, JPEG, PNG, GIF 파일만 업로드 가능</p>
        <p class="crop-file-info">• 사진 크기는 100×140 픽셀로 노출됩니다.</p>
        <div class="crop-buttons">
            <button class="btn-outline" onclick="cancelCrop()">취소</button>
            <button class="btn-primary" onclick="submitCrop()">등록</button>
        </div>
    </div>
</div>