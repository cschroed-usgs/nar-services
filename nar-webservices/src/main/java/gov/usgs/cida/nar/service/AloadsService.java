package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.dao.AloadsDao;
import gov.usgs.cida.nar.mybatis.model.Aloads;
import gov.usgs.cida.nar.mybatis.model.WaterYearIntervalWithConstituent;
import gov.usgs.cida.nar.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AloadsService implements NARService<Aloads>, IConstituentFilterable, IModtypeFilterable{

	private AloadsDao dao;
	
	private List<String> siteQwId;
	private List<String> constit;
	private List<String> modtypeExcludes;
	private String startDate;
	private String endDate;
	
	public AloadsService() {
		this(new AloadsDao());
	}
	
	public AloadsService(AloadsDao dao) {
		this.dao = dao;
		this.siteQwId = null;
		this.constit = null;
		this.modtypeExcludes = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	public List<Aloads> request(List<String> siteQwId, List<String> constit, List<String> modtypeExcludes,
			String startDate, String endDate) {
		
		Integer startWy = DateUtil.getWaterYear(startDate);
		Integer endWy = DateUtil.getWaterYear(endDate);
		return dao.getAloads(siteQwId, constit, modtypeExcludes, startWy, endWy);
	}

	@Override
	public List<Aloads> request() {
		return request(siteQwId, constit, modtypeExcludes, startDate, endDate);
	}

	@Override
	public void setSiteQwId(List<String> siteQwId) {
		this.siteQwId = siteQwId;
	}

	@Override
	public void setConstit(List<String> constit) {
		this.constit = constit;
	}

	@Override
	public void setModtypeExcludes(List<String> modtypeExcludes) {
		this.modtypeExcludes = modtypeExcludes;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public TimeStepDensity getTimeStepDensity() {
		return TimeStepDensity.ANNUAL;
	}

	@Override
	public TimeSeriesCategory getTimeSeriesCategory() {
		return TimeSeriesCategory.LOAD;
	}

	@Override
	/**
	 * using this instance's site qw id and excluded modtypes, determine the
	 * availability of data
	 */
	public List<TimeSeriesAvailability> getAvailability() {
		return getAvailability(this.siteQwId.get(0), this.modtypeExcludes);
	}
	
	public List<TimeSeriesAvailability> getAvailability(String siteQwId, List<String> excludedModtypes) {
		List<TimeSeriesAvailability> availability = new ArrayList<>();
		List<WaterYearIntervalWithConstituent> wyIntervalsWithConstits = this.dao.getAvailability(siteQwId, excludedModtypes);
		if(null != wyIntervalsWithConstits && !wyIntervalsWithConstits.isEmpty()){
			for(WaterYearIntervalWithConstituent wyIntervalWithConstit : wyIntervalsWithConstits){
				LocalDateTime start = new LocalDateTime(wyIntervalWithConstit.getStart(), 1, 1, 0, 0);
				LocalDateTime end = new LocalDateTime(wyIntervalWithConstit.getEnd(), 1, 1, 0, 0);
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
		return availability;
	}
}
