package hireSystem.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import hireSystem.common.HireSystemAbstractMapper;
import hireSystem.vo.HireUserVo;
import hireSystem.vo.UserAgreeDefVo;
import hireSystem.vo.UserAgreementVo;

@Repository("hireSignupDao")
public class HireSignupDao extends HireSystemAbstractMapper {
	
	/**
	 * 중복아이디 체크
	 * @param id 중복체크할 아이디
	 * @return  0 : 없음 /  1 : 있음
	 */
	public int checkDuplicationID(String id) {
		return selectOne("hireSignupDao.checkDuplicationID", id);
	}
	
	/**
	 * 유저정보 등록
	 * @param hireUserVo 유저정보 vo
	 * @return 성공여부
	 */
	public int insertUserInfo(HireUserVo hireUserVo) {
		return insert("hireSignupDao.insertUserInfo", hireUserVo);
	}
	
	/**
	 * 약관동의 리스트
	 * @return 약관동의 리스트
	 */
	public List<UserAgreeDefVo> selectAgreeList() {
		return selectList("hireSignupDao.selectAgreeList");
	}
	
	/**
	 * 유저 약관동의 정보 저장
	 * @param userAgreeInfo 유저 약관동의한 부분/비동의 부분 데이터리스트
	 * @return 성공여부
	 */
	public int insertUserAgreement(List<UserAgreementVo> userAgreeInfo) {
		return insert("hireSignupDao.insertUserAgreement", userAgreeInfo);
	}


}
