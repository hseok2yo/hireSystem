package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hireSystem.service.HireCareerService;
import hireSystem.vo.HireCareerVo;

@Controller
@RequestMapping("/hireSystem/resume")
public class HireCareerController {

    @Resource(name = "hireCareerService")
    private HireCareerService hireCareerService;

    /**
     * 경력 저장 (신규 insert / 기존 update 공통)
     * careerId 가 null 이면 insert, 있으면 update
     */
    @RequestMapping("/careerSave.do")
    @ResponseBody
    public Map<String, Object> careerSave(HireCareerVo careerVo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            int loginUserNum = (int) session.getAttribute("loginUserNum");
            hireCareerService.saveCareer(careerVo, loginUserNum);
            result.put("resumeId", careerVo.getResumeId());
            result.put("result",   true);
            result.put("message",  "저장완료");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 경력 삭제
     */
    @RequestMapping("/careerDelete.do")
    @ResponseBody
    public Map<String, Object> careerDelete(@RequestParam int careerId) {
        Map<String, Object> result = new HashMap<>();
        try {
            hireCareerService.deleteCareer(careerId);
            result.put("result",  true);
            result.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
