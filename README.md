# 🧑‍💼 HireSystem - 취업 정보 게시판 프로젝트

Spring  + eGovFrame 기반의 취업 정보 커뮤니티 웹 서비스입니다.  
회원가입/로그인부터 기업정보api(워크넷), 게시판 CRUD, CKEditor5 이미지 업로드, 카카오 소셜 로그인, 이메일 인증까지 구현했습니다.

\---

## 🛠 기술 스택

|분류|기술|
|-|-|
|Language|Java 11|
|Framework|Spring 5.3.20, eGovFrame 4.x|
|ORM|MyBatis (MyBatis Generator)|
|DB|MySQL|
|View|JSP, JSTL|
|Build|Maven|
|기타|Lombok, CKEditor5, Kakao OAuth2, JavaMail|

\---

## 📁 프로젝트 구조

src/main/java/hireSystem/

├── common/

│   ├── LoginInterceptor.java        # 일반 페이지 로그인 인터셉터

│   ├── RestLoginInterceptor.java    # REST/Ajax 로그인 인터셉터

│   ├── CkEditor5Controller.java     # CKEditor 이미지 업로드 처리

│   ├── CommonFileService.java       # 공통 파일 저장/삭제/비교

│   └── ImageServeController.java    # 이미지 서빙

├── web/

│   ├── HireBoardListController.java # 게시판 CRUD

│   ├── HireLoginController.java     # 로그인/로그아웃

│   ├── HireSignupController.java    # 회원가입

│   ├── HireCompanyController.java   # 기업 정보

│   └── KakaoAuthController.java     # 카카오 OAuth

├── email/

│   ├── EmailController.java

│   └── EmailService.java            # 이메일 인증번호 발송

├── service/                         # 서비스 인터페이스 및 구현체

├── vo/                              # VO 클래스

└── ...



src/main/webapp/js/hireSystem/

├── common.js          # 공통 Fetch 유틸 (apiLoginCommonFetch) 

├── ckeditorcustom.js  # CKEditor 5 커스텀 설정 및 이미지 업로드 어댑터

├── boardWrite.js      # 글쓰기

├── boardEdit.js       # 글수정

├── boardList.js       # 목록

├── login.js           # 로그인

└── signup.js          # 회원가입

### 회원

* 일반 회원가입 (이메일 인증)
* 카카오 소셜 로그인 (OAuth2)
* 로그인 인터셉터 (비로그인 접근 차단)
* 아이디/비밀번호 로그인 (세션 기반)
* 로그인 상태 유지 선택 (30분 / 24시간)



### 인터셉터

* LoginInterceptor : 일반 페이지 접근 시 비로그인이면 로그인 페이지로 리다이렉트 (redirectUrl 파라미터 유지) / Fetch/Ajax 요청 시 비로그인이면 HTTP 401 반환



### 공통 Fetch 유틸 (`common.js`)

* `apiLoginCommonFetch(url, options)` : 모든 Ajax 요청에 `X-Requested-With` 헤더 자동 삽입
* 401 응답 시 현재 URL을 `redirectUrl`로 인코딩해 로그인 페이지로 자동 이동
* 프로젝트 내 모든 비동기 요청에서 공통으로 사용



### 게시판
* 워크넷 api 기업정보목록 사용
* 게시글 목록 조회 (페이징, 카테고리 필터)
* 게시글 등록 / 수정 / 삭제
* CKEditor5 텍스트 에디터
* 게시글 수정 시 추가/삭제될 이미지 구분하여 들어가도록 구현



### CKEditor 5 커스텀 (`ckeditorcustom.js`)

* 이미지 업로드 커스텀 어댑터 (`MyUploadAdapter`) 구현
* 이미지 업로드 시 `/ckEditor/upload/image.do`로 POST 전송
* CKEditor 5 방식 JSON 응답 형식 (`{ url: "..." }`) 처리



### 파일/이미지 관리 (`CommonFileService`)

* UUID 기반 파일명 생성으로 중복 방지
* 파일 단건/다건 삭제
* `diffImages()` : 게시글 수정 시 에디터에 남아있는 이미지와 DB 저장 이미지를 비교해 삭제/추가할 파일명 자동 계산



### 이메일 인증 (`EmailService`)

* 6자리 랜덤 인증번호 생성 및 발송
* 인증번호 만료 시간 관리 (Map 기반)
* `@Scheduled`로 만료된 인증번호 자동 정리



### 이미지 업로드

* CKEditor5 이미지 업로드 → 서버 저장
* TEMP / STORE 상태 관리 (게시글 등록 전 임시저장)
* 게시글 수정 시 삭제된 이미지 자동 정리 (DB + 파일)
* `CommonFileService`로 파일 처리 공통화



## 보안 관련 설정

* 로그아웃 후 뒤로가기 방지: `Cache-Control: no-store` 헤더 적용
* Referer 검증: 로그아웃 후 복귀 시 허용된 경로만 리다이렉트
* 카카오 Client ID, Redirect URI 등 민감 정보는 `globals.properties`로 관리 (git 비포함 권장)



## ⚙️ 실행 방법

### 1\. DB 설정

`src/main/resources/egovframework/spring/context-datasource.xml` 에서 DB 연결 정보 수정
<property name="url" value="jdbc:mysql://localhost:3306/hireSystem?useSSL=false\&amp;characterEncoding=UTF-8"/>
<property name="username" value="root"/>
<property name="password" value="your\_password"/>


### 2\. 파일 저장 경로 설정

`src/main/resources/egovframework/property/globals.properties` 수정

image.store.path=C:/your/path/hireSystem/images/store/

image.store.url=/image/store.do?filename=



### 3. 카카오 로그인 설정 (선택)

카카오 개발자 콘솔에서 앱 키 발급 후 설정 파일에 추가



### 4\. 서버 실행

Apache Tomcat 8.5 배포 후 실행





