package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.mybatis.dao.AloadsDao;
import gov.usgs.cida.nar.mybatis.model.Aloads;
import gov.usgs.cida.nar.util.DateUtil;
import java.util.List;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AloadsService implements NARService<Aloads>{

	private AloadsDao dao;
	
	private List<String> siteQwId;
	private List<String> constit;
	private List<String> modtypeExcludes;
	private String startDate;
	private String endDate;
	
	public AloadsService() {
		this(new AloadsDao());
	}
	
	public AloadsService(AloadsDao dao) {
		this.dao = dao;
	}
	
	public List<Aloads> request(List<String> siteQwId, List<String> constit, List<String> modtypeExcludes,
			String startDate, String endDate) {
		
		Integer startWy = DateUtil.getWaterYear(startDate);
		Integer endWy = DateUtil.getWaterYear(endDate);
		return dao.getAloads(siteQwId, constit, modtypeExcludes, startWy, endWy);
	}

	@Override
	public List<Aloads> request() {
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
