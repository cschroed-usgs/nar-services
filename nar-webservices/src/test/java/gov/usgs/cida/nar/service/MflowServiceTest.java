package gov.usgs.cida.nar.service;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.dao.AflowDao;
import gov.usgs.cida.nar.mybatis.dao.MflowDao;
import gov.usgs.cida.nar.mybatis.model.Aflow;
import gov.usgs.cida.nar.mybatis.model.Mflow;
import gov.usgs.cida.nar.mybatis.model.WaterYearAndMonthInterval;
import gov.usgs.cida.nar.mybatis.model.WaterYearInterval;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MflowServiceTest {
	
	private final MflowDao mockedDao;
	private final List<Mflow> expectedMflow;
	private MflowService instance;
	private static final String SITE_QW_ID = "100";
	private static final String SITE_FLOW_ID = "100";
	private static final int START_YEAR = 1990;
	private static final int START_MONTH = 3;
	private static final String STRING_START_DATE = START_YEAR + "-01-01";
	private static final int END_YEAR = 2000;
	private static final int END_MONTH = 9;
	private static final String STRING_END_DATE = END_YEAR + "-01-01";
	
	private final Random rand = new Random(42L);
	private Double randNum(int min, int max) {
		int randomNum = this.rand.nextInt((max - min) + 1) + min;
		return 0.0 + randomNum;
	}
	
	public MflowServiceTest() {
		mockedDao = mock(MflowDao.class);
		expectedMflow = new ArrayList<>();
		int monthLoopStart, monthLoopEnd;
		for (int year=START_YEAR; year<=END_YEAR; year++) {
			if(year == START_YEAR){
				monthLoopStart = START_MONTH;
			} else {
				monthLoopStart = 1;
			}
			if(year == END_YEAR){
				monthLoopEnd = END_MONTH;
			} else {
				monthLoopEnd = 12;
			}
			
			for(int month = monthLoopStart; month <= monthLoopEnd; month++){
				Mflow mflow = new Mflow();
				mflow.setWy(year);
				mflow.setMonth(month);
				mflow.setSiteAbb("foo");
				mflow.setSiteQwId(SITE_QW_ID);
				mflow.setSiteFlowId(SITE_FLOW_ID);
				mflow.setFlow(randNum(1, 4242));
				expectedMflow.add(mflow);
			}
		}
	}
	@Before
	public void setup() {
		instance = new MflowService(mockedDao);
	}
	/**
	 * Test of request method, of class MflowService.
	 */
	@Test
	public void testRequest() {
		when(mockedDao.getMflow(Lists.newArrayList(SITE_QW_ID), START_YEAR, END_YEAR)).thenReturn(expectedMflow);
		List<Mflow> expResult = expectedMflow;
		List<Mflow> result = instance.request(Lists.newArrayList(SITE_QW_ID), STRING_START_DATE, STRING_END_DATE);
		assertEquals(expResult, result);
		verify(mockedDao).getMflow(Lists.newArrayList(SITE_QW_ID), START_YEAR, END_YEAR);
	}
	
	/**
	 * Test of getAvailability method, of class MflowService
	 */
	@Test
	public void testGetAvailability() {
		WaterYearAndMonthInterval daoInterval = new WaterYearAndMonthInterval(START_YEAR, START_MONTH, END_YEAR, END_MONTH);
		
		TimeSeriesAvailability expectedAvailability = new TimeSeriesAvailability(
			TimeSeriesCategory.FLOW, 
			TimeStepDensity.MONTHLY, 
			new LocalDateTime(START_YEAR, START_MONTH, 1, 0, 0),
			new LocalDateTime(END_YEAR, END_MONTH, 1, 0, 0),
			null
		);
		
		instance.setSiteQwId(Lists.newArrayList(SITE_QW_ID));
		when(mockedDao.getAvailability(SITE_QW_ID)).thenReturn(daoInterval);
		List<TimeSeriesAvailability> result = instance.getAvailability();
		assertEquals(expectedAvailability, result.get(0));
	}
}
