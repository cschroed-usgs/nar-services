package gov.usgs.cida.nar.util;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

public class LocalDateTimeJsonSerializer implements JsonSerializer<LocalDateTime> {

	@Override
	public JsonElement serialize(final LocalDateTime dateTime, final Type typeOfSrc, final JsonSerializationContext context) {
		String isoStringDate = dateTime.toString(ISODateTimeFormat.dateTime());
		final JsonPrimitive jsonPrimitive = new JsonPrimitive(isoStringDate);
		return jsonPrimitive;
	}
}
