package hireSystem.service.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HireSignUpMapper {
	int checkDuplicationID(String id);

	int updateUserPhoto(Map<String, Object> updateMap);
}
