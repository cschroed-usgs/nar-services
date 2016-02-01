package gov.usgs.cida.nar.domain;

import org.joda.time.DateTime;


public class TimeSeriesAvailability {
	public final TimeSeriesCategory timeSeriesCategory;
	public final TimeStepDensity timeStepDensity;
	public final DateTime startTime;
	public final DateTime endTime;
	
	public TimeSeriesAvailability(TimeSeriesCategory timeSeriesCategory, TimeStepDensity timeStepDensity, DateTime startTime, DateTime endTime){
		this.timeSeriesCategory = timeSeriesCategory;
		this.timeStepDensity = timeStepDensity;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
