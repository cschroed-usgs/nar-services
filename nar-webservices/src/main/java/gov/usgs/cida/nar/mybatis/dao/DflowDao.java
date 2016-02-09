package gov.usgs.cida.nar.mybatis.dao;

import com.google.common.collect.Lists;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.QUERY_PACKAGE;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.SITE_QW;
import gov.usgs.cida.nar.mybatis.model.DateInterval;
import gov.usgs.cida.nar.mybatis.model.Dflow;
import gov.usgs.cida.nar.mybatis.model.WaterYearInterval;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DflowDao extends BaseDao {

	private static final Logger log = LoggerFactory.getLogger(DflowDao.class);
	
	public List<Dflow> getDflow(List<String> siteQwId, Date startDate, Date endDate) {
		List<Dflow> result = null;
		
		Map<String, Object> params = new HashMap<>(7);
		params.put(SITE_QW, siteQwId);
		params.put(START_DATE, startDate);
		params.put(END_DATE, endDate);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".DflowMapper.getDflow", params);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param siteQwId
	 * @return null if unavailable, otherwise return a fully-formed 
	 * WaterYearInterval
	 */
	public DateInterval getAvailability(String siteQwId){
		DateInterval result = null;
		
		Map<String, Object> params = new HashMap<>();
		//Must put the Site QW ID in a list to re-use retrieval queries
		params.put(SITE_QW, Lists.newArrayList(siteQwId));
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectOne(QUERY_PACKAGE + ".DflowMapper.getAvailability", params);
		}
		
		return result;
	}
	
}
