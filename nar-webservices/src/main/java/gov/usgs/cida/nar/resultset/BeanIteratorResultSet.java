package gov.usgs.cida.nar.resultset;

import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.resultset.inmemory.PeekingResultSet;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;
import gov.usgs.cida.nude.util.NudeUtils;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class BeanIteratorResultSet extends PeekingResultSet {

	private Iterator iterator;
	
	public BeanIteratorResultSet(Iterator iterator, ColumnGrouping colGroups) {
		this.iterator = iterator;
		this.columns = colGroups;
	}
	
	@Override
	protected void addNextRow() throws SQLException {
		TableRow row = null;
		if (iterator.hasNext()) {
			Object next = iterator.next();
			row = NudeUtils.makeTableRow(next);
		}
		if (row != null) {
			this.nextRows.add(row);
		}
		
	}

	@Override
	public String getCursorName() throws SQLException {
		return "BeanIteratorResultSet";
	}

}
