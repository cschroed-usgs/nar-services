package gov.usgs.cida.nar.domain;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalDateTime;


public class PesticideTimeSeriesAvailability extends TimeSeriesAvailability {
	private AggregationType aggregationType; //none (if real measurement), time-weighted, moving average
	private ComparisonCategory comparisonCategory; //human health, aquatic life
	private int comparisonOrder; //1-based index. 1 for closest to benchmark, 2 for 2nd-closest to bencmark, etc.
	
	public PesticideTimeSeriesAvailability(){}
	
	public PesticideTimeSeriesAvailability(TimeSeriesCategory timeSeriesCategory, TimeStepDensity timeStepDensity, LocalDateTime startTime, LocalDateTime endTime, String constit, AggregationType aggregationType, ComparisonCategory comparisonCategory, int comparisonOrder){
		super(timeSeriesCategory, timeStepDensity, startTime, endTime, constit);
		this.aggregationType = aggregationType;
		this.comparisonCategory = comparisonCategory;
		this.comparisonOrder = comparisonOrder;
		
		
	}
	@Override
	public int hashCode(){
		
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(getAggregationType())
			.append(getComparisonCategory())
			.append(getComparisonOrder())
			.appendSuper(super.hashCode());
		
		return hcb.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PesticideTimeSeriesAvailability other = (PesticideTimeSeriesAvailability) obj;
		if (this.getComparisonOrder() != other.getComparisonOrder()) {
			return false;
		}
		if (this.getAggregationType() != other.getAggregationType()) {
			return false;
		}
		if (this.getComparisonCategory() != other.getComparisonCategory()) {
			return false;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "PesticideTimeSeriesAvailability{" + "aggregationType=" + getAggregationType() + ", comparisonCategory=" + getComparisonCategory() + ", comparisonOrder=" + getComparisonOrder() + 
			super.toString() +
			'}';
	}

	/**
	 * @return the aggregationType
	 */
	public AggregationType getAggregationType() {
		return aggregationType;
	}

	/**
	 * @param aggregationType the aggregationType to set
	 */
	public void setAggregationType(AggregationType aggregationType) {
		this.aggregationType = aggregationType;
	}

	/**
	 * @return the comparisonCategory
	 */
	public ComparisonCategory getComparisonCategory() {
		return comparisonCategory;
	}

	/**
	 * @param comparisonCategory the comparisonCategory to set
	 */
	public void setComparisonCategory(ComparisonCategory comparisonCategory) {
		this.comparisonCategory = comparisonCategory;
	}

	/**
	 * @return the comparisonOrder
	 */
	public int getComparisonOrder() {
		return comparisonOrder;
	}

	/**
	 * @param comparisonOrder the comparisonOrder to set
	 */
	public void setComparisonOrder(int comparisonOrder) {
		this.comparisonOrder = comparisonOrder;
	}
}
