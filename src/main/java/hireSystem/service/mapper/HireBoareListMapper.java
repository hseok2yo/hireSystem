package hireSystem.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import hireSystem.vo.BoardWriteVo;

@Mapper
public interface HireBoareListMapper {

	int boardInsert(BoardWriteVo writeVo);

	List<BoardWriteVo> selectList(BoardWriteVo vo);

	int selectTotalCount(BoardWriteVo getVo);

	BoardWriteVo selectDetail(int boardNum);

	void updateDetailCnt(int boardNum);

}
