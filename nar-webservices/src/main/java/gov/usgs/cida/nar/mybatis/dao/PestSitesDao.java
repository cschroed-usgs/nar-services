package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.domain.Pesticide;
import gov.usgs.cida.nar.domain.PesticideBuilder;
import gov.usgs.cida.nar.mybatis.model.PestSites;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gov.usgs.cida.nar.mybatis.dao.BaseDao.QUERY_PACKAGE;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.SITE_QW;
import java.util.ArrayList;

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

	public List<Pesticide> getMostDetectedPesticides(String siteQwId) {
		List<PesticideBuilder> pestBuilders = null;
		List<Pesticide> pesticides = new ArrayList<>();
		Map<String, Object> params = new HashMap<>(3);
		params.put(SITE_QW, siteQwId);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			pestBuilders = session.selectList(QUERY_PACKAGE + ".PestSitesMapper.getMostDetectedPesticides", params);
		}
		if(null == pestBuilders || pestBuilders.isEmpty()){
			//return empty list
		} else if(2 == pestBuilders.size()) { 
			for(PesticideBuilder builder : pestBuilders){
				Pesticide pest = builder.build();
				pesticides.add(pest);
			}
		} else {
			throw new RuntimeException("Duplicate records exist for a single site. There should only be one record per site.");
		}
		return pesticides;
	}
	
}
