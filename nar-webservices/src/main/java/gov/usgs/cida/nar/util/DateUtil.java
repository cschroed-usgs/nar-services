package gov.usgs.cida.nar.util;

import java.sql.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Pulling out common date functions used across services
 * 
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DateUtil {
	
	private static final DateTimeFormatter isoDateTimeParser = ISODateTimeFormat.dateTimeParser().withOffsetParsed();
	private static final DateTimeFormatter nonStandardDateTimePaser = DateTimeFormat.forPattern("MM/dd/yyyy");
	
	/**
	 * Get water year from date string
	 * @param dateStr date as ISO8601 date/time string
	 * @return water year, null if dateStr is null
	 */
	public static Integer getWaterYear(String dateStr) {
		Integer wy = null;
		if (null != dateStr) {
			wy = multiParse(dateStr).plusMonths(3).getYear();
		}
		return wy;
	}
	
	/**
	 * Get old java date from a string.
	 * Uses joda to parse, this is mostly to pass to mybatis
	 * @param dateStr date as ISO8601 date/time string
	 * @return java.util.Date or null if dateStr is null
	 */
	public static Date getSqlDate(String dateStr) {
		Date date = null;
		if (null != dateStr) {
				date = new Date(multiParse(dateStr).getMillis());
		}
		return date;
	}
		
	private static DateTime multiParse(String dateStr) {
		DateTime dt = null;
		try {
			dt = isoDateTimeParser.parseDateTime(dateStr);
		} catch (IllegalArgumentException ex) {
			dt = nonStandardDateTimePaser.parseDateTime(dateStr);
		}
		return dt;
	}
}
