package gov.usgs.cida.nar.mybatis.dao;

import static gov.usgs.cida.nar.mybatis.dao.BaseDao.QUERY_PACKAGE;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.SITE_QW;
import gov.usgs.cida.nar.mybatis.model.DateIntervalWithConstituent;
import gov.usgs.cida.nar.mybatis.model.Discqw;
import java.util.ArrayList;
import java.util.Date;
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
public class DiscqwDao extends BaseDao {

	private static final Logger log = LoggerFactory.getLogger(DiscqwDao.class);
	
	public List<Discqw> getDiscqw(List<String> siteQwId, List<String> constit, Date startDate, Date endDate) {
		List<Discqw> result = null;
		
		Map<String, Object> params = new HashMap<>(11);
		params.put(SITE_QW, siteQwId);
		params.put(CONSTIT, constit);
		params.put(START_DATE, startDate);
		params.put(END_DATE, endDate);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".DiscqwMapper.getDiscqw", params);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param siteQwId
	 * @param constit constituent
	 * @return empty list if nothing available, or a list of 
	 * constituent-tagged intervals of water years that excludes the 
	 * parameterized modtypes
	 */
	public List<DateIntervalWithConstituent> getAvailability(String siteQwId, String constit) {
		List<DateIntervalWithConstituent> availability = new ArrayList<>();
		
		Map<String, Object> params = new HashMap<>(11);
		//Must put the Site QW ID and CONSTIT in a list to re-use retrieval queries
		params.put(SITE_QW, Lists.newArrayList(siteQwId));
		if(constit != null){
			params.put(CONSTIT, Lists.newArrayList(constit));
		}
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			availability = session.selectList(QUERY_PACKAGE + ".DiscqwMapper.getAvailability", params);
			
		}
		return availability;
	}
	
}
