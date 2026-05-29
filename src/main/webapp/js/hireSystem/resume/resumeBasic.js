// =============================================
// DOMContentLoaded - 변수 할당 + 이벤트 등록만
// =============================================
document.addEventListener('DOMContentLoaded', function() {

    var viewBlock       = document.getElementById('basicInfoView');		//기본정보 show폼
    var editBlock       = document.getElementById('basicInfoEdit');		//숨겨진 기본정보 수정페이지
    var photoEditButton = document.querySelector('.photo-edit-btn');	//사진수정
    var editButton      = document.querySelector('.edit-basic-info');	//기본정보 수정
    var cancelButton    = document.querySelector('.cancel-basic-info'); //기본정보 취소
    var saveButton      = document.querySelector('.save-basic-info');	//기본정보 저장

    // 초기 상태
    showViewMode(viewBlock, editBlock, photoEditButton);

    // 기본정보 수정
    if (editButton) {
        editButton.addEventListener('click', function(e) {
            e.preventDefault();
            showEditMode(viewBlock, editBlock, photoEditButton);
        });
    }
	// 기본정보 취소
    if (cancelButton) {
        cancelButton.addEventListener('click', function(e) {
            e.preventDefault();
            showViewMode(viewBlock, editBlock, photoEditButton);
        });
    }
	// 기본정보 저장
    if (saveButton) {
        saveButton.addEventListener('click', function(e) {
            e.preventDefault();
            //서버저장 로직
			var formData = buildFormData('basicInfoEdit'); // 섹션 안 input/select/textarea 자동 수집 
			fetch('/hireSystem/resume/saveBasicResume.do', {
				method: 'POST',
				body: formData
			})
				.then(response => response.json())
				.then(data => {
					console.log(data);
					showViewMode(viewBlock, editBlock, photoEditButton);
				})
				.catch(error => {
					console.error(error);
				});
			

        });
    }

    // 사진 수정 버튼 → 크롭 모달 열기
    if (photoEditButton) {
        photoEditButton.addEventListener('click', function() {
            openCropModal();
        });
    }





});



// =============================================
// 기본정보 보기/수정 모드 전환
// =============================================
function showViewMode(viewBlock, editBlock, photoEditButton) {
    if (viewBlock && editBlock) {
        viewBlock.classList.remove('hidden');
        editBlock.classList.add('hidden');
    }
    if (photoEditButton) photoEditButton.classList.add('hidden');
}

function showEditMode(viewBlock, editBlock, photoEditButton) {
    if (viewBlock && editBlock) {
        viewBlock.classList.add('hidden');
        editBlock.classList.remove('hidden');
    }
    if (photoEditButton) photoEditButton.classList.remove('hidden');
}



