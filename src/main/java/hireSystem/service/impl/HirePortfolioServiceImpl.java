package hireSystem.service.impl;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import hireSystem.common.CommonFileService;
import hireSystem.common.CommonUtil;
import hireSystem.service.HirePortfolioService;
import hireSystem.service.dao.HirePortfolioDao;
import hireSystem.vo.HirePortfolioVo;

@Service("hirePortfolioService")
public class HirePortfolioServiceImpl extends EgovAbstractServiceImpl implements HirePortfolioService {

    @Resource(name = "hirePortfolioDao")
    private HirePortfolioDao hirePortfolioDao;

    @Resource(name = "commonUtil")
    private CommonUtil commonUtil;

    @Resource(name = "propertiesService")
    private EgovPropertyService propertiesService;

    @Resource(name = "commonFileService")
	private CommonFileService commonFileService;

    @Override
    public int savePortfolio(HirePortfolioVo vo, MultipartFile portfolioFile, int loginUserNum) throws Exception {

        // resumeId 없으면 resume 먼저 생성
        int resumeId = commonUtil.getOrCreateResumeId(vo.getResumeId(), loginUserNum);
        vo.setResumeId(resumeId);
        int result = 0;

        // 파일 업로드 처리
        if ("file".equals(vo.getFileType()) && portfolioFile != null && !portfolioFile.isEmpty()) {

        	// 수정이면 기존파일 삭제
            if (vo.getPortfolioId() != null) {
            	HirePortfolioVo oldVo = hirePortfolioDao.selectPortfolio(vo.getPortfolioId());
            	if (oldVo != null && oldVo.getSavedName() != null) {
            	    commonFileService.deleteFile(oldVo.getSavedPath(), oldVo.getSavedName());
            	}
            }

            String originalName = portfolioFile.getOriginalFilename();
            String savedName    = UUID.randomUUID().toString() + "_" + originalName;
            String savedPath    = propertiesService.getString("resumeportfolio.store.path");

            File dir = new File(savedPath);
            if (!dir.exists()) dir.mkdirs();

            portfolioFile.transferTo(new File(savedPath + savedName));

            vo.setOriginalName(originalName);
            vo.setSavedName(savedName);
            vo.setSavedPath(savedPath);
            vo.setFileSize(portfolioFile.getSize());
        }

        if (vo.getPortfolioId() != null) {
        	result = hirePortfolioDao.updatePortfolio(vo);
        } else {
        	result = hirePortfolioDao.insertPortfolio(vo);
        }
        return result;
    }

	@Override
	public int deletePortfolio(Integer portfolioId) {
		HirePortfolioVo oldVo = hirePortfolioDao.selectPortfolio(portfolioId);
	    if (oldVo != null && oldVo.getSavedName() != null) {
	        File oldFile = new File(oldVo.getSavedPath() + oldVo.getSavedName());
	        if (oldFile.exists()) oldFile.delete();
	    }
		int result = hirePortfolioDao.deletePortfolio(portfolioId);
		return result;
	}

	@Override
	public List<HirePortfolioVo> selectPortfolioList(int resumeId) {
	    return hirePortfolioDao.selectPortfolioList(resumeId);
	}


}
