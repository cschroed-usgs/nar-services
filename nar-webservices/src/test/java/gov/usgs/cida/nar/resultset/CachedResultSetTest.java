package gov.usgs.cida.nar.resultset;

import gov.usgs.cida.nude.column.Column;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.column.SimpleColumn;
import gov.usgs.cida.nude.resultset.inmemory.StringTableResultSet;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;
import gov.usgs.cida.sos.ObservationMetadata;
import gov.usgs.cida.sos.OrderedFilter;

import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author jiwalker
 */
public class CachedResultSetTest {

	private static final Column procedure = new SimpleColumn(ObservationMetadata.PROCEDURE_ELEMENT);
	private static final Column observedProp = new SimpleColumn(ObservationMetadata.OBSERVED_PROPERTY_ELEMENT);
	
	private static ColumnGrouping cg;
	static {
		List<Column> columns = new LinkedList<>();
		columns.add(procedure);
		columns.add(observedProp);
		cg = new ColumnGrouping(columns);
	}
	
	private ResultSet makeResultSet() {
		StringTableResultSet rs = new StringTableResultSet(cg);
		rs.addRow(makeRow("6", "prop2"));
		rs.addRow(makeRow("6", "prop1"));
		rs.addRow(makeRow("1", "prop3"));
		rs.addRow(makeRow("2", "prop1"));
		rs.addRow(makeRow("1", "prop1"));
		return rs;
	}
	
	private TableRow makeRow(String prim, String val) {
		Map<Column, String> row = new HashMap<>();
		row.put(procedure, prim);
		row.put(observedProp, val);
		return new TableRow(cg, row);
	}

	/**
	 * Test of serialize method, of class CachedResultSet.
	 */
	@Test
	public void testSerialize() throws Exception {
		ResultSet rset = makeResultSet();
		File file = File.createTempFile("testSerialize", "tmp");

		CachedResultSet.serialize(rset, file);
		CachedResultSet instance = new CachedResultSet(file);
		assertThat(instance.getMetaData().getColumnCount(), is(equalTo(2)));
		
		instance.next();
		TableRow row1 = TableRow.buildTableRow(instance);
		assertThat("6", is(equalTo(row1.getValue(procedure))));
		assertThat("prop2", is(equalTo(row1.getValue(observedProp))));
		
		instance.next();
		TableRow row2 = TableRow.buildTableRow(instance);
		assertThat("6", is(equalTo(row2.getValue(procedure))));
		assertThat("prop1", is(equalTo(row2.getValue(observedProp))));
		
		instance.next();
		TableRow row3 = TableRow.buildTableRow(instance);
		assertThat("1", is(equalTo(row3.getValue(procedure))));
		assertThat("prop3", is(equalTo(row3.getValue(observedProp))));
		
		instance.next();
		TableRow row4 = TableRow.buildTableRow(instance);
		assertThat("2", is(equalTo(row4.getValue(procedure))));
		assertThat("prop1", is(equalTo(row4.getValue(observedProp))));
		
		instance.next();
		TableRow row5 = TableRow.buildTableRow(instance);
		assertThat("1", is(equalTo(row5.getValue(procedure))));
		assertThat("prop1", is(equalTo(row5.getValue(observedProp))));
		
		rset.close();
		FileUtils.deleteQuietly(file);
	}
	
	/**
	 * Test of serialize method, of class CachedResultSet.
	 */
	@Test
	public void testSortedSerialize() throws Exception {
		ResultSet rset = makeResultSet();
		File file = File.createTempFile("testSortedSerialize", "tmp");
		
		//TODO
		//Sorting filters
//		SortedSet<OrderedFilter> filters = new TreeSet<>();
//		CachedResultSet.sortedSerialize(rset, filters, file);
//		CachedResultSet instance = new CachedResultSet(file);
//		assertThat(instance.getMetaData().getColumnCount(), is(equalTo(2)));
//		
//		while (instance.next()) {
//			TableRow row = TableRow.buildTableRow(instance);
//			String value = row.getValue(valueCol);
//			assertThat("test" + i++, is(equalTo(value)));
//		}
		rset.close();
		FileUtils.deleteQuietly(file);
	}

}
