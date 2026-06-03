package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hireSystem.service.HireResumeSkillService;
import hireSystem.vo.HireSkillVo;

@Controller
@RequestMapping("/hireSystem/resume")
public class HireResumeSkillController {

	@Resource(name ="hireResumeSkillService")
	private HireResumeSkillService hireResumeSkillService;

	@RequestMapping("/skillAdd.do")
    @ResponseBody
    public Map<String, Object> skillAdd(HireSkillVo skillVo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            int loginUserNum = (int) session.getAttribute("loginUserNum");
            hireResumeSkillService.addSkill(skillVo, loginUserNum);
            result.put("resumeId", skillVo.getResumeId());
            result.put("skillId",  skillVo.getSkillId());   // insert 후 selectKey로 채워진 값
            result.put("result",   true);
            result.put("message",  "추가되었습니다.");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping("/skillDelete.do")
    @ResponseBody
    public Map<String, Object> skillDelete(@RequestParam int skillId) {
        Map<String, Object> result = new HashMap<>();
        try {
        	hireResumeSkillService.deleteSkill(skillId);
            result.put("result",  true);
            result.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
