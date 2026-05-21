package hireSystem.service;

import hireSystem.vo.BoardWriteVo;

public interface HireBoareListService {
	
	/**
	 * 게시글 등록
	 * @param writeVo 게시글 정보들
	 * @return 성공여부 1 성공 0 실패
	 */
	int boardInsert(BoardWriteVo writeVo);
	
	
	
}
