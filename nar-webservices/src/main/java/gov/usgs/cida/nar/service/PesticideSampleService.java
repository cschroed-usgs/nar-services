package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.Pesticide;
import gov.usgs.cida.nar.mybatis.dao.PesticideSampleDao;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.model.DateIntervalWithConstituent;
import gov.usgs.cida.nar.mybatis.model.PesticideSample;
import gov.usgs.cida.nar.util.DateUtil;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.NotFoundException;
import org.joda.time.LocalDateTime;



public class PesticideSampleService implements NARService<PesticideSample> {
	
	private PesticideSampleDao dao;
	protected List<String> siteQwId;
	private String startDate;
	private String endDate;
	private final PestSitesService sitesService;
	private List<String> constit;
	
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
	public List<PesticideSample> request() {
		return request(siteQwId, constit, startDate, endDate);
	}

	public List<PesticideSample> request(List<String> siteQwId, List<String> constit, String startDateStr, String endDateStr) {
		Date startDateSql = DateUtil.getSqlDate(startDateStr);
		Date endDateSql = DateUtil.getSqlDate(endDateStr);
		return dao.getPesticideSample(siteQwId, constit, startDateSql, endDateSql);
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
	/**
	 * @throws javax.ws.rs.NotFoundException if pesticide information is unavailable for the site
	 */
	public List<TimeSeriesAvailability> getAvailability() {
		String siteQw = siteQwId.get(0);
		List<Pesticide> pests = sitesService.getMostDetectedPesticides(siteQw);
		
		if (null == pests || pests.isEmpty()){
			throw new NotFoundException("Could not determine the most frequently-detected pesticides for site '" + siteQw +"'.");
		}
		List<TimeSeriesAvailability> availability = new ArrayList<>();
		for(Pesticide pesticide : pests){
			
			//Note that pesticides, in addition to nutrients, 
			//are considered to be "constituents":
			//http://water.usgs.gov/nawqa/constituents/pesticides.html
			
			List<DateIntervalWithConstituent> dateIntervalsWithConstits = this.dao.getAvailability(siteQw, pesticide.getName());
			if (null != dateIntervalsWithConstits && !dateIntervalsWithConstits.isEmpty()) {
				for (DateIntervalWithConstituent dateIntervalWithConstit : dateIntervalsWithConstits) {
					LocalDateTime start = new LocalDateTime(dateIntervalWithConstit.getStart());
					LocalDateTime end = new LocalDateTime(dateIntervalWithConstit.getEnd());
					TimeSeriesAvailability tsa = new TimeSeriesAvailability(
						this.getTimeSeriesCategory(),
						this.getTimeStepDensity(),
						start,
						end,
						pesticide.getFullName()
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

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the constit
	 */
	public List<String> getConstit() {
		return constit;
	}

	/**
	 * @param constit the constit to set
	 */
	public void setConstit(List<String> constit) {
		this.constit = constit;
	}

}
