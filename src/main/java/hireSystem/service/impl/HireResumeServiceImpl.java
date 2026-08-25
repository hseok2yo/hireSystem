package hireSystem.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hireSystem.common.CommonUtil;
import hireSystem.common.PagingUtil;
import hireSystem.service.HireResumeService;
import hireSystem.service.dao.HireActivityDao;
import hireSystem.service.dao.HireCareerDao;
import hireSystem.service.dao.HireCertificationDao;
import hireSystem.service.dao.HireCoverLetterDao;
import hireSystem.service.dao.HireEducationDao;
import hireSystem.service.dao.HirePortfolioDao;
import hireSystem.service.dao.HireResumeDao;
import hireSystem.service.dao.HireResumeSkillDao;
import hireSystem.service.dao.HireSignupDao;
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

@Slf4j
@Service("hireResumeService")
public class HireResumeServiceImpl extends EgovAbstractServiceImpl implements HireResumeService {

    @Resource(name = "hireResumeDao")
    private HireResumeDao hireResumeDao;

    @Resource(name = "propertiesService")
    private EgovPropertyService propertiesService;

    @Resource(name = "hireSignupDao")
    private HireSignupDao hireSignupDao;

    @Resource(name = "commonUtil")
    private CommonUtil commonUtil;

    @Resource(name = "hirePortfolioDao")
    private HirePortfolioDao hirePortfolioDao;

    @Resource(name = "hireCareerDao")
    private HireCareerDao hireCareerDao;

    @Resource(name = "hireEducationDao")
    private HireEducationDao hireEducationDao;

    @Resource(name = "hireCertificationDao")
    private HireCertificationDao hireCertificationDao;

    @Resource(name = "hireActivityDao")
    private HireActivityDao hireActivityDao;

    @Resource(name = "hireCoverLetterDao")
    private HireCoverLetterDao hireCoverLetterDao;

    @Resource(name = "hireResumeSkillDao")
    private HireResumeSkillDao hireResumeSkillDao;

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

        int getPage      = Integer.parseInt(map.get("page").toString());
        int loginUserNum = Integer.parseInt(map.get("loginUserNum").toString());

        int pageSize  = 5;
        int offset    = (getPage - 1) * pageSize;
        int blockSize = 5;

        vo.setPage(getPage);
        vo.setUserNum(loginUserNum);
        vo.setPageSize(pageSize);
        vo.setOffset(offset);
        vo.setSearchSort((String) map.get("searchSort"));

        List<HireResumeVo> selectList = hireResumeDao.selectResumeSubInfo(vo);
        int totalCount = hireResumeDao.selectSubTotalCount(vo);

        Map<String, Object> result = PagingUtil.getPaging(getPage, totalCount, pageSize, blockSize);
        result.put("list", selectList);

        return result;
    }

    @Override
    public int saveBasicResume(HireUserVo hireVo, HireResumeVo vo) {
        hireSignupDao.updateBasicHireUserInfo(hireVo);

        if (vo.getResumeId() == null) {
            return hireResumeDao.insertBasicResume(vo);
        } else {
            return hireResumeDao.updateBasicResume(vo);
        }
    }

    @Override
    public int saveResume(HireResumeVo vo, int loginUserNum) {
    	int resumeId = commonUtil.getOrCreateResumeId(vo.getResumeId(), loginUserNum);
        vo.setResumeId(resumeId);

        // 서버단 검증: 클라이언트가 보낸 sectionVisible 중 실제 데이터가 있는 섹션만 최종 반영
        String filteredSectionVisible = filterVisibleSections(resumeId, vo.getSectionVisible());
        vo.setSectionVisible(filteredSectionVisible);

        int result = hireResumeDao.updateResume(vo);
        return result;
    }

    /**
     * 옵션 섹션(activity, certification, portfolio, coverLetter) 중
     * 실제 DB 데이터가 존재하는 섹션만 걸러서 반환하는 공통 검증 로직.
     * updateSectionVisible.do, resumeSave.do 양쪽에서 공통으로 사용한다.
     */
    @Override
    public String filterVisibleSections(int resumeId, String sectionVisible) {
        if (sectionVisible == null || sectionVisible.isEmpty()) {
            return "";
        }

        String[] requested = sectionVisible.split(",");
        StringBuilder filtered = new StringBuilder();

        for (String targetId : requested) {
            targetId = targetId.trim();
            boolean hasData;
            switch (targetId) {
                case "#activity":
                    hasData = !hireActivityDao.selectActivityList(resumeId).isEmpty();
                    break;
                case "#certification":
                    hasData = !hireCertificationDao.selectCertificationInfo(resumeId).isEmpty();
                    break;
                case "#portfolio":
                    hasData = !hirePortfolioDao.selectPortfolioList(resumeId).isEmpty();
                    break;
                case "#coverLetter":
                    hasData = !hireCoverLetterDao.selectCoverLetterList(resumeId).isEmpty();
                    break;
                default:
                    hasData = false;
            }
            if (hasData) {
                if (filtered.length() > 0) filtered.append(",");
                filtered.append(targetId);
            }
        }

        return filtered.toString();
    }

	@Override
	public EgovMap selectResume(int resumeId) {
		EgovMap map = new EgovMap();
		map.put("resumeId", resumeId);
		return hireResumeDao.selectResume(map);
	}

	@Override
	@Transactional("hireSystemTxManager")
	public boolean deleteResume(Integer resumeId, int loginUserNum) {

	    log.info("========== 이력서 삭제 시작 ==========");
	    log.info("resumeId : {}, loginUserNum : {}", resumeId, loginUserNum);

	    // 1. 삭제할 파일 목록 조회
	    List<HirePortfolioVo> fileList =
	            hirePortfolioDao.selectPortfolioList(resumeId);

	    log.info("삭제할 파일 개수 : {}",
	            fileList != null ? fileList.size() : 0);

	    // 2. 이력서 삭제
	    Map<String, Object> param = new HashMap<>();
	    param.put("resumeId", resumeId);
	    param.put("loginUserNum", loginUserNum);

	    // DB CASCADE로 자식 테이블까지 삭제
	    int result = hireResumeDao.deleteResume(param);

	    log.info("DB 이력서 삭제 결과 : {}", result);

	    if (result <= 0) {
	        log.warn("이력서 DB 삭제 실패 - resumeId : {}, loginUserNum : {}",
	                resumeId, loginUserNum);
	        return false;
	    }

	    log.info("이력서 DB 삭제 성공 - resumeId : {}", resumeId);

	    // 3. 실제 파일 삭제
	    if (fileList != null) {

	        for (HirePortfolioVo file : fileList) {

	        	if ("file".equals(file.getFileType())) {

	        	    File targetFile = new File(
	        	            file.getSavedPath(),
	        	            file.getSavedName()
	        	    );

	        	    log.info("파일 삭제 시도 : {}", targetFile.getAbsolutePath());

	        	    if (targetFile.exists()) {

	        	        boolean deleted = targetFile.delete();

	        	        if (deleted) {
	        	            log.info("파일 삭제 성공 : {}", targetFile.getAbsolutePath());
	        	        } else {
	        	            log.error("파일 삭제 실패 : {}", targetFile.getAbsolutePath());
	        	            return false;
	        	        }

	        	    } else {
	        	        log.warn("파일이 존재하지 않음 : {}", targetFile.getAbsolutePath());
	        	    }
	        	}
	        }
	    }

	    log.info("========== 이력서 삭제 전체 성공 - resumeId : {} ==========",
	            resumeId);

	    return true;
	}

	@Override
	@Transactional("hireSystemTxManager")
	public int duplicateResume(int resumeId, int loginUserNum) {

		// 0. 원본 조회 + 소유자 검증 (resumeId는 프론트에서 넘어오므로 반드시 확인)
		EgovMap map = new EgovMap();
		map.put("resumeId", resumeId);
		EgovMap origin = hireResumeDao.selectResume(map);

		if (origin == null || origin.get("userNum") == null
				|| (int) origin.get("userNum") != loginUserNum) {
			throw new IllegalArgumentException("본인 이력서만 복제할 수 있습니다.");
		}

		// 1. 새 RESUME 로우 생성
		HireResumeVo newResume = new HireResumeVo();
		newResume.setUserNum(loginUserNum);
		newResume.setTitle(origin.get("title") + " 복사본");
		hireResumeDao.insertBasicResume(newResume); // 실행 후 newResume.resumeId 채워짐

		newResume.setSectionVisible((String) origin.get("sectionVisible"));
		hireResumeDao.updateResume(newResume); // sectionVisible까지 반영

		int newResumeId = newResume.getResumeId();

		// 2. 경력
		for (HireCareerVo v : hireCareerDao.selectCareerInfo(resumeId)) {
			v.setCareerId(null);
			v.setResumeId(newResumeId);
			hireCareerDao.insertCareer(v);
		}

		// 3. 학력
		for (HireEducationVo v : hireEducationDao.selectEducationInfo(resumeId)) {
			v.setEducationId(null);
			v.setResumeId(newResumeId);
			hireEducationDao.insertEducation(v);
		}

		// 4. 자격/어학/수상
		for (HireCertificationVo v : hireCertificationDao.selectCertificationInfo(resumeId)) {
			v.setCertificationId(null);
			v.setResumeId(newResumeId);
			hireCertificationDao.insertCertification(v);
		}

		// 5. 경험/활동/교육
		for (HireActivityVo v : hireActivityDao.selectActivityList(resumeId)) {
			v.setActivityId(null);
			v.setResumeId(newResumeId);
			hireActivityDao.insertActivity(v);
		}

		// 6. 자기소개서
		for (HireCoverLetterVo v : hireCoverLetterDao.selectCoverLetterList(resumeId)) {
			v.setClId(null);
			v.setResumeId(newResumeId);
			hireCoverLetterDao.insertCoverLetter(v);
		}

		// 7. 스킬
		for (HireSkillVo v : hireResumeSkillDao.selectSkillInfo(resumeId)) {
			v.setSkillId(null);
			v.setResumeId(newResumeId);
			hireResumeSkillDao.insertSkill(v);
		}

		// 8. 포트폴리오 - DB row뿐 아니라 실물 파일도 복사해야 함
		//    (안 하면 원본 삭제 시 복제본이 같은 파일을 참조하다 깨짐)
		for (HirePortfolioVo v : hirePortfolioDao.selectPortfolioList(resumeId)) {
			v.setPortfolioId(null);
			v.setResumeId(newResumeId);

			if ("file".equals(v.getFileType()) && v.getSavedName() != null) {
				try {
					String newSavedName = UUID.randomUUID() + "_" + v.getOriginalName();
					Files.copy(
							Paths.get(v.getSavedPath(), v.getSavedName()),
							Paths.get(v.getSavedPath(), newSavedName)
					);
					v.setSavedName(newSavedName);
				} catch (IOException e) {
					throw new RuntimeException("포트폴리오 파일 복제 실패: " + v.getOriginalName(), e);
				}
			}
			hirePortfolioDao.insertPortfolio(v);
		}

		return newResumeId;
	}

	@Override
	public int updateSectionVisible(int resumeId, String sectionVisible) {
		HireResumeVo vo = new HireResumeVo();
		vo.setResumeId(resumeId);
		vo.setSectionVisible(sectionVisible);
		return hireResumeDao.updateSectionVisible(vo);
	}

}