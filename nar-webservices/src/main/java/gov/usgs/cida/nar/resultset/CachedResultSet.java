package gov.usgs.cida.nar.resultset;

import gov.usgs.cida.nude.column.CGResultSetMetaData;
import gov.usgs.cida.nude.column.Column;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.column.SimpleColumn;
import gov.usgs.cida.nude.resultset.inmemory.PeekingResultSet;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;
import gov.usgs.cida.sos.ObservationMetadata;
import gov.usgs.cida.sos.OrderedFilter;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 * @author thongsav
 */
public class CachedResultSet extends PeekingResultSet {
	private static final Column PROCEDURE_IN_COL = new SimpleColumn(ObservationMetadata.PROCEDURE_ELEMENT);
	private static final Column OBSERVED_PROPERTY_IN_COL = new SimpleColumn(ObservationMetadata.OBSERVED_PROPERTY_ELEMENT);
	private static final Column FEATURE_OF_INTEREST_IN_COL = new SimpleColumn(ObservationMetadata.FEATURE_OF_INTEREST_ELEMENT);
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -1057296193256896787L;
	
	private static final Logger log = LoggerFactory.getLogger(CachedResultSet.class);
	
	private final ObjectInputStream objInputStream;
	
	public CachedResultSet(File file) throws IOException {
		this.objInputStream = new ObjectInputStream(new FileInputStream(file));
		this.metadata = deserializeMetadata(this.objInputStream);
		/* This is cheating because I know the implementation */
		if (this.metadata instanceof CGResultSetMetaData) {
			this.columns = ((CGResultSetMetaData)this.metadata).getColumnGrouping();
		} else {
			try {
				List<Column> colList = new LinkedList<>();
				for (int i = 1; i<=this.metadata.getColumnCount(); i++) {
					colList.add(new SimpleColumn(this.metadata.getColumnName(i)));
				}
				this.columns = new ColumnGrouping(colList);
			} catch(SQLException ex) {
				log.error("Unable to get column info", ex);
			}
		}
	}
	
	/**
	 * Write to disk a serialized resultset
	 * @param rset {@link java.sql.Resultset} to cache to disk
	 * @param file {@link java.io.File} to write to
	 * @throws java.io.IOException
	 * @throws java.sql.SQLException
	 */
	public static void serialize(ResultSet rset, File file) throws IOException, SQLException {
		FileOutputStream f = new FileOutputStream(file);
		try (ObjectOutput s = new ObjectOutputStream(f)) {
			ColumnGrouping columnGrouping = ColumnGrouping.getColumnGrouping(rset);
			ResultSetMetaData metaData = new CGResultSetMetaData(columnGrouping);
			s.writeObject(metaData);

			while (rset.next()) {
				TableRow tr = TableRow.buildTableRow(rset);
				s.writeObject(tr);
			}
		}
	}
	
	/**
	 * Does an in-memory sort of the result set based on the OrderedFilters passed in.
	 *  
	 * @param rset {@link java.sql.Resultset} to sort and cache to disk
	 * @param file {@link java.io.File} to write to
	 * @throws java.io.IOException
	 * @throws java.sql.SQLException
	 */
	public static void sortedSerialize(ResultSet rset, SortedSet<OrderedFilter> filters, File file) throws IOException, SQLException {
		FileOutputStream out = new FileOutputStream(file);
		
		//In-memory data structure to store rows that are not yet serialized out to disc.
		//Key is the filter sort, value is a list of TableRows which match the sort key. 
		HashMap<String, List<TableRow>> inMemoryCache = new HashMap<>();
		
		try (ObjectOutput s = new ObjectOutputStream(out)) {
			ColumnGrouping columnGrouping = ColumnGrouping.getColumnGrouping(rset);
			ResultSetMetaData metaData = new CGResultSetMetaData(columnGrouping);
			s.writeObject(metaData);
			
			//loop through filters...
			//For each filter, loop through the inMemoryCache looking to pluck rows that match plucking out rows that match the sort order. When done,
			//continue down the result set either plucking matching rows or moving rows to the inMemoryCache.
			//Repeat until no filters are left
			for(OrderedFilter f : filters) {
				List<String> keysToRemove = new ArrayList<>();
				for(String k : inMemoryCache.keySet()) { //pluck from inMemory
					List<TableRow> currentRows = inMemoryCache.get(k);
					if(currentRows != null && currentRows.size() > 0) {
						List<TableRow> rowsToRemove = new ArrayList<>();
						for(TableRow tr : currentRows) {
							if(matchesFilter(tr, f)) {
								s.writeObject(tr);
								rowsToRemove.add(tr);
							}
						}
						for(TableRow remove : rowsToRemove) {
							currentRows.remove(remove);
						}
					} else if(currentRows != null && currentRows.size() == 0) {
						keysToRemove.add(k);
					}
				}
				for(String remove : keysToRemove) {
					inMemoryCache.remove(remove);
				}
				
				//go through result set, worst case will have entire result set dumped into inMemoryCache
				while (rset.next()) {
					TableRow tr = TableRow.buildTableRow(rset);
					if(matchesFilter(tr, f)) { 
						s.writeObject(tr);
					} else { //does not match, put into cache
						String key = tr.getValue(PROCEDURE_IN_COL) + tr.getValue(OBSERVED_PROPERTY_IN_COL) + tr.getValue(FEATURE_OF_INTEREST_IN_COL);
						List<TableRow> cachedRows = inMemoryCache.get(key);
						if(cachedRows == null) {
							cachedRows = new ArrayList<>();
							inMemoryCache.put(key, cachedRows);
						}
						cachedRows.add(tr);
					}
				}
			}
			
			//serialize rows left in the inMemoryCache in order found
			List<String> keysToRemove = new ArrayList<>();
			for(String k : inMemoryCache.keySet()) { 
				List<TableRow> currentRows = inMemoryCache.get(k);
				if(currentRows != null && currentRows.size() > 0) {
					List<TableRow> rowsToRemove = new ArrayList<>();
					for(TableRow tr : currentRows) {
						s.writeObject(tr);
						rowsToRemove.add(tr);
					}
					for(TableRow remove : rowsToRemove) {
						currentRows.remove(remove);
					}
				} else if(currentRows != null && currentRows.size() == 0) {
					keysToRemove.add(k);
				}
			}
			for(String remove : keysToRemove) {
				inMemoryCache.remove(remove);
			}

		}
	}
	
	
	private static boolean matchesFilter(TableRow row, OrderedFilter filter) {
		boolean allEqual = true;
		if (row != null) {
			if (filter.procedure != null &&
					!filter.procedure.equals(row.getValue(PROCEDURE_IN_COL))) {
				allEqual = false;
			}
			if (filter.observedProperty != null &&
					!filter.observedProperty.equals(row.getValue(OBSERVED_PROPERTY_IN_COL))) {
				allEqual = false;
			}
			if (filter.featureOfInterest != null &&
					!filter.featureOfInterest.equals(row.getValue(FEATURE_OF_INTEREST_IN_COL))) {
				allEqual = false;
			}
		}
		return allEqual;
	}
	

	@Override
	protected void addNextRow() throws SQLException {
		TableRow row = null;
		try {
			Object obj = objInputStream.readObject();
			if (obj instanceof TableRow) {
				row = (TableRow)obj;
			}
		} catch (EOFException e) {
			// Done
			row = null;
		} catch (ClassNotFoundException | IOException e) {
			log.debug(e.getMessage(), e);
		}
		if (row != null) {
			this.nextRows.add(row);
		}
	}

	@Override
	public String getCursorName() throws SQLException {
		return "cacheCursor";
	}
	
	private static ResultSetMetaData deserializeMetadata(ObjectInputStream input) {
		ResultSetMetaData result = null;
		try {
			Object obj = input.readObject();
			if (obj instanceof ResultSetMetaData) {
				result = (ResultSetMetaData)obj;
			} else {
				throw new ClassCastException("Object read is not of correct type");
			}
		}
		catch (EOFException ex) {
			log.debug("No metadata for resultset, making empty metadata");
			result = new CGResultSetMetaData(new ColumnGrouping(new LinkedList<Column>()));
		}
		catch (IOException | ClassNotFoundException ex) {
			log.error(ex.getMessage(), ex);
		}

		return result;
	}

	@Override
	public void close() throws SQLException {
		IOUtils.closeQuietly(this.objInputStream);
		super.close();
	}

}
