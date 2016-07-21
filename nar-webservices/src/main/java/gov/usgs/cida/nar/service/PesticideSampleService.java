package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.PesticideSampleDao;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.dao.AflowDao;
import gov.usgs.cida.nar.mybatis.model.PestSites;
import gov.usgs.cida.nar.mybatis.model.WaterYearIntervalWithConstituent;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDateTime;


class PesticideSampleService implements NARService<PestSites> {
	
	private PesticideSampleDao dao;
	private List<String> siteQwId;
	private String startDate;
	private String endDate;
	private final PestSitesService sitesService;
	
	public PesticideSampleService() {
		this(new PesticideSampleDao(), new PestSitesService());
	}

	public PesticideSampleService(PesticideSampleDao dao, PestSitesService sitesService) {
		this.sitesService = sitesService;
		this.dao = dao;
		this.siteQwId = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	@Override
	public List<? extends PestSites> request() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public TimeStepDensity getTimeStepDensity() {
		return TimeStepDensity.DISCRETE;
	}

	@Override
	public TimeSeriesCategory getTimeSeriesCategory() {
		return TimeSeriesCategory.CONCENTRATION;
	}

	@Override
	public List<TimeSeriesAvailability> getAvailability() {
		String siteQw = siteQwId.get(0);
		List<String> mostCommonPesticides = sitesService.getMostDetectedPesticides(siteQw);
		List<TimeSeriesAvailability> availability = new ArrayList<>();
		for(String commonPesticide : mostCommonPesticides){
			
			List<WaterYearIntervalWithConstituent> wyIntervalsWithConstits = this.dao.getAvailability(siteQw, commonPesticide);
			if (null != wyIntervalsWithConstits && !wyIntervalsWithConstits.isEmpty()) {
				for (WaterYearIntervalWithConstituent wyIntervalWithConstit : wyIntervalsWithConstits) {
					LocalDateTime start = new LocalDateTime(wyIntervalWithConstit.getStartYear(), 1, 1, 0, 0);
					LocalDateTime end = new LocalDateTime(wyIntervalWithConstit.getEndYear(), 1, 1, 0, 0);
					TimeSeriesAvailability tsa = new TimeSeriesAvailability(
						this.getTimeSeriesCategory(),
						this.getTimeStepDensity(),
						start,
						end,
						wyIntervalWithConstit.getConstit()
					);
					availability.add(tsa);
				}
		}
		}
		return availability;
	}

	@Override
	public void setSiteQwId(List<String> siteQwId) {
		this.siteQwId = siteQwId;
	}

}
