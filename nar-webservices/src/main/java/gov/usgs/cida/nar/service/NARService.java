package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesCategory;
import gov.usgs.cida.nar.domain.TimeStepDensity;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 * @param <NARData> implementation comes with subclass of NARData
 */
public interface NARService<NARData> {

	List<? extends NARData> request();
	TimeStepDensity getTimeStepDensity();
	TimeSeriesCategory getTimeSeriesCategory();
	boolean isAvailable();
	void setSiteQwId(List<String> siteQwId);
}
