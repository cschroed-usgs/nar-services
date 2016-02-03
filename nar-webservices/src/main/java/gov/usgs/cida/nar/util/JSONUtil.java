package gov.usgs.cida.nar.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;
import gov.usgs.cida.nar.util.DateTimeJsonSerializer;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class JSONUtil {
	
	/**
	 * Wraps GSON toJSON call, setup and reusable setup can go here
	 * @param object
	 * @return 
	 */
	public static String toJSON(Object object) {
		return getBuilder().toJson(object);
	}

	public static Gson getBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(DateTime.class, new DateTimeJsonSerializer());
		return builder.create();
	}
}
