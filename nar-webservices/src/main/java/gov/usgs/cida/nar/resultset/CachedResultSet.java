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
import java.io.FileNotFoundException;
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
import java.util.UUID;

import org.apache.commons.io.FileUtils;
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
	
	private static final int ROW_SIZE_FOR_SERIALIZATION = 10000;  //low numbers = lower memory usage, higher disk access/usage
	
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
		try (FileOutputStream f = new FileOutputStream(file); ObjectOutput s = new ObjectOutputStream(f)) {
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
	 * Does a sort of a result set and serializes the sorted data into a file. The sorting is combination of in-memory
	 * and in disk sorting to balance memory usage with number of file system accesses.
	 *  
	 * @param rset {@link java.sql.Resultset} to sort and cache to disk
	 * @param file {@link java.io.File} to write to
	 * @throws java.io.IOException
	 * @throws java.sql.SQLException
	 */
	public static void sortedSerialize(ResultSet rset, SortedSet<OrderedFilter> filters, File file) throws IOException, SQLException {
		List<File> dataSubsetFiles = new ArrayList<>();
		
		try {
			ColumnGrouping columnGrouping = ColumnGrouping.getColumnGrouping(rset);
			//Read N number or rows to serialize and sort out to disc, to limit number of rows we
			//keep in-memory at any given time.
			List<TableRow> currentRowSet = new ArrayList<>();
			while(rset.next()) {
				TableRow tr = TableRow.buildTableRow(rset);
				currentRowSet.add(tr);
				
				if(currentRowSet.size() >= ROW_SIZE_FOR_SERIALIZATION) { //reached subset size
					dataSubsetFiles.add(sortedSerialize(currentRowSet, columnGrouping, filters));
					currentRowSet = new ArrayList<>();
				}
			}
			//last subset
			if(currentRowSet.size() > 0) {
				dataSubsetFiles.add(sortedSerialize(currentRowSet, columnGrouping, filters));
				currentRowSet = new ArrayList<>();
			}
			
			//merge all data into a single File on disk
			binaryMergeSortedDataSubsets(dataSubsetFiles, file);
		} finally {
			for(File f : dataSubsetFiles) {
				FileUtils.deleteQuietly(f);
			}
		}
	}
	
	/**
	 * Recursively merge a set of files into. The terminal case writes the fully merged set out to file out.
	 * @param dataSubsetFiles set of files to merge
	 * @param out target for final merged data
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static void binaryMergeSortedDataSubsets(final List<File> dataSubsetFiles, File out) throws FileNotFoundException, IOException {
		//terminal case 1, only one file, copy it to the out file
		if(dataSubsetFiles.size() == 1) {
			try (FileOutputStream f = new FileOutputStream(out); FileInputStream in = new FileInputStream(dataSubsetFiles.get(0))) {
				IOUtils.copy(in, f);
			}
		} else { //recursive case, divide file set in half and merge the halves recursively
			File mergedLeftSetFile = FileUtils.getFile(FileUtils.getTempDirectory(), UUID.randomUUID().toString() + ".merged.subset");
			File mergedRightSetFile = FileUtils.getFile(FileUtils.getTempDirectory(), UUID.randomUUID().toString() + ".merged.subset");
			
			List<File> leftFileSet = dataSubsetFiles.subList(0, dataSubsetFiles.size()/2);
			List<File> rightFileSet = dataSubsetFiles.subList(dataSubsetFiles.size()/2, dataSubsetFiles.size());
			
			//merge the subsets
			try{
				binaryMergeSortedDataSubsets(leftFileSet, mergedLeftSetFile);
				binaryMergeSortedDataSubsets(rightFileSet, mergedRightSetFile);

				CachedResultSet leftRows = new CachedResultSet(mergedLeftSetFile);
				CachedResultSet rightRows  =new CachedResultSet(mergedRightSetFile);
				
				try (FileOutputStream f = new FileOutputStream(out); ObjectOutput s = new ObjectOutputStream(f)) {
					ColumnGrouping columnGrouping = ColumnGrouping.getColumnGrouping(leftRows);
					ResultSetMetaData metaData = new CGResultSetMetaData(columnGrouping);
					s.writeObject(metaData);
					
					mergeToOutput(leftRows, rightRows, s);
				} catch (SQLException e) {
					log.warn("Error merging subsets", e);
				} 
			} finally {
				FileUtils.deleteQuietly(mergedLeftSetFile);
				FileUtils.deleteQuietly(mergedRightSetFile);
			}
		}
	}
	
	private static void mergeToOutput(CachedResultSet leftRows, CachedResultSet rightRows, ObjectOutput s) throws IOException, SQLException {
		TableRow currentLeft = getNextRow(leftRows);
		TableRow currentRight = getNextRow(rightRows);
		
		while (currentLeft != null || currentRight != null) {
			if(currentLeft == null) {
				s.writeObject(currentRight);
				currentRight = getNextRow(rightRows);
			} else if(currentRight == null) {
				s.writeObject(currentLeft);
				currentLeft = getNextRow(leftRows);
			} else if(compareRows(currentLeft, currentRight) < 0) {
				s.writeObject(currentLeft);
				currentLeft = getNextRow(leftRows);
			} else {
				s.writeObject(currentRight);
				currentRight = getNextRow(rightRows);
			}
		}
	}
	
	private static TableRow getNextRow(CachedResultSet rset) throws SQLException {
		TableRow row = null;
		
		if(rset.next()) {
			row = TableRow.buildTableRow(rset);
		}
		
		return row;
	}
	
	//Sort by featuresOfInterest, 
	private static int compareRows(TableRow row1, TableRow row2) {
		return new OrderedFilter(row1.getValue(PROCEDURE_IN_COL), row1.getValue(OBSERVED_PROPERTY_IN_COL), row1.getValue(FEATURE_OF_INTEREST_IN_COL)).
				compareTo(new OrderedFilter(row2.getValue(PROCEDURE_IN_COL), row2.getValue(OBSERVED_PROPERTY_IN_COL), row2.getValue(FEATURE_OF_INTEREST_IN_COL)));
	}
	
	/**
	 * Sort's a list of TableRows based on the OrderedFilters passed in.
	 * 
	 * @param rows TableRows to sort
	 * @param columnGrouping ColumnGrouping describing the tablerows
	 * @param filters filters used for sorting
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	private static File sortedSerialize(final List<TableRow> rows, ColumnGrouping columnGrouping, SortedSet<OrderedFilter> filters) throws IOException, SQLException {
		File file = FileUtils.getFile(FileUtils.getTempDirectory(), UUID.randomUUID().toString() + ".sorted.subset");
		
		//In-memory data structure to store rows that are not yet serialized out to disc.
		//Key is the filter sort, value is a list of TableRows which match the sort key. 
		HashMap<String, List<TableRow>> inMemoryCache = new HashMap<>();
		
		try (FileOutputStream out = new FileOutputStream(file); ObjectOutput s = new ObjectOutputStream(out)) {
			ResultSetMetaData metaData = new CGResultSetMetaData(columnGrouping);
			s.writeObject(metaData);
			
			//move all TRs into HashMap cache
			for(TableRow tr : rows) {
				if(filters.size() > 0 && matchesFilter(tr, filters.first())) { 
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
			
			//for each filter serialize the matching rows out to the file
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
		
		return file;
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
