package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.model.Aloads;
import gov.usgs.cida.nar.mybatis.model.WaterYearIntervalWithConstituent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AloadsDao extends BaseDao {

	private static final Logger log = LoggerFactory.getLogger(AloadsDao.class);
	
	public List<Aloads> getAloads(List<String> siteQwId, List<String> constit, List<String> modtypeExcludes,
			Integer startWy, Integer endWy) {
		List<Aloads> result = null;
		
		Map<String, Object> params = new HashMap<>(11);
		params.put(SITE_QW, siteQwId);
		params.put(CONSTIT, constit);
		params.put(MODTYPE_EXCLUDE, modtypeExcludes);
		params.put(WY_START, startWy);
		params.put(WY_END, endWy);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".AloadsMapper.getAloads", params);
			
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param siteQwId
	 * @param modtypeExclusions an optional list of modtypes to ignore when calculating the range
	 * @param constit an optional constituent on which to filter
	 * @return empty list if nothing available, or a list of 
	 * constituent-tagged intervals of water years that excludes the 
	 * parameterized modtypes
	 */
	public List<WaterYearIntervalWithConstituent> getAvailability(String siteQwId, List<String> modtypeExclusions, String constit) {
		List<WaterYearIntervalWithConstituent> availability = new ArrayList<>();
		
		Map<String, Object> params = new HashMap<>(11);
		//Must put all params in a list to re-use retrieval queries
		params.put(SITE_QW, Lists.newArrayList(siteQwId));
		
		if(null != modtypeExclusions && !modtypeExclusions.isEmpty()){
			params.put(MODTYPE_EXCLUDE, modtypeExclusions);
		}
		if(null != constit && 0 != constit.length()){
			params.put(CONSTIT, Lists.newArrayList(constit));
		}
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			availability = session.selectList(QUERY_PACKAGE + ".AloadsMapper.getAvailability", params);
		}
		return availability;
	}
	
}
