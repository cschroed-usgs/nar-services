package gov.usgs.cida.nar.webservice;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.mybatis.model.PestSites;
import gov.usgs.cida.nar.service.PestSitesService;
import gov.usgs.cida.nar.util.JSONUtil;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
@Path("/pesticides")
public class PesticidesWebservice {

	private static final Logger log = LoggerFactory.getLogger(PesticidesWebservice.class);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/summary/site/{siteQwId}")
	public Response getPestSitesBySite(@PathParam("siteQwId")String siteQwId) {
		log.debug("Pest sites requested for {}", siteQwId);
		PestSitesService service = new PestSitesService();
		List<PestSites> pestSites = service.request(Lists.newArrayList(siteQwId));
		return Response.ok(JSONUtil.toJSON(pestSites)).build();
	}
}
