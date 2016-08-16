package gov.usgs.cida.nar.domain;

import org.force66.beantester.BeanTester;
import org.junit.Test;

public class PesticideTimeSeriesAvailabilityTest {

	@Test
	public void testBeanNess() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(PesticideTimeSeriesAvailability.class);
	}

}