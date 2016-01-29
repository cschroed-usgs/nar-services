package gov.usgs.cida.nar.domain;


public class TimeSeriesDensityCategoryPair {
	
	public final TimeStepDensity density;
	public final TimeSeriesCategory category;
	
	public TimeSeriesDensityCategoryPair(TimeStepDensity tsDensity, TimeSeriesCategory tsCategory){
		this.density = tsDensity;
		this.category = tsCategory;
	}
}
