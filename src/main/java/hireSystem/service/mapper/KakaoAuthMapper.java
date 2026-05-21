package hireSystem.service.mapper;

import org.apache.ibatis.annotations.Mapper;

import hireSystem.vo.HireUserVo;
import hireSystem.vo.KakaoUserVo;

@Mapper
public interface KakaoAuthMapper {
	
	/**
	 * @param kakaoUser api에서 조회한 사용자정보
	 * @return 있으면 1 없으면 0
	 */
	public HireUserVo selectJoinID(KakaoUserVo kakaoUser);

}
