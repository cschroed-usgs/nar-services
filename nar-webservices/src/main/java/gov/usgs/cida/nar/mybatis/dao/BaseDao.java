package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class BaseDao {
	
	public static final String QUERY_PACKAGE = "gov.usgs.cida.nar.mybatis.mappers";
	
	protected final SqlSessionFactory sqlSessionFactory;

	public BaseDao() {
		this(MyBatisConnectionFactory.getSqlSessionFactory());
	}

	public BaseDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
