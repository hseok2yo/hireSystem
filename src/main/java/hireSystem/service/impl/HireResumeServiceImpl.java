package hireSystem.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;

import hireSystem.service.HireResumeService;
import hireSystem.service.dao.HireResumeDao;

@Service("hireResumeService")
public class HireResumeServiceImpl  extends EgovAbstractServiceImpl implements HireResumeService{
	
	@Resource(name = "hireResumeDao")
	private HireResumeDao hireResumeDao;
	
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;

	@Override
	public EgovMap selectResumeUserInfo(int loginUserNum) {
		
		
		EgovMap map = hireResumeDao.selectResumeUserInfo(loginUserNum);

		
		if (map.get("userPhotoName") != null) {
		    String url = propertiesService.getString("resume.store.url") + (String) map.get("userPhotoName");
		    map.put("userPhotoUrl", url);  // userPhotoName 덮어쓰지 말고 새 키로 추가
		}
		
		return map;
	}

	@Override
	public int insertEmptyResume(int loginUserNum) {
		return hireResumeDao.insertEmptyResume(loginUserNum);
	}
	
}
