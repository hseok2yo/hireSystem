package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hireSystem.service.HireActivityService;
import hireSystem.vo.HireActivityVo;

@Controller
@RequestMapping("/hireSystem/resume")
public class HireActivityController {

    @Resource(name = "hireActivityService")
    private HireActivityService hireActivityService;

    /**
     * 경험/활동/교육 저장 (신규 insert / 기존 update 공통)
     * activityId 가 null 이면 insert, 있으면 update
     */
    @RequestMapping("/activitySave.do")
    @ResponseBody
    public Map<String, Object> activitySave(HireActivityVo activityVo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            int loginUserNum = (int) session.getAttribute("loginUserNum");
            hireActivityService.saveActivity(activityVo, loginUserNum);
            result.put("resumeId", activityVo.getResumeId());
            result.put("result",   true);
            result.put("message",  "저장완료");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 경험/활동/교육 삭제
     */
    @RequestMapping("/activityDelete.do")
    @ResponseBody
    public Map<String, Object> activityDelete(@RequestParam int activityId) {
        Map<String, Object> result = new HashMap<>();
        try {
            hireActivityService.deleteActivity(activityId);
            result.put("result",  true);
            result.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
