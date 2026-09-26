package hireSystem.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;

import hireSystem.common.PagingUtil;
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

	    if (searchVo.getPage() == 0) searchVo.setPage(1);
	    int pageSize = searchVo.getPageSize();
	    int blockSize = 5;

	    // totalCount 먼저 조회
	    int totalCount = hireJobPostingDao.selectJobPostingCount(searchVo);

	    // 페이징 계산 (offset 포함)
	    Map<String, Object> result = PagingUtil.getPaging(searchVo.getPage(), totalCount, pageSize, blockSize);

	    // offset 세팅 후 목록 조회
	    searchVo.setOffset((int) result.get("offset"));
	    List<HireJobPostingVo> selectList = hireJobPostingDao.selectJobPostingList(searchVo);

	    result.put("list", selectList);

	    return result;
	}

	@Override
	public EgovMap normalJobDetail(int jobPostingId) {

		hireJobPostingDao.increaseViewCnt(jobPostingId); //조회수 + 1

		return hireJobPostingDao.normalJobDetail(jobPostingId);
	}

}
