package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.MloadsDao;
import gov.usgs.cida.nar.mybatis.model.Mloads;
import java.util.List;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class MloadsService {

	public MloadsService() {
		
	}
	
	public List<Mloads> request(String siteQwId, String constit, List<String> modtypeExcludes,
			String startDate, String endDate) {
		MloadsDao dao = new MloadsDao();
		DateTimeFormatter dateTimeParser = ISODateTimeFormat.dateTimeParser();
		Integer startWy = null;
		Integer endWy = null;
		if (startDate != null) {
			startWy = dateTimeParser.parseDateTime(startDate).plusMonths(3).getYear();
		}
		if (endDate != null) {
			endWy = dateTimeParser.parseDateTime(endDate).plusMonths(3).getYear();
		}
		return dao.getMloads(siteQwId, constit, modtypeExcludes, startWy, endWy);
	}
}
