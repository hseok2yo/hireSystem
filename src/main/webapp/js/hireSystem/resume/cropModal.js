// =============================================
// 크롭 모달 전역 변수
// =============================================
let cropper;


// =============================================
// 모달 열기 / 닫기
// =============================================
function openCropModal() {
    document.getElementById('cropModal').classList.remove('hidden');

    const img = document.getElementById('cropImage');
    
    // src가 있을 때만 Cropper 초기화
    if (img.getAttribute('src') && img.getAttribute('src').trim() !== '') {
        const placeholder = document.getElementById('cropPlaceholder');
        placeholder.style.display = 'none';
        img.style.display = 'block';

        if (cropper) { cropper.destroy(); cropper = null; }

        cropper = new Cropper(img, {
            aspectRatio: 100 / 140,
            preview: '#preview',
            viewMode: 1,
        });
    }
}

function cancelCrop() {
	document.getElementById('cropModal').classList.add('hidden');
	// 파일명 초기화
    document.querySelector('.crop-file-name').textContent = '선택된 파일 없음';
    
    // 파일 input 초기화
    document.getElementById('photoFile').value = '';
    
    // Cropper 초기화
    if (cropper) { cropper.destroy(); cropper = null; }
}


// =============================================
// 파일 선택 → Cropper 초기화
// =============================================
function initCropper(file) {
	if (!file) return;
	document.querySelector('.crop-file-name').textContent = file.name;


	const reader = new FileReader();
	reader.onload = function(ev) {
		const img = document.getElementById('cropImage');
		const placeholder = document.getElementById('cropPlaceholder');

		placeholder.style.display = 'none';
		img.style.display = 'block';

		if (cropper) { cropper.destroy(); cropper = null; }

		img.src = ev.target.result;
		cropper = new Cropper(img, {
			aspectRatio: 100 / 140,
			preview: '#preview',
			viewMode: 3,
		});
	};
	reader.readAsDataURL(file);
}


// =============================================
// 등록 버튼 → 크롭 후 서버 업로드
// =============================================
function submitCrop() {
	if (!cropper) return;
	cropper.getCroppedCanvas({ width: 100, height: 140 }).toBlob(function(blob) {
		const formData = new FormData();
		formData.append('upload', blob, 'profile.jpg');

		fetch('/hireSystem/resume/image/uploadPhoto.do', {
			method: 'POST',
			body: formData
		})
			.then(res => res.json())
			.then(data => {
				if (data.success) {
					document.querySelector('.photo-box img').src = data.url;
					cancelCrop();
				} else {
					alert(data.message); // 서버에서 보낸 메시지
				}
			})
			.catch(err => {
				// 네트워크 오류 등 fetch 자체 실패
				alert('업로드 중 오류가 발생했습니다.');
				console.error(err);
			});
	});
}


// =============================================
// DOMContentLoaded - 이벤트 등록
// =============================================
document.addEventListener('DOMContentLoaded', function() {

	document.getElementById('photoFile').addEventListener('change', function(e) {
		initCropper(e.target.files[0]);
	});

});
