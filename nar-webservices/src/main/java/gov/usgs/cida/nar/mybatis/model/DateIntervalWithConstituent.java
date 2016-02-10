package gov.usgs.cida.nar.mybatis.model;

import java.util.Date;


public class DateIntervalWithConstituent extends DateInterval {
	private String constit;
	
	public DateIntervalWithConstituent(Date start, Date end, String constit) {
		super(start, end);
		this.constit = constit;
	}
	
	public DateIntervalWithConstituent(java.sql.Date start, java.sql.Date end, String constit) {
		super(start, end);
		this.constit = constit;
	}

	/**
	 * @return the constit
	 */
	public String getConstit() {
		return constit;
	}

	/**
	 * @param constit the constit to set
	 */
	public void setConstit(String constit) {
		this.constit = constit;
	}
	
}
