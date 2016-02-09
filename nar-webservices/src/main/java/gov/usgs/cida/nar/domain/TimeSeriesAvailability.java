package gov.usgs.cida.nar.domain;

import com.google.common.base.Objects;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;


public class TimeSeriesAvailability {
	private TimeSeriesCategory timeSeriesCategory;
	private TimeStepDensity timeStepDensity;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String constit;
	
	public TimeSeriesAvailability(){}
	
	public TimeSeriesAvailability(TimeSeriesCategory timeSeriesCategory, TimeStepDensity timeStepDensity, LocalDateTime startTime, LocalDateTime endTime, String constit){
		this.timeSeriesCategory = timeSeriesCategory;
		this.timeStepDensity = timeStepDensity;
		this.startTime = startTime;
		this.endTime = endTime;
		this.constit = constit;
		
	}
	@Override
	public int hashCode(){
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(this.getTimeSeriesCategory())
			.append(getTimeStepDensity())
			.append(getStartTime())
			.append(getEndTime())
			.append(getConstit());
		
		return hcb.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		boolean equal = true;
		if(obj == null){
			equal = false;
		} else if (!this.getClass().equals(obj.getClass())){
			equal = false;
		} else {
			final TimeSeriesAvailability other = (TimeSeriesAvailability)obj;
			equal = 
				Objects.equal(this.getTimeSeriesCategory(), other.getTimeSeriesCategory())
				&& Objects.equal(this.getTimeStepDensity(), other.getTimeStepDensity())
				&& Objects.equal(this.getStartTime(), other.getStartTime())
				&& Objects.equal(this.getEndTime(), other.getEndTime())
				&& Objects.equal(this.getConstit(), other.getConstit())
				;
		}
		
		return equal;
	}

	/**
	 * @return the timeSeriesCategory
	 */
	public TimeSeriesCategory getTimeSeriesCategory() {
		return timeSeriesCategory;
	}

	/**
	 * @param timeSeriesCategory the timeSeriesCategory to set
	 */
	public void setTimeSeriesCategory(TimeSeriesCategory timeSeriesCategory) {
		this.timeSeriesCategory = timeSeriesCategory;
	}
	
	@Override
	public String toString(){
		return "{" 
			+ "\ntimestepDensity:" + this.getTimeStepDensity().toString()
			+ "\n timeseriesCategory:" + this.getTimeSeriesCategory().toString()
			+ "\n startTime:" + this.getStartTime().toString(ISODateTimeFormat.dateTime())
			+ "\n endTime:" + this.getEndTime().toString(ISODateTimeFormat.dateTime())
			+ "\n constit:" + this.getConstit() +
			"\n}";
	}

	/**
	 * @return the timeStepDensity
	 */
	public TimeStepDensity getTimeStepDensity() {
		return timeStepDensity;
	}

	/**
	 * @param timeStepDensity the timeStepDensity to set
	 */
	public void setTimeStepDensity(TimeStepDensity timeStepDensity) {
		this.timeStepDensity = timeStepDensity;
	}

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the constit
	 */
	public String getConstit() {
		return constit;
	}

	/**
	 * @param constit the constit to set
	 */
	public void setConstit(String constit) {
		this.constit = constit;
	}
}
