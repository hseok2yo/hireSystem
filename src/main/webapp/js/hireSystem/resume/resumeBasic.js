// =============================================
// DOMContentLoaded - 변수 할당 + 이벤트 등록만
// =============================================
document.addEventListener('DOMContentLoaded', function() {

    var viewBlock       = document.getElementById('basicInfoView');		//기본정보 show폼
    var editBlock       = document.getElementById('basicForm');		//숨겨진 기본정보 수정페이지
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
			var formData = new FormData(document.getElementById('basicFormElem'));
			formData.append('resumeId', RESUME_CTX.resumeId);
			formData.append('userNum',  RESUME_CTX.userNum);

			fetch('/hireSystem/resume/saveBasicResume.do', {
				method: 'POST',
				body: formData
			})
				.then(response => response.json())
				.then(data => {
					console.log(data);
					if(data.result) {
				        if(data.resumeId) {
				            document.getElementById("resumeId").value = data.resumeId;
							RESUME_CTX.resumeId = data.resumeId;
				        }
					}
					alert(data.message);
					showViewMode(viewBlock, editBlock, photoEditButton);
					updateBasicInfoView(); //보여주는 view도 데이터수정
				})
				.catch(error => {
					console.error(error);
					alert("오류가 발생했습니다. 다시 시도해주세요.");
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

function updateBasicInfoView() {
    const form = document.getElementById("basicFormElem");
    const getValue = (name) => form.querySelector(`[name='${name}']`).value;

    // 이름/생년 (구조 단순해서 그대로 유지)
    document.querySelector("#basicInfoView h3").textContent = getValue("userNm");
    const birthDate = getValue("birthDate");
    if (birthDate) {
        document.querySelector("#basicInfoView p").textContent = birthDate.substring(0, 4) + "년";
    }

    // data-field로 매핑 (nth-child 제거)
    const fieldMap = {
        "userEmail":   getValue("userEmail"),
        "userPhone":   getValue("userPhone"),
        "userAddress": getValue("addressFirst") + " " + getValue("addressSecond")
    };
    Object.keys(fieldMap).forEach(function(field) {
        var el = document.querySelector("#basicInfoView [data-field='" + field + "'] span");
        if (el) el.textContent = fieldMap[field];
    });
}