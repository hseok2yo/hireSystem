package hireSystem.service.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import hireSystem.vo.BoardWriteVo;

@Mapper
public interface HireBoardListMapper {

	int boardInsert(BoardWriteVo writeVo);

	List<BoardWriteVo> selectList(BoardWriteVo vo);

	int selectTotalCount(BoardWriteVo getVo);

	BoardWriteVo selectDetail(int boardNum);

	void updateDetailCnt(int boardNum);

	int boardUpdate(BoardWriteVo writeVo);

	int boardDelete(int boardNum);



}
