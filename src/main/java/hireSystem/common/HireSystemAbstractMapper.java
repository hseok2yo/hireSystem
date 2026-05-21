package hireSystem.common;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.mybatis.spring.SqlSessionTemplate;

public abstract class HireSystemAbstractMapper extends EgovAbstractMapper {

    @Resource(name = "hireSystemSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
	
//	@Resource(name = "hireSystemSqlSession")
//	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
//		super.setSqlSessionFactory(sqlSession);
//	}
}