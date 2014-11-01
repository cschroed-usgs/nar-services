package gov.usgs.cida.sos;

import gov.usgs.cida.nude.time.DateRange;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class ObservationMetadata {
	
	public static final String CLEAR_METADATA_ELEMENT = "OM_Observation";

	public static final String TIME_PERIOD_ELEMENT = "TimePeriod";
	public static final String BEGIN_POSITION_ELEMENT = "beginPosition";
	public static final String END_POSITION_ELEMENT = "endPosition";
	
	public static final String PROCEDURE_ELEMENT = "procedure";
	public static final String OBSERVED_PROPERTY_ELEMENT = "observedProperty";
	public static final String FEATURE_OF_INTEREST_ELEMENT = "featureOfInterest";
	
	public static final String UOM_ELEMENT = "uom";
	public static final String CODE_ATTRIBUTE = "code";
	
	private DateRange timePeriod = null;
	private String procedure = null;
	private String observedProperty = null;
	private String featureOfInterest = null;
	private String defaultUnits = null;

	public DateRange timePeriod() {
		return timePeriod;
	}

	public ObservationMetadata timePeriod(DateRange timePeriod) {
		this.timePeriod = timePeriod;
		return this;
	}

	public String procedure() {
		return procedure;
	}

	public ObservationMetadata procedure(String procedure) {
		this.procedure = procedure;
		return this;
	}

	public String observedProperty() {
		return observedProperty;
	}

	public ObservationMetadata observedProperty(String observedProperty) {
		this.observedProperty = observedProperty;
		return this;
	}

	public String featureOfInterest() {
		return featureOfInterest;
	}

	public ObservationMetadata featureOfInterest(String featureOfInterest) {
		this.featureOfInterest = featureOfInterest;
		return this;
	}

	public String defaultUnits() {
		return defaultUnits;
	}

	public ObservationMetadata defaultUnits(String defaultUnits) {
		this.defaultUnits = defaultUnits;
		return this;
	}

	@Override
	public boolean equals(Object meta) {
		if (meta == null) {
			return false;
		}
		if (meta == this) {
			return true;
		}
		if (meta instanceof ObservationMetadata) {
			ObservationMetadata obj = (ObservationMetadata) meta;
			return new EqualsBuilder()
					.append(this.timePeriod(), obj.timePeriod())
					.append(this.procedure(), obj.procedure())
					.append(this.observedProperty(), obj.observedProperty())
					.append(this.featureOfInterest(), obj.featureOfInterest())
					.append(this.defaultUnits(), obj.defaultUnits())
					.isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(37, 73)
				.append(this.timePeriod)
				.append(this.procedure)
				.append(this.observedProperty)
				.append(this.featureOfInterest)
				.append(this.defaultUnits)
				.toHashCode();
	}
	
}
