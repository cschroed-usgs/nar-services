package gov.usgs.cida.nar.resultset;

import gov.usgs.cida.nude.resultset.inmemory.PeekingResultSet;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;

import java.sql.SQLException;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public abstract class OGCResultSet extends PeekingResultSet {
	
	@Override
	public void close() {
		try {
			super.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected abstract TableRow makeNextRow();
	
	@Override
	public String getCursorName() {
		return "OGCStreamingResultSet";
	}
	
	@Override
	protected void addNextRow() {
		TableRow row = this.makeNextRow();
		if (row != null) {
			this.nextRows.add(row);
		}
	}

}
