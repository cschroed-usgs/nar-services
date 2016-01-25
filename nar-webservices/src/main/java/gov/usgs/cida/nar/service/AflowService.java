package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.AflowDao;
import gov.usgs.cida.nar.mybatis.model.Aflow;
import gov.usgs.cida.nar.util.DateUtil;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AflowService {

	private AflowDao dao;
	
	public AflowService() {
		this(new AflowDao());
	}
	
	public AflowService(AflowDao dao) {
		this.dao = dao;
	}
	
	public List<Aflow> request(String siteQwId, String startDate, String endDate) {
		Integer startWy = DateUtil.getWaterYear(startDate);
		Integer endWy = DateUtil.getWaterYear(endDate);
		return dao.getAflow(siteQwId, startWy, endWy);
	}
}
