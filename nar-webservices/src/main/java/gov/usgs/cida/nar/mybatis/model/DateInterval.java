package gov.usgs.cida.nar.mybatis.model;

import java.util.Date;

/**
 * Date pair whose members are parsed according to the context of use.
 * @author cschroed
 */
public class DateInterval extends NARData {

	private Date start;
	private Date end;
	
	public DateInterval(java.sql.Date start, java.sql.Date end) {
		this.start = start;
		this.end = end;
	}
	
	public DateInterval(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	/**
	 * @return the startYear
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param startYear the startYear to set
	 */
	public void setStart(Date startYear) {
		this.start = startYear;
	}

	/**
	 * @return the endYear
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param endYear the endYear to set
	 */
	public void setEnd(Date endYear) {
		this.end = endYear;
	}

	public static boolean isInitialized(DateInterval interval) {
		return !(
			null == interval
			|| null == interval.getEnd()
			|| null == interval.getStart()
		);
	}
}
