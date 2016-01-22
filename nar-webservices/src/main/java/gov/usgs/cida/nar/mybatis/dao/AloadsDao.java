package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.model.Aloads;
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
public class AloadsDao extends BaseDao {

	private static final Logger log = LoggerFactory.getLogger(AloadsDao.class);
	
	public List<Aloads> getAloads(String siteQwId, String constit, List<String> modtypeExcludes,
			Integer startWy, Integer endWy) {
		List<Aloads> result = null;
		
		Map<String, Object> params = new HashMap<>(11);
		params.put("siteQwId", siteQwId);
		params.put("constit", constit);
		params.put("modtypeExcludes", modtypeExcludes);
		params.put("startWy", startWy);
		params.put("endWy", endWy);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".AloadsMapper.getAloads", params);
		}
		
		return result;
	}
	
}
