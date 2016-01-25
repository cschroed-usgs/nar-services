package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class BaseDao {
	
	public static final String QUERY_PACKAGE = "gov.usgs.cida.nar.mybatis.mappers";
	public static final String SITE_QW = "siteQwId";
	public static final String WY_START = "startWy";
	public static final String WY_END = "endWy";
	public static final String CONSTIT = "constit";
	public static final String MODTYPE_EXCLUDE = "modtypeExcludes";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	
	protected final SqlSessionFactory sqlSessionFactory;

	public BaseDao() {
		this(MyBatisConnectionFactory.getSqlSessionFactory());
	}

	public BaseDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
