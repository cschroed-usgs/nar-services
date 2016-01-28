package gov.usgs.cida.nude.util;

import gov.usgs.cida.nude.column.Column;
import gov.usgs.cida.nude.column.SimpleColumn;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;
import gov.usgs.cida.nude.time.DateRange;
import gov.usgs.cida.sos.Observation;
import gov.usgs.cida.sos.ObservationMetadata;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 * @author jiwalker
 */
public class NudeUtilsTest {
	
	public class TestBean {
		
		private String string;
		private int integer;
		private float floatingpoint;
		private boolean bool;
		private DateTime dt;
		private DateRange dr;
		private Date date;

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}

		public int getInteger() {
			return integer;
		}

		public void setInteger(int integer) {
			this.integer = integer;
		}

		public float getFloatingpoint() {
			return floatingpoint;
		}

		public void setFloatingpoint(float floatingpoint) {
			this.floatingpoint = floatingpoint;
		}

		public boolean isBool() {
			return bool;
		}

		public void setBool(boolean bool) {
			this.bool = bool;
		}

		public DateTime getDt() {
			return dt;
		}

		public void setDt(DateTime dt) {
			this.dt = dt;
		}

		public DateRange getDr() {
			return dr;
		}

		public void setDr(DateRange dr) {
			this.dr = dr;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

	}
	/**
	 * Test of makeFlatRowMap method, of class NudeUtils.
	 */
	@Test
	public void testMakeRowMap() {
		Observation bean = new Observation()
				.metadata(new ObservationMetadata()
						.defaultUnits("ft")
						.featureOfInterest("test")
						.observedProperty("test")
						.procedure("test")
						.timePeriod(new DateRange()))
				.time(new DateTime())
				.value("1.2");
				
		Map<Column, String> result = NudeUtils.makeFlatRowMap(bean);
		assertThat(result.get(new SimpleColumn("defaultUnits")), is(equalTo("ft")));
		assertThat(result.get(new SimpleColumn("featureOfInterest")), is(equalTo("test")));
		assertThat(result.get(new SimpleColumn("observedProperty")), is(equalTo("test")));
		assertThat(result.get(new SimpleColumn("procedure")), is(equalTo("test")));
		assertThat(result.get(new SimpleColumn("timePeriod")), is(notNullValue()));
		assertThat(result.get(new SimpleColumn("value")), is(equalTo("1.2")));
	}
	
	@Test
	public void testIsLiteral() {
		assertThat(NudeUtils.isValue("test"), is(true));
		assertThat(NudeUtils.isValue(true), is(true));
		assertThat(NudeUtils.isValue(1), is(true));
		assertThat(NudeUtils.isValue(1.4), is(true));
		assertThat(NudeUtils.isValue('c'), is(true));
		assertThat(NudeUtils.isValue(new HashMap<String, String>()), is(false));
		assertThat(NudeUtils.isValue(new Date()), is(true));
	}
	
	@Test
	public void testMakeString() {
		TestBean testBean = new TestBean();
		testBean.setString("bar");
		testBean.setInteger(22);
		testBean.setFloatingpoint(99.4f);
		testBean.setBool(true);
		testBean.setDate(new Date(0));
		testBean.setDt(new DateTime(0, DateTimeZone.UTC));
		testBean.setDr(new DateRange(new DateTime(0, DateTimeZone.UTC), new DateTime(3600 * 24 * 1000, DateTimeZone.UTC)));
		TableRow tableRow = NudeUtils.makeTableRow(testBean);
		assertThat("strings converted", tableRow.getValue(new SimpleColumn("string")), is(equalTo("bar")));
		assertThat("ints converted", tableRow.getValue(new SimpleColumn("integer")), is(equalTo("22")));
		assertThat("floats converted", tableRow.getValue(new SimpleColumn("floatingpoint")), is(equalTo("99.4")));
		assertThat("bools converted", tableRow.getValue(new SimpleColumn("bool")), is(equalTo("true")));
		assertThat("datetime converted", tableRow.getValue(new SimpleColumn("dt")), is(equalTo("1970-01-01T00:00:00.000Z")));
		assertThat("daterange", tableRow.getValue(new SimpleColumn("dr")), is(equalTo("1970-01-01T00:00:00.000Z/1970-01-02T00:00:00.000Z")));
		assertThat("dates converted", tableRow.getValue(new SimpleColumn("date")), is(equalTo("1970-01-01T00:00:00.000Z")));
	}
}
