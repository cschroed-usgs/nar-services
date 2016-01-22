package gov.usgs.cida.nar.util;

import java.sql.Date;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Pulling out common date functions used across services
 * 
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DateUtil {
	
	private static final DateTimeFormatter dateTimeParser = ISODateTimeFormat.dateTimeParser().withOffsetParsed();
	
	/**
	 * Get water year from date string
	 * @param dateStr date as ISO8601 date/time string
	 * @return water year, null if dateStr is null
	 */
	public static Integer getWaterYear(String dateStr) {
		Integer wy = null;
		if (null != dateStr) {
			wy = dateTimeParser.parseDateTime(dateStr).plusMonths(3).getYear();
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
			date = new Date(dateTimeParser.parseDateTime(dateStr).getMillis());
		}
		return date;
	}
}
