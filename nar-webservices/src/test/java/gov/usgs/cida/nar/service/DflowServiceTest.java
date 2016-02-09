package gov.usgs.cida.nar.service;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.dao.DflowDao;
import gov.usgs.cida.nar.mybatis.model.Dflow;
import gov.usgs.cida.nar.mybatis.model.DateInterval;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class DflowServiceTest {
	
	private final DflowDao mockedDao;
	private final List<Dflow> expectedDflow;
	private DflowService instance;
	private static final String SITE_QW_ID = "100";
	private static final int START_YEAR = 1990;
	//Java.util.Date's years set 0 := 1900
	private static final int JAVA_UTIL_DATE_OFFSET = 1900;
	private static final Date START_DATE = new Date(START_YEAR - JAVA_UTIL_DATE_OFFSET, 0, 1);
	private static final String STRING_START_DATE = START_YEAR + "-01-01";
	private static final int END_YEAR = 2000;
	private static final Date END_DATE = new Date(END_YEAR - JAVA_UTIL_DATE_OFFSET, 0, 1);
	private static final String STRING_END_DATE = END_YEAR + "-01-01";
	
	public DflowServiceTest() {
		mockedDao = mock(DflowDao.class);
		expectedDflow = new ArrayList<>();
		for (int i=0; i<=10; i++) {
			Dflow dflow = new Dflow();
			dflow.setWy(START_YEAR + i);
			dflow.setDate(new Date(START_YEAR + i , 0, 1));
			dflow.setSiteAbb("foo");
			dflow.setSiteQwId(SITE_QW_ID);
			dflow.setSiteFlowId("100");
			dflow.setFlow(7.0 * i);
			expectedDflow.add(dflow);
		}
	}
	@Before
	public void setup(){
		instance = new DflowService(mockedDao);
	}
	/**
	 * Test of request method, of class DflowService.
	 */
	@Test
	public void testRequest() {
		when(mockedDao.getDflow(Lists.newArrayList(SITE_QW_ID), START_DATE, END_DATE)).thenReturn(expectedDflow);
		List<Dflow> expResult = expectedDflow;
		List<Dflow> result = instance.request(Lists.newArrayList(SITE_QW_ID), STRING_START_DATE, STRING_END_DATE);
		assertEquals(expResult, result);
		verify(mockedDao).getDflow(Lists.newArrayList(SITE_QW_ID), START_DATE, END_DATE);
	}
	
	/**
	 * Test of getAvailability method, of class AflowService
	 */
	@Test
	public void testGetAvailability() {
		DateInterval daoInterval = new DateInterval(START_DATE, END_DATE);
		
		TimeSeriesAvailability expectedAvailability = new TimeSeriesAvailability(
			TimeSeriesCategory.FLOW, 
			TimeStepDensity.DAILY, 
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
