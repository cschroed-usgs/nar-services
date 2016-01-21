package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.MyBatisConnectionFactory;
import gov.usgs.cida.nar.mybatis.model.Mflow;
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
public class MflowDao {

	private static final Logger log = LoggerFactory.getLogger(MflowDao.class);
	
	private final SqlSessionFactory sqlSessionFactory;

	public MflowDao() {
		this.sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	}

	public MflowDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<Mflow> getMflow(String siteQwId, Integer startWy, Integer endWy) {
		List<Mflow> result = null;
		
		Map<String, Object> params = new HashMap<>(7);
		params.put("siteQwId", siteQwId);
		params.put("startWy", startWy);
		params.put("endWy", endWy);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(MyBatisConnectionFactory.QUERY_PACKAGE + ".MflowMapper.getMflow", params);
		}
		
		return result;
	}
	
}
