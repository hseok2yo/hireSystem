package hireSystem.service;

import hireSystem.vo.HireUserVo;
import hireSystem.vo.KakaoUserVo;

public interface KakaoAuthService {
	
	/**
	 * @param kakaoUser api에서 조회한 사용자정보
	 * @return 있으면 1 없으면 0
	 */
	public HireUserVo selectJoinID(KakaoUserVo kakaoUser);

}
