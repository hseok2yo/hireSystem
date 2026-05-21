package hireSystem.email;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    // 인증번호 임시 저장
    private Map<String, String> codeMap = new HashMap<>();
    
    //만료시간 체크용 임시저장
    private Map<String, Long> expireMap = new HashMap<>();
    
    //만료시간(?분)
    private static final long EXPIRE_TIME = 1 * 60 * 1000; 

    public long sendCode(String email) {
    	//랜덤 6자리수 생성
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        
        //이메일 인증번호 저장
        codeMap.put(email, code);
        //이메일계정당 만료시간 저장
        long expireTime = System.currentTimeMillis() + EXPIRE_TIME;
        expireMap.put(email, expireTime);
        
        // 현재 요청한 유저 정보
        logger.info("========== 인증번호 요청 ==========");
        logger.info("요청 이메일: {}", email);
        logger.info("인증번호: {}", code);
        logger.info("만료시간: {}초 후", EXPIRE_TIME / 1000);
        logger.info("==================================");

        
        // 현재 전체 요청 현황
        logger.info("========== 전체 요청 현황 ==========");
        for (String key : codeMap.keySet()) {
            long remainTime = (expireMap.get(key) - System.currentTimeMillis()) / 1000;
            logger.info("이메일: {} | 인증번호: {} | 남은시간: {}", 
                key, 
                codeMap.get(key), 
                remainTime > 0 ? remainTime + "초" : "만료됨");
        }
        logger.info("==================================");
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("[HireSystem] 이메일 인증번호 안내");
            helper.setText(settingText(code), true); //html사용
            mailSender.send(message);
            return EXPIRE_TIME / 1000; // 성공하면 남은시간(초) 반환
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * 
     * @param email
     * @param inputCode
     * @return
     */
    public String verifyCodeResult(String email, String inputCode) {
    	
    	cleanExpiredCodes(); // 인증 시도할 때마다 만료된거 정리(스케줄러 사용하려면 주석치고 스케줄러 주석해제)
    	
    	//이메일 계정에 할당된 인증코드가져오기
        String savedCode = codeMap.get(email);
        //이메일 계정에 할당된 만료시간 가져오기
        Long expireTime = expireMap.get(email);

		// 현재 요청한 유저 정보
		logger.info("========== 인증번호 확인 ==========");
		logger.info("이메일: {}", email);
		logger.info("입력한 인증번호: {}", inputCode);
		logger.info("저장된 인증번호: {}", savedCode);

		// cleanExpiredCodes()에서 만료된거 지웠으니까 null이면 요청안했거나 만료된거
	    if (savedCode == null || expireTime == null) {
	        logger.info("결과: 시간초과 또는 인증번호 없음");
	        logger.info("==================================");
	        return "expire";
	    }
		
	    long remainTime = (expireTime - System.currentTimeMillis()) / 1000;
	    logger.info("남은시간: {}초", remainTime); // 여기오면 무조건 양수
	    
	    // 전체 요청 현황
	    logger.info("========== 전체 요청 현황 ==========");
	    for (String key : codeMap.keySet()) {
	        long remain = (expireMap.get(key) - System.currentTimeMillis()) / 1000;
	        logger.info("이메일: {} | 인증번호: {} | 남은시간: {}초", key, codeMap.get(key), remain);
	    }
	    logger.info("==================================");
	    
        if (savedCode.equals(inputCode)) {
            logger.info("결과: 인증성공");
            codeMap.remove(email);
            expireMap.remove(email);
            return "ok";
        }
        
        logger.info("결과: 인증번호 틀림");
        return "fail";
    }
    
    //@Scheduled(fixedDelay = 60000) // 1분마다 실행
    public void cleanExpiredCodes() {
    	logger.info("========== 만료된 인증번호 정리 {} ==========", 
    	        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        
        Iterator<String> iterator = expireMap.keySet().iterator();
        while (iterator.hasNext()) {
            String email = iterator.next();
            if (System.currentTimeMillis() > expireMap.get(email)) {
                logger.info("만료 삭제: {}", email);
                codeMap.remove(email);  // codeMap 삭제
                iterator.remove();      // expireMap 삭제 (iterator로 안전하게 삭제)
            }
        }
        
        logger.info("정리 완료 | 남은 요청 수: {}", codeMap.size());
        logger.info("==========================================");
    }
    
    public String settingText(String code) {
    	long minutes = EXPIRE_TIME / (60 * 1000); // 밀리초 → 분 변환
    	
    	String text = "<div style='max-width:500px; margin:0 auto; font-family:Arial, sans-serif;'>" +
                "<div style='background-color:#2c3e50; padding:20px; text-align:center;'>" +
                "<h1 style='color:white; margin:0;'>HireSystem</h1>" +
            "</div>" +
            "<div style='padding:30px; background-color:#f9f9f9;'>" +
                "<h2 style='color:#2c3e50;'>이메일 인증번호 안내</h2>" +
                "<p style='color:#555;'>안녕하세요.<br>HireSystem 회원가입을 위한 인증번호를 안내드립니다.</p>" +
                "<div style='background-color:#2c3e50; border-radius:8px; padding:20px; text-align:center; margin:20px 0;'>" +
                    "<p style='color:#aaa; margin:0 0 10px 0;'>인증번호</p>" +
                    "<h1 style='color:white; letter-spacing:10px; margin:0;'>" + code + "</h1>" +
                "</div>" +
                "<p style='color:#555;'>인증번호는 <b>" + minutes + "분간</b> 유효합니다.</p>" +
                "<p style='color:#aaa; font-size:12px;'>본인이 요청하지 않으셨다면 이 메일을 무시하셔도 됩니다.</p>" +
            "</div>" +
            "<div style='background-color:#eee; padding:15px; text-align:center;'>" +
                "<p style='color:#aaa; font-size:12px; margin:0;'>© 2025 HireSystem. All rights reserved.</p>" +
            "</div>" +
        "</div>";
    	return text;
    }
    
    
}