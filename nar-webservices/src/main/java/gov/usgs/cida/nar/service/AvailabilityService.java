package gov.usgs.cida.nar.service;

import com.google.common.collect.Lists;
import gov.usgs.cida.nar.domain.TimeSeriesAvailability;
import gov.usgs.cida.nar.mybatis.model.NARData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AvailabilityService {

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
			new MflowService()
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
