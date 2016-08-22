package gov.usgs.cida.nar.domain;


public enum ComparisonCategory {
	NONE,//no comparison is being made
	ABSOLUTE, //frequency relative to all other relevant samples (ex: at a site and/or in a specific year)
	//frequency relative to how close measurements are to benchmarks:
	HUMAN_HEALTH,
	AQUATIC_LIFE,
}
