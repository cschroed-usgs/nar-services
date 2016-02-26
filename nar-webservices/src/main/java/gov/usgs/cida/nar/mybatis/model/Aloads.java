package gov.usgs.cida.nar.mybatis.model;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class Aloads extends NARData {

	private String siteAbb;
	private String siteQwId;
	private String siteFlowId;
	private String constit;
	private Integer wy;
	private String modtype;
	private String tons;
	private String tonsL95;
	private String tonsU95;
	private String fwc;
	private String yield;

	
	public String getSiteAbb() {
		return siteAbb;
	}

	public void setSiteAbb(String siteAbb) {
		this.siteAbb = siteAbb;
	}

	public String getSiteQwId() {
		return siteQwId;
	}

	public void setSiteQwId(String siteQwId) {
		this.siteQwId = siteQwId;
	}

	public String getSiteFlowId() {
		return siteFlowId;
	}

	public void setSiteFlowId(String siteFlowId) {
		this.siteFlowId = siteFlowId;
	}

	public Integer getWy() {
		return wy;
	}

	public void setWy(Integer wy) {
		this.wy = wy;
	}

	public String getConstit() {
		return constit;
	}

	public void setConstit(String constit) {
		this.constit = constit;
	}

	public String getModtype() {
		return modtype;
	}

	public void setModtype(String modtype) {
		this.modtype = modtype;
	}

	public String getTons() {
		return tons;
	}

	public void setTons(String tons) {
		this.tons = tons;
	}

	public String getTonsL95() {
		return tonsL95;
	}

	public void setTonsL95(String tonsL95) {
		this.tonsL95 = tonsL95;
	}

	public String getTonsU95() {
		return tonsU95;
	}

	public void setTonsU95(String tonsU95) {
		this.tonsU95 = tonsU95;
	}

	public String getFwc() {
		return fwc;
	}

	public void setFwc(String fwc) {
		this.fwc = fwc;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

}
