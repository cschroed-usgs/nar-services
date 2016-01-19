package gov.usgs.cida.nar.resultset;

import gov.usgs.cida.nar.service.SosTableRowComparator;
import gov.usgs.cida.nude.column.Column;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.column.SimpleColumn;
import gov.usgs.cida.nude.resultset.inmemory.StringTableResultSet;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;
import gov.usgs.cida.sos.Observation;
import gov.usgs.cida.sos.ObservationMetadata;

import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author jiwalker
 */
public class CachedResultSetTest {

	private static final Column procedure = new SimpleColumn(ObservationMetadata.PROCEDURE_ELEMENT);
	private static final Column observedProp = new SimpleColumn(ObservationMetadata.OBSERVED_PROPERTY_ELEMENT);
	private static final Column feature = new SimpleColumn(ObservationMetadata.FEATURE_OF_INTEREST_ELEMENT);
	private static final Column time = new SimpleColumn(Observation.TIME_ELEMENT);
	
	private static final int ROW_SIZE_FOR_SERIALIZATION = 10;
	
	private static ColumnGrouping cg;
	static {
		List<Column> columns = new LinkedList<>();
		columns.add(procedure);
		columns.add(observedProp);
		columns.add(feature);
		columns.add(time);
		cg = new ColumnGrouping(columns);
	}
	
	private ResultSet makeResultSet() {
		StringTableResultSet rs = new StringTableResultSet(cg);
		rs.addRow(makeRow("2", "prop2", "6", "time"));
		rs.addRow(makeRow("1", "prop2", "6", "time"));
		rs.addRow(makeRow("3", "prop3", "1", "time"));
		rs.addRow(makeRow("2", "prop1", "2", "time"));
		rs.addRow(makeRow("1", "prop3", "1", "time"));
		rs.addRow(makeRow("1", "prop3", "1", "time2"));
		return rs;
	}
	
	/**
	 * This result set should have enough rows to use sorting out to file system
	 */
	private ResultSet makeLargeResultSet() {
		StringTableResultSet rs = new StringTableResultSet(cg);

		int rowNum = ROW_SIZE_FOR_SERIALIZATION * 5 + (1/ROW_SIZE_FOR_SERIALIZATION); //should result in 5 and half pages
		for(int i = 1; i <= rowNum; i++) {
			String flip = leftPadInt(rowNum - i);
			rs.addRow(makeRow("feat-" + flip, "prop-" + flip, "proc-" + flip, "time-" + flip));
		}
		return rs;
	}
	
	//So that numbers are alphabetically ordered
	private String leftPadInt(int i) {
		String val = String.valueOf(i);
		while(val.length() < 10) {
			val = "0" + val;
		}
		return val;
	}
	
	private TableRow makeRow(String feat, String prop, String proc, String inTime) {
		Map<Column, String> row = new HashMap<>();
		row.put(feature, feat);
		row.put(observedProp, prop);
		row.put(procedure, proc);
		row.put(time, inTime);
		return new TableRow(cg, row);
	}

	/**
	 * Test of serialize method, of class CachedResultSet.
	 */
	@Test
	public void testSerialize() throws Exception {
		ResultSet rset = makeResultSet();
		File file = File.createTempFile("testSerialize", ".test.tmp");

		CachedResultSet.serialize(rset, file);
		CachedResultSet instance = new CachedResultSet(file);
		assertThat(instance.getMetaData().getColumnCount(), is(equalTo(4)));
		
		instance.next();
		TableRow row1 = TableRow.buildTableRow(instance);
		assertThat("6", is(equalTo(row1.getValue(procedure))));
		assertThat("prop2", is(equalTo(row1.getValue(observedProp))));
		assertThat("2", is(equalTo(row1.getValue(feature))));
		
		instance.next();
		TableRow row2 = TableRow.buildTableRow(instance);
		assertThat("6", is(equalTo(row2.getValue(procedure))));
		assertThat("prop2", is(equalTo(row2.getValue(observedProp))));
		assertThat("1", is(equalTo(row2.getValue(feature))));
		
		instance.next();
		TableRow row3 = TableRow.buildTableRow(instance);
		assertThat("1", is(equalTo(row3.getValue(procedure))));
		assertThat("prop3", is(equalTo(row3.getValue(observedProp))));
		assertThat("3", is(equalTo(row3.getValue(feature))));
		
		instance.next();
		TableRow row4 = TableRow.buildTableRow(instance);
		assertThat("2", is(equalTo(row4.getValue(procedure))));
		assertThat("prop1", is(equalTo(row4.getValue(observedProp))));
		assertThat("2", is(equalTo(row4.getValue(feature))));
		
		instance.next();
		TableRow row5 = TableRow.buildTableRow(instance);
		assertThat("1", is(equalTo(row5.getValue(procedure))));
		assertThat("prop3", is(equalTo(row5.getValue(observedProp))));
		assertThat("1", is(equalTo(row5.getValue(feature))));
		
		rset.close();
		instance.close();
		assertTrue("Temporary testSerialize files deleted", FileUtils.deleteQuietly(file));
	}
	
	/**
	 * Test of serialize method, of class CachedResultSet.
	 */
	@Test
	public void testSortedSerialize() throws Exception {
		ResultSet rset = makeResultSet();
		File file = File.createTempFile("testSortedSerialize", ".test.tmp");
		
		CachedResultSet.sortedSerialize(rset, new SosTableRowComparator(), file);
		CachedResultSet instance = new CachedResultSet(file);
		assertThat(instance.getMetaData().getColumnCount(), is(equalTo(4)));

		instance.next();
		TableRow row1 = TableRow.buildTableRow(instance);
		assertThat("6", is(equalTo(row1.getValue(procedure))));
		assertThat("prop2", is(equalTo(row1.getValue(observedProp))));
		assertThat("1", is(equalTo(row1.getValue(feature))));
		
		instance.next();
		TableRow row2a = TableRow.buildTableRow(instance);
		assertThat("1", is(equalTo(row2a.getValue(procedure))));
		assertThat("prop3", is(equalTo(row2a.getValue(observedProp))));
		assertThat("1", is(equalTo(row2a.getValue(feature))));
		assertThat("time", is(equalTo(row2a.getValue(time))));
		
		instance.next();
		TableRow row2b = TableRow.buildTableRow(instance);
		assertThat("1", is(equalTo(row2b.getValue(procedure))));
		assertThat("prop3", is(equalTo(row2b.getValue(observedProp))));
		assertThat("1", is(equalTo(row2b.getValue(feature))));
		assertThat("time2", is(equalTo(row2b.getValue(time))));
		
		instance.next();
		TableRow row3 = TableRow.buildTableRow(instance);
		assertThat("2", is(equalTo(row3.getValue(procedure))));
		assertThat("prop1", is(equalTo(row3.getValue(observedProp))));
		assertThat("2", is(equalTo(row3.getValue(feature))));
		
		instance.next();
		TableRow row4 = TableRow.buildTableRow(instance);
		assertThat("6", is(equalTo(row4.getValue(procedure))));
		assertThat("prop2", is(equalTo(row4.getValue(observedProp))));
		assertThat("2", is(equalTo(row4.getValue(feature))));
		
		instance.next(); //this row didn't match any of the filters and fell to the bottom
		TableRow row5 = TableRow.buildTableRow(instance);
		assertThat("1", is(equalTo(row5.getValue(procedure))));
		assertThat("prop3", is(equalTo(row5.getValue(observedProp))));
		assertThat("3", is(equalTo(row5.getValue(feature))));
		
		rset.close();
		instance.close();
		assertTrue("Temporary testSortedSerialize files deleted", FileUtils.deleteQuietly(file));
	}
	
	@Test
	public void testSortedSerialize_Disk() throws Exception {
		ResultSet rset = makeLargeResultSet();
		File file = File.createTempFile("testSortedSerializeLarge", ".test.tmp");
		
		CachedResultSet.sortedSerialize(rset, new SosTableRowComparator(), file, ROW_SIZE_FOR_SERIALIZATION);
		CachedResultSet instance = new CachedResultSet(file);
		assertThat(instance.getMetaData().getColumnCount(), is(equalTo(4)));

		//each row should be sorted from lowest to highest
		int i = 0;
		while(instance.next()) {
			TableRow row = TableRow.buildTableRow(instance);
			String rowNum = leftPadInt(i);
			assertThat("feat-" + rowNum, is(equalTo(row.getValue(feature))));
			assertThat("prop-" + rowNum, is(equalTo(row.getValue(observedProp))));
			assertThat("proc-" + rowNum, is(equalTo(row.getValue(procedure))));
			assertThat("time-" + rowNum, is(equalTo(row.getValue(time))));
			i++;
		}
		
		rset.close();
		instance.close();
		assertTrue("Temporary testSortedSerializeLarge files deleted", FileUtils.deleteQuietly(file));
	}
}
