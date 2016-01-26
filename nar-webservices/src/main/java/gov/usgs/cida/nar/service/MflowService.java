package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.MflowDao;
import gov.usgs.cida.nar.mybatis.model.Mflow;
import gov.usgs.cida.nar.util.DateUtil;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class MflowService implements NARService {

	private MflowDao dao;
	
	private List<String> siteQwId;
	private String startDate;
	private String endDate;
	
	public MflowService() {
		this(new MflowDao());
	}
	
	public MflowService(MflowDao dao) {
		this.dao = dao;
		this.siteQwId = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	public List<Mflow> request(List<String> siteQwId, String startDate, String endDate) {
		Integer startWy = DateUtil.getWaterYear(startDate);
		Integer endWy = DateUtil.getWaterYear(endDate);
		return dao.getMflow(siteQwId, startWy, endWy);
	}

	@Override
	public List request() {
		return request(siteQwId, startDate, endDate);
	}

	public void setSiteQwId(List<String> siteQwId) {
		this.siteQwId = siteQwId;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
