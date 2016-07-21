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
	private static final String ERROR_MSG_SUFFIX = "not available for pesticide sites, try the pesticide sample API instead";
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
		throw new UnsupportedOperationException("getTimeStepDensity " + ERROR_MSG_SUFFIX);
	}
	@Override
	public TimeSeriesCategory getTimeSeriesCategory() {
		throw new UnsupportedOperationException("getTimeSeriesCategory " + ERROR_MSG_SUFFIX);
	}

	@Override
	public List<TimeSeriesAvailability> getAvailability() {
		throw new UnsupportedOperationException("getAvailability " + ERROR_MSG_SUFFIX);
	}
	
	/**
	 * 
	 * @return a list of the names of the most-detected pesticides for a site
	 */
	public List<String> getMostDetectedPesticides(String siteQwId) {
		return dao.getMostDetectedPesticides(siteQwId);
	}

}
