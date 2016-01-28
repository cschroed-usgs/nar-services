package gov.usgs.cida.nar.connector;

import gov.usgs.cida.nar.mybatis.model.NARData;
import gov.usgs.cida.nar.resultset.BeanIteratorResultSet;
import gov.usgs.cida.nar.service.NARService;
import gov.usgs.cida.nude.connector.IConnector;
import gov.usgs.cida.nude.connector.parser.IParser;
import java.io.Closeable;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public abstract class MyBatisConnector implements IConnector, Closeable {

	private NARService<NARData> service;
	
	public MyBatisConnector(NARService service) {
		this.service = service;
	}
	
	@Override
	public ResultSet getResultSet() {
		List<? extends NARData> data = service.request();
		Iterator<? extends NARData> iterator = data.iterator();
		BeanIteratorResultSet resultSet = new BeanIteratorResultSet(iterator, this.getExpectedColumns());
		return resultSet;
	}

	@Override
	public void close() throws IOException {
		return;
	}
	
	@Override
	public void addInput(ResultSet rs) {
		return;
	}

	@Override
	public String getStatement() {
		return null;
	}
	
	@Override
	public IParser getParser() {
		return null;
	}

	@Override
	public boolean isValidInput() {
		return true;
	}

	@Override
	public boolean isReady() {
		return true;
	}

}
