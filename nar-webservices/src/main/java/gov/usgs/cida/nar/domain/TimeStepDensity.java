package gov.usgs.cida.nar.domain;

public enum TimeStepDensity {
	//These names are the same as the client-side ids.
	//Adjust this tight coupling with care.
	ANNUAL,
	MONTHLY,
	DAILY,
	DISCRETE, //No regular pattern
	EVERY_21_DAYS,
	EVERY_60_DAYS,
}
