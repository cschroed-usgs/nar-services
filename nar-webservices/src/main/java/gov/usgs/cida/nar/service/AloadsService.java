package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.AloadsDao;
import gov.usgs.cida.nar.mybatis.model.Aloads;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AloadsService {

	public AloadsService() {
		
	}
	
	public List<Aloads> request(String siteQwId, String constit, List<String> modtypeExcludes,
			String startDate, String endDate) {
		AloadsDao dao = new AloadsDao();
		DateTimeFormatter dateTimeParser = ISODateTimeFormat.dateTimeParser();
		Integer startWy = null;
		Integer endWy = null;
		if (startDate != null) {
			startWy = dateTimeParser.parseDateTime(startDate).plusMonths(3).getYear();
		}
		if (endDate != null) {
			endWy = dateTimeParser.parseDateTime(endDate).plusMonths(3).getYear();
		}
		return dao.getAloads(siteQwId, constit, modtypeExcludes, startWy, endWy);
	}
}
