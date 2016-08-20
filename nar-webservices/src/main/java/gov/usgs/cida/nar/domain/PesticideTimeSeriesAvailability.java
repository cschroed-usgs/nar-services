package gov.usgs.cida.nar.domain;

import gov.usgs.cida.nar.domain.constituent.ConstituentCategorization;
import java.util.Objects;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalDateTime;


public class PesticideTimeSeriesAvailability extends TimeSeriesAvailability {
	private AggregationType aggregationType; //none (if real measurement), time-weighted, moving average
	private ComparisonCategorization comparisonCategorization; //human health, aquatic life
	private ConstituentCategorization constituentCategorization;
	
	public PesticideTimeSeriesAvailability(){}
	
	public PesticideTimeSeriesAvailability(TimeSeriesCategory timeSeriesCategory, TimeStepDensity timeStepDensity, LocalDateTime startTime, LocalDateTime endTime, String constit, AggregationType aggregationType, ComparisonCategorization comparisonCategorization, ConstituentCategorization constituentCategorization){
		super(timeSeriesCategory, timeStepDensity, startTime, endTime, constit);
		this.aggregationType = aggregationType;
		this.comparisonCategorization = comparisonCategorization;
		this.constituentCategorization = constituentCategorization;
		
	}
	@Override
	public int hashCode(){
		
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(getAggregationType())
			.append(getComparisonCategorization())
			.append(getConstituentCategorization())
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
		if (this.getAggregationType() != other.getAggregationType()) {
			return false;
		}
		if (!Objects.equals(this.getComparisonCategorization(), other.getComparisonCategorization())) {
			return false;
		}
		if (!Objects.equals(this.getConstituentCategorization(), other.getConstituentCategorization())) {
			return false;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "PesticideTimeSeriesAvailability{" + "aggregationType=" + getAggregationType() + ", comparisonCategorization=" + getComparisonCategorization() + ", constituentCategorization=" + getConstituentCategorization() +
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
	public ComparisonCategorization getComparisonCategorization() {
		return comparisonCategorization;
	}

	/**
	 * @param comparisonCategory the comparisonCategory to set
	 */
	public void setComparisonCategorization(ComparisonCategorization comparisonCategorization) {
		this.comparisonCategorization = comparisonCategorization;
	}

	/**
	 * @return the constituentCategorization
	 */
	public ConstituentCategorization getConstituentCategorization() {
		return constituentCategorization;
	}

	/**
	 * @param constituentCategorization the constituentCategorization to set
	 */
	public void setConstituentCategorization(ConstituentCategorization constituentCategorization) {
		this.constituentCategorization = constituentCategorization;
	}
}
