package gov.usgs.cida.nar.webservice;

import gov.usgs.cida.nar.mybatis.model.Aflow;
import gov.usgs.cida.nar.mybatis.model.Aloads;
import gov.usgs.cida.nar.service.AflowService;
import gov.usgs.cida.nar.service.AloadsService;
import gov.usgs.cida.nar.util.JSONUtil;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
@Path("/timeseries")
public class TimeseriesWebservice {

	private final static Logger log = LoggerFactory.getLogger(TimeseriesWebservice.class);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/aflow/site/{siteQwId}")
	public Response getAflowBySite(@PathParam("siteQwId")String siteQwId,
			@QueryParam("startTime")String startTime, @QueryParam("endTime")String endTime) {
		log.debug("Aflow requested from {} for {} to {}", siteQwId, startTime, endTime);
		AflowService service = new AflowService();
		List<Aflow> aflows = service.request(siteQwId, startTime, endTime);
		return Response.ok(JSONUtil.toJSON(aflows)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/aloads/site/{siteQwId}")
	public Response getAloadsBySite(@PathParam("siteQwId")String siteQwId,
			@QueryParam("constit")String constit, @QueryParam("excludeModtype")List<String> excludeModtype,
			@QueryParam("startTime")String startTime, @QueryParam("endTime")String endTime) {
		log.debug("Aflow requested from {} for {} from {} to {}", siteQwId, constit, startTime, endTime);
		AloadsService service = new AloadsService();
		List<Aloads> aloads = service.request(siteQwId, constit, excludeModtype, startTime, endTime);
		return Response.ok(JSONUtil.toJSON(aloads)).build();
	}
}
