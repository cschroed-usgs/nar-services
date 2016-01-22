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
public class DflowService {

	private DflowDao dao;
	public DflowService() {
		this(new DflowDao());
	}
	
	public DflowService(DflowDao dao) {
		this.dao = dao;
	}
	
	public List<Dflow> request(String siteQwId, String startDateStr, String endDateStr) {
		Date startDate = DateUtil.getSqlDate(startDateStr);
		Date endDate = DateUtil.getSqlDate(endDateStr);
		return dao.getDflow(siteQwId, startDate, endDate);
	}
}
