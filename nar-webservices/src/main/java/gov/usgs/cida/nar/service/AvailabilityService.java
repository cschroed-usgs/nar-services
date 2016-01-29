package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.domain.TimeSeriesDensityCategoryPair;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

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
	
	public List<TimeSeriesDensityCategoryPair> request(String siteQwId, String constit, List<String> modtypeExcludes) {
		ArrayList<TimeSeriesDensityCategoryPair> availabilityPairs = new ArrayList<>();
		for (NARService narService : serviceLoader ){
			if(narService instanceof IConstituentFilterable){
				LinkedList<String> constituents = new LinkedList<>();
				constituents.add(constit);
				((IConstituentFilterable)narService).setConstit(constituents);
			}
			if(narService instanceof IModtypeFilterable){
				((IModtypeFilterable)narService).setModtypeExcludes(modtypeExcludes);
			}
			if(narService.isAvailable()){
				TimeSeriesDensityCategoryPair pair = new TimeSeriesDensityCategoryPair(
					narService.getTimeStepDensity(),
					narService.getTimeSeriesCategory()
				);
				availabilityPairs.add(pair);
			}
		}
		return availabilityPairs;
	}

	
	public List<String> request() {
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
