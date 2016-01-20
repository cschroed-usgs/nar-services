package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.MyBatisConnectionFactory;
import gov.usgs.cida.nar.mybatis.model.Dflow;
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
public class DflowDao {

	private static final Logger log = LoggerFactory.getLogger(DflowDao.class);
	
	private final SqlSessionFactory sqlSessionFactory;

	public DflowDao() {
		this.sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	}

	public DflowDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<Dflow> getDflow(String siteQwId, Date startDate, Date endDate) {
		List<Dflow> result = null;
		
		Map<String, Object> params = new HashMap<>(7);
		params.put("siteQwId", siteQwId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(MyBatisConnectionFactory.QUERY_PACKAGE + ".DflowMapper.getDflow", params);
		}
		
		return result;
	}
	
}
