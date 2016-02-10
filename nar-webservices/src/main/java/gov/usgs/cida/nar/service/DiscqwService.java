package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.dao.DiscqwDao;
import gov.usgs.cida.nar.mybatis.model.DateInterval;
import gov.usgs.cida.nar.mybatis.model.DateIntervalWithConstituent;
import gov.usgs.cida.nar.mybatis.model.Discqw;
import gov.usgs.cida.nar.util.DateUtil;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DiscqwService implements NARService<Discqw>, IConstituentFilterable {

	private DiscqwDao dao;
	
	private List<String> siteQwId;
	private List<String> constit;
	private String startDate;
	private String endDate;
	
	public DiscqwService() {
		this(new DiscqwDao());
	}
	
	public DiscqwService(DiscqwDao dao) {
		this.dao = dao;
		this.siteQwId = null;
		this.constit = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	public List<Discqw> request(List<String> siteQwId, List<String> constit, String startDateStr, String endDateStr) {
		Date startDateSql = DateUtil.getSqlDate(startDateStr);
		Date endDateSql = DateUtil.getSqlDate(endDateStr);
		return dao.getDiscqw(siteQwId, constit, startDateSql, endDateSql);
	}

	@Override
	public List<Discqw> request() {
		return request(siteQwId, constit, startDate, endDate);
	}
	
	@Override
	public void setSiteQwId(List<String> siteQwId) {
		this.siteQwId = siteQwId;
	}

	@Override
	public void setConstit(List<String> constit) {
		this.constit = constit;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
		return getAvailability(this.siteQwId.get(0), this.constit.get(0));
	}

	/**
	 * 
	 * @param siteQwId non-optional site id
	 * @param constit optional constituent to filter by
	 * @return list of availability for the specified filter params
	 */
	public List<TimeSeriesAvailability> getAvailability(String siteQwId, String constit) {
		List<TimeSeriesAvailability> availability = new ArrayList<>();
		List<DateIntervalWithConstituent> intervals = this.dao.getAvailability(siteQwId, constit);
		for(DateIntervalWithConstituent interval : intervals) {
			if( null!= interval){
				LocalDateTime startTime = new LocalDateTime(interval.getStart());
				LocalDateTime endTime = new LocalDateTime(interval.getEnd());
				TimeSeriesAvailability tsa = new TimeSeriesAvailability(
					this.getTimeSeriesCategory(),
					this.getTimeStepDensity(),
					startTime,
					endTime,
					interval.getConstit()
				);
				availability.add(tsa);
			}
		}
		return availability;
	}
}
