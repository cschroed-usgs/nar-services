package gov.usgs.cida.nar.connector;

import gov.usgs.cida.nar.mybatis.model.Mloads;
import gov.usgs.cida.nar.service.MloadsService;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.util.NudeUtils;
import java.util.Date;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class MloadsConnector extends MyBatisConnector {

	public MloadsConnector(MloadsService service) {
		super(service);
	}

	@Override
	public ColumnGrouping getExpectedColumns() {
		// Setting up a dummy bean for bean typing (TODO do proper reflection)
		Mloads mloads = new Mloads();
		mloads.setSiteQwId("");
		mloads.setSiteFlowId("");
		mloads.setSiteAbb("");
		mloads.setWy(-1);
		mloads.setConstit("");
		mloads.setModtype("");
		mloads.setMonth(-1);
		mloads.setTons("");
		mloads.setTonsL95("");
		mloads.setTonsU95("");
		return NudeUtils.makeColumnGrouping(mloads);
	}
}
