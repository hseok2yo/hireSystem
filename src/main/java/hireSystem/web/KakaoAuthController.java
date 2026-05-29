package hireSystem.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.egovframe.rte.fdl.property.EgovPropertyService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import hireSystem.service.KakaoAuthService;
import hireSystem.vo.HireUserVo;
import hireSystem.vo.KakaoUserVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/auth/kakaoHireSystem")
public class KakaoAuthController {
	
    @Resource(name= "kakaoAuthService")
    private KakaoAuthService kakaoAuthService;

    @Resource(name = "propertiesService")
    private EgovPropertyService propertiesService;
    
    @RequestMapping("/kakaoAuth.do")
    public String kakaoAuth(@RequestParam(required = false) String returnUrl, HttpSession session) {
    	String clientId = propertiesService.getString("kakao.clientId");
    	String redirectUri = propertiesService.getString("kakao.redirectUri");

    	// returnUrl 세션 저장(이전페이지로 돌아가려고)
        session.setAttribute("returnUrl", returnUrl);
        
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
              + "?client_id=" + clientId
              + "&redirect_uri=" + redirectUri
              + "&response_type=code"
          	+ "&prompt=login";
        return "redirect:" + kakaoAuthUrl;
        
        
    }
    
    
    @GetMapping("/callback.do")
    public String kakaoCallback(@RequestParam String code
    		,HttpSession session) throws Exception {
		// ① 인가코드 로그 확인
		log.info("카카오 인가코드 : {}", code);
		// ② 인가코드로 토큰 요청
		String accessToken = getAccessToken(code);

        // ② 토큰으로 사용자 정보 요청
        KakaoUserVo kakaoUser = getUserInfo(accessToken);

		// ③ DB 조회 → 신규면 회원가입, 기존이면 로그인(id토큰으로 조회)
        HireUserVo HireUserVo = kakaoAuthService.selectJoinID(kakaoUser);
        
        if(HireUserVo != null) { //로그인성공
        	session.setAttribute("loginUserNum", HireUserVo.getUserNum());
        	session.setAttribute("loginUser", HireUserVo.getKakaoId());
        	session.setAttribute("loginNm", HireUserVo.getUserNm());
            session.setMaxInactiveInterval(60 * 60 * 24); // 카카오는 remember 체크 없으니 1일
            // returnUrl 꺼내기
            String returnUrl = (String) session.getAttribute("returnUrl");
            session.removeAttribute("returnUrl");
            
            // returnUrl에서 이전 url주소 꺼내서 이동
            if(returnUrl != null && !returnUrl.isEmpty()) {
                return "redirect:" + returnUrl;
            }
            
            return "redirect:/hireSystem/main.do"; // 메인으로
        }else { // 신규회원
        	session.setAttribute("kakaoUser", kakaoUser); //사용자 정보
            return "redirect:/hireSystem/signup/signup.do?type=kakao"; //회원가입 페이지
        }


    }
    
    private String getAccessToken(String code) throws Exception {
    	String clientId = propertiesService.getString("kakao.clientId");
    	String redirectUri = propertiesService.getString("kakao.redirectUri");
    	String clientSecret = propertiesService.getString("kakao.clientSecret");

        // 요청 URL 연결 카카오 토큰 발급 URL에 연결 준비 
        URL url = new URL("https://kauth.kakao.com/oauth/token");
        
        //conn = 카카오 서버랑 연결된 통로 같은 것
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
		// 바디에 데이터 담아서 보낼 수 있게 허용
		conn.setDoOutput(true);
        //공식문서에서 요구하는 헤더 설정
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 바디 작성 & 로 구분해서 이어붙이는 게 application/x-www-form-urlencoded 형식
        String params = "grant_type=authorization_code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&code=" + code
                + "&client_secret=" + clientSecret;

        // 바디 전송 
        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes(StandardCharsets.UTF_8));
        }

        // 응답 읽기 
        //getInputStream() : 카카오 서버가 보내준 응답 받는 통로 열기
        //BufferedReader : 응답을 한줄씩 읽기
        //while 반복으로 끝까지 읽어서 sb에 이어붙임
        //responseBody = 최종적으로 카카오가 보내준 JSON 문자열
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);

            String responseBody = sb.toString();

            // ③ 토큰 응답 로그 확인
            log.info("카카오 토큰 응답 : {}", responseBody);

            // access_token만 꺼내서 반환
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            String accessToken = json.get("access_token").getAsString();

            log.info("카카오 액세스 토큰 : {}", accessToken);

            return accessToken;
        }
    }
    
    private KakaoUserVo getUserInfo(String accessToken) throws Exception {
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        
        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        
        int responseCode = conn.getResponseCode();
        log.info("사용자 정보 요청 응답코드 : {}", responseCode);
        
        BufferedReader br;
        if (responseCode >= 200 && responseCode <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        StringBuilder responseSb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            responseSb.append(line);
        }
        br.close();
        
        String result = responseSb.toString();
        // ======= 전체 응답 로그 =======
        log.info("===== 카카오 사용자 정보 전체 =====");
        log.info("{}", result);
        log.info("===================================");
        
        // JSON 파싱
        JSONObject jsonObject = new JSONObject(result);
        
        String id = jsonObject.get("id").toString();
        
        JSONObject kakaoAccount = jsonObject.getJSONObject("kakao_account");
        JSONObject profile = kakaoAccount.getJSONObject("profile");
        
        String nickname = profile.getString("nickname");
        String email = kakaoAccount.optString("email", ""); // 이메일은 없을 수도 있음
        String profileImage = profile.optString("profile_image_url", "");
        
        KakaoUserVo kakaoUser = new KakaoUserVo();
        kakaoUser.setId(id);
        kakaoUser.setNickname(nickname);
        kakaoUser.setEmail(email);
        kakaoUser.setProfileImage(profileImage);
        
        //log.info("카카오 사용자 정보 - id: {}, nickname: {}", id, nickname);
        log.info("kakaoUser : {}", kakaoUser);
        
        return kakaoUser;
    }
    
    
    
}
