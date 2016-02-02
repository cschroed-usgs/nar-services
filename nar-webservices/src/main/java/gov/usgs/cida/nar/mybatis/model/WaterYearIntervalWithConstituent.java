package gov.usgs.cida.nar.mybatis.model;


public class WaterYearIntervalWithConstituent extends WaterYearInterval {
	private String constit;
	
	public WaterYearIntervalWithConstituent(Integer start, Integer end, String constit) {
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
