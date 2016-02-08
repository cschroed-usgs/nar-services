package gov.usgs.cida.nar.mybatis.model;

import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import org.force66.beantester.BeanTester;
import org.junit.Test;
import static org.junit.Assert.*;

public class WaterYearAndMonthIntervalTest {
	
	public WaterYearAndMonthIntervalTest (){}
	
	@Test
	public void testBeanFunctionality() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(TimeSeriesAvailability.class);
	}
	
	@Test
	public void testIsInitialized() {
		WaterYearAndMonthInterval instance = new WaterYearAndMonthInterval(
			null,
			null,
			null,
			null
		);
		assertFalse("object constructed with all nulls is not marked initialized", instance.isInitialized());
		
		instance = new WaterYearAndMonthInterval(
			1990,
			1,
			1999,
			1
		);
		assertTrue("object constructed with values for all fields is marked initialized", instance.isInitialized());
	}
}
