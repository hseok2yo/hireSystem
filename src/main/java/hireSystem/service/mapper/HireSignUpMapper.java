package hireSystem.service.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HireSignUpMapper {
	int checkDuplicationID(String id);
}
