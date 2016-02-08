package gov.usgs.cida.nar.mybatis.model;

/**
 * Integer pair whose members are parsed according to the context of use.
 * @author cschroed
 */
public class WaterYearInterval extends NARData {

	private Integer startYear;
	private Integer endYear;

	public WaterYearInterval(Integer start, Integer end) {
		this.startYear = start;
		this.endYear = end;
	}
	/**
	 * @return the startYear
	 */
	public Integer getStartYear() {
		return startYear;
	}

	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	/**
	 * @return the endYear
	 */
	public Integer getEndYear() {
		return endYear;
	}

	/**
	 * @param endYear the endYear to set
	 */
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}
	
}
