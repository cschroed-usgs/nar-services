package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.model.Dflow;
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
public class DflowDao extends BaseDao {

	private static final Logger log = LoggerFactory.getLogger(DflowDao.class);
	
	public List<Dflow> getDflow(String siteQwId, Date startDate, Date endDate) {
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
	
}
