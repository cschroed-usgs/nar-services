package gov.usgs.cida.nar.domain;

import com.google.common.base.Objects;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;


public class TimeSeriesAvailability {
	private TimeSeriesCategory timeSeriesCategory;
	public TimeStepDensity timeStepDensity;
	public DateTime startTime;
	public DateTime endTime;
	public String constit;
	
	public TimeSeriesAvailability(){}
	
	public TimeSeriesAvailability(TimeSeriesCategory timeSeriesCategory, TimeStepDensity timeStepDensity, DateTime startTime, DateTime endTime, String constit){
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
			.append(timeStepDensity)
			.append(startTime)
			.append(endTime)
			.append(constit);
		
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
				Objects.equal(this.timeSeriesCategory, other.timeSeriesCategory)
				&& Objects.equal(this.timeStepDensity, other.timeStepDensity)
				&& Objects.equal(this.startTime, other.startTime)
				&& Objects.equal(this.endTime, other.endTime)
				&& Objects.equal(this.constit, other.constit)
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
}
