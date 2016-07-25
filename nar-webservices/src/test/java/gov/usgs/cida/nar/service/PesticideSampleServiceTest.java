package gov.usgs.cida.nar.service;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.Herbicide;
import gov.usgs.cida.nar.domain.NonHerbicide;
import gov.usgs.cida.nar.domain.Pesticide;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.mybatis.dao.PesticideSampleDao;
import gov.usgs.cida.nar.mybatis.model.DateIntervalWithConstituent;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class PesticideSampleServiceTest {

	PesticideSampleService instance;
	final String MOCK_SITE_ID = "42";
	final String MOCK_HERBICIDE_NAME = "Someherbicideozine";
	final String MOCK_NON_HERBICIDE_NAME = "Somefungicideorinsectocideozine";
	List<Pesticide> mostCommonPesticides;
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
		mostCommonPesticides = new ArrayList<>();
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
		Herbicide mockHerbicide = new Herbicide(MOCK_HERBICIDE_NAME);
		NonHerbicide mockNonHerbicide = new NonHerbicide(MOCK_NON_HERBICIDE_NAME);
		mostCommonPesticides.add(mockHerbicide);
		mostCommonPesticides.add(mockNonHerbicide);
		
		when(sitesService.getMostDetectedPesticides(MOCK_SITE_ID)).thenReturn(mostCommonPesticides);
		instance.setSiteQwId(Lists.newArrayList(MOCK_SITE_ID));
		
		LocalDateTime herbStart = new LocalDateTime(1990, 1, 1, 12, 0);
		LocalDateTime herbEnd = new LocalDateTime(1991, 1, 1, 12, 0);
		
		LocalDateTime nonHerbStart = new LocalDateTime(2000, 1, 1, 12, 0);
		LocalDateTime nonHerbEnd = new LocalDateTime(2001, 1, 1, 12, 0);
		
		DateIntervalWithConstituent herbInterval = new DateIntervalWithConstituent(herbStart.toDate(), herbEnd.toDate(), MOCK_HERBICIDE_NAME);
		when(sampleDao.getAvailability(MOCK_SITE_ID, MOCK_HERBICIDE_NAME)).thenReturn(Lists.newArrayList(herbInterval));
		
		DateIntervalWithConstituent nonHerbInterval = new DateIntervalWithConstituent(nonHerbStart.toDate(), nonHerbEnd.toDate(), MOCK_NON_HERBICIDE_NAME);
		when(sampleDao.getAvailability(MOCK_SITE_ID, MOCK_NON_HERBICIDE_NAME)).thenReturn(Lists.newArrayList(nonHerbInterval));
		
		List<TimeSeriesAvailability> actualAvailability = instance.getAvailability();
		
		TimeSeriesAvailability nonHerbAvailability = new TimeSeriesAvailability(
			instance.getTimeSeriesCategory(),
			instance.getTimeStepDensity(),
			nonHerbStart,
			nonHerbEnd,
			mockNonHerbicide.getFullName()
		);
		TimeSeriesAvailability herbAvailability = new TimeSeriesAvailability(
			instance.getTimeSeriesCategory(),
			instance.getTimeStepDensity(),
			herbStart,
			herbEnd,
			mockHerbicide.getFullName()
		);
		
		assertTrue("results must contain an herbicide with the correct dates", actualAvailability.contains(herbAvailability));
		assertTrue("results must contain a non-herbicide with the correct dates", actualAvailability.contains(nonHerbAvailability));
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
