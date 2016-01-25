package gov.usgs.cida.nar.resultset;

import gov.usgs.cida.nude.resultset.inmemory.PeekingResultSet;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;
import gov.usgs.cida.nude.util.NudeUtils;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class BeanResultSet extends PeekingResultSet {

	private Iterator iterator;
	
	public BeanResultSet(Iterator iterator) {
		this.iterator = iterator;
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
		return "IteratorWrappingResultSet";
	}

}
