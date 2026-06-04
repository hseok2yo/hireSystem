package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hireSystem.service.HireCoverLetterService;
import hireSystem.vo.HireCoverLetterVo;

/**
 * HireCoverLetterController
 *
 * <p>자기소개서 항목 저장 / 수정 / 삭제 REST 컨트롤러</p>
 */
@Controller
@RequestMapping("/hireSystem/resume")
public class HireCoverLetterController {

    @Resource(name = "hireCoverLetterService")
    private HireCoverLetterService hireCoverLetterService;

    /**
     * 자기소개서 항목 저장 (신규 insert)
     */
    @RequestMapping("/coverLetterSave.do")
    @ResponseBody
    public Map<String, Object> coverLetterSave(HireCoverLetterVo coverLetterVo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            int loginUserNum = (int) session.getAttribute("loginUserNum");
            hireCoverLetterService.saveCoverLetter(coverLetterVo, loginUserNum);
            result.put("resumeId", coverLetterVo.getResumeId());
            result.put("result",   true);
            result.put("message",  "저장완료");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 자기소개서 항목 삭제
     */
    @RequestMapping("/coverLetterDelete.do")
    @ResponseBody
    public Map<String, Object> coverLetterDelete(@RequestParam int clId) {
        Map<String, Object> result = new HashMap<>();
        try {
            hireCoverLetterService.deleteCoverLetter(clId);
            result.put("result",  true);
            result.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
