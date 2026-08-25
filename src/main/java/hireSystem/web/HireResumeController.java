package hireSystem.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hireSystem.service.HireActivityService;
import hireSystem.service.HireCareerService;
import hireSystem.service.HireCertificationService;
import hireSystem.service.HireCoverLetterService;
import hireSystem.service.HireEducationService;
import hireSystem.service.HirePortfolioService;
import hireSystem.service.HireResumeService;
import hireSystem.service.HireResumeSkillService;
import hireSystem.service.HireSignupService;
import hireSystem.vo.HireActivityVo;
import hireSystem.vo.HireCareerVo;
import hireSystem.vo.HireCertificationVo;
import hireSystem.vo.HireCoverLetterVo;
import hireSystem.vo.HireEducationVo;
import hireSystem.vo.HirePortfolioVo;
import hireSystem.vo.HireResumeVo;
import hireSystem.vo.HireSkillVo;
import hireSystem.vo.HireUserVo;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/hireSystem/resume")
@Slf4j
public class HireResumeController {

    private final String path = "hireSystem/resume/";

    @Resource(name = "hireSignupService")
    private HireSignupService hireSignupService;

    @Resource(name = "hireResumeService")
    private HireResumeService hireResumeService;

    @Resource(name = "hireCareerService")
    private HireCareerService hireCareerService;

    @Resource(name = "hireEducationService")
    private HireEducationService hireEducationService;

    @Resource(name = "hireResumeSkillService")
    private HireResumeSkillService hireResumeSkillService;

    @Resource(name = "hireActivityService")
    private HireActivityService hireActivityService;

    @Resource(name = "hireCertificationService")
    private HireCertificationService hireCertificationService;

    @Resource(name = "hireCoverLetterService")
    private HireCoverLetterService hireCoverLetterService;

    @Resource(name = "hirePortfolioService")
    private HirePortfolioService hirePortfolioService;


    // ---------------------------------------------------------------
    // 이력서 메인 목록
    // ---------------------------------------------------------------

    @RequestMapping("/resumeMain.do")
    public String resumeMain(
            @RequestParam(defaultValue = "1")      int    page,
            @RequestParam(defaultValue = "recent") String searchSort,
            HttpSession session, Model model) {

        Integer loginUserNum = (Integer) session.getAttribute("loginUserNum");
        EgovMap map = new EgovMap();
        map.put("page",        page);
        map.put("searchSort",  searchSort);
        map.put("loginUserNum", loginUserNum);

        model.addAttribute("mainResume", hireResumeService.selectResumeMainInfo(map));
        model.addAttribute("subResume",  hireResumeService.selectResumeSubInfo(map));

        return path + "resumeMain";
    }

    // ---------------------------------------------------------------
    // 이력서 신규 등록 폼
    // ---------------------------------------------------------------

    @RequestMapping("/resumeForm.do")
    public String resumeForm(HttpSession session, Model model) {
        int loginUserNum = (int) session.getAttribute("loginUserNum");

        model.addAttribute("commonUserNum", loginUserNum);
        model.addAttribute("userInfo", hireResumeService.selectHireUserInfo(loginUserNum));

        return path + "resumeForm";
    }

    // ---------------------------------------------------------------
    // 프로필 이미지 업로드
    // ---------------------------------------------------------------

    @PostMapping("/image/uploadPhoto.do")
    @ResponseBody
    public Map<String, Object> uploadPhoto(
            @RequestParam("upload")                    MultipartFile file,
            @RequestParam(value = "original", required = false) MultipartFile original,
            HttpSession session) throws IOException {

        int loginUserNum = (int) session.getAttribute("loginUserNum");
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> photoResult = hireSignupService.updateUserPhoto(file, original, loginUserNum);
            result.put("success",     true);
            result.put("url",         photoResult.get("url"));
            result.put("originalUrl", photoResult.get("originalUrl"));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "파일 업로드에 실패했습니다.");
        }
        return result;
    }

    // ---------------------------------------------------------------
    // 이력서 기본정보 저장
    // ---------------------------------------------------------------

    @PostMapping("/saveBasicResume.do")
    @ResponseBody
    public Map<String, Object> saveBasicResume(
            @ModelAttribute HireUserVo  hireVo,
            @ModelAttribute HireResumeVo vo) {

        Map<String, Object> resultMap = new HashMap<>();
        int result = hireResumeService.saveBasicResume(hireVo, vo);

        if (result > 0) {
            resultMap.put("result",   true);
            resultMap.put("message",  "저장완료");
            resultMap.put("resumeId", vo.getResumeId());
        } else {
            resultMap.put("result",  false);
            resultMap.put("message", "저장실패");
        }
        return resultMap;
    }

    // ---------------------------------------------------------------
    // 이력서 수정 페이지
    // ---------------------------------------------------------------

    @RequestMapping("/edit.do")
    public String edit(
            @RequestParam int resumeId,
            HttpSession session,
            Model model) {

        int loginUserNum = (int) session.getAttribute("loginUserNum");

        //공통부분
        model.addAttribute("commonUserNum",  loginUserNum);
        model.addAttribute("commonResumeId", resumeId);

        // 1. 기본정보
        model.addAttribute("userInfo", hireResumeService.selectHireUserInfo(loginUserNum));
        // 이력서정보
        model.addAttribute("resume", hireResumeService.selectResume(resumeId));

        // 2. 경력사항 (HireCareerService 사용)
        List<HireCareerVo> careerList = hireCareerService.selectCareerInfo(resumeId);
        model.addAttribute("careerInfo",   careerList);
        model.addAttribute("totalCareer",  hireCareerService.calculateTotalCareer(careerList));

        // 3. 학력사항 (HireEducationService 사용)
        List<HireEducationVo> educationList = hireEducationService.selectEducationInfo(resumeId);
        model.addAttribute("educationInfo", educationList);

        // 4. 스킬
        List<HireSkillVo> skillList = hireResumeSkillService.selectSkillInfo(resumeId);
        model.addAttribute("skillInfo", skillList);

        // 5. 경험/활동/교육
        List<HireActivityVo> activityList = hireActivityService.selectActivityList(resumeId);
        model.addAttribute("activityList", activityList);

        // 6. 자격/어학/수상
        List<HireCertificationVo> certificationList = hireCertificationService.selectCertificationInfo(resumeId);
        model.addAttribute("certificationInfo", certificationList);

        // 6. 포트폴리오
        List<HirePortfolioVo> portfolioList = hirePortfolioService.selectPortfolioList(resumeId);
        model.addAttribute("portfolioList", portfolioList);

        // 7. 자기소개서
        List<HireCoverLetterVo> coverLetterList = hireCoverLetterService.selectCoverLetterList(resumeId);
        model.addAttribute("coverLetterList", coverLetterList);

        return path + "resumeForm";
    }

    @RequestMapping("/resumeSave.do")
    @ResponseBody
    public Map<String, Object> resumeTitleSave(HireResumeVo vo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            int loginUserNum = (int) session.getAttribute("loginUserNum");
            hireResumeService.saveResume(vo, loginUserNum);
            result.put("result", true);
        } catch (Exception e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/resumeDelete.do")
    @ResponseBody
    public Map<String, Object> resumeDelete(HireResumeVo vo, HttpSession session) {
    	Map<String, Object> result = new HashMap<>();

    	try {
            int loginUserNum = (int) session.getAttribute("loginUserNum");

            hireResumeService.deleteResume(vo.getResumeId(), loginUserNum);

            result.put("result", true);

        } catch (Exception e) {
        	log.error("이력서 삭제 중 오류 발생", e);

            result.put("result", false);
            result.put("message", e.getMessage());
        }

    	return result;
    }

    @PostMapping("/duplicate.do")
    @ResponseBody
    public Map<String, Object> duplicateResume(
            @RequestParam int resumeId,
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();
        int loginUserNum = (int) session.getAttribute("loginUserNum");

        try {
            int newResumeId = hireResumeService.duplicateResume(resumeId, loginUserNum);
            result.put("result", true);
            result.put("resumeId", newResumeId);
        } catch (IllegalArgumentException e) {
            result.put("result", false);
            result.put("message", e.getMessage());
        } catch (Exception e) {
            log.error("이력서 복제 중 오류 발생", e);
            result.put("result", false);
            result.put("message", "복제에 실패했습니다.");
        }
        return result;
    }

 // ---------------------------------------------------------------
    // 이력서 인쇄/PDF용 읽기전용 뷰
    // ---------------------------------------------------------------

    @RequestMapping("/print.do")
    public String print(
            @RequestParam int resumeId,
            HttpSession session,
            Model model) {

        int loginUserNum = (int) session.getAttribute("loginUserNum");

        model.addAttribute("userInfo", hireResumeService.selectHireUserInfo(loginUserNum));
        model.addAttribute("resume", hireResumeService.selectResume(resumeId));

        List<HireCareerVo> careerList = hireCareerService.selectCareerInfo(resumeId);
        model.addAttribute("careerInfo", careerList);
        model.addAttribute("totalCareer", hireCareerService.calculateTotalCareer(careerList));

        model.addAttribute("educationInfo", hireEducationService.selectEducationInfo(resumeId));
        model.addAttribute("skillInfo", hireResumeSkillService.selectSkillInfo(resumeId));
        model.addAttribute("activityList", hireActivityService.selectActivityList(resumeId));
        model.addAttribute("certificationInfo", hireCertificationService.selectCertificationInfo(resumeId));
        model.addAttribute("portfolioList", hirePortfolioService.selectPortfolioList(resumeId));
        model.addAttribute("coverLetterList", hireCoverLetterService.selectCoverLetterList(resumeId));

        return path + "resumePrint";
    }


}