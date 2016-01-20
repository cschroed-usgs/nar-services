package gov.usgs.cida.nar.mybatis.model;

import java.util.Date;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class Discqw {

	private String siteAbb;
	private String siteQwId;
	private String siteFlowId;
	private String constit;
	private Date date;
	private Integer wy;
	private Double concentration;
	private String remark;

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

	public String getConstit() {
		return constit;
	}

	public void setConstit(String constit) {
		this.constit = constit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getWy() {
		return wy;
	}

	public void setWy(Integer wy) {
		this.wy = wy;
	}

	public Double getConcentration() {
		return concentration;
	}

	public void setConcentration(Double concentration) {
		this.concentration = concentration;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
