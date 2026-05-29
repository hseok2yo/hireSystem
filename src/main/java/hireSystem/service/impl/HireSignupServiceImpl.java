package hireSystem.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import hireSystem.common.CommonFileService;
import hireSystem.service.HireSignupService;
import hireSystem.service.dao.HireResumeDao;
import hireSystem.service.dao.HireSignupDao;
import hireSystem.service.mapper.HireSignUpMapper;
import hireSystem.vo.HireUserVo;
import hireSystem.vo.UserAgreeDefVo;
import hireSystem.vo.UserAgreementVo;

@Service("hireSignupService")
public class HireSignupServiceImpl extends EgovAbstractServiceImpl implements HireSignupService{
	
	@Resource(name = "hireSignupDao")
	private HireSignupDao hireDao;
	
	@Resource(name = "hireResumeDao")
	private HireResumeDao hireResumeDao;
	
	@Autowired
	private HireSignUpMapper hireMapper;
	
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;
	
	@Resource(name = "commonFileService")
	private CommonFileService commonFileService;
	
	@Override
	public int checkDuplicationID(String id) {
//		return hireDao.checkDuplicationID(id);
		return hireMapper.checkDuplicationID(id);
	}
	
	@SuppressWarnings("deprecation")
	@Transactional("hireSystemTxManager")
	@Override
	public boolean registMember(HireUserVo hireUserVo) {
		
		int results = 0;
		//1. 회원정보 등록
		hireUserVo.setUserPw(StringEscapeUtils.unescapeHtml4(hireUserVo.getUserPw()));
		results = hireDao.insertUserInfo(hireUserVo); //등록한 회원번호 받아옴
		
		hireResumeDao.insertEmptyResume(hireUserVo.getUserNum()); //이력서 최소1개 있기 위해서 임시이력서 테이블 생성
		
		if (results <= 0) {
	        return false;
	    }
		
		//2.회원 약권정보 저장
		int agreeInsertCnt = InsertUserAgreeData(hireUserVo, hireUserVo.getUserNum()); 
		if (agreeInsertCnt <= 0) {
			return false;
		}
		 // 🔥 테스트용
//	    if (true) {
//	        throw new RuntimeException("트랜잭션 롤백 테스트");
//	    }
		
		return true;
	}

	private int InsertUserAgreeData(HireUserVo hireUserVo, int getInsertUserNum) {
		int successNo = 0;
		
		//회원이 체크한 약관동의 체크박스 값들
		List<String> agreementsList = hireUserVo.getAgreements();
		
		// Set으로 변환 contains()는 해시로 바로 찾음
		Set<String> checkedSet = new LinkedHashSet<>(agreementsList);
		
		//등록할 유저약관정보 저징리스트
		List<UserAgreementVo> userAgreeInfo = new ArrayList<>();
		
		//DB에 있는 약관동의 리스트 조회
		List<UserAgreeDefVo> agreeList = hireDao.selectAgreeList();
	
		/** 회원이 동의한 값, 동의하지 않은 값 Y, N으로 구분해서 값 세팅 후 DB에 저장*/
		for(UserAgreeDefVo vo : agreeList) {
		    String code = vo.getAgreeCd();
		    // Set에서 빠르게 검색
		    if(checkedSet.contains(code)) {
		    	UserAgreementVo agreeVo = SettingUserAgreeValue(getInsertUserNum, code, "Y");
		    	userAgreeInfo.add(agreeVo);
		        //System.out.println(code + " = Y");  // 체크됨
		    } else {
		    	UserAgreementVo agreeVo = SettingUserAgreeValue(getInsertUserNum, code, "N");
		    	userAgreeInfo.add(agreeVo);
		       // System.out.println(code + " = N");  // 체크 안됨
		    }
		}
		
		//insert하는부분 부터 시작
		if (!userAgreeInfo.isEmpty()) {
			successNo = hireDao.insertUserAgreement(userAgreeInfo);
		}
		
		return successNo;
		
	}

	private UserAgreementVo SettingUserAgreeValue(int getInsertUserNum, String code, String value) {
		UserAgreementVo agreeVo = new UserAgreementVo();
    	agreeVo.setUserNum(getInsertUserNum);
    	agreeVo.setAgreeCd(code);
    	agreeVo.setAgreeYn(value);
    	agreeVo.setPages("regist");
		return agreeVo;
	}

	@Override
	public HireUserVo selectHireUserInfo(int userNum) {
		return hireDao.selectHireUserInfo(userNum);
	}

	@Override
	public String updateUserPhoto(MultipartFile file, int userNum) throws IOException {
		String uploadPath = propertiesService.getString("resume.store.path");
	    String storeUrl   = propertiesService.getString("resume.store.url");

	    String filename = commonFileService.saveFile(file, uploadPath);

	    // 기존 파일명 조회
	    HireUserVo hireUserVo = hireDao.selectHireUserInfo(userNum);
	    String oldFileName = hireUserVo.getUserPhotoName();
	    String oldFilePath = hireUserVo.getUserPhotoPath();
	    if(oldFileName != null && oldFilePath != null) {
	    	commonFileService.deleteFile(oldFilePath, oldFileName);
	    }
	    
	    Map<String, Object> updateMap = new HashMap<>();
	    updateMap.put("userNum", userNum);
	    updateMap.put("userPhotoName", filename);
	    updateMap.put("userPhotoPath", uploadPath);

	    hireMapper.updateUserPhoto(updateMap);

	    return storeUrl + filename; // 뷰에서 쓸 URL 반환
	}
	
	
	


	
}
