package gov.usgs.cida.nar.mybatis.model;

/**
 * Integer pair whose members are parsed according to the context of use.
 * @author cschroed
 */
public class WaterYearInterval extends NARData {

	private Integer start;
	private Integer end;

	public WaterYearInterval(Integer start, Integer end) {
		this.start = start;
		this.end = end;
	}
	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Integer getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Integer end) {
		this.end = end;
	}
	
}
