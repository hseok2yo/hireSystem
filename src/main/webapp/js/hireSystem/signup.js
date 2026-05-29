
/** 전역변수 */
let checkingId = ""; //중복체크 전용 아이디전역변수
var timerInterval = null; // 전역변수로 선언
var verifiedEmail = null; // 인증완료된 이메일 저장

document.addEventListener('DOMContentLoaded', function() {
	
	const isKakao = document.getElementById('isKakao').value === 'true';
	
	// 전체 동의 체크박스 처리 (공통)
	agreeCheckboxClick();
	
	console.log("카카오인가 isKakao"+isKakao);
	if(isKakao) {
        // 카카오 전용 초기화
        validateFormKakao();
		// 카카오인데 이메일 없는 경우 인증 초기화
	    const kakaoEmailVerified = document.getElementById('kakaoEmailVerified').value;
	    if (kakaoEmailVerified === 'false') {
	        emailVerifyClick(); //이메일 인증클릭
	        emailVerifyCheck(); //이메일 인증번호 확인
	    }
        
    } else {
        // 일반 전용 초기화
        passwordSetting();     		//비밀번호 보임/숨김
        checkDuplicationID();   	//아이디 중복체크
        emailVerifyClick();     	//이메일 인증클릭
        emailVerifyCheck();			//이메일 인증번호 확인
        validateFormOnLoad();		// 3.폼 제출 전 유효성 검사
    }


});



//이메일 인증 클릭
function emailVerifyClick() {
	// 인증번호 전송
	document.querySelector("#verify-btn").addEventListener("click", function() {
		var email = document.getElementById("userEmail").value;

		if (email === "") {
			alert("이메일을 입력해주세요");
			return;
		}
		
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/hireSystem/sendCode.do");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		xhr.onload = function() {
			var response = JSON.parse(xhr.responseText);
        	
			if (xhr.status === 200 && response.result === "sent") {
				document.getElementById("codeArea").style.display = "block"; // 입력창 보이기
				startTimer(parseInt(response.expireTime)); //타이머실행
        		document.getElementById("sendMsg").textContent = "인증번호가 전송되었습니다."; // alert 대신 화면에 텍스트로 표시
			}
		};

		xhr.send("email=" + encodeURIComponent(email));
	});
}

//인증번호 타이머
function startTimer(timeLeft) {
    var timerEl = document.getElementById("timer");
    
    if (timerInterval) {
        clearInterval(timerInterval);
    }
    
    timerInterval = setInterval(function() {
        var min = Math.floor(timeLeft / 60);
        var sec = timeLeft % 60;
        
        timerEl.textContent = "(" + min + "분 " + (sec < 10 ? "0" + sec : sec) + "초)";
        
        if (timeLeft <= 0) {
            clearInterval(timerInterval);
            timerEl.textContent = "(만료됨)";
            document.getElementById("authCode").disabled = true; //인증번호 입력란
		} else {
			document.getElementById("authCode").disabled = false; //인증번호 입력란
		}
        
        timeLeft--;
    }, 1000);
}


//이메일 인증번호 확인
function emailVerifyCheck() {
	
	document.querySelector("#verify-btn-check").addEventListener("click", function() {
		var email = document.getElementById("userEmail").value;
		var code = document.getElementById("authCode").value;

		if (code === "") {
			alert("인증번호를 입력해주세요");
			return;
		}

		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/hireSystem/verifyCode.do");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		xhr.onload = function() {
			var response = JSON.parse(xhr.responseText);
			console.log(response.result);
			if (response.result === "ok") {
				alert("인증 완료!");
				verifiedEmail = document.getElementById("userEmail").value; // 인증된 이메일 저장
				
				document.getElementById("codeArea").style.display = "none"; //인증번호 영역
				document.getElementById("authCode").disabled = true; //인증번호 재입력 방지
				document.getElementById("authCode").value = ""; //인증번호 초기화
				
				document.getElementById("sendMsg").textContent = "✔ 이메일 인증 완료";
				document.getElementById("sendMsg").style.color = "green";
				
			} else if (response.result === "expire") {
				alert("인증번호가 만료되었습니다. 다시 요청해주세요.");
			} else {
				alert("인증번호가 틀렸습니다.");
			}
		};

		xhr.send("email=" + encodeURIComponent(email) + "&code=" + encodeURIComponent(code));
	});
}

//이메일 인증 유효성체크
function emailValidAccess() {
	var currentEmail = document.getElementById("userEmail").value;
    
    // 인증된 이메일이랑 현재 이메일 비교
    if (verifiedEmail === null || verifiedEmail !== currentEmail) {
        alert("이메일 인증을 해주세요.");
        return false;
    }

	return true;
}

/** 1. 비밀번호 보임/숨김 */
function passwordSetting() {
	/** 1. 비밀번호 보임/숨김 */
	// toggle-password 클래스를 가진 모든 요소 가져오기
	const buttons = document.querySelectorAll('.toggle-password');

	// 하나씩 순회하면서 이벤트 추가
	for (let i = 0; i < buttons.length; i++) {
		let button = buttons[i];

		button.addEventListener('click', function() {
			const input = this.previousElementSibling;
			const icon = this.querySelector('i');

			if (input.type === 'password') {
				input.type = 'text';
				icon.classList.remove('fa-eye');
				icon.classList.add('fa-eye-slash');
			} else {
				input.type = 'password';
				icon.classList.remove('fa-eye-slash');
				icon.classList.add('fa-eye');
			}
		});
	}
}
// 2.전체 동의 체크박스 처리
function agreeCheckboxClick() {
	const agreeAllCheckbox = document.getElementById('agreeAll');
	const agreementCheckboxes = document.querySelectorAll('input[name="agreements"]');

	agreeAllCheckbox.addEventListener('change', function() {
		agreementCheckboxes.forEach(checkbox => {
			checkbox.checked = this.checked;
		});
	});

	agreementCheckboxes.forEach(checkbox => {
		checkbox.addEventListener('change', function() {
			const allChecked = Array.from(agreementCheckboxes).every(c => c.checked);
			agreeAllCheckbox.checked = allChecked;
		});
	});
}

// 카카오 폼 제출 전 유효성 검사
function validateFormKakao() {
    document.getElementById('signupForm').addEventListener('keydown', function(e) {
        if (e.key === 'Enter') e.preventDefault();
    });
    
    document.getElementById('signupForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        // 이름, 휴대폰만 체크
        if (!nameCheck()) return;
        if (!phoneCheck()) return;
        // 카카오 이메일 여부 확인
        const kakaoEmailVerified = document.getElementById('kakaoEmailVerified').value;
        if (kakaoEmailVerified === 'false') {
            // 카카오 이메일 없는 경우 → 직접입력 + 인증 필요
            if (!emailCheck()) return;
            if (!emailValidAccess()) return;
        }
        // 약관동의
        const requiredAgreements = Array.from(document.querySelectorAll('input[name="agreements"].required'));
        if (!requiredAgreements.every(checkbox => checkbox.checked)) {
            alert('필수 약관에 모두 동의해주세요.');
            return;
        }
        
        this.submit();
    });
}

// 3.폼 제출 전 유효성 검사
function validateFormOnLoad() {
	document.getElementById('signupForm').addEventListener('keydown', function(e) {
	  if (e.key === 'Enter') {
	    e.preventDefault();
	    //console.log('엔터 차단');
	  }
	});
		
	document.getElementById('signupForm').addEventListener('submit', function(e) {
		e.preventDefault();
		
		//1)아이디
		if (!idCheck()) return;
		
		//2)이메일
		if (!emailCheck()) return;
	
		if(!emailValidAccess()) return;
	
		//3)비밀번호, 비밀번호 확인
		if (!passwordCheck()) return;
		
		//4)이름
		if (!nameCheck()) return;
		
		//5)휴대폰 번호
		if (!phoneCheck()) return;
		
		
		// 6)필수 약관 동의 확인
		const requiredAgreements = Array.from(document.querySelectorAll('input[name="agreements"].required'));
		if (!requiredAgreements.every(checkbox => checkbox.checked)) {
			alert('필수 약관에 모두 동의해주세요.');
			return;
		}

		// 7)모든 검증 통과 시 폼 제출
		this.submit();
	});
}

//아이디 유효성 체크
function idCheck() {
	var idInput = document.querySelector("input[name='userId']");
	var id = idInput.value.trim();
	
	if(id == '') {
		alert("아이디를 입력하세요");
		return false;
	}
	
	if( checkingId != id) {
		alert("중복체크가 필요합니다")
		return false;
	}
	
	return true;
}

//이름 유효성 체크
function nameCheck() {
	var nameInput = document.querySelector("input[name='userNm']");
	var name = nameInput.value.trim();
	
	if(name == '') {
		alert("이름을 입력하세요")
		return false;
	}
	
	return true;
}


//폰번호 유효성 체크
function phoneCheck() {
	var phoneInput = document.querySelector("input[name='userPhone']");
	var phone = phoneInput.value.replace(/[^0-9]/g, '');
	
	if(phone == '') {
		alert("휴대폰 번호를 입력하세요")
		return false;
	}
	
	const regex = /^01[016789]\d{7,8}$/;
	
	if(!regex.test(phone)){
		alert("휴대폰 형식이 맞지 않습니다")
        return false;
    }

	// 하이픈 자동 추가
    if (phone.length === 10) { // 010-123-4567
        phoneInput.value = phone.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
    } else if (phone.length === 11) { // 010-1234-5678
        phoneInput.value = phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    } else {
        // 혹시 잘못된 길이
        phoneInput.value = phone;
    }

	return true;
}


//이메일 유효성 체크
function emailCheck() {

	const emailInput = document.getElementById('userEmail');
	const email = emailInput.value.trim(); // 앞뒤 공백 제거
	const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 기본 이메일 정규식

	if (email === "") {
		alert('이메일을 입력해주세요.');
		emailInput.focus();
		return false;
	}

	if (!regex.test(email)) {
		alert('이메일 형식을 맞춰주세요');
		return false;
	}

	return true;
}

//비번유효성 체크
function passwordCheck() {
	const passwordInput = document.getElementById('userPw');
	let password = passwordInput.value.trim();
	const passwordConfirmInput = document.getElementById('passwordConfirm');
	let passwordConfirm = passwordConfirmInput.value.trim();

	if (password.length == 0) {
		alert('비밀번호를 입력해주세요.');
		passwordInput.focus();
		return false;
	}

	if (passwordConfirm.length == 0) {
		alert('비밀번호 확인란을 입력해주세요.');
		passwordConfirmInput.focus();
		return false;
	}

	if (password !== passwordConfirm) {
		alert('비밀번호가 일치하지 않습니다.');
		return false;
	}

	// 비밀번호 정규식 검사
	const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;
	if (!passwordRegex.test(password)) {
		alert('비밀번호는 영문, 숫자, 특수문자를 포함하여 8-20자로 입력해주세요.');
		return false;
	}

	return true;
}


//중복아이디 체크
function checkDuplicationID() {
	
	let checkButton = document.getElementById('idcheck');
	let idInput = document.getElementById('userId');
	let resultDiv = document.getElementById('idcheck-result');

	checkButton.addEventListener('click', function() {
		const userId = idInput.value.trim();
		
		if (userId === '') {
			resultDiv.textContent = '아이디를 입력하세요.';
			resultDiv.style.color = 'red';
			return;
		}

		var xhr = new XMLHttpRequest();
		xhr.open('POST', '/hireSystem/signup/checkDuplicationID.do', true);
		xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
		xhr.send(JSON.stringify({ id: userId }));
		
		xhr.onreadystatechange = function() {
			if (xhr.readyState === 4 && xhr.status === 200) {
				try {
					var data = JSON.parse(xhr.responseText);
					//console.log(xhr.responseText);
					//console.log(data);
					//console.log(data.exists);
					
					/** false 사용중, true 사용x */
					if (!data.exists) { //false일 경우
						resultDiv.textContent = '이미 사용 중인 아이디입니다.';
						resultDiv.style.color = 'red';
						checkingId = '';//중복체크 아이디 저장
						
					} else { //true일 경우
						resultDiv.textContent = '사용 가능한 아이디입니다!';
						resultDiv.style.color = 'green';
						
						checkingId = userId; //중복체크 아이디 저장
						//console.log("체크된 id:", checkingId);
					}
				} catch (e) {
					resultDiv.textContent = '서버 응답 처리 중 오류 발생';
					resultDiv.style.color = 'red';
				}
			} else {
				resultDiv.textContent = '서버 요청 실패 (' + xhr.status + ')';
				resultDiv.style.color = 'red';
			}
		};


	});
}


