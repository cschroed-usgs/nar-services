package gov.usgs.cida.nar.service;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.mybatis.dao.AloadsDao;
import gov.usgs.cida.nar.mybatis.dao.DiscqwDao;
import gov.usgs.cida.nar.mybatis.model.Aloads;
import gov.usgs.cida.nar.mybatis.model.DateIntervalWithConstituent;
import gov.usgs.cida.nar.mybatis.model.Discqw;
import gov.usgs.cida.nar.mybatis.model.WaterYearIntervalWithConstituent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DiscqwServiceTest {
	private final DiscqwDao mockedDao;
	private final List<Discqw> expectedDiscqws;
	private DiscqwService instance;
	private static final String SITE_QW_ID = "100";
	private static final String SITE_FLOW_ID = "100";
	private static final int JAVA_UTIL_YEAR_OFFSET = 1900;
	private static final int START_YEAR = 1990;
	private static final Date START_DATE = new Date(START_YEAR-JAVA_UTIL_YEAR_OFFSET, 0, 1);
	private static final int END_YEAR = 2000;
	private static final Date END_DATE = new Date(END_YEAR-JAVA_UTIL_YEAR_OFFSET, 0, 1);
	private static final String INCLUDED_MODTYPE = "REG";
	private static final String EXCLUDED_MODTYPE = "COMP";
	private static final String CONSTIT_1 = "NO2_NO3";
	private static final String CONSTIT_2 = "SSC";
	private static final String SITE_ABB = "foo";
	
	public DiscqwServiceTest() {
		mockedDao = mock(DiscqwDao.class);
		expectedDiscqws = new ArrayList<>();
		
		for (int i=0; i<=10; i++) {
			Discqw discqw = new Discqw();
			discqw.setWy(START_YEAR + i);
			discqw.setDate(new Date(START_YEAR - JAVA_UTIL_YEAR_OFFSET, 0, 1));
			discqw.setSiteAbb(SITE_ABB);
			discqw.setSiteQwId(SITE_QW_ID);
			discqw.setSiteFlowId(SITE_FLOW_ID);
			discqw.setConstit( i % 2 == 0 ? CONSTIT_1 : CONSTIT_2);
			
			discqw.setConcentration(i * 7.0);
			discqw.setRemark(i % 2 == 0 ? ">" : "");
			
			expectedDiscqws.add(discqw);
		}
	}
	
	@Before
	public void setUp() {
		instance = new DiscqwService(mockedDao);
	}

	@Test
	public void testRequest() {
		List<String> siteQwIds = Lists.newArrayList(SITE_QW_ID);
		List<String> constits = Lists.newArrayList(CONSTIT_1, CONSTIT_2);
		List<String> excludedModtypes = Lists.newArrayList(EXCLUDED_MODTYPE);
		
		when(mockedDao.getDiscqw(siteQwIds, constits, START_DATE, END_DATE)).thenReturn(expectedDiscqws);
		List<Discqw> expResult = expectedDiscqws;
		List<Discqw> result = instance.request(siteQwIds, constits, "" + START_YEAR, "" + END_YEAR);
		assertEquals(expResult, result);
		verify(mockedDao).getDiscqw(siteQwIds, constits, START_DATE, END_DATE);
	}
	
	/**
	 * Test of getAvailability method, of class AloadsService.
	 */
	@Test
	public void testGetAvailability() {
		
		TimeSeriesAvailability expectedAvailabilityForConstit1 = new TimeSeriesAvailability(
			instance.getTimeSeriesCategory(),
			instance.getTimeStepDensity(),
			new LocalDateTime(START_YEAR, 1, 1, 0, 0),
			new LocalDateTime(END_YEAR, 1, 1, 0, 0),
			CONSTIT_1
		);
		
		/* since constit 2, only shows up on odd modtypes, and the 
		 * mock data starts and ends on even, the availability for
		 * constit 2 will be one year more than the test data start
		 * and one year less than the test data end
		 */
		TimeSeriesAvailability expectedAvailabilityForConstit2 = new TimeSeriesAvailability(
			instance.getTimeSeriesCategory(),
			instance.getTimeStepDensity(),
			new LocalDateTime(START_YEAR+1, 1, 1, 0, 0),
			new LocalDateTime(END_YEAR-1, 1, 1, 0, 0),
			CONSTIT_2
		);
		
		DateIntervalWithConstituent intervalForConstit1 = new DateIntervalWithConstituent(START_DATE, END_DATE, CONSTIT_1);
		DateIntervalWithConstituent intervalForConstit2 = new DateIntervalWithConstituent(new DateTime(START_DATE).plusYears(1).toDate(), new DateTime(END_DATE).minusYears(1).toDate(), CONSTIT_2);
		
		when(mockedDao.getAvailability(SITE_QW_ID, null)).thenReturn(Lists.newArrayList(intervalForConstit1, intervalForConstit2));
		List<TimeSeriesAvailability> result = instance.getAvailability(SITE_QW_ID, null);
		TimeSeriesAvailability resultTsaForConstit1 = result.get(0);
		TimeSeriesAvailability resultTsaForConstit2 = result.get(1);
		assertEquals(expectedAvailabilityForConstit1, resultTsaForConstit1);
		assertEquals(expectedAvailabilityForConstit2, resultTsaForConstit2);
	}
	
}
