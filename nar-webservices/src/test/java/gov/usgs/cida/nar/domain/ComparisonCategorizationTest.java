package gov.usgs.cida.nar.domain;

import org.force66.beantester.BeanTester;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComparisonCategorizationTest {

	public ComparisonCategorizationTest() {
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
	public void testBeanNess() {
		BeanTester beanTester = new BeanTester();
		beanTester.addExcludedField("order");//these are tested below
		beanTester.testBean(ComparisonCategorization.class);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetOrderRejectsBadOrders(){
		ComparisonCategorization instance = new ComparisonCategorization();
		instance.setOrder(ComparisonCategorization.ORDER_INDEX_BASE - 1);
	}
	
	@Test
	public void testSetOrderAcceptsGoodOrders(){
		ComparisonCategorization instance = new ComparisonCategorization();
		instance.setOrder(ComparisonCategorization.ORDER_INDEX_BASE);
		assertEquals(ComparisonCategorization.ORDER_INDEX_BASE, instance.getOrder());
		instance.setOrder(ComparisonCategorization.ORDER_INDEX_BASE + 1);
		assertEquals(ComparisonCategorization.ORDER_INDEX_BASE + 1, instance.getOrder());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorRejectsBadOrders(){
		ComparisonCategorization instance = new ComparisonCategorization(
			ComparisonCategory.ABSOLUTE, ComparisonCategorization.ORDER_INDEX_BASE - 1
		);
	}

}
