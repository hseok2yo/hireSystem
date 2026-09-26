package hireSystem.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hireSystem.service.HireApplicationService;
import hireSystem.service.HirePortfolioService;
import hireSystem.service.HireResumeService;
import hireSystem.vo.HirePortfolioVo;
import hireSystem.vo.HireResumeVo;
import lombok.extern.slf4j.Slf4j;

/**
 * [채용공고 지원하기]
 *
 * 자체 DB 채용공고(HIRE_JOB_POSTING)에 대한 지원 처리.
 * 로그인 여부는 dispatcher-servlet.xml에 등록된 LoginInterceptor가 먼저 체크한다.
 * (비로그인 + AJAX 요청이면 인터셉터가 401을 내려주고, 프론트의 apiLoginCommonFetch가
 *  이를 감지해 로그인 페이지로 리다이렉트한다.)
 *
 * 지원 모달의 "이력서 변경" / "첨부파일" 조회는 이 컨트롤러에서 처리하고,
 * 실제 파일 추가/삭제는 이력서 자체의 첨부파일이므로 기존 HirePortfolioController의
 * portfolioSave.do / portfolioDelete.do를 그대로 재사용한다 (별도 테이블을 새로 만들지 않음).
 */
@Controller
@RequestMapping("/hireSystem/apply")
@Slf4j
public class HireApplyController {

	@Resource(name = "hireApplicationService")
	private HireApplicationService hireApplicationService;

	@Resource(name = "hireResumeService")
	private HireResumeService hireResumeService;

	@Resource(name = "hirePortfolioService")
	private HirePortfolioService hirePortfolioService;

	/**
	 * [지원 모달] "이력서 변경" 목록
	 * 로그인 유저의 전체 이력서를 대표이력서 우선 / 최근수정순으로 반환한다.
	 */
	@RequestMapping("/resumeOptions.do")
	@ResponseBody
	public Map<String, Object> resumeOptions(HttpSession session) {

		Map<String, Object> result = new HashMap<>();
		try {
			int loginUserNum = (int) session.getAttribute("loginUserNum");
			List<HireResumeVo> list = hireResumeService.selectResumeListAll(loginUserNum);

			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			log.error("[지원하기] 이력서 목록 조회 오류", e);
			result.put("result", false);
			result.put("message", "이력서 목록을 불러오지 못했습니다.");
		}
		return result;
	}

	/**
	 * [지원 모달] 선택된 이력서에 첨부된 파일 목록 (RESUME_PORTFOLIO)
	 */
	@RequestMapping("/portfolioList.do")
	@ResponseBody
	public Map<String, Object> portfolioList(
			@RequestParam int resumeId,
			HttpSession session) {

		Map<String, Object> result = new HashMap<>();
		try {
			int loginUserNum = (int) session.getAttribute("loginUserNum");

			if (!isOwnResume(resumeId, loginUserNum)) {
				result.put("result", false);
				result.put("message", "본인 소유의 이력서만 조회할 수 있습니다.");
				return result;
			}

			List<HirePortfolioVo> list = hirePortfolioService.selectPortfolioList(resumeId);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			log.error("[지원하기] 첨부파일 목록 조회 오류", e);
			result.put("result", false);
			result.put("message", "첨부파일 목록을 불러오지 못했습니다.");
		}
		return result;
	}

	@RequestMapping("/applyJob.do")
	@ResponseBody
	public Map<String, Object> applyJob(
			@RequestParam int jobPostingId,
			@RequestParam(required = false) Integer resumeId,
			HttpSession session) {

		Map<String, Object> result = new HashMap<>();
		try {
			int loginUserNum = (int) session.getAttribute("loginUserNum");

			int usedResumeId = hireApplicationService.applyJob(jobPostingId, resumeId, loginUserNum);

			result.put("result", true);
			result.put("message", "지원이 완료되었습니다.");
			result.put("resumeId", usedResumeId);
		} catch (IllegalStateException e) {
			// 중복 지원 / 대표이력서 없음 / 타인 이력서 선택 등 비즈니스 예외
			result.put("result", false);
			result.put("message", e.getMessage());
		} catch (Exception e) {
			log.error("[지원하기] 처리 중 오류", e);
			result.put("result", false);
			result.put("message", "지원 처리 중 오류가 발생했습니다.");
		}
		return result;
	}

	/** resumeId가 loginUserNum 소유인지 검증하는 공통 로직 */
	private boolean isOwnResume(int resumeId, int loginUserNum) {
		EgovMap resumeMap = hireResumeService.selectResume(resumeId);
		if (resumeMap == null || resumeMap.get("userNum") == null) {
			return false;
		}
		return Integer.parseInt(resumeMap.get("userNum").toString()) == loginUserNum;
	}
}
