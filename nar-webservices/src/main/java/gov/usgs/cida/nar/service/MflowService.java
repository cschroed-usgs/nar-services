package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.MflowDao;
import gov.usgs.cida.nar.mybatis.model.Mflow;
import java.util.List;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class MflowService {

	public MflowService() {
		
	}
	
	public List<Mflow> request(String siteQwId, String startDate, String endDate) {
		MflowDao dao = new MflowDao();
		DateTimeFormatter dateTimeParser = ISODateTimeFormat.dateTimeParser();
		Integer startWy = null;
		Integer endWy = null;
		if (startDate != null) {
			startWy = dateTimeParser.parseDateTime(startDate).plusMonths(3).getYear();
		}
		if (endDate != null) {
			endWy = dateTimeParser.parseDateTime(endDate).plusMonths(3).getYear();
		}
		return dao.getMflow(siteQwId, startWy, endWy);
	}
}
