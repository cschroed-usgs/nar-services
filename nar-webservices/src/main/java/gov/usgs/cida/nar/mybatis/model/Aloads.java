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
	private Double tons;
	private Double tonsL95;
	private Double tonsU95;
	private Double fwc;
	private Double yield;
	
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

	public Double getTons() {
		return tons;
	}

	public void setTons(Double tons) {
		this.tons = tons;
	}

	public Double getTonsL95() {
		return tonsL95;
	}

	public void setTonsL95(Double tonsL95) {
		this.tonsL95 = tonsL95;
	}

	public Double getTonsU95() {
		return tonsU95;
	}

	public void setTonsU95(Double tonsU95) {
		this.tonsU95 = tonsU95;
	}

	public Double getFwc() {
		return fwc;
	}

	public void setFwc(Double fwc) {
		this.fwc = fwc;
	}

	public Double getYield() {
		return yield;
	}

	public void setYield(Double yield) {
		this.yield = yield;
	}

}
