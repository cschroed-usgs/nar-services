package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.PestSitesDao;
import gov.usgs.cida.nar.mybatis.model.PestSites;
import java.util.List;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author jiwalker
 */
public class PestSitesServiceTest {
	
	private PestSitesDao mockedDao;
	private static final String SITE_QW_ID = "100";
	
	@Before
	public void setup() {
		mockedDao = mock(PestSitesDao.class);
		PestSites summary = new PestSites();
		summary.setAq1("aq1");
		summary.setN3(3);
		summary.setPlot("YES");
		when(mockedDao.getPestSites(Lists.newArrayList(SITE_QW_ID)))
				.thenReturn(Lists.newArrayList(summary));
	}
	
	/**
	 * Test of request method, of class PestSitesService.
	 */
	@Test
	public void testRequest() {
		PestSitesService instance = new PestSitesService(mockedDao);
		List<PestSites> result = instance.request(Lists.newArrayList(SITE_QW_ID));
		PestSites first = result.get(0);
		assertThat(first.getAq1(), is(equalTo("aq1")));
		assertThat(first.getHerbSamp(), is(nullValue()));
		assertThat(first.getPlot(), is(equalTo("YES")));
		assertThat(first.getN3(), is(equalTo(3)));
		verify(mockedDao).getPestSites(Lists.newArrayList(SITE_QW_ID));
	}
	
}
