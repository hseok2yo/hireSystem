package hireSystem.email;

import java.util.HashMap;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hireSystem")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendCode.do")
    @ResponseBody
    public EgovMap sendCode(String email) {
    	long expireSeconds = emailService.sendCode(email);
        
        EgovMap result = new EgovMap();
        result.put("result", "sent");
        
        if (expireSeconds == -1) {
            result.put("result", "fail");
        } else {
            result.put("result", "sent");
            result.put("expireTime", String.valueOf(expireSeconds));
        }
        return result;
    }

    @PostMapping("/verifyCode.do")
    @ResponseBody
    public Map<String, String> verifyCode(String email, String code) {
    	Map<String, String> result = new HashMap<>();
    	
    	result.put("result", emailService.verifyCodeResult(email, code));
        return result;
        
    }
}