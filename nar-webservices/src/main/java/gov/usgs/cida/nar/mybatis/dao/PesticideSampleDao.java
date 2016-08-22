package gov.usgs.cida.nar.mybatis.dao;

import com.google.common.base.Strings;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.CONSTIT;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.END_DATE;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.QUERY_PACKAGE;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.SITE_QW;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.START_DATE;
import gov.usgs.cida.nar.mybatis.model.DateIntervalWithConstituent;
import gov.usgs.cida.nar.mybatis.model.PesticideSample;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;


public class PesticideSampleDao extends BaseDao {

	public List<DateIntervalWithConstituent> getAvailability(String siteQwId, String constituent) {
		List<DateIntervalWithConstituent> availability = new ArrayList<>();
		
		Map<String, Object> params = new HashMap<>(11);
		//Must put all params in a list to re-use retrieval queries
		params.put(SITE_QW, siteQwId);
		
		if(!Strings.isNullOrEmpty(constituent)){
			params.put(CONSTIT, constituent);
		}
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			availability = session.selectList(QUERY_PACKAGE + ".PesticideSampleMapper.getAvailability", params);
		}
		for(DateIntervalWithConstituent interval : availability) {
			interval.setConstit(constituent);
		}
		return availability;
	}


	public List<PesticideSample> getPesticideSamples(List<String> siteQwId, List<String> constit, Date startDate, Date endDate) {
		List<PesticideSample> result = null;
		
		Map<String, Object> params = new HashMap<>(11);
		params.put(SITE_QW, siteQwId);
		params.put(CONSTIT, constit);
		params.put(START_DATE, startDate);
		params.put(END_DATE, endDate);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".PesticideSampleMapper.getPesticideSamples", params);
		}
		
		return result;
	}

}
