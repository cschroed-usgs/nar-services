package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.MloadsDao;
import gov.usgs.cida.nar.mybatis.model.Mloads;
import gov.usgs.cida.nar.util.DateUtil;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class MloadsService implements NARService {

	private MloadsDao dao;
	
	private List<String> siteQwId;
	private List<String> constit;
	private List<String> modtypeExcludes;
	private String startDate;
	private String endDate;
	
	public MloadsService() {
		this(new MloadsDao());
	}
	
	public MloadsService(MloadsDao dao) {
		this.dao = dao;
		this.siteQwId = null;
		this.constit = null;
		this.modtypeExcludes = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	public List<Mloads> request(List<String> siteQwId, List<String> constit, List<String> modtypeExcludes,
			String startDate, String endDate) {
		
		Integer startWy = DateUtil.getWaterYear(startDate);
		Integer endWy = DateUtil.getWaterYear(endDate);
		return dao.getMloads(siteQwId, constit, modtypeExcludes, startWy, endWy);
	}

	@Override
	public List request() {
		return request(siteQwId, constit, modtypeExcludes, startDate, endDate);
	}

	public void setSiteQwId(List<String> siteQwId) {
		this.siteQwId = siteQwId;
	}

	public void setConstit(List<String> constit) {
		this.constit = constit;
	}

	public void setModtypeExcludes(List<String> modtypeExcludes) {
		this.modtypeExcludes = modtypeExcludes;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
