package hireSystem.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;

import hireSystem.service.HireResumeService;
import hireSystem.service.dao.HireResumeDao;
import hireSystem.service.dao.HireSignupDao;
import hireSystem.vo.HireResumeVo;

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


}
