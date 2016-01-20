package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.MyBatisConnectionFactory;
import gov.usgs.cida.nar.mybatis.model.Aflow;
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
public class AflowDao {

	private static final Logger log = LoggerFactory.getLogger(AflowDao.class);
	
	private final SqlSessionFactory sqlSessionFactory;

	public AflowDao() {
		this.sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	}

	public AflowDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public static final String queryPackage = "gov.usgs.cida.nar.mybatis.mappers";
	
	public List<Aflow> getAflow(String siteQwId, Integer startWy, Integer endWy) {
		List<Aflow> result = null;
		
		Map<String, Object> params = new HashMap<>(7);
		params.put("siteQwId", siteQwId);
		params.put("startWy", startWy);
		params.put("endWy", endWy);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(queryPackage + ".AflowMapper.getAflow", params);
		}
		
		return result;
	}
	
}
