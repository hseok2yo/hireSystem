

document.addEventListener("DOMContentLoaded", function(event) {
    
	//카카오 로그인
	kakaoLoginButtonEvent();
	
	

	//form 전 유효성 체크 후 submit
	validateFormOnLoad();

});


function kakaoLoginButtonEvent() {
	
	var kakaoBtn = document.querySelector("#kakaoLogin");
	
	kakaoBtn.addEventListener("click", function() {
		//location.href = kakaoAuthUrl;  //login.jsp에 있음
		const returnUrl = document.getElementById("redirectUrl").value;
		location.href = "/auth/kakaoHireSystem/kakaoAuth.do?returnUrl=" + encodeURIComponent(returnUrl);
	});
}

//form제출 전 유효성 검사
function validateFormOnLoad() {
	
	const form = document.getElementById("loginForm");

    form.addEventListener("submit", function(event) {
        event.preventDefault(); // 🔥 기본 submit 막기

        const formData = new FormData(form);

		// 🔥 FormData를 일반 객체로 변환
		const data = Object.fromEntries(formData.entries());
		
		// ✅ 체크박스는 체크 안 하면 FormData에 아예 안 들어오기 때문에 따로 처리
        data.remember = document.getElementById("remember").checked ? "true" : "false";
		
		fetch(form.action, {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(data)
		}) 

        .then(response => response.json())  // 컨트롤러 반환 타입에 맞게
        .then(result => {

            console.log("서버응답:", result);
				
            if (result.result === "success") {
				const redirectUrl = document.getElementById("redirectUrl").value;
				
				location.replace(redirectUrl); //로그인페이지 뒤로가기 방지
                

            } else {
                alert("아이디 또는 비밀번호가 틀렸습니다.");
            }

        })
        .catch(error => {
            console.error("에러 발생:", error);
        });

    });
}