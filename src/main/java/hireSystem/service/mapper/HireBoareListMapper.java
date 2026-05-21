package hireSystem.service.mapper;

import org.apache.ibatis.annotations.Mapper;

import hireSystem.vo.BoardWriteVo;

@Mapper
public interface HireBoareListMapper {

	int boardInsert(BoardWriteVo writeVo);

}
