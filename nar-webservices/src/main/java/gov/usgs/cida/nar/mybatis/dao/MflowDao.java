package gov.usgs.cida.nar.mybatis.dao;

import com.google.common.collect.Lists;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.QUERY_PACKAGE;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.SITE_QW;
import gov.usgs.cida.nar.mybatis.model.Mflow;
import gov.usgs.cida.nar.mybatis.model.WaterYearAndMonthInterval;
import gov.usgs.cida.nar.mybatis.model.WaterYearInterval;
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
	
	public List<Mflow> getMflow(List<String> siteQwId, Integer startWy, Integer endWy) {
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
	
	/**
	 * 
	 * @param siteQwId
	 * @return null if unavailable, otherwise return a fully-formed 
	 * WaterYearInterval
	 */
	public WaterYearAndMonthInterval getAvailability(String siteQwId){
		WaterYearAndMonthInterval result = null;
		
		Map<String, Object> params = new HashMap<>();
		params.put("single_site_qw_id", siteQwId);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectOne(QUERY_PACKAGE + ".MflowMapper.getAvailability", params);
		}
		
		return result;
	}
	
}
