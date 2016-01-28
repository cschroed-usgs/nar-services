package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.DflowDao;
import gov.usgs.cida.nar.mybatis.model.Dflow;
import gov.usgs.cida.nar.util.DateUtil;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DflowService implements NARService {

	private DflowDao dao;
	
	private List<String> siteQwId;
	private String startDate;
	private String endDate;
	
	public DflowService() {
		this(new DflowDao());
	}
	
	public DflowService(DflowDao dao) {
		this.dao = dao;
		this.siteQwId = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	public List<Dflow> request(List<String> siteQwId, String startDateStr, String endDateStr) {
		Date startDateSql = DateUtil.getSqlDate(startDateStr);
		Date endDateSql = DateUtil.getSqlDate(endDateStr);
		return dao.getDflow(siteQwId, startDateSql, endDateSql);
	}
	
	@Override
	public List<Dflow> request() {
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
