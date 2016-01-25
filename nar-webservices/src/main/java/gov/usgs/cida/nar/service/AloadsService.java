package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.AloadsDao;
import gov.usgs.cida.nar.mybatis.model.Aloads;
import gov.usgs.cida.nar.util.DateUtil;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AloadsService {

	private AloadsDao dao;
	
	public AloadsService() {
		this(new AloadsDao());
	}
	
	public AloadsService(AloadsDao dao) {
		this.dao = dao;
	}
	
	public List<Aloads> request(String siteQwId, String constit, List<String> modtypeExcludes,
			String startDate, String endDate) {
		
		Integer startWy = DateUtil.getWaterYear(startDate);
		Integer endWy = DateUtil.getWaterYear(endDate);
		return dao.getAloads(siteQwId, constit, modtypeExcludes, startWy, endWy);
	}
}
