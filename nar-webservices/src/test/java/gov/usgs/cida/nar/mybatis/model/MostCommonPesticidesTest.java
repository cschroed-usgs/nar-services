package gov.usgs.cida.nar.mybatis.model;

import gov.usgs.cida.nar.domain.PesticideType;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MostCommonPesticidesTest {
	MostCommonPesticides instance;
	public MostCommonPesticidesTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
		instance = new MostCommonPesticides();
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of getTypesToNames method, of class MostCommonPesticides.
	 */
	@Test
	public void testGetTypesToNames() {
		String MOCK_HERBICIDE = "Someherbicide";
		String MOCK_NON_HERBICIDE = "Somenonherbicide";
		instance.setHerbicide(MOCK_HERBICIDE);
		instance.setNonHerbicide(MOCK_NON_HERBICIDE);
		
		Map<PesticideType, String> results = instance.getTypesToNames();
		
		assertEquals(results.get(PesticideType.HERBICIDE), MOCK_HERBICIDE);
		assertEquals(results.get(PesticideType.NON_HERBICIDE), MOCK_NON_HERBICIDE);
		
	}

}
