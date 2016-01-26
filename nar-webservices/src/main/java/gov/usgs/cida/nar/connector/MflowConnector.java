package gov.usgs.cida.nar.connector;

import gov.usgs.cida.nar.mybatis.model.Mflow;
import gov.usgs.cida.nar.service.MflowService;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.util.NudeUtils;
import java.util.Date;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class MflowConnector extends MyBatisConnector {

	public MflowConnector(MflowService service) {
		super(service);
	}

	@Override
	public ColumnGrouping getExpectedColumns() {
		// Setting up a dummy bean for bean typing (TODO do proper reflection)
		Mflow mflow = new Mflow();
		mflow.setSiteQwId("");
		mflow.setSiteFlowId("");
		mflow.setSiteAbb("");
		mflow.setWy(-1);
		mflow.setMonth(-1);
		mflow.setFlow(Double.NaN);
		return NudeUtils.makeColumnGrouping(mflow);
	}
}
