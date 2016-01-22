package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.MflowDao;
import gov.usgs.cida.nar.mybatis.model.Mflow;
import gov.usgs.cida.nar.util.DateUtil;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class MflowService {

	private MflowDao dao;
	
	public MflowService() {
		this(new MflowDao());
	}
	
	public MflowService(MflowDao dao) {
		this.dao = dao;
	}
	
	public List<Mflow> request(String siteQwId, String startDate, String endDate) {
		Integer startWy = DateUtil.getWaterYear(startDate);
		Integer endWy = DateUtil.getWaterYear(endDate);
		return dao.getMflow(siteQwId, startWy, endWy);
	}
}
