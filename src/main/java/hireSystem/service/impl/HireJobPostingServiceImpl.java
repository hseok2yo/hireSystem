package hireSystem.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import hireSystem.service.HireJobPostingService;
import hireSystem.service.dao.HireJobPostingDao;
import hireSystem.vo.HireJobPostingVo;
import hireSystem.vo.JobPostingSearchVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hireJobPostingService")
public class HireJobPostingServiceImpl extends EgovAbstractServiceImpl implements HireJobPostingService {

	@Resource(name = "hireJobPostingDao")
	private HireJobPostingDao hireJobPostingDao;

	private static final int PAGE_BLOCK_SIZE = 10;

	@Override
	public Map<String, Object> selectJobPostingList(JobPostingSearchVo searchVo) {

		if (searchVo.getPage() <= 0) searchVo.setPage(1);
		if (searchVo.getNumOfRows() <= 0) searchVo.setNumOfRows(10);
		searchVo.setOffset((searchVo.getPage() - 1) * searchVo.getNumOfRows());

		List<HireJobPostingVo> list = hireJobPostingDao.selectJobPostingList(searchVo);
		int totalCount = hireJobPostingDao.selectJobPostingCount(searchVo);

		int currentPage = searchVo.getPage();
		int numOfRows = searchVo.getNumOfRows();
		int totalPages = (int) Math.ceil((double) totalCount / numOfRows);
		if (totalPages < 1) totalPages = 1;

		int blockStart = ((currentPage - 1) / PAGE_BLOCK_SIZE) * PAGE_BLOCK_SIZE + 1;
		int blockEnd = Math.min(blockStart + PAGE_BLOCK_SIZE - 1, totalPages);

		log.info("[일반채용정보 목록조회] keyword={}, totalCount={}, currentPage={}/{}",
				searchVo.getKeyword(), totalCount, currentPage, totalPages);

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("list", list);
		result.put("totalCount", totalCount);
		result.put("currentPage", currentPage);
		result.put("totalPages", totalPages);
		result.put("blockStart", blockStart);
		result.put("blockEnd", blockEnd);
		result.put("numOfRows", numOfRows);

		return result;
	}

	@Override
	public HireJobPostingVo selectJobPostingDetail(int jobPostingId) {
		hireJobPostingDao.updateViewCount(jobPostingId);
		return hireJobPostingDao.selectJobPostingDetail(jobPostingId);
	}

	@Override
	public int insertJobPosting(HireJobPostingVo jobPostingVo) {
		if (jobPostingVo.getOngoingYn() == null || jobPostingVo.getOngoingYn().isEmpty()) {
			jobPostingVo.setOngoingYn("Y");
		}
		return hireJobPostingDao.insertJobPosting(jobPostingVo);
	}
}
