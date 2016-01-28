package gov.usgs.cida.nar.webservice;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.mybatis.model.Aflow;
import gov.usgs.cida.nar.mybatis.model.Aloads;
import gov.usgs.cida.nar.mybatis.model.Dflow;
import gov.usgs.cida.nar.mybatis.model.Discqw;
import gov.usgs.cida.nar.mybatis.model.Mflow;
import gov.usgs.cida.nar.mybatis.model.Mloads;
import gov.usgs.cida.nar.service.AflowService;
import gov.usgs.cida.nar.service.AloadsService;
import gov.usgs.cida.nar.service.DflowService;
import gov.usgs.cida.nar.service.DiscqwService;
import gov.usgs.cida.nar.service.MflowService;
import gov.usgs.cida.nar.service.MloadsService;
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
		List<Aflow> aflows = service.request(Lists.newArrayList(siteQwId), startTime, endTime);
		return Response.ok(JSONUtil.toJSON(aflows)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/aloads/site/{siteQwId}")
	public Response getAloadsBySite(@PathParam("siteQwId")String siteQwId,
			@QueryParam("constit")List<String> constit, @QueryParam("excludeModtype")List<String> excludeModtype,
			@QueryParam("startTime")String startTime, @QueryParam("endTime")String endTime) {
		log.debug("Aloads requested from {} for {} from {} to {}", siteQwId, constit, startTime, endTime);
		AloadsService service = new AloadsService();
		List<Aloads> aloads = service.request(Lists.newArrayList(siteQwId), constit, excludeModtype, startTime, endTime);
		return Response.ok(JSONUtil.toJSON(aloads)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dflow/site/{siteQwId}")
	public Response getDflowBySite(@PathParam("siteQwId")String siteQwId,
			@QueryParam("startTime")String startTime, @QueryParam("endTime")String endTime) {
		log.debug("Dflow requested from {} from {} to {}", siteQwId, startTime, endTime);
		DflowService service = new DflowService();
		List<Dflow> dflow = service.request(Lists.newArrayList(siteQwId), startTime, endTime);
		return Response.ok(JSONUtil.toJSON(dflow)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/discqw/site/{siteQwId}")
	public Response getDiscqwBySite(@PathParam("siteQwId")String siteQwId,
			@QueryParam("constit")List<String> constit, @QueryParam("startTime")String startTime, @QueryParam("endTime")String endTime) {
		log.debug("Discqw requested from {} for {} from {} to {}", siteQwId, constit, startTime, endTime);
		DiscqwService service = new DiscqwService();
		List<Discqw> discqw = service.request(Lists.newArrayList(siteQwId), constit, startTime, endTime);
		return Response.ok(JSONUtil.toJSON(discqw)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/mflow/site/{siteQwId}")
	public Response getMflowBySite(@PathParam("siteQwId")String siteQwId,
			@QueryParam("startTime")String startTime, @QueryParam("endTime")String endTime) {
		log.debug("Aflow requested from {} from {} to {}", siteQwId, startTime, endTime);
		MflowService service = new MflowService();
		List<Mflow> mflow = service.request(Lists.newArrayList(siteQwId), startTime, endTime);
		return Response.ok(JSONUtil.toJSON(mflow)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/mloads/site/{siteQwId}")
	public Response getMloadsBySite(@PathParam("siteQwId")String siteQwId,
			@QueryParam("constit")List<String> constit, @QueryParam("excludeModtype")List<String> excludeModtype,
			@QueryParam("startTime")String startTime, @QueryParam("endTime")String endTime) {
		log.debug("Aflow requested from {} for {} from {} to {}", siteQwId, constit, startTime, endTime);
		MloadsService service = new MloadsService();
		List<Mloads> mloads = service.request(Lists.newArrayList(siteQwId), constit, excludeModtype, startTime, endTime);
		return Response.ok(JSONUtil.toJSON(mloads)).build();
	}
}
