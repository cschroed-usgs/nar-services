package gov.usgs.cida.nar.mybatis.model;

import org.force66.beantester.BeanTester;
import org.junit.Test;

public class PesticideSampleTest {

	@Test
	public void testBeanNess() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(PesticideSample.class);
	}

}