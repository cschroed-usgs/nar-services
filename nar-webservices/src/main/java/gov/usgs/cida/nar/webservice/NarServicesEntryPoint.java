package gov.usgs.cida.nar.webservice;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author thongsav
 */
@ApplicationPath("/service")
public class NarServicesEntryPoint extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<>();

		// webservices
		classes.add(DownloadService.class);
		classes.add(TimeseriesWebservice.class);
		classes.add(PesticidesWebservice.class);

		return classes;
	}
}
