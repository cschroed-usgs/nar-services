package gov.usgs.cida.nar.mybatis.dao;

import static gov.usgs.cida.nar.mybatis.dao.BaseDao.CONSTIT;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.QUERY_PACKAGE;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.SITE_QW;
import gov.usgs.cida.nar.mybatis.model.DateIntervalWithConstituent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSession;


public class PesticideSampleDao extends BaseDao {

	public List<DateIntervalWithConstituent> getAvailability(String siteQwId, String constituent) {
		List<DateIntervalWithConstituent> availability = new ArrayList<>();
		
		Map<String, Object> params = new HashMap<>(11);
		//Must put all params in a list to re-use retrieval queries
		params.put(SITE_QW, Lists.newArrayList(siteQwId));
		
		if(null != constituent && 0 != constituent.length()){
			params.put(CONSTIT, Lists.newArrayList(constituent));
		}
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			availability = session.selectList(QUERY_PACKAGE + ".PesticideSampleMapper.getAvailability", params);
		}
		return availability;
	}

}
