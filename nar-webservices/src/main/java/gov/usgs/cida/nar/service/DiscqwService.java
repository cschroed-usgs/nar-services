package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.DiscqwDao;
import gov.usgs.cida.nar.mybatis.model.Discqw;
import gov.usgs.cida.nar.util.DateUtil;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DiscqwService {

	DiscqwDao dao;
	
	public DiscqwService() {
		this(new DiscqwDao());
	}
	
	public DiscqwService(DiscqwDao dao) {
		this.dao = dao;
	}
	
	public List<Discqw> request(String siteQwId, String constit, String startDateStr, String endDateStr) {
		Date startDate = DateUtil.getSqlDate(startDateStr);
		Date endDate = DateUtil.getSqlDate(endDateStr);
		return dao.getDiscqw(siteQwId, constit, startDate, endDate);
	}
}
