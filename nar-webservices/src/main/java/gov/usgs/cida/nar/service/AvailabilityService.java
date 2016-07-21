package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.mybatis.model.NARData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AvailabilityService {
	private static final Logger log = LoggerFactory.getLogger(AvailabilityService.class);
	private String siteQwId;
	private String constit;
	private List<String> modtypeExcludes;
	
	public AvailabilityService() {
		this.siteQwId = null;
		this.constit = null;
		this.modtypeExcludes = null;
	}
	
	public List<TimeSeriesAvailability> request(String siteQwId, String constit, List<String> modtypeExcludes) {
		ArrayList<TimeSeriesAvailability> overallAvailability = new ArrayList<>();
		ArrayList<NARService<? extends NARData>> narServices = new ArrayList<NARService<? extends NARData>>(Arrays.asList(
			new AflowService(),
			new AloadsService(),
			new MflowService(),
			new MloadsService(),
			new DflowService(),
			new DiscqwService(),
			new PesticideSampleService()
		));
		for (NARService narService : narServices ){
			LinkedList<String> siteQwIds = new LinkedList<>();
			siteQwIds.add(siteQwId);
			narService.setSiteQwId(siteQwIds);
			if(narService instanceof IConstituentFilterable){
				LinkedList<String> constituents = new LinkedList<>();
				constituents.add(constit);
				((IConstituentFilterable)narService).setConstit(constituents);
			}
			if(narService instanceof IModtypeFilterable){
				((IModtypeFilterable)narService).setModtypeExcludes(modtypeExcludes);
			}
			
			List<TimeSeriesAvailability> availabilityForOneDataType = null;
			try{
				
				availabilityForOneDataType = narService.getAvailability();
			}
			catch(NotFoundException e){
				log.warn(e.getMessage());
			}
			if(null != availabilityForOneDataType && !availabilityForOneDataType.isEmpty()){
				overallAvailability.addAll(availabilityForOneDataType);
			}
		}
		return overallAvailability;
	}

	
	public List<TimeSeriesAvailability> request() {
		return request(siteQwId, constit, modtypeExcludes);
	}

	public void setSiteQwId(String siteQwId) {
		this.siteQwId = siteQwId;
	}

	public void setConstit(String constit) {
		this.constit = constit;
	}

	public void setModtypeExcludes(List<String> modtypeExcludes) {
		this.modtypeExcludes = modtypeExcludes;
	}


}
