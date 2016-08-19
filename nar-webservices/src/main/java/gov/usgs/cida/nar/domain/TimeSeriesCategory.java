
package gov.usgs.cida.nar.domain;


public enum TimeSeriesCategory {
	//these names are the same as the client-side ids.
	//Adjust this tight coupling with care.
	FLOW,
	LOAD,
	CONCENTRATION, //used for nutrients and suspended sediment
	PESTICIDE_CONCENTRATION // used for pesticides only
}
