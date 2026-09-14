package hireSystem.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hireSystem.service.HireJobPostingService;
import hireSystem.vo.HireJobPostingVo;
import hireSystem.vo.JobPostingSearchVo;
import lombok.extern.slf4j.Slf4j;

/**
 * [일반채용정보 - 자체 DB 채용공고]
 *
 * 채용정보 화면(서브탭: 일반채용정보 / 공공기관채용정보) 중 "일반채용정보" 탭.
 * 공공기관채용정보(RecruitController, 공공데이터 API)와 달리 이쪽은
 * 기업회원이 직접 작성해서 HIRE_JOB_POSTING 테이블에 저장된 채용공고를 보여주고,
 * 상세페이지에서 "지원하기"로 이력서 기반 지원까지 이어지는 영역이다.
 *
 * ※ 지금은 목록/상세 조회만 붙어있고, 기업회원 공고 작성 화면 및
 *   지원하기(이력서 매칭/지원내역 저장) 기능은 다음 단계에서 추가한다.
 */
@Slf4j
@Controller
@RequestMapping("/hireSystem/job")
public class NormalJobController {

	private final String path = "hireSystem/job/";

	@Resource(name = "hireJobPostingService")
	private HireJobPostingService hireJobPostingService;

	/**
	 * @return 일반채용정보(자체 DB) 목록 화면 이동
	 */
	@RequestMapping("/normalJob.do")
	public String normalJob(@ModelAttribute JobPostingSearchVo searchVo, Model model) {

		log.info("[normalJob.do] START page={}, keyword={}, hireType={}, recrutSe={}, ongoingYn={}",
				searchVo.getPage(), searchVo.getKeyword(), searchVo.getHireType(),
				searchVo.getRecrutSe(), searchVo.getOngoingYn());

		Map<String, Object> result = hireJobPostingService.selectJobPostingList(searchVo);

		model.addAttribute("jobList", result.get("list"));
		model.addAttribute("totalCount", result.get("totalCount"));
		model.addAttribute("currentPage", result.get("currentPage"));
		model.addAttribute("totalPages", result.get("totalPages"));
		model.addAttribute("blockStart", result.get("blockStart"));
		model.addAttribute("blockEnd", result.get("blockEnd"));
		model.addAttribute("numOfRows", result.get("numOfRows"));

		// 검색조건 화면 유지용
		model.addAttribute("keyword", searchVo.getKeyword());
		model.addAttribute("hireType", searchVo.getHireType());
		model.addAttribute("recrutSe", searchVo.getRecrutSe());
		model.addAttribute("ongoingYn", searchVo.getOngoingYn());
		model.addAttribute("workRegion", searchVo.getWorkRegion());

		log.info("[normalJob.do] DONE totalCount={}", result.get("totalCount"));

		return path + "normalJob";
	}

	/**
	 * @return 일반채용정보 상세 페이지 이동 (지원하기는 다음 단계에서 연결)
	 */
	@RequestMapping("/normalJobDetail.do")
	public String normalJobDetail(@RequestParam int jobPostingId, Model model) {

		log.info("[normalJobDetail.do] START jobPostingId={}", jobPostingId);

		HireJobPostingVo job = hireJobPostingService.selectJobPostingDetail(jobPostingId);
		model.addAttribute("job", job);

		return path + "normalJobDetail";
	}
}
