package hireSystem.service.impl;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hireSystem.service.KakaoAuthService;
import hireSystem.service.mapper.KakaoAuthMapper;
import hireSystem.vo.HireUserVo;
import hireSystem.vo.KakaoUserVo;

@Service("kakaoAuthService")
public class KakaoAuthServiceImpl extends EgovAbstractServiceImpl implements KakaoAuthService{
	
	@Autowired
	//@Resource(name = "kakaoAuthMapper")
	public KakaoAuthMapper kakaoAuthMapper;
	
	@Override
	public HireUserVo selectJoinID(KakaoUserVo kakaoUser) {
		return kakaoAuthMapper.selectJoinID(kakaoUser);
	}
	
	
	
}
