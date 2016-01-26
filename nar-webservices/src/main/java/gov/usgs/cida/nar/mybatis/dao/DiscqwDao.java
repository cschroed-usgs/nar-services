package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.model.Discqw;
import java.util.Date;
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
	
}
