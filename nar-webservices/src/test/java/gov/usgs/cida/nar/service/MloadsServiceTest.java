package gov.usgs.cida.nar.service;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.mybatis.dao.MloadsDao;
import gov.usgs.cida.nar.mybatis.model.Mloads;
import gov.usgs.cida.nar.mybatis.model.WaterYearIntervalWithConstituent;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MloadsServiceTest {
	private final MloadsDao mockedDao;
	private final List<Mloads> expectedMloads;
	private MloadsService instance;
	private static final String SITE_QW_ID = "100";
	private static final String SITE_FLOW_ID = "100";
	private static final int START_YEAR = 1990;
	private static final int END_YEAR = 2000;
	private static final String INCLUDED_MODTYPE = "REG";
	private static final String EXCLUDED_MODTYPE = "COMP";
	private static final String CONSTIT_1 = "NO2_NO3";
	private static final String CONSTIT_2 = "SSC";
	private static final String SITE_ABB = "foo";
	
	public MloadsServiceTest() {
		mockedDao = mock(MloadsDao.class);
		expectedMloads = new ArrayList<>();
		
		for (int i=0; i<=10; i++) {
			Mloads mloads = new Mloads();
			mloads.setWy(START_YEAR + i);
			mloads.setSiteAbb(SITE_ABB);
			mloads.setSiteQwId(SITE_QW_ID);
			mloads.setSiteFlowId(SITE_FLOW_ID);
			mloads.setTonsL95(1.0 * i);
			mloads.setTons(2.0 * i);
			mloads.setTonsU95(3.0 * i);
			mloads.setConstit( i % 2 == 0 ? CONSTIT_1 : CONSTIT_2);
			mloads.setModtype(INCLUDED_MODTYPE);
			
			expectedMloads.add(mloads);
		}
	}
	
	@Before
	public void setUp() {
		instance = new MloadsService(mockedDao);
	}

	@Test
	public void testRequest() {
		List<String> siteQwIds = Lists.newArrayList(SITE_QW_ID);
		List<String> constits = Lists.newArrayList(CONSTIT_1, CONSTIT_2);
		List<String> excludedModtypes = Lists.newArrayList(EXCLUDED_MODTYPE);
		
		when(mockedDao.getMloads(siteQwIds, constits, excludedModtypes, START_YEAR, END_YEAR)).thenReturn(expectedMloads);
		List<Mloads> expResult = expectedMloads;
		List<Mloads> result = instance.request(siteQwIds, constits, excludedModtypes, "" + START_YEAR, "" + END_YEAR);
		assertEquals(expResult, result);
		verify(mockedDao).getMloads(siteQwIds, constits, excludedModtypes, START_YEAR, END_YEAR);
	}
	
	/**
	 * Test of getAvailability method, of class MloadsService.
	 */
	@Test
	public void testGetAvailability() {
		
		TimeSeriesAvailability expectedAvailabilityForConstit1 = new TimeSeriesAvailability(
			instance.getTimeSeriesCategory(),
			instance.getTimeStepDensity(),
			new LocalDateTime(START_YEAR, MloadsService.MAY, 1, 0, 0),
			new LocalDateTime(END_YEAR, MloadsService.MAY, 1, 0, 0),
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
			new LocalDateTime(START_YEAR+1, MloadsService.MAY, 1, 0, 0),
			new LocalDateTime(END_YEAR-1, MloadsService.MAY, 1, 0, 0),
			CONSTIT_2
		);
		
		WaterYearIntervalWithConstituent intervalForConstit1 = new WaterYearIntervalWithConstituent(START_YEAR, END_YEAR, CONSTIT_1);
		WaterYearIntervalWithConstituent intervalForConstit2 = new WaterYearIntervalWithConstituent(START_YEAR+1, END_YEAR-1, CONSTIT_2);
		
		List<String> excludedModtypes = Lists.newArrayList(EXCLUDED_MODTYPE);
		
		when(mockedDao.getAvailability(SITE_QW_ID, excludedModtypes)).thenReturn(Lists.newArrayList(intervalForConstit1, intervalForConstit2));
		List<TimeSeriesAvailability> result = instance.getAvailability(SITE_QW_ID, excludedModtypes);
		TimeSeriesAvailability resultTsaForConstit1 = result.get(0);
		TimeSeriesAvailability resultTsaForConstit2 = result.get(1);
		assertEquals(expectedAvailabilityForConstit1, resultTsaForConstit1);
		assertEquals(expectedAvailabilityForConstit2, resultTsaForConstit2);
	}
	
}
