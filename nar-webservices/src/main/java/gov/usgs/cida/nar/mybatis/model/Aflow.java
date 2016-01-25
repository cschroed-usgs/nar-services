package gov.usgs.cida.nar.mybatis.model;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class Aflow extends NARData {

	private String siteAbb;
	private String siteQwId;
	private String siteFlowId;
	private Integer wy;
	private Double flow;
	
	/**
	 * Get the value of siteAbb
	 *
	 * @return the value of siteAbb
	 */
	public String getSiteAbb() {
		return siteAbb;
	}

	/**
	 * Set the value of siteAbb
	 *
	 * @param siteAbb new value of siteAbb
	 */
	public void setSiteAbb(String siteAbb) {
		this.siteAbb = siteAbb;
	}

	/**
	 * Get the value of siteQwId
	 *
	 * @return the value of siteQwId
	 */
	public String getSiteQwId() {
		return siteQwId;
	}

	/**
	 * Set the value of siteQwId
	 *
	 * @param siteQwId new value of siteQwId
	 */
	public void setSiteQwId(String siteQwId) {
		this.siteQwId = siteQwId;
	}

	/**
	 * Get the value of siteFlowId
	 *
	 * @return the value of siteFlowId
	 */
	public String getSiteFlowId() {
		return siteFlowId;
	}

	/**
	 * Set the value of siteFlowId
	 *
	 * @param siteFlowId new value of siteFlowId
	 */
	public void setSiteFlowId(String siteFlowId) {
		this.siteFlowId = siteFlowId;
	}

	/**
	 * Get the value of wy
	 *
	 * @return the value of wy
	 */
	public Integer getWy() {
		return wy;
	}

	/**
	 * Set the value of wy
	 *
	 * @param wy new value of wy
	 */
	public void setWy(Integer wy) {
		this.wy = wy;
	}

	/**
	 * Get the value of flow
	 *
	 * @return the value of flow
	 */
	public Double getFlow() {
		return flow;
	}

	/**
	 * Set the value of flow
	 *
	 * @param flow new value of flow
	 */
	public void setFlow(Double flow) {
		this.flow = flow;
	}

}
