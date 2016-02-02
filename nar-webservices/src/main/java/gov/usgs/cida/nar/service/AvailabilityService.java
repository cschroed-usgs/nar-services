package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import org.joda.time.Interval;

public class AvailabilityService {

	private String siteQwId;
	private String constit;
	private List<String> modtypeExcludes;
	private static final ServiceLoader<NARService> serviceLoader = ServiceLoader.load(NARService.class);
	
	public AvailabilityService() {
		this.siteQwId = null;
		this.constit = null;
		this.modtypeExcludes = null;

	}
	
	public List<TimeSeriesAvailability> request(String siteQwId, String constit, List<String> modtypeExcludes) {
		ArrayList<TimeSeriesAvailability> overallAvailability = new ArrayList<>();
		for (NARService narService : serviceLoader ){
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
			List<TimeSeriesAvailability> availabilityForOneDataType = narService.getAvailability();
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
