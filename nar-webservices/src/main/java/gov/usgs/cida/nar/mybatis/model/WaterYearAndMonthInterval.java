package gov.usgs.cida.nar.mybatis.model;


public class WaterYearAndMonthInterval extends WaterYearInterval {

	private Integer startMonth;
	private Integer endMonth;
	public WaterYearAndMonthInterval(Integer startYear, Integer startMonth, Integer endYear, Integer endMonth) {
		super(startYear, endYear);
		this.startMonth = startMonth;
		this.endMonth = endMonth;
	}

	/**
	 * @return the startMonth
	 */
	public Integer getStartMonth() {
		return startMonth;
	}

	/**
	 * @param startMonth the startMonth to set
	 */
	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	/**
	 * @return the endMonth
	 */
	public Integer getEndMonth() {
		return endMonth;
	}

	/**
	 * @param endMonth the endMonth to set
	 */
	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}
	
	public boolean isInitialized(){
		return !(
			null == this.getStartYear()
			|| null == this.getEndYear()
			|| null == this.getStartMonth()
			|| null == this.getEndMonth()
			);
	}
}
