package gov.usgs.cida.nar.connector;

import gov.usgs.cida.nar.mybatis.model.Aflow;
import gov.usgs.cida.nar.service.AflowService;
import gov.usgs.cida.nude.column.Column;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.column.SimpleColumn;
import gov.usgs.cida.nude.util.NudeUtils;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AflowConnector extends MyBatisConnector {

	public AflowConnector(AflowService service) {
		super(service);
	}

	@Override
	public ColumnGrouping getExpectedColumns() {
		// Setting up a dummy bean for bean typing
		Aflow aflow = new Aflow();
		aflow.setFlow(Double.NaN);
		aflow.setSiteAbb("");
		aflow.setSiteFlowId("");
		aflow.setSiteQwId("");
		aflow.setWy(-1);
		return NudeUtils.makeColumnGrouping(aflow);
	}
}
