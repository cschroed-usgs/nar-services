package gov.usgs.cida.nar.util;

import java.sql.Date;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author jiwalker
 */
public class DateUtilTest {

	@Test
	public void testGetWaterYearShort() {
		String dateStr = "2000-01-01";
		int result = DateUtil.getWaterYear(dateStr);
		assertEquals(2000, result);
	}
	
	@Test
	public void testGetWaterYearShortEndOfYear() {
		String dateStr = "1922-11-01";
		int result = DateUtil.getWaterYear(dateStr);
		assertEquals(1923, result);
	}
	
	@Test
	public void testGetWaterYearFull() {
		String dateStr = "1992-03-04T01:02:04.000Z";
		int result = DateUtil.getWaterYear(dateStr);
		assertEquals(1992, result);
	}
	
	@Test
	public void testGetWaterYearEnd() {
		String dateStr = "2009-09-30T23:59:59.500Z";
		int result = DateUtil.getWaterYear(dateStr);
		assertEquals(2009, result);
	}
	
	@Test
	public void testGetWaterYearStart() {
		String dateStr = "1995-10-01T00:00:00.000Z";
		int result = DateUtil.getWaterYear(dateStr);
		assertEquals(1996, result);
	}

	@Test
	public void testGetSqlDateRoundTrip() {
		String dateStr = "1971-03-21T11:19:00.000Z";
		Date sql = DateUtil.getSqlDate(dateStr);
		DateTime result = new DateTime(sql.getTime());
		assertEquals(1971, result.getYear());
		assertEquals(3, result.getMonthOfYear());
		assertEquals(21, result.getDayOfMonth());
	}
	
	@Test
	public void testGetSqlDateStartOfDay() {
		String dateStr = "1971-03-21T00:00:00.000Z";
		Date sql = DateUtil.getSqlDate(dateStr);
		DateTime result = new DateTime(sql.getTime(), DateTimeZone.UTC);
		assertEquals(1971, result.getYear());
		assertEquals(3, result.getMonthOfYear());
		assertEquals(21, result.getDayOfMonth());
	}
	
	@Test
	public void testGetSqlDateEndOfDay() {
		String dateStr = "1971-03-21T23:59:59.000Z";
		Date sql = DateUtil.getSqlDate(dateStr);
		DateTime result = new DateTime(sql.getTime());
		assertEquals(1971, result.getYear());
		assertEquals(3, result.getMonthOfYear());
		assertEquals(21, result.getDayOfMonth());
	}
	
}
