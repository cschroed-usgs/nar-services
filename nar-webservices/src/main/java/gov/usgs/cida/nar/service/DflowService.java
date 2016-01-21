package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.DflowDao;
import gov.usgs.cida.nar.mybatis.model.Dflow;
import java.util.Date;
import java.util.List;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DflowService {

	public DflowService() {
		
	}
	
	public List<Dflow> request(String siteQwId, String startDateStr, String endDateStr) {
		DflowDao dao = new DflowDao();
		DateTimeFormatter dateTimeParser = ISODateTimeFormat.dateTimeParser();
		Date startDate = null;
		Date endDate = null;
		if (startDateStr != null) {
			startDate = dateTimeParser.parseDateTime(startDateStr).toDate();
		}
		if (endDateStr != null) {
			endDate = dateTimeParser.parseDateTime(endDateStr).toDate();
		}
		return dao.getDflow(siteQwId, startDate, endDate);
	}
}
