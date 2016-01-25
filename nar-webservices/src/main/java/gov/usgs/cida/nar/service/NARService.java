package gov.usgs.cida.nar.service;

import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 * @param <NARData> implementation comes with subclass of NARData
 */
public interface NARService<NARData> {

	List<? extends NARData> request();

}
