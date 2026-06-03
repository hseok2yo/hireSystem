package hireSystem.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import hireSystem.common.CommonUtil;
import hireSystem.service.HireCareerService;
import hireSystem.service.dao.HireCareerDao;
import hireSystem.service.dao.HireResumeDao;
import hireSystem.vo.HireCareerVo;

@Service("hireCareerService")
public class HireCareerServiceImpl extends EgovAbstractServiceImpl implements HireCareerService {

    @Resource(name = "hireCareerDao")
    private HireCareerDao hireCareerDao;

    /** resume 미생성 시 resume 먼저 생성하기 위해 주입 */
    @Resource(name = "hireResumeDao")
    private HireResumeDao hireResumeDao;

    @Resource(name = "commonUtil")
	private CommonUtil commonUtil;

    // ---------------------------------------------------------------
    // 조회
    // ---------------------------------------------------------------

    @Override
    public List<HireCareerVo> selectCareerInfo(int resumeId) {
        List<HireCareerVo> careerList = hireCareerDao.selectCareerInfo(resumeId);

        for (HireCareerVo vo : careerList) {
            Date startDate = vo.getStartDate();
            Date endDate   = vo.getEndDate();

            // 재직중이면 오늘 날짜 기준 계산
            if ("Y".equals(vo.getCurrentYn())) {
                endDate = new Date();
            }

            vo.setDuration(calculateDuration(startDate, endDate));
        }

        return careerList;
    }

    // ---------------------------------------------------------------
    // 저장 / 수정
    // ---------------------------------------------------------------

    @Override
    public int saveCareer(HireCareerVo careerVo, int loginUserNum) {
        // resumeId 없으면 resume 먼저 생성
        int resumeId = commonUtil.getOrCreateResumeId(careerVo.getResumeId(), loginUserNum);
        careerVo.setResumeId(resumeId);

        if (careerVo.getCareerId() != null) {
            return hireCareerDao.updateCareer(careerVo);
        } else {
            return hireCareerDao.insertCareer(careerVo);
        }
    }

    // ---------------------------------------------------------------
    // 삭제
    // ---------------------------------------------------------------

    @Override
    public int deleteCareer(int careerId) {
        return hireCareerDao.deleteCareer(careerId);
    }

    // ---------------------------------------------------------------
    // 총 경력 계산
    // ---------------------------------------------------------------

    @Override
    public String calculateTotalCareer(List<HireCareerVo> careerList) {
        int totalMonths = 0;

        for (HireCareerVo vo : careerList) {
            Date endDate = vo.getEndDate();
            if ("Y".equals(vo.getCurrentYn())) {
                endDate = new Date();
            }
            totalMonths += calculateMonths(vo.getStartDate(), endDate);
        }

        int years  = totalMonths / 12;
        int months = totalMonths % 12;
        return years + "년 " + months + "개월";
    }


    /** 두 날짜 사이의 총 개월 수 */
    private int calculateMonths(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        int yearDiff  = end.get(Calendar.YEAR)  - start.get(Calendar.YEAR);
        int monthDiff = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);

        return yearDiff * 12 + monthDiff;
    }

    /** 개월 수를 "N년 M개월" 형식으로 변환 */
    private String calculateDuration(Date startDate, Date endDate) {
        int totalMonths = calculateMonths(startDate, endDate);
        int years  = totalMonths / 12;
        int months = totalMonths % 12;
        return years + "년 " + months + "개월";
    }
}
