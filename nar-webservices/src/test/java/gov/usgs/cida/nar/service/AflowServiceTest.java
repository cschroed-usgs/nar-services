package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.AflowDao;
import gov.usgs.cida.nar.mybatis.model.Aflow;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author jiwalker
 */
public class AflowServiceTest {
	
	private AflowDao mockedDao;
	private List<Aflow> expectedAflow;
	
	public AflowServiceTest() {
		mockedDao = mock(AflowDao.class);
		expectedAflow = new ArrayList<>();
		for (int i=0; i<=10; i++) {
			Aflow aflow = new Aflow();
			aflow.setWy(1990 + i);
			aflow.setSiteAbb("foo");
			aflow.setSiteQwId("100");
			aflow.setSiteFlowId("100");
			aflow.setFlow(7.0 * i);
			expectedAflow.add(aflow);
		}
	}

	/**
	 * Test of request method, of class AflowService.
	 */
	@Test
	public void testRequest() {
		String siteQwId = "100";
		String startDate = "1990-01-01";
		String endDate = "2000-01-01";
		AflowService instance = new AflowService(mockedDao);
		when(mockedDao.getAflow(siteQwId, 1990, 2000)).thenReturn(expectedAflow);
		List<Aflow> expResult = expectedAflow;
		List<Aflow> result = instance.request(siteQwId, startDate, endDate);
		assertEquals(expResult, result);
		verify(mockedDao).getAflow(siteQwId, 1990, 2000);
	}
	
}
