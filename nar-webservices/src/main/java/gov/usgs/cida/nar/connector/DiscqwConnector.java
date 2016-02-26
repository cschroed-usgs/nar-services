package gov.usgs.cida.nar.connector;

import gov.usgs.cida.nar.mybatis.model.Discqw;
import gov.usgs.cida.nar.service.DiscqwService;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.util.NudeUtils;
import java.util.Date;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DiscqwConnector extends MyBatisConnector {

	public DiscqwConnector(DiscqwService service) {
		super(service);
	}

	@Override
	public ColumnGrouping getExpectedColumns() {
		// Setting up a dummy bean for bean typing (TODO do proper reflection)
		Discqw discqw = new Discqw();
		discqw.setSiteQwId("");
		discqw.setSiteFlowId("");
		discqw.setSiteAbb("");
		discqw.setWy(-1);
		discqw.setDate(new Date());
		discqw.setConcentration("");
		discqw.setConstit("");
		discqw.setRemark("");
		return NudeUtils.makeColumnGrouping(discqw);
	}
}
