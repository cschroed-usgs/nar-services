package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.MyBatisConnectionFactory;
import gov.usgs.cida.nar.mybatis.model.Discqw;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DiscqwDao {

	private static final Logger log = LoggerFactory.getLogger(DiscqwDao.class);
	
	private final SqlSessionFactory sqlSessionFactory;

	public DiscqwDao() {
		this.sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	}

	public DiscqwDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<Discqw> getDiscqw(String siteQwId, String constit, Date startDate, Date endDate) {
		List<Discqw> result = null;
		
		Map<String, Object> params = new HashMap<>(11);
		params.put("siteQwId", siteQwId);
		params.put("constit", constit);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(MyBatisConnectionFactory.QUERY_PACKAGE + ".DiscqwMapper.getDiscqw", params);
		}
		
		return result;
	}
	
}
