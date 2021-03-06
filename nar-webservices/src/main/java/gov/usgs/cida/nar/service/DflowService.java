package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.dao.DflowDao;
import gov.usgs.cida.nar.mybatis.model.DateInterval;
import gov.usgs.cida.nar.mybatis.model.Dflow;
import gov.usgs.cida.nar.mybatis.model.WaterYearInterval;
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
public class DflowService implements NARService<Dflow> {

	private DflowDao dao;
	
	private List<String> siteQwId;
	private String startDate;
	private String endDate;
	
	public DflowService() {
		this(new DflowDao());
	}
	
	public DflowService(DflowDao dao) {
		this.dao = dao;
		this.siteQwId = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	public List<Dflow> request(List<String> siteQwId, String startDateStr, String endDateStr) {
		Date startDateSql = DateUtil.getSqlDate(startDateStr);
		Date endDateSql = DateUtil.getSqlDate(endDateStr);
		return dao.getDflow(siteQwId, startDateSql, endDateSql);
	}
	
	@Override
	public List<Dflow> request() {
		return request(siteQwId, startDate, endDate);
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public TimeStepDensity getTimeStepDensity() {
		return TimeStepDensity.DAILY;
	}

	@Override
	public TimeSeriesCategory getTimeSeriesCategory() {
		return TimeSeriesCategory.FLOW;
	}

	@Override
	public List<TimeSeriesAvailability> getAvailability() {
		List<TimeSeriesAvailability> availability = new ArrayList<>();
		DateInterval interval = this.dao.getAvailability(this.siteQwId.get(0));
		if( null!= interval){
			LocalDateTime startTime = new LocalDateTime(interval.getStart());
			LocalDateTime endTime = new LocalDateTime(interval.getEnd());
			TimeSeriesAvailability tsa = new TimeSeriesAvailability(
				this.getTimeSeriesCategory(),
				this.getTimeStepDensity(),
				startTime,
				endTime,
				null
			);
			availability.add(tsa);
		}
		return availability;
	}

	@Override
	public void setSiteQwId(List<String> siteQwId) {
		this.siteQwId = siteQwId;
	}
	
}
