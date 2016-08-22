package gov.usgs.cida.nar.domain.constituent;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import org.force66.beantester.BeanTester;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConstituentCategorizationTest {

	public ConstituentCategorizationTest() {
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
		beanTester.testBean(ConstituentCategorization.class);
	}

	@Test
	public void testMAP(){
		for(ConstituentSubcategory subcategory: ConstituentSubcategory.values()){
			assertNotNull(ConstituentCategorization.MAP.get(subcategory));
		}
	}
	
	@Test
	public void isValidSubcategoryOfCategory(){
		ArrayList<ConstituentSubcategory> validSubcategories = Lists.newArrayList(ConstituentSubcategory.HERBICIDE, ConstituentSubcategory.NON_HERBICIDE);
		ArrayList<ConstituentCategory> validCategoriesForSubcategories = Lists.newArrayList(ConstituentCategory.PESTICIDE);
		ArrayList<ConstituentCategory> invalidCategoriesForSubcategories = Lists.newArrayList(ConstituentCategory.values());
		invalidCategoriesForSubcategories.removeAll(validCategoriesForSubcategories);
		for(ConstituentSubcategory subcategory : validSubcategories){
			for(ConstituentCategory validCategory : validCategoriesForSubcategories){
				assertTrue( subcategory + " should be a valid subcategory of " + validCategory, ConstituentCategorization.isValidSubcategoryOfCategory(subcategory, validCategory));
			}
			for(ConstituentCategory invalidCategory : invalidCategoriesForSubcategories){
				assertFalse( subcategory + " should be an ivalid subcategory of " + invalidCategory, ConstituentCategorization.isValidSubcategoryOfCategory(subcategory, invalidCategory));
			}
		}
	}
	
	
}
