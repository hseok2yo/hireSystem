package hireSystem.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import hireSystem.vo.BoardWriteVo;

public interface HireBoardListService {
	
	/**
	 * 게시글 등록
	 * @param writeVo 게시글 정보들
	 * @param session 
	 * @return 성공여부 1 성공 0 실패
	 */
	int boardInsert(BoardWriteVo writeVo, HttpSession session);

	Map<String, Object> selectList(BoardWriteVo writeVo);

	BoardWriteVo selectDetail(int boardNum);

	BoardWriteVo selectDetailForEdit(int boardNum);
	
	int insertTempImgInfo(Map<String, Object> insertMap);

	int boardUpdate(BoardWriteVo writeVo);

	int boardDelete(int boardNum);

}
