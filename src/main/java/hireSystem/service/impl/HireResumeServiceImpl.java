package hireSystem.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;

import hireSystem.common.PagingUtil;
import hireSystem.service.HireResumeService;
import hireSystem.service.dao.HireResumeDao;
import hireSystem.service.dao.HireSignupDao;
import hireSystem.vo.HireCareerVo;
import hireSystem.vo.HireResumeVo;
import hireSystem.vo.HireUserVo;

@Service("hireResumeService")
public class HireResumeServiceImpl  extends EgovAbstractServiceImpl implements HireResumeService{

	@Resource(name = "hireResumeDao")
	private HireResumeDao hireResumeDao;

	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;

	@Resource(name = "hireSignupDao")
	private HireSignupDao hireSignupDao;

	@Override
	public EgovMap selectHireUserInfo(int loginUserNum) {

		EgovMap map = hireSignupDao.selectHireUserInfo(loginUserNum);


		if (map.get("userPhotoName") != null) {
		    String url = propertiesService.getString("resume.store.url") + (String) map.get("userPhotoName");
		    map.put("userPhotoUrl", url);
		}

	    if (map.get("userPhotoOriginalname") != null) {
	        String originalUrl = propertiesService.getString("resume.store.url") + map.get("userPhotoOriginalname");
	        map.put("userPhotoOriginalName", originalUrl);
	    }

		return map;
	}

	@Override
	public int insertEmptyResume(int loginUserNum) {
		return hireResumeDao.insertEmptyResume(loginUserNum);
	}

	@Override
	public int insertBasicResume(HireResumeVo vo) {
		return hireResumeDao.insertBasicResume(vo);
	}

	@Override
	public int updateBasicResume(HireResumeVo vo) {

		return hireResumeDao.updateBasicResume(vo);
	}

	@Override
	public HireResumeVo selectResumeMainInfo(EgovMap map) {
		int loginUserNum = Integer.parseInt(map.get("loginUserNum").toString());
		return hireResumeDao.selectResumeMainInfo(loginUserNum);
	}

	@Override
	public Map<String, Object> selectResumeSubInfo(EgovMap map) {

		HireResumeVo vo = new HireResumeVo();

		int getPage =  Integer.parseInt(map.get("page").toString());
		int loginUserNum = Integer.parseInt(map.get("loginUserNum").toString());

		int page = getPage;
		int pageSize = 5; //가져올 게시글 수
		int offset = (page - 1) * pageSize;
		int blockSize = 5; //페이징 수

		vo.setPage(getPage);
		vo.setUserNum(loginUserNum);
		vo.setPageSize(pageSize);
		vo.setOffset(offset);
		vo.setSearchSort((String) map.get("searchSort"));

		List<HireResumeVo> selectList = hireResumeDao.selectResumeSubInfo(vo); //게시글 조회
		int totalCount = hireResumeDao.selectSubTotalCount(vo); //총 게시글 수

		Map<String, Object> result = PagingUtil.getPaging(page, totalCount, pageSize, blockSize);
		result.put("list", selectList);

		return result;
	}

	@Override
	public int saveBasicResume(HireUserVo hireVo, HireResumeVo vo) {
		// 1. 기본정보 업뎃
	    hireSignupDao.updateBasicHireUserInfo(hireVo);

	    // 2. resume insert/update
	    if (vo.getResumeId() == null) {
	        return hireResumeDao.insertBasicResume(vo);
	    } else {
	        return hireResumeDao.updateBasicResume(vo);
	    }

	}

	@Override
	public List<HireCareerVo> selectCareerInfo(int resumeId) {
		List<HireCareerVo> careerList = hireResumeDao.selectCareerInfo(resumeId);

		for(HireCareerVo vo : careerList) {

	        Date startDate = vo.getStartDate();
	        Date endDate = vo.getEndDate();

	        // 재직중이면 오늘 날짜 기준 계산
	        if("Y".equals(vo.getCurrentYn())) {
	            endDate = new Date();
	        }

	        String duration = calculateDuration(startDate, endDate);

	        vo.setDuration(duration);
	    }

		return careerList;
	}

	private int calculateMonths(Date startDate, Date endDate) {

	    Calendar start = Calendar.getInstance();
	    start.setTime(startDate);

	    Calendar end = Calendar.getInstance();
	    end.setTime(endDate);

	    int yearDiff =
	            end.get(Calendar.YEAR)
	            - start.get(Calendar.YEAR);

	    int monthDiff =
	            end.get(Calendar.MONTH)
	            - start.get(Calendar.MONTH);

	    return yearDiff * 12 + monthDiff;
	}

	private String calculateDuration(Date startDate, Date endDate) {

	    int totalMonths =
	            calculateMonths(startDate, endDate);

	    int years = totalMonths / 12;
	    int months = totalMonths % 12;

	    return years + "년 " + months + "개월";
	}

	@Override
	public String calculateTotalCareer(List<HireCareerVo> careerList) {

	    int totalMonths = 0;

	    for(HireCareerVo vo : careerList) {

	        Date endDate = vo.getEndDate();

	        if ("Y".equals(vo.getCurrentYn())) {
	            endDate = new Date();
	        }

	        totalMonths += calculateMonths(
	            vo.getStartDate(),
	            endDate
	        );
	    }

	    int years = totalMonths / 12;
	    int months = totalMonths % 12;

	    return years + "년 " + months + "개월";
	}


	@Override
	public int deleteCareer(int careerId) {

		return hireResumeDao.deleteCareer(careerId);
	}


	@Override
	public int saveCareer(HireCareerVo careerVo, int userNum) {
	    int resumeId = getOrCreateResumeId(careerVo.getResumeId(), userNum);
	    careerVo.setResumeId(resumeId);

	    if (careerVo.getCareerId() != null) {
	        return hireResumeDao.updateCareer(careerVo);
	    } else {
	        return hireResumeDao.insertCareer(careerVo);
	    }
	}

	public int getOrCreateResumeId(Integer resumeId, int userNum) {
	    if (resumeId != null && resumeId != 0) return resumeId;

	    HireResumeVo resumeVo = new HireResumeVo();
	    resumeVo.setUserNum(userNum);
	    hireResumeDao.insertBasicResume(resumeVo);
	    return resumeVo.getResumeId();
	}


}
