package gov.usgs.cida.nar.service;

import gov.usgs.cida.nude.column.Column;
import gov.usgs.cida.nude.column.SimpleColumn;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;
import gov.usgs.cida.sos.ObservationMetadata;

import java.util.Comparator;

public class SosTableRowComparator implements Comparator<TableRow> {
	private static final Column PROCEDURE_IN_COL = new SimpleColumn(ObservationMetadata.PROCEDURE_ELEMENT);
	private static final Column OBSERVED_PROPERTY_IN_COL = new SimpleColumn(ObservationMetadata.OBSERVED_PROPERTY_ELEMENT);
	private static final Column FEATURE_OF_INTEREST_IN_COL = new SimpleColumn(ObservationMetadata.FEATURE_OF_INTEREST_ELEMENT);

	@Override
	public int compare(TableRow o1, TableRow o2) {
		if (o1.getValue(FEATURE_OF_INTEREST_IN_COL).equals(o2.getValue(FEATURE_OF_INTEREST_IN_COL))) {
			if (o1.getValue(OBSERVED_PROPERTY_IN_COL).equals(o2.getValue(OBSERVED_PROPERTY_IN_COL))) {
				return o1.getValue(PROCEDURE_IN_COL).compareTo(o2.getValue(PROCEDURE_IN_COL));
			} else {
				return o1.getValue(OBSERVED_PROPERTY_IN_COL).compareTo(o2.getValue(OBSERVED_PROPERTY_IN_COL));
			}
		} else {
			return o1.getValue(FEATURE_OF_INTEREST_IN_COL).compareTo(o2.getValue(FEATURE_OF_INTEREST_IN_COL));
		}
	}
}
