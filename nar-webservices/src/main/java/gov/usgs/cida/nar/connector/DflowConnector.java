package gov.usgs.cida.nar.connector;

import gov.usgs.cida.nar.mybatis.model.Dflow;
import gov.usgs.cida.nar.service.DflowService;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.util.NudeUtils;
import java.util.Date;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DflowConnector extends MyBatisConnector {

	public DflowConnector(DflowService service) {
		super(service);
	}

	@Override
	public ColumnGrouping getExpectedColumns() {
		// Setting up a dummy bean for bean typing (TODO do proper reflection)
		Dflow dflow = new Dflow();
		dflow.setSiteQwId("");
		dflow.setSiteFlowId("");
		dflow.setSiteAbb("");
		dflow.setWy(-1);
		dflow.setDate(new Date());
		dflow.setFlow(Double.NaN);
		return NudeUtils.makeColumnGrouping(dflow);
	}
}
