package gov.usgs.cida.nar.mybatis.model;

import org.force66.beantester.BeanTester;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PesticideSampleTest {

	public PesticideSampleTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testBean() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(PesticideSample.class);
	}

}
