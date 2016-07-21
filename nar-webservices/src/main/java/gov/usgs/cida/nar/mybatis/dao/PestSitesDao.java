package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.mybatis.model.Aflow;
import gov.usgs.cida.nar.mybatis.model.PestSites;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gov.usgs.cida.nar.mybatis.dao.BaseDao.QUERY_PACKAGE;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.SITE_QW;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class PestSitesDao extends BaseDao {

	private static final Logger log = LoggerFactory.getLogger(PestSitesDao.class);
	
	public List<PestSites> getPestSites(List<String> siteQwId) {
		List<PestSites> result = null;
		
		Map<String, Object> params = new HashMap<>(3);
		params.put(SITE_QW, siteQwId);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".PestSitesMapper.getPestSites", params);
		}
		
		return result;
	}

	public List<String> getMostDetectedPesticides(String siteQwId) {
		List<String> result = null;
		
		Map<String, Object> params = new HashMap<>(3);
		params.put(SITE_QW, siteQwId);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".PestSitesMapper.getMostDetectedPesticides", params);
		}
		
		return result;
	}
	
}
