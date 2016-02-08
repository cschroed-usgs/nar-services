package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import java.util.List;
/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 * @param <NARData> implementation comes with subclass of NARData
 */
public interface NARService<NARData> {

	public List<? extends NARData> request();
	public TimeStepDensity getTimeStepDensity();
	public TimeSeriesCategory getTimeSeriesCategory();
	
	/**
	 * Based on criteria in the instance variables, find the availability
	 * of data for this data type
	 * @return an empty list if no data available, an Interval otherwise
	 */
	public List<TimeSeriesAvailability> getAvailability();
	public void setSiteQwId(List<String> siteQwId);
}
