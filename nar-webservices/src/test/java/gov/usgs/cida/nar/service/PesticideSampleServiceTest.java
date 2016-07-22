package gov.usgs.cida.nar.service;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.PesticideType;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.mybatis.dao.PesticideSampleDao;
import gov.usgs.cida.nar.mybatis.model.DateIntervalWithConstituent;
import gov.usgs.cida.nar.mybatis.model.MostCommonPesticides;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PesticideSampleServiceTest {

	PesticideSampleService instance;
	final String MOCK_SITE_ID = "42";
	final String MOCK_HERBICIDE = "Someherbicideozine";
	final String MOCK_NON_HERBICIDE = "Somefungicideorinsectocideozine";
	MostCommonPesticides mcp;
	PesticideSampleDao sampleDao;
	PestSitesService sitesService;

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
		sampleDao = mock(PesticideSampleDao.class);
		mcp = new MostCommonPesticides();
		sitesService = mock(PestSitesService.class);
		instance = new PesticideSampleService(sampleDao, sitesService);
		
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of getAvailability method, of class PesticideSampleService.
	 */
	@Test
	public void testGetAvailability() {
		mcp.setHerbicide(MOCK_HERBICIDE);
		mcp.setNonHerbicide(MOCK_NON_HERBICIDE);
		
		when(sitesService.getMostDetectedPesticides(MOCK_SITE_ID)).thenReturn(mcp);
		instance.setSiteQwId(Lists.newArrayList(MOCK_SITE_ID));
		
		LocalDateTime herbStart = new LocalDateTime(1990, 1, 1, 12, 0);
		LocalDateTime herbEnd = new LocalDateTime(1991, 1, 1, 12, 0);
		
		LocalDateTime nonHerbStart = new LocalDateTime(2000, 1, 1, 12, 0);
		LocalDateTime nonHerbEnd = new LocalDateTime(2001, 1, 1, 12, 0);
		
		DateIntervalWithConstituent herbInterval = new DateIntervalWithConstituent(herbStart.toDate(), herbEnd.toDate(), MOCK_HERBICIDE);
		when(sampleDao.getAvailability(MOCK_SITE_ID, MOCK_HERBICIDE)).thenReturn(Lists.newArrayList(herbInterval));
		
		DateIntervalWithConstituent nonHerbInterval = new DateIntervalWithConstituent(nonHerbStart.toDate(), nonHerbEnd.toDate(), MOCK_NON_HERBICIDE);
		when(sampleDao.getAvailability(MOCK_SITE_ID, MOCK_NON_HERBICIDE)).thenReturn(Lists.newArrayList(nonHerbInterval));
		
		List<TimeSeriesAvailability> actualAvailability = instance.getAvailability();
		
		List<TimeSeriesAvailability> expectedAvailability = Lists.newArrayList(
			new TimeSeriesAvailability(
				instance.getTimeSeriesCategory(),
				instance.getTimeStepDensity(),
				nonHerbStart,
				nonHerbEnd,
				instance.getFullNameForPesticide(PesticideType.NON_HERBICIDE, MOCK_NON_HERBICIDE)
			),
			new TimeSeriesAvailability(
				instance.getTimeSeriesCategory(),
				instance.getTimeStepDensity(),
				herbStart,
				herbEnd,
				instance.getFullNameForPesticide(PesticideType.HERBICIDE, MOCK_HERBICIDE)
			)
		
		);
		assertEquals(expectedAvailability, actualAvailability);
	}

	/**
	 * Test of setSiteQwId method, of class PesticideSampleService.
	 */
	@Test
	public void testSetSiteQwId() {
		List<String> ids = Lists.newArrayList(MOCK_SITE_ID);
		instance.setSiteQwId(ids);
		assertEquals(ids, instance.siteQwId);
	}

	@Test
	public void testGetTimeSeriesCategory(){
		assertNotNull(instance.getTimeSeriesCategory());
	}
	
	@Test
	public void testGetTimeStepDensity(){
		assertNotNull(instance.getTimeStepDensity());
	}
}
