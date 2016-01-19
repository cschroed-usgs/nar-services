package gov.usgs.cida.nar.resultset;

import gov.usgs.cida.nar.connector.SOSClient;
import gov.usgs.cida.nar.connector.SOSConnector;
import gov.usgs.cida.nar.service.DownloadType;
import gov.usgs.cida.nude.column.Column;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.column.SimpleColumn;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;
import gov.usgs.cida.sos.Observation;
import gov.usgs.cida.sos.ObservationMetadata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class SOSResultSet extends OGCResultSet {
	
	private static final Logger log = LoggerFactory.getLogger(SOSResultSet.class);
	
	private ResultSet loadedResultSet;

	public SOSResultSet(SOSClient client, ColumnGrouping colGroups) {
		loadedResultSet = client.readFile();
		this.columns = colGroups;
	}

	@Override
	public void close() {
		try {
			loadedResultSet.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		super.close();
	}
	
	@Override
	protected TableRow makeNextRow() {
		TableRow row = null;
		try {
			TableRow inRow = null;
			if(loadedResultSet.next()) {
				inRow = TableRow.buildTableRow(loadedResultSet);
				Map<Column, String> resultMap = new HashMap<>();
				for (Column col : columns) {
					String attribute = null;
					if (col.equals(SOSConnector.SOS_DATE_COL)) {
						attribute = inRow.getValue(new SimpleColumn(Observation.TIME_ELEMENT));
					}
					else if (col.equals(SOSConnector.SOS_MOD_TYPE_COL)) {
						String procedure = inRow.getValue(new SimpleColumn(ObservationMetadata.PROCEDURE_ELEMENT));
						attribute = DownloadType.getModTypeFromProcedure(procedure);
					}
					else if (col.equals(SOSConnector.SOS_CONSTITUENT_COL)) {
						attribute = inRow.getValue(new SimpleColumn(ObservationMetadata.OBSERVED_PROPERTY_ELEMENT));
					}
					else if (col.equals(SOSConnector.SOS_SITE_COL)) {
						attribute = inRow.getValue(new SimpleColumn(ObservationMetadata.FEATURE_OF_INTEREST_ELEMENT));
					}
					else {
						attribute = inRow.getValue(new SimpleColumn(Observation.VALUE_ELEMENT));
					}
					resultMap.put(col, attribute);
				}
				row = new TableRow(columns, resultMap);
			}
		} catch (SQLException ex) {
			log.debug("Problem with resultset", ex);
		}
		return row;
	}

}
