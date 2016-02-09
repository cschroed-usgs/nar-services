package gov.usgs.cida.nar.mybatis.model;

import org.force66.beantester.BeanTester;
import org.junit.Test;
import static org.junit.Assert.*;

public class WaterYearIntervalTest {
	
	public WaterYearIntervalTest (){}
	
	@Test
	public void testBeanFunctionality() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(WaterYearIntervalTest.class);
	}
	
	@Test
	public void testIsInitialized() {
		WaterYearInterval instance = new WaterYearInterval(
			null,
			null
		);
		assertFalse("object constructed with all nulls is not marked initialized", WaterYearInterval.isInitialized(instance));
		
		instance = new WaterYearInterval(
			1990,
			1999
		);
		assertTrue("object constructed with values for all fields is marked initialized", WaterYearInterval.isInitialized(instance));
	}
}
