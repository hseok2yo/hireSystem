package hireSystem.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hireSystem.service.HireApplicationService;
import hireSystem.service.HireResumeService;
import hireSystem.service.dao.HireApplicationDao;
import hireSystem.service.dao.HireJobPostingDao;
import hireSystem.vo.HireApplicationVo;
import hireSystem.vo.HireResumeVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hireApplicationService")
public class HireApplicationServiceImpl extends EgovAbstractServiceImpl implements HireApplicationService {

	@Resource(name = "hireApplicationDao")
	private HireApplicationDao hireApplicationDao;

	@Resource(name = "hireJobPostingDao")
	private HireJobPostingDao hireJobPostingDao;

	@Resource(name = "hireResumeService")
	private HireResumeService hireResumeService;

	@Override
	@Transactional("hireSystemTxManager")
	public int applyJob(int jobPostingId, Integer resumeId, int loginUserNum) {

		// 1. 중복 지원 체크
		HireApplicationVo checkVo = new HireApplicationVo();
		checkVo.setJobPostingId(jobPostingId);
		checkVo.setUserNum(loginUserNum);

		int dupCount = hireApplicationDao.countApplication(checkVo);
		if (dupCount > 0) {
			throw new IllegalStateException("이미 지원한 공고입니다.");
		}

		int useResumeId;

		if (resumeId != null) {
			// 2-A. 지원 모달에서 선택한 이력서 - 본인 소유인지 반드시 검증
			//      (resumeId는 클라이언트가 보내는 값이므로 서버에서 신뢰하면 안 됨)
			EgovMap resumeMap = hireResumeService.selectResume(resumeId);
			if (resumeMap == null || resumeMap.get("userNum") == null
					|| Integer.parseInt(resumeMap.get("userNum").toString()) != loginUserNum) {
				throw new IllegalStateException("본인 소유의 이력서만 선택할 수 있습니다.");
			}
			useResumeId = resumeId;
		} else {
			// 2-B. 선택값이 없으면 대표이력서로 자동 지원 (기존 동작과 호환)
			EgovMap paramMap = new EgovMap();
			paramMap.put("loginUserNum", loginUserNum);
			HireResumeVo mainResume = hireResumeService.selectResumeMainInfo(paramMap);

			if (mainResume == null || mainResume.getResumeId() == null) {
				throw new IllegalStateException("대표이력서를 먼저 등록해주세요.");
			}
			useResumeId = mainResume.getResumeId();
		}

		// 3. 지원내역 저장
		HireApplicationVo applyVo = new HireApplicationVo();
		applyVo.setJobPostingId(jobPostingId);
		applyVo.setUserNum(loginUserNum);
		applyVo.setResumeId(useResumeId);

		hireApplicationDao.insertApplication(applyVo);

		// 4. 공고 지원자수 +1
		hireJobPostingDao.increaseApplyCnt(jobPostingId);

		log.info("[지원하기] jobPostingId={}, loginUserNum={}, resumeId={}",
				jobPostingId, loginUserNum, useResumeId);

		return useResumeId;
	}
}
