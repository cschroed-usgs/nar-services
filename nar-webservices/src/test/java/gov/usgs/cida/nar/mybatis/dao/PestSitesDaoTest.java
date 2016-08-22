package gov.usgs.cida.nar.mybatis.dao;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.ComparisonCategorization;
import gov.usgs.cida.nar.domain.ComparisonCategory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PestSitesDaoTest {
	
	SqlSessionFactory mockSessionFactory = Mockito.mock(SqlSessionFactory.class);
	PestSitesDao instance;
	
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
		instance = new PestSitesDao(mockSessionFactory);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testColNameParseWithGoodComparisonAndOrderStrings() {
		
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
	
	@Test
	public void testColNameParseWithTooShortStrings(){
		String testStr = "";
		for(int i = 0; i < PestSitesDao.MIN_COMPARISON_CATEGORY_COLUMN_SIZE; i++){
			if(i != 0){
				testStr += "z";
			}
			try{
				instance.getComparisonCategorizationForColumnName(testStr);
			} catch(IllegalArgumentException e){
				break;//this is expected, so proceed
			}
			fail("Should throw an exception for Strings with length < " + PestSitesDao.MIN_COMPARISON_CATEGORY_COLUMN_SIZE);
		}
		//else, pass the test
	}
	
	/**
	 * Ensure that the query post-processing errors loudly if 
	 * getComparisonCategorizationForColumnName is incorrectly 
	 * creating duplicate ComparisonCategorizations from column name
	 */
	@Test(expected = RuntimeException.class)
	public void testProcessPesticidesClosestToBenchmarksQueryResults(){
		PestSitesDao mockDao = mock(PestSitesDao.class);
		//let the real method be called
		when(mockDao.processPesticidesClosestToBenchmarksQueryResults(anyMap())).thenCallRealMethod();
		
		//but break the related method -- make it return the same thing
		//every time regardless of input
		when(mockDao.getComparisonCategorizationForColumnName(anyString())).thenReturn(new ComparisonCategorization());

		Map<String, String> queryResult = new HashMap<>();
		
		//add two results so that getComparisonCategorizationForColumnName
		//is called twice, thus returning a duplicate.
		queryResult.put("values", "are");
		queryResult.put("unimportant", "here");
		
		//this should throw an error
		mockDao.processPesticidesClosestToBenchmarksQueryResults(queryResult);
		fail("Error should be thrown if duplicate ComparisonCategorizations are found");
	}

}
