package hireSystem.service.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardImageMapper {
	
	int insertTempImgInfo(Map<String, Object> insertMap);

	int updateBoardNumAndStatus(@Param("filenames") List<String> filenames, @Param("boardNum") int boardNum);

	List<String> selectFilenamesByBoardNum(int boardNum);
	
	int deleteByBoardNum(int boardNum);

	int deleteByFilename(String fileName);

}
