package gov.usgs.cida.nar.resultset;

import gov.usgs.cida.nude.column.CGResultSetMetaData;
import gov.usgs.cida.nude.column.Column;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.column.SimpleColumn;
import gov.usgs.cida.nude.resultset.inmemory.PeekingResultSet;
import gov.usgs.cida.nude.resultset.inmemory.TableRow;

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
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
	protected static final int ROW_SIZE_FOR_SERIALIZATION = 500000;  //low numbers = lower memory usage, higher disk access/usage
	
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
	 */
	public static void serialize(ResultSet rset, File file) throws IOException {
		try (FileOutputStream f = new FileOutputStream(file); ObjectOutput s = new ObjectOutputStream(f)) {
			ColumnGrouping columnGrouping = ColumnGrouping.getColumnGrouping(rset);
			ResultSetMetaData metaData = new CGResultSetMetaData(columnGrouping);
			s.writeObject(metaData);

			while (rset.next()) {
				TableRow tr = TableRow.buildTableRow(rset);
				s.writeObject(tr);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while attempting to serialize ResultSet", e);
		} 
	}
	
	/**
	 * Does a sort of a result set and serializes the sorted data into a file. The sorting is combination of in-memory
	 * and in disk sorting to balance memory usage with number of file system accesses.
	 *  
	 * @param rset {@link java.sql.Resultset} to sort and cache to disk
	 * @param file {@link java.io.File} to write to
	 * @throws java.io.IOException
	 */
	public static void sortedSerialize(ResultSet rset, Comparator<TableRow> sortBy, File file) throws IOException {
		List<File> dataSubsetFiles = new ArrayList<>();
		
		try {
			ColumnGrouping columnGrouping = ColumnGrouping.getColumnGrouping(rset);
			//Read N number or rows to serialize and sort out to disc, to limit number of rows we
			//keep in-memory at any given time.
			TableRow[] currentRowSet = new TableRow[ROW_SIZE_FOR_SERIALIZATION];
			int arrayPointer = -1;
			while(rset.next()) {
				arrayPointer++;
				
				TableRow tr = TableRow.buildTableRow(rset);
				currentRowSet[arrayPointer] = tr;
				
				if(arrayPointer == ROW_SIZE_FOR_SERIALIZATION - 1) { //reached subset size
					dataSubsetFiles.add(sortedSerialize(currentRowSet, columnGrouping, sortBy));
					currentRowSet = new TableRow[ROW_SIZE_FOR_SERIALIZATION];
					arrayPointer = -1;
				}
			}
			//last subset
			if(arrayPointer >= 0) {
				TableRow[] lastSet = Arrays.copyOfRange(currentRowSet, 0, arrayPointer + 1);
				dataSubsetFiles.add(sortedSerialize(lastSet, columnGrouping, sortBy));
				currentRowSet = null;
			}
			
			//merge all data into a single File on disk
			binaryMergeSortedDataSubsets(dataSubsetFiles, sortBy, file);
		} catch (SQLException e) {
			log.warn("Unhandled SQLException", e);
		} finally {
			for(File f : dataSubsetFiles) {
				FileUtils.deleteQuietly(f);
			}
		}
	}
	
	/**
	 * Sort's a list of TableRows based on the comparator
	 * 
	 * @param rows TableRows to sort
	 * @param columnGrouping ColumnGrouping describing the tablerows
	 * @param filters filters used for sorting
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	private static File sortedSerialize(final TableRow[] rows, ColumnGrouping columnGrouping, Comparator<TableRow> sortBy) throws IOException, SQLException {
		File file = FileUtils.getFile(FileUtils.getTempDirectory(), UUID.randomUUID().toString() + ".sorted.subset");
		
		try (FileOutputStream out = new FileOutputStream(file); ObjectOutput s = new ObjectOutputStream(out)) {
			ResultSetMetaData metaData = new CGResultSetMetaData(columnGrouping);
			s.writeObject(metaData);
			
			Arrays.sort(rows, sortBy);
			for(TableRow r : rows) {
				s.writeObject(r);
			}
		}
		
		return file;
	}
	
	/**
	 * Recursively merge a set of files into. The terminal case writes the fully merged set out to file out.
	 * @param dataSubsetFiles set of files to merge
	 * @param out target for final merged data
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static void binaryMergeSortedDataSubsets(final List<File> dataSubsetFiles, Comparator<TableRow> rowComparator, File out) throws FileNotFoundException, IOException {
		//terminal case 1, only one file, copy it to the out file
		if(dataSubsetFiles.size() == 1) {
			try (FileOutputStream f = new FileOutputStream(out); FileInputStream in = new FileInputStream(dataSubsetFiles.get(0))) {
				IOUtils.copy(in, f);
			}
		} else { //recursive case, divide file set in half and merge the halves recursively
			//merge the subsets
			File mergedLeftSetFile = FileUtils.getFile(FileUtils.getTempDirectory(), UUID.randomUUID().toString() + ".merged.subset");
			File mergedRightSetFile = FileUtils.getFile(FileUtils.getTempDirectory(), UUID.randomUUID().toString() + ".merged.subset");

			CachedResultSet leftRows = null; 
			CachedResultSet rightRows = null;
			try{
				List<File> leftFileSet = dataSubsetFiles.subList(0, dataSubsetFiles.size()/2);
				List<File> rightFileSet = dataSubsetFiles.subList(dataSubsetFiles.size()/2, dataSubsetFiles.size());
				
				binaryMergeSortedDataSubsets(leftFileSet, rowComparator, mergedLeftSetFile);
				binaryMergeSortedDataSubsets(rightFileSet, rowComparator, mergedRightSetFile);

				leftRows = new CachedResultSet(mergedLeftSetFile);
				rightRows = new CachedResultSet(mergedRightSetFile);
				try (FileOutputStream f = new FileOutputStream(out); ObjectOutput s = new ObjectOutputStream(f)) {
					ColumnGrouping columnGrouping = ColumnGrouping.getColumnGrouping(leftRows);
					ResultSetMetaData metaData = new CGResultSetMetaData(columnGrouping);
					s.writeObject(metaData);
					
					mergeToOutput(leftRows, rightRows, rowComparator, s);
				}
			} finally {
				leftRows.close();
				rightRows.close();
				boolean leftDelete = FileUtils.deleteQuietly(mergedLeftSetFile);
				boolean rightDelete = FileUtils.deleteQuietly(mergedRightSetFile);
				log.trace("File " + mergedLeftSetFile.getName() + " delete status: " + leftDelete + 
						"\nFile " + mergedRightSetFile.getName() + " delete status: " + rightDelete);
			}
		}
	}
	
	private static void mergeToOutput(CachedResultSet leftRows, CachedResultSet rightRows, Comparator<TableRow> rowComparator, ObjectOutput s) throws IOException {
		TableRow currentLeft = getNextRow(leftRows);
		TableRow currentRight = getNextRow(rightRows);
		
		while (currentLeft != null || currentRight != null) {
			if(currentLeft == null) {
				s.writeObject(currentRight);
				currentRight = getNextRow(rightRows);
			} else if(currentRight == null) {
				s.writeObject(currentLeft);
				currentLeft = getNextRow(leftRows);
			} else if(rowComparator.compare(currentLeft, currentRight) < 0) {
				s.writeObject(currentLeft);
				currentLeft = getNextRow(leftRows);
			} else {
				s.writeObject(currentRight);
				currentRight = getNextRow(rightRows);
			}
		}
	}
	
	private static TableRow getNextRow(CachedResultSet rset) {
		TableRow row = null;
		
		try {
			if(rset.next()) {
				row = TableRow.buildTableRow(rset);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error getting next row", e);
		}
		
		return row;
	}
	
	@Override
	protected void addNextRow() {
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
	public String getCursorName() {
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
	public void close() {
		IOUtils.closeQuietly(this.objInputStream);
		try {
			super.close();
		} catch (SQLException e) {
			throw new RuntimeException("Could not close CachedResultSet", e);
		}
	}

}
