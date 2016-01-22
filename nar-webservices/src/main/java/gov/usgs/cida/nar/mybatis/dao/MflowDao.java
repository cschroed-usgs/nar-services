package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.model.Mflow;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class MflowDao extends BaseDao {

	private static final Logger log = LoggerFactory.getLogger(MflowDao.class);
	
	public List<Mflow> getMflow(String siteQwId, Integer startWy, Integer endWy) {
		List<Mflow> result = null;
		
		Map<String, Object> params = new HashMap<>(7);
		params.put(SITE_QW, siteQwId);
		params.put(WY_START, startWy);
		params.put(WY_END, endWy);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".MflowMapper.getMflow", params);
		}
		
		return result;
	}
	
}
