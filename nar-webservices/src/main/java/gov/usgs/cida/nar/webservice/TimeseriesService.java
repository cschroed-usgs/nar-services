package gov.usgs.cida.nar.webservice;

import gov.usgs.cida.nar.mybatis.dao.AflowDao;
import gov.usgs.cida.nar.mybatis.model.Aflow;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gov.usgs.cida.nar.util.JSONUtil.toJSON;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
@Path("/timeseries")
public class TimeseriesService {

	private final static Logger log = LoggerFactory.getLogger(TimeseriesService.class);
	
	@GET
	@Path("/aflow/{siteQwId}")
	@Produces("application/json")
	public Response getAflow(@PathParam("siteQwId")String siteQwId) {
		AflowDao dao = new AflowDao();
		List<Aflow> aflow = dao.getAflow(siteQwId);
		return Response.ok(toJSON(aflow)).build();
	}
}
