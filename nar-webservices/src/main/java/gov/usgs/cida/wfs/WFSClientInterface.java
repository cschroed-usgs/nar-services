package gov.usgs.cida.wfs;

import java.io.IOException;
import java.net.MalformedURLException;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.opengis.filter.Filter;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public interface WFSClientInterface extends AutoCloseable {

    public void setupDatastoreFromGetCaps(String getCapsUrlString) throws MalformedURLException, IOException;
    
    public void setupDatastoreFromEndpoint(String wfsUrl) throws MalformedURLException, IOException;
    
    public SimpleFeatureCollection getFeatureCollection(String typeName, Filter filter) throws IOException;
    
    public String[] getTypeNames() throws IOException;

}
