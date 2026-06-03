package hireSystem.service.impl;

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
import hireSystem.vo.HireResumeVo;
import hireSystem.vo.HireUserVo;

@Service("hireResumeService")
public class HireResumeServiceImpl extends EgovAbstractServiceImpl implements HireResumeService {

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
}
