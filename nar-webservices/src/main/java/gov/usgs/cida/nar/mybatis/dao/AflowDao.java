package gov.usgs.cida.nar.mybatis.dao;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.mybatis.model.Aflow;
import gov.usgs.cida.nar.mybatis.model.WaterYearInterval;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AflowDao extends BaseDao {

	private static final Logger log = LoggerFactory.getLogger(AflowDao.class);
	
	public List<Aflow> getAflow(List<String> siteQwId, Integer startWy, Integer endWy) {
		List<Aflow> result = null;
		
		Map<String, Object> params = new HashMap<>(7);
		params.put(SITE_QW, siteQwId);
		params.put(WY_START, startWy);
		params.put(WY_END, endWy);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".AflowMapper.getAflow", params);
		}
		
		return result;
	}
	/**
	 * 
	 * @param siteQwId
	 * @return null if unavailable, otherwise return a fully-formed 
	 * WaterYearInterval
	 */
	public WaterYearInterval getAvailability(String siteQwId){
		WaterYearInterval result = null;
		
		Map<String, Object> params = new HashMap<>();
		//Must put the Site QW ID in a list to re-use retrieval queries
		params.put(SITE_QW, Lists.newArrayList(siteQwId));
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectOne(QUERY_PACKAGE + ".AflowMapper.getAvailability", params);
		}
		
		return result;
	}
}
