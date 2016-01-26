package gov.usgs.cida.nar.connector;

import gov.usgs.cida.nar.mybatis.model.Aloads;
import gov.usgs.cida.nar.service.AloadsService;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.util.NudeUtils;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class AloadsConnector extends MyBatisConnector {

	public AloadsConnector(AloadsService service) {
		super(service);
	}

	@Override
	public ColumnGrouping getExpectedColumns() {
		// Setting up a dummy bean for bean typing (TODO do proper reflection)
		Aloads aloads = new Aloads();
		aloads.setConstit("");
		aloads.setFwc(Double.NaN);
		aloads.setModtype("");
		aloads.setSiteAbb("");
		aloads.setSiteFlowId("");
		aloads.setSiteQwId("");
		aloads.setTons(Double.NaN);
		aloads.setTonsL95(Double.NaN);
		aloads.setTonsU95(Double.NaN);
		aloads.setWy(-1);
		aloads.setYield(Double.NaN);
		return NudeUtils.makeColumnGrouping(aloads);
	}
}
