package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.MloadsDao;
import gov.usgs.cida.nar.mybatis.model.Mloads;
import gov.usgs.cida.nar.util.DateUtil;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class MloadsService {

	private MloadsDao dao;
	
	public MloadsService() {
		this(new MloadsDao());
	}
	
	public MloadsService(MloadsDao dao) {
		this.dao = dao;
	}
	
	public List<Mloads> request(String siteQwId, String constit, List<String> modtypeExcludes,
			String startDate, String endDate) {
		
		Integer startWy = DateUtil.getWaterYear(startDate);
		Integer endWy = DateUtil.getWaterYear(endDate);
		return dao.getMloads(siteQwId, constit, modtypeExcludes, startWy, endWy);
	}
}
