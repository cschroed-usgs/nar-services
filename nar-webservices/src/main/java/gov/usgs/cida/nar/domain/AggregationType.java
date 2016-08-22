package gov.usgs.cida.nar.domain;


public enum AggregationType {
	NONE, //Not an aggregation, likely an original sample
	MOVING_AVERAGE,
	TIME_WEIGHTED_MEAN,
}
