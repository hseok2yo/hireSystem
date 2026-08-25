package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hireSystem.service.HireCertificationService;
import hireSystem.service.HireResumeService;
import hireSystem.vo.HireCertificationVo;

@Controller
@RequestMapping("/hireSystem/resume")
public class HireCertificationController {

    @Resource(name = "hireCertificationService")
    private HireCertificationService hireCertificationService;
    @Resource(name = "hireResumeService")
    private HireResumeService hireResumeService;

    /**
     * 자격사항 저장 (신규 insert / 기존 update 공통)
     * certificationId 가 null 이면 insert, 있으면 update
     */
    @RequestMapping("/certificationSave.do")
    @ResponseBody
    public Map<String, Object> certificationSave(
            HireCertificationVo certVo,
            @RequestParam(value = "sectionVisible", required = false) String sectionVisible,
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();
        try {
            int loginUserNum = (int) session.getAttribute("loginUserNum");
            hireCertificationService.saveCertification(certVo, loginUserNum);

            int resumeId = certVo.getResumeId();

            if (sectionVisible != null) {
                String filteredValue = hireResumeService.filterVisibleSections(resumeId, sectionVisible);
                hireResumeService.updateSectionVisible(resumeId, filteredValue);
            }

            result.put("resumeId", resumeId);
            result.put("result",   true);
            result.put("message",  "저장완료");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 자격사항 삭제
     */
    @RequestMapping("/certificationDelete.do")
    @ResponseBody
    public Map<String, Object> certificationDelete(
            @RequestParam int certificationId,
            @RequestParam int resumeId,
            @RequestParam(value = "sectionVisible", required = false) String sectionVisible) {

        Map<String, Object> result = new HashMap<>();
        try {
            hireCertificationService.deleteCertification(certificationId);

            if (sectionVisible != null) {
                String filteredValue = hireResumeService.filterVisibleSections(resumeId, sectionVisible);
                hireResumeService.updateSectionVisible(resumeId, filteredValue);
            }

            result.put("result",  true);
            result.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            result.put("result",  false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
