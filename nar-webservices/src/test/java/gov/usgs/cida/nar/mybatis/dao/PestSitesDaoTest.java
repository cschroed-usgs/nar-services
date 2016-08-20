package gov.usgs.cida.nar.mybatis.dao;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.ComparisonCategorization;
import gov.usgs.cida.nar.domain.ComparisonCategory;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

public class PestSitesDaoTest {
	
	//Test both lower and upper case are valid
	List<String> goodStringComparisonBenchmarks = Lists.newArrayList("HH", "AQ", "hh", "aq");
	
	//test boundary conditions
	List<String> goodStringOrders = Lists.newArrayList("1", "2", "9", "10", "99", "100", "101");
	
	public PestSitesDaoTest() {
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
	public void testColNameParseWithGoodComparisonAndOrderStrings() {
		SqlSessionFactory mockSessionFactory = Mockito.mock(SqlSessionFactory.class);
		PestSitesDao instance = new PestSitesDao(mockSessionFactory);
		
		for(String comparison : goodStringComparisonBenchmarks){
			ComparisonCategory expectedComparisonCategory = null;
			if("aq".equals(comparison.toLowerCase())){
				expectedComparisonCategory = ComparisonCategory.AQUATIC_LIFE;
			} else if ("hh".equals(comparison.toLowerCase())) {
				expectedComparisonCategory = ComparisonCategory.HUMAN_HEALTH;
			}
			
			for(String order : goodStringOrders){
				int expectedOrder = Integer.parseInt(order);
				ComparisonCategorization expected = new ComparisonCategorization(expectedComparisonCategory, expectedOrder);
				ComparisonCategorization actual = instance.getComparisonCategorizationForColumnName(comparison + order);
				assertEquals(expected, actual);
			}
		}
	}


}
