package gov.usgs.cida.nar.mybatis.model;

import java.sql.Date;
import org.force66.beantester.BeanTester;
import org.junit.Test;
import static org.junit.Assert.*;

public class DateIntervalTest {
	
	public DateIntervalTest (){}
	
	@Test
	public void testBeanFunctionality() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(DateIntervalTest.class);
	}
	
	@Test
	public void testIsInitialized() {
		DateInterval instance = new DateInterval(
			null,
			null
		);
		assertFalse("object constructed with all nulls is not marked initialized", DateInterval.isInitialized(instance));
		
		instance = new DateInterval(
			new Date(1990, 01, 01),
			new Date(2000, 01, 01)
		);
		assertTrue("object constructed with values for all fields is marked initialized", DateInterval.isInitialized(instance));
	}
}
