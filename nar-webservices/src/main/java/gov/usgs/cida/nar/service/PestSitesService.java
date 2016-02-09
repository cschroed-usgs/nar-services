package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.dao.PestSitesDao;
import gov.usgs.cida.nar.mybatis.model.PestSites;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class PestSitesService implements NARService<PestSites> {

	private PestSitesDao dao;
	private List<String> siteQwId;
	
	public PestSitesService() {
		this(new PestSitesDao());
	}
	
	public PestSitesService(PestSitesDao dao) {
		this.dao = dao;
		this.siteQwId = null;
	}
	
	public List<PestSites> request(List<String> siteQwId) {
		return dao.getPestSites(siteQwId);
	}

	@Override
	public List<PestSites> request() {
		return request(siteQwId);
	}

	@Override
	public void setSiteQwId(List<String> siteQwId) {
		this.siteQwId = siteQwId;
	}

	@Override
	public TimeStepDensity getTimeStepDensity() {
		throw new UnsupportedOperationException("getAvailability not available for pesticides");
	}
	@Override
	public TimeSeriesCategory getTimeSeriesCategory() {
		throw new UnsupportedOperationException("getAvailability not available for pesticides");
	}

	@Override
	public List<TimeSeriesAvailability> getAvailability() {
		throw new UnsupportedOperationException("getAvailability not available for pesticides");
	}

}
