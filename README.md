🧑‍💼 HireSystem - 취업 정보 게시판 프로젝트
Spring + eGovFrame 기반의 취업 정보 커뮤니티 웹 서비스입니다.  
회원가입/로그인, 워크넷 기업정보 API, 게시판 CRUD, 이력서 작성, CKEditor5 이미지 업로드, 카카오 소셜 로그인, 이메일 인증까지 구현했습니다.
---
🛠 기술 스택
분류	기술
Language	Java 11
Framework	Spring 5.3.20, eGovFrame 4.x
ORM	MyBatis (MyBatis Generator)
DB	MySQL
View	JSP, JSTL
Build	Maven
기타	Lombok, CKEditor5, Kakao OAuth2, JavaMail, Cropper.js
---
📁 프로젝트 구조
```
src/main/java/hireSystem/
├── common/
│   ├── LoginInterceptor.java          # 일반 페이지 로그인 인터셉터
│   ├── RestLoginInterceptor.java      # REST/Ajax 로그인 인터셉터
│   ├── CkEditor5Controller.java       # CKEditor 이미지 업로드 처리
│   ├── CommonFileService.java         # 공통 파일 저장/삭제/비교
│   ├── CommonFileController.java      # 공통 파일 다운로드/삭제 엔드포인트
│   ├── CommonUtil.java                # 공통 유틸리티
│   ├── PagingUtil.java                # 페이징 처리 유틸
│   └── ImageServeController.java      # 이미지 서빙
├── web/
│   ├── HireBoardListController.java   # 게시판 CRUD
│   ├── HireLoginController.java       # 로그인/로그아웃
│   ├── HireSignupController.java      # 회원가입
│   ├── HireCompanyController.java     # 기업 정보
│   ├── KakaoAuthController.java       # 카카오 OAuth
│   ├── HireResumeController.java      # 이력서 메인/기본정보
│   ├── HireEducationController.java   # 이력서 - 학력
│   ├── HireCareerController.java      # 이력서 - 경력
│   ├── HireCertificationController.java  # 이력서 - 자격증
│   ├── HireActivityController.java    # 이력서 - 활동/수상
│   ├── HirePortfolioController.java   # 이력서 - 포트폴리오
│   ├── HireCoverLetterController.java # 이력서 - 자기소개서
│   └── HireResumeSkillController.java # 이력서 - 보유 스킬
├── email/
│   ├── EmailController.java
│   └── EmailService.java              # 이메일 인증번호 발송
├── service/                           # 서비스 인터페이스 및 구현체
├── vo/                                # VO 클래스
└── ...

src/main/webapp/js/hireSystem/
├── common.js                          # 공통 Fetch 유틸 (apiLoginCommonFetch)
├── ckeditorcustom.js                  # CKEditor5 커스텀 설정 및 이미지 업로드 어댑터
├── board/
│   ├── boardWrite.js                  # 글쓰기
│   ├── boardEdit.js                   # 글수정
│   └── boardList.js                   # 목록
├── resume/
│   ├── resumeMain.js                  # 이력서 목록/관리
│   ├── resumeBasic.js                 # 기본정보 (프로필 사진 포함)
│   ├── cropModal.js                   # 프로필 이미지 크롭 모달
│   ├── resumeEducation.js             # 학력
│   ├── resumeCareer.js                # 경력
│   ├── resumeCertification.js         # 자격증
│   ├── resumeActivity.js              # 활동/수상
│   ├── resumePortfolio.js             # 포트폴리오
│   ├── resumeCoverLetter.js           # 자기소개서
│   ├── resumeSkill.js                 # 보유 스킬
│   └── resumeCommon.js                # 이력서 공통 유틸
├── login.js                           # 로그인
└── signup.js                          # 회원가입
```
---
✅ 주요 기능
👤 회원
일반 회원가입 (이메일 인증 + 약관 동의)
카카오 소셜 로그인 (OAuth2)
로그인 인터셉터 (비로그인 접근 차단)
아이디/비밀번호 로그인 (세션 기반)
로그인 상태 유지 선택 (30분 / 24시간)
회원가입 약관 동의 흐름  
`signupFormAgree.jsp` → 약관 동의 → 일반 회원가입(`signupFormNormal.jsp`) 또는 카카오 회원가입(`signupFormKakao.jsp`) 분기 처리
🔐 인터셉터
`LoginInterceptor` — 일반 페이지 접근 시 비로그인이면 로그인 페이지로 리다이렉트 (`redirectUrl` 파라미터 유지)  
`RestLoginInterceptor` — Fetch/Ajax 요청 시 비로그인이면 HTTP 401 반환
📡 공통 Fetch 유틸 (`common.js`)
`apiLoginCommonFetch(url, options)` — 모든 Ajax 요청에 `X-Requested-With` 헤더 자동 삽입  
401 응답 시 현재 URL을 `redirectUrl`로 인코딩해 로그인 페이지로 자동 이동  
프로젝트 내 모든 비동기 요청에서 공통으로 사용
---
📋 게시판
워크넷 API 기업정보 연동
게시글 목록 조회 (페이징, 카테고리 필터)
게시글 등록 / 수정 / 삭제
CKEditor5 텍스트 에디터
CKEditor5 커스텀 (`ckeditorcustom.js`)  
커스텀 이미지 업로드 어댑터 (`MyUploadAdapter`) 구현  
이미지 업로드 시 `/ckEditor/upload/image.do`로 POST 전송  
CKEditor5 방식 JSON 응답 형식 (`{ url: "..." }`) 처리
파일/이미지 관리 (`CommonFileService`)  
UUID 기반 파일명 생성으로 중복 방지  
파일 단건/다건 삭제  
`diffImages()` — 게시글 수정 시 에디터에 남아있는 이미지와 DB 저장 이미지를 비교해 삭제/추가할 파일명 자동 계산
---
📄 이력서
이력서를 구성하는 각 항목을 독립된 페이지에서 CRUD 처리하며, 별도의 컨트롤러/서비스/DAO/매퍼로 분리 관리합니다.
항목	설명
기본정보	이름, 연락처, 프로필 사진 등 기본 인적사항
학력	학교명, 전공, 입/졸업 기간 등
경력	회사명, 직무, 재직 기간 등
자격증	자격증명, 발급기관, 취득일 등
활동/수상	대외활동, 공모전, 수상 이력 등
포트폴리오	파일/링크 기반 포트폴리오 첨부
자기소개서	문항별 자기소개서 작성
보유 스킬	기술 스택 및 숙련도 입력
프로필 이미지 크롭 모달 (`cropModal.js`, `Cropper.js`)  
이미지 업로드 시 크롭 모달 팝업  
원하는 영역을 선택하여 프로필 사진으로 저장  
크롭 결과를 Blob으로 변환 후 서버에 전송
---
📧 이메일 인증 (`EmailService`)
6자리 랜덤 인증번호 생성 및 발송
인증번호 만료 시간 관리 (Map 기반)
`@Scheduled`로 만료된 인증번호 자동 정리
---
🖼 이미지 업로드
CKEditor5 이미지 업로드 → 서버 저장
TEMP / STORE 상태 관리 (게시글 등록 전 임시저장)
게시글 수정 시 삭제된 이미지 자동 정리 (DB + 파일)
`CommonFileService`로 파일 처리 공통화
---
🔒 보안 관련 설정
로그아웃 후 뒤로가기 방지: `Cache-Control: no-store` 헤더 적용
Referer 검증: 로그아웃 후 복귀 시 허용된 경로만 리다이렉트
카카오 Client ID, Redirect URI 등 민감 정보는 `globals.properties`로 관리 (git 비포함 권장)
