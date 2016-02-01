package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import gov.usgs.cida.nar.mybatis.dao.AflowDao;
import gov.usgs.cida.nar.mybatis.model.Aflow;
import gov.usgs.cida.nar.mybatis.model.WaterYearInterval;
import gov.usgs.cida.nar.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AflowService implements NARService<Aflow> {

	private AflowDao dao;
	private List<String> siteQwId;
	private String startDate;
	private String endDate;
	
	public AflowService() {
		this(new AflowDao());
	}
	
	public AflowService(AflowDao dao) {
		this.dao = dao;
		this.siteQwId = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	public List<Aflow> request(List<String> siteQwId, String startDate, String endDate) {
		Integer startWy = DateUtil.getWaterYear(startDate);
		Integer endWy = DateUtil.getWaterYear(endDate);
		return dao.getAflow(siteQwId, startWy, endWy);
	}

	@Override
	public List<Aflow> request() {
		return request(siteQwId, startDate, endDate);
	}

	@Override
	public void setSiteQwId(List<String> siteQwId) {
		this.siteQwId = siteQwId;
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
		return TimeSeriesCategory.FLOW;
	}

	@Override
	public List<TimeSeriesAvailability> getAvailability() {
		List<TimeSeriesAvailability> availability = new ArrayList<>();
		WaterYearInterval intTimeInterval = this.dao.getAvailability(this.siteQwId.get(0));
		if( null != intTimeInterval && null != intTimeInterval.getStart() && null != intTimeInterval.getEnd()){
			DateTime startTime = new DateTime(intTimeInterval.getStart(),1, 1, 0, 0);
			DateTime endTime = new DateTime(intTimeInterval.getEnd(),1, 1, 0, 0);
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
}
