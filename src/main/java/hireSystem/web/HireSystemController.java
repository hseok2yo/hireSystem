package hireSystem.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hireSystem.service.RecruitApiService;
import hireSystem.vo.RecruitSearchParam;

@Controller
@RequestMapping("/hireSystem")
public class HireSystemController {

	private static final Logger logger = LoggerFactory.getLogger(HireSystemController.class);

	private final String path = "hireSystem/";

	// 메인페이지 "주목할만한 채용공고" 섹션에 보여줄 건수 (많이 안 보여줘도 되니 6건만)
	private static final int MAIN_PAGE_JOB_COUNT = 6;

	@Resource(name = "recruitApiService")
	private RecruitApiService recruitApiService;

	/**
	 * @return 메인페이지 이동
	 *
	 * 기존에는 "주목할만한 채용공고" 섹션이 네이버/카카오 하드코딩된 더미데이터였음.
	 * -> RecruitApiService로 실제 진행중인 채용공고 몇 건만 가져와서 그 자리에 채워준다.
	 * (검색/필터/페이징은 필요없고 "최근 진행중 공고 몇 건 간단히" 보여주는 용도라
	 *  RecruitController처럼 복잡하게 안 만들고 여기서 바로 조회 + 최소 파싱만 함)
	 */
	@RequestMapping(value = "/main.do")
	public String main(Model model) {

		RecruitSearchParam param = new RecruitSearchParam();
		param.setPageNo(1);
		param.setNumOfRows(MAIN_PAGE_JOB_COUNT);
		param.setOngoingYn("Y"); // 마감된 공고 대신 진행중인 공고만 노출

		List<Map<String, Object>> featuredJobs = new ArrayList<>();
		try {
			String json = recruitApiService.getRecruitListJson(param);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(json);
			JsonNode resultNode = root.path("result");

			if (resultNode.isArray()) {
				for (JsonNode node : resultNode) {
					featuredJobs.add(mapper.convertValue(node,
							new TypeReference<LinkedHashMap<String, Object>>() {}));
				}
			}
			logger.info("[메인페이지 채용공고 조회] 파싱된 건수={}건", featuredJobs.size());
		} catch (Exception e) {
			// 메인페이지는 채용공고 섹션 하나 때문에 전체 페이지가 안 뜨면 안 되므로,
			// 실패해도 빈 리스트로 두고 화면은 정상적으로 렌더링되게 한다 (JSP에서 empty 처리).
			logger.error("[메인페이지 채용공고 조회 실패] 원인={}", e.toString(), e);
		}

		model.addAttribute("featuredJobs", featuredJobs);

		return path + "hireSystem";
	}

}