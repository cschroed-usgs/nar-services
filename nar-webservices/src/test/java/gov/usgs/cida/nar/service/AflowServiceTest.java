package gov.usgs.cida.nar.service;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.dao.AflowDao;
import gov.usgs.cida.nar.mybatis.model.Aflow;
import gov.usgs.cida.nar.mybatis.model.WaterYearInterval;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author jiwalker
 */
public class AflowServiceTest {
	
	private final AflowDao mockedDao;
	private final List<Aflow> expectedAflow;
	private AflowService instance;
	private static final String SITE_QW_ID = "100";
	private static final int START_YEAR = 1990;
	private static final String STRING_START_DATE = START_YEAR + "-01-01";
	private static final int END_YEAR = 2000;
	private static final String STRING_END_DATE = END_YEAR + "-01-01";
	
	public AflowServiceTest() {
		mockedDao = mock(AflowDao.class);
		expectedAflow = new ArrayList<>();
		for (int i=0; i<=10; i++) {
			Aflow aflow = new Aflow();
			aflow.setWy(START_YEAR + i);
			aflow.setSiteAbb("foo");
			aflow.setSiteQwId(SITE_QW_ID);
			aflow.setSiteFlowId("100");
			aflow.setFlow(7.0 * i);
			expectedAflow.add(aflow);
		}
	}
	@Before
	public void setup(){
		instance = new AflowService(mockedDao);
	}
	/**
	 * Test of request method, of class AflowService.
	 */
	@Test
	public void testRequest() {
		when(mockedDao.getAflow(Lists.newArrayList(SITE_QW_ID), START_YEAR, END_YEAR)).thenReturn(expectedAflow);
		List<Aflow> expResult = expectedAflow;
		List<Aflow> result = instance.request(Lists.newArrayList(SITE_QW_ID), STRING_START_DATE, STRING_END_DATE);
		assertEquals(expResult, result);
		verify(mockedDao).getAflow(Lists.newArrayList(SITE_QW_ID), START_YEAR, END_YEAR);
	}
	
	/**
	 * Test of getAvailability method, of class AflowService
	 */
	@Test
	public void testGetAvailability() {
		WaterYearInterval daoInterval = new WaterYearInterval(START_YEAR, END_YEAR);
		
		TimeSeriesAvailability expectedAvailability = new TimeSeriesAvailability(
			TimeSeriesCategory.FLOW, 
			TimeStepDensity.ANNUAL, 
			new LocalDateTime(START_YEAR, 1, 1, 0, 0),
			new LocalDateTime(END_YEAR, 1, 1, 0, 0),
			null
		);
		
		instance.setSiteQwId(Lists.newArrayList(SITE_QW_ID));
		when(mockedDao.getAvailability(SITE_QW_ID)).thenReturn(daoInterval);
		List<TimeSeriesAvailability> result = instance.getAvailability();
		assertEquals(expectedAvailability, result.get(0));
	}
}
