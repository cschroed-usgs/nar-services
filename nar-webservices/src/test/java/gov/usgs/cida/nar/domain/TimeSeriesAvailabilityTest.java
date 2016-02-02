/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.nar.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.force66.beantester.BeanTester;
import org.joda.time.DateTime;

/**
 *
 * @author cschroed
 */
public class TimeSeriesAvailabilityTest {
	
	@Test
	public void standardBeanCompliance() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(TimeSeriesAvailability.class);
		beanTester.testBean(TimeSeriesAvailability.class, new Object[]{
			TimeSeriesCategory.LOAD,
			TimeStepDensity.ANNUAL,
			new DateTime(1990, 1, 1, 0, 0),
			new DateTime(2000, 1, 1, 0, 0),
			"NO2_NO3"
		});
	}
}
