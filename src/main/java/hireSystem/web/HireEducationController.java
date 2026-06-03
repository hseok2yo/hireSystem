package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hireSystem.service.HireEducationService;
import hireSystem.vo.HireEducationVo;

@Controller
@RequestMapping("/hireSystem/resume")
public class HireEducationController {

    @Resource(name = "hireEducationService")
    private HireEducationService hireEducationService;

    /**
     * 학력 저장 (신규 insert / 기존 update 공통)
     * educationId 가 null 이면 insert, 있으면 update
     */
    @RequestMapping("/educationSave.do")
    @ResponseBody
    public Map<String, Object> educationSave(HireEducationVo educationVo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            int loginUserNum = (int) session.getAttribute("loginUserNum");
            hireEducationService.saveEducation(educationVo, loginUserNum);
            result.put("resumeId", educationVo.getResumeId());
            result.put("result",   true);
            result.put("message",  "저장완료");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 학력 삭제
     */
    @RequestMapping("/educationDelete.do")
    @ResponseBody
    public Map<String, Object> educationDelete(@RequestParam int educationId) {
        Map<String, Object> result = new HashMap<>();
        try {
            hireEducationService.deleteEducation(educationId);
            result.put("result",  true);
            result.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
