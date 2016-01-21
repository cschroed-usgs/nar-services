package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.DiscqwDao;
import gov.usgs.cida.nar.mybatis.model.Discqw;
import java.util.Date;
import java.util.List;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DiscqwService {

	public DiscqwService() {
		
	}
	
	public List<Discqw> request(String siteQwId, String constit, String startDateStr, String endDateStr) {
		DiscqwDao dao = new DiscqwDao();
		DateTimeFormatter dateTimeParser = ISODateTimeFormat.dateTimeParser();
		Date startDate = null;
		Date endDate = null;
		if (startDateStr != null) {
			startDate = dateTimeParser.parseDateTime(startDateStr).toDate();
		}
		if (endDateStr != null) {
			endDate = dateTimeParser.parseDateTime(endDateStr).toDate();
		}
		return dao.getDiscqw(siteQwId, constit, startDate, endDate);
	}
}
