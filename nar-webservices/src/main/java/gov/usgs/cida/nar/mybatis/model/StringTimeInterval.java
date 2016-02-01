package gov.usgs.cida.nar.mybatis.model;

/**
 * String pair whose members are parsed according to the context of use.
 * @author cschroed
 */
public class StringTimeInterval extends NARData {

	private String start;
	private String end;

	public StringTimeInterval(String start, String end) {
		this.start = start;
		this.end = end;
	}
	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	
}
