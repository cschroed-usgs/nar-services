package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.PesticideType;
import gov.usgs.cida.nar.mybatis.dao.PesticideSampleDao;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.model.DateIntervalWithConstituent;
import gov.usgs.cida.nar.mybatis.model.MostCommonPesticides;
import gov.usgs.cida.nar.mybatis.model.PestSites;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.NotFoundException;
import org.joda.time.LocalDateTime;



class PesticideSampleService implements NARService<PestSites> {
	
	private PesticideSampleDao dao;
	protected List<String> siteQwId;
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

	protected String getFullNameForPesticide(PesticideType type, String name){
		final String PEST_PREFIX = "PESTICIDE";
		final String DELIM = "/";
		
		
		String fullName = PEST_PREFIX + DELIM + type + DELIM + name;
		
		return fullName;
	}
	
	@Override
	/**
	 * @throws javax.ws.rs.NotFoundException if pesticide information is unavailable for the site
	 */
	public List<TimeSeriesAvailability> getAvailability() {
		String siteQw = siteQwId.get(0);
		MostCommonPesticides pests = sitesService.getMostDetectedPesticides(siteQw);
		
		if (null == pests){
			throw new NotFoundException("Could not determine the most frequently-detected pesticides for site '" + siteQw +"'.");
		}
		Map<PesticideType, String> mostCommonPesticides = pests.getTypesToNames();
		List<TimeSeriesAvailability> availability = new ArrayList<>();
		for(Map.Entry<PesticideType, String> pesticide : mostCommonPesticides.entrySet()){
			PesticideType pesticideType = pesticide.getKey();
			String pesticideName = pesticide.getValue();
			
			//Note that pesticides, in addition to nutrients, 
			//are considered to be "constituents":
			//http://water.usgs.gov/nawqa/constituents/pesticides.html
			
			List<DateIntervalWithConstituent> dateIntervalsWithConstits = this.dao.getAvailability(siteQw, pesticideName);
			if (null != dateIntervalsWithConstits && !dateIntervalsWithConstits.isEmpty()) {
				for (DateIntervalWithConstituent dateIntervalWithConstit : dateIntervalsWithConstits) {
					LocalDateTime start = new LocalDateTime(dateIntervalWithConstit.getStart());
					LocalDateTime end = new LocalDateTime(dateIntervalWithConstit.getEnd());
					TimeSeriesAvailability tsa = new TimeSeriesAvailability(
						this.getTimeSeriesCategory(),
						this.getTimeStepDensity(),
						start,
						end,
						getFullNameForPesticide(pesticideType, pesticideName)
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
