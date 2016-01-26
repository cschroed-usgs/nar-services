package gov.usgs.cida.nar.service;

import gov.usgs.cida.nar.connector.AflowConnector;
import gov.usgs.cida.nar.connector.AloadsConnector;
import gov.usgs.cida.nar.connector.DflowConnector;
import gov.usgs.cida.nar.connector.DiscqwConnector;
import gov.usgs.cida.nar.connector.MflowConnector;
import gov.usgs.cida.nar.connector.MloadsConnector;
import gov.usgs.cida.nar.connector.MyBatisConnector;
import gov.usgs.cida.nar.transform.FourDigitYearTransform;
import gov.usgs.cida.nar.transform.PrefixStripTransform;
import gov.usgs.cida.nar.transform.QwIdToFlowIdTransform;
import gov.usgs.cida.nar.transform.ToDayDateTransform;
import gov.usgs.cida.nar.transform.ToMonthNumberTransform;
import gov.usgs.cida.nar.transform.WaterYearTransform;
import gov.usgs.cida.nude.column.Column;
import gov.usgs.cida.nude.column.ColumnGrouping;
import gov.usgs.cida.nude.column.SimpleColumn;
import gov.usgs.cida.nude.filter.FilterStageBuilder;
import gov.usgs.cida.nude.filter.FilterStep;
import gov.usgs.cida.nude.filter.NudeFilterBuilder;
import gov.usgs.cida.nude.filter.transform.ColumnAlias;
import gov.usgs.cida.nude.out.Dispatcher;
import gov.usgs.cida.nude.out.StreamResponse;
import gov.usgs.cida.nude.out.TableResponse;
import gov.usgs.cida.nude.plan.Plan;
import gov.usgs.cida.nude.plan.PlanStep;
import gov.usgs.webservices.framework.basic.MimeType;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.IOUtils;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copied from SosAggregationService, should take its place
 * @author jiwalker
 */
public class TabularDownloadService {
	private static final Logger log = LoggerFactory.getLogger(TabularDownloadService.class);

	private static final String DATE_IN_COL = "date";
	private static final String MONTH_IN_COL= "month";
	private static final String WY_IN_COL = "wy";
	private static final String SITE_QW_ID_IN_COL = "siteQwId";
	private static final String SITE_FLOW_ID_IN_COL = "siteFlowId";

	private static final String QW_CONSTIT_IN_COL = "constit";
	private static final String MOD_TYPE_IN_COL = "modtype";
	private static final String QW_CONCENTRATION_IN_COL = "concentration";
	private static final String REMARK_IN_COL = "remark";

	private static final String FLOW_IN_COL = "flow";
	
	private static final String AN_MASS_UPPER_95_IN_COL = "tonsU95";
	private static final String AN_MASS_LOWER_95_IN_COL = "tonsL95";
	private static final String AN_MASS_IN_COL = "tons";
	private static final String AN_YIELD_IN_COL = "yield";
	private static final String AN_CONC_FLOW_WEIGHTED_IN_COL = "fwc";
	
	private static final String MON_MASS_UPPER_95_IN_COL = "tonsU95";
	private static final String MON_MASS_IN_COL = "tons";
	private static final String MON_FLOW_IN_COL = "flow";
	private static final String MON_MASS_LOWER_95_IN_COL= "tonsL95";
	
	private static final String SITE_FLOW_ID_OUT_COL = "SITE_FLOW_ID";
	private static final String SITE_QW_ID_OUT_COL = "SITE_QW_ID";
	private static final String DATE_OUT_COL = "DATE";
	private static final String WY_OUT_COL = "WY";
	private static final String FLOW_OUT_COL = "FLOW";

	private static final String QW_CONSTIT_OUT_COL = "CONSTIT";
	private static final String QW_CONCENTRATION_OUT_COL = "CONCENTRATION"; 
	private static final String MOD_TYPE_OUT_COL = "MODTYPE"; 
	private static final String REMARK_OUT_COL = "REMARK";

	private static final String AN_MASS_UPPER_95_OUT_COL = "TONS_U95";
	private static final String AN_MASS_LOWER_95_OUT_COL = "TONS_L95";
	private static final String AN_MASS_OUT_COL = "TONS_LOAD";
	private static final String AN_YIELD_OUT_COL = "YIELD";
	private static final String AN_CONC_FLOW_WEIGHTED_OUT_COL = "FWC";

	private static final String MON_MASS_UPPER_95_OUT_COL = "TONS_U95";
	private static final String MON_MASS_OUT_COL = "TONS_LOAD";
	private static final String MON_FLOW_OUT_COL = "FLOW";
	private static final String MON_MASS_LOWER_95_OUT_COL= "TONS_L95";
	
	private static final String MONTH_OUT_COL= "MONTH";
	
	private static final String PROPERTY_PREFIX = "http://cida.usgs.gov/def/NAR/property/";
	
	private DownloadType type;
	private final SimpleFeatureCollection siteFeatures;
	
	public TabularDownloadService(DownloadType type, SimpleFeatureCollection siteFeatures) {
		this.type = type;
		this.siteFeatures = siteFeatures;
	}
	
	public void streamData(final OutputStream output,
			final MimeType mimeType,
			final List<String> constituent,
			final String startDateTime,
			final String endDateTime,
			final String header) throws IOException {
		log.trace("Beginning stream for {}", this.type.name());
		
		final StringReader headerReader = new StringReader(header);
		final MyBatisConnector connector = buildConnector(type, siteFeatures, constituent, startDateTime, endDateTime);
		
		List<PlanStep> steps = new LinkedList<>();
		PlanStep connectorStep;
		connectorStep = new PlanStep() {
			
			@Override
			public ResultSet runStep(ResultSet rs) {
				ResultSet resultSet = null;
				
				//Write out what's left of the header
				if (mimeType == MimeType.CSV || mimeType == MimeType.TAB) { //TODO use NUDE for this header writing
					//write a single byte to keep the stream active
					try {
						int nextByte = headerReader.read();
						while(nextByte > -1) {
							output.write(nextByte);
							output.flush();
							nextByte = headerReader.read();
						}
					} catch (IOException e) {
						log.debug("Exception writing remaining header fragment", e);
					}
				}
				
				if (connector.isReady()) {
					resultSet = connector.getResultSet();
				}
				
				return resultSet;
			}

			@Override
			public ColumnGrouping getExpectedColumns() {
				return connector.getExpectedColumns();
			}
		};
		
		steps.add(connectorStep);

		//do necessary transformations by download type
		switch(this.type) {
			case annualLoad:
				steps.addAll(getAnnualLoadSteps(steps));
				break;
			case mayLoad:
				steps.addAll(getMayLoadSteps(steps));
				break;
			case annualFlow:
				steps.addAll(getAnnualFlowSteps(steps));
				break;
			case mayFlow:
				steps.addAll(getMonthlyFlowSteps(steps));
				break;
			case dailyFlow:
				steps.addAll(getDailyFlowSteps(steps));
				break;
			case sampleConcentrations:
				steps.addAll(getDiscreteQwSteps(steps));
				break;
			default: //nothing
		}
		
		Plan plan = new Plan(steps);
		
		ResultSet runStep = Plan.runPlan(plan);
		TableResponse tr = new TableResponse(runStep);
		StreamResponse sr = null;
		try {
			sr = Dispatcher.buildFormattedResponse(mimeType, tr);
		} catch (IOException| SQLException | XMLStreamException ex) {
			log.error("Unable to build formatted response", ex);
		}
		if (sr != null && output != null) {
			StreamResponse.dispatch(sr, new PrintWriter(output));
			output.flush();
			IOUtils.closeQuietly(connector);
		}
	}
	
	private List<PlanStep> getAnnualLoadSteps(final List<PlanStep> prevSteps) {
		List<PlanStep> steps = new ArrayList<>();
		
		//rename columns to specified headers
		ColumnGrouping originals = prevSteps.get(prevSteps.size()-1).getExpectedColumns();
		FilterStep renameColsStep = new FilterStep(new NudeFilterBuilder(originals)
						.addFilterStage(new FilterStageBuilder(originals)
							.addTransform(new SimpleColumn(SITE_QW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_QW_ID_IN_COL) + 1)))
							.addTransform(new SimpleColumn(SITE_FLOW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_FLOW_ID_IN_COL) + 1)))
							.addTransform(new SimpleColumn(QW_CONSTIT_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), QW_CONSTIT_IN_COL) + 1)))
							.addTransform(new SimpleColumn(WY_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), WY_IN_COL) + 1)))
							.addTransform(new SimpleColumn(MOD_TYPE_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), MOD_TYPE_IN_COL) + 1)))
							.addTransform(new SimpleColumn(AN_CONC_FLOW_WEIGHTED_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), AN_CONC_FLOW_WEIGHTED_IN_COL) + 1)))
							.addTransform(new SimpleColumn(AN_MASS_LOWER_95_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), AN_MASS_LOWER_95_IN_COL) + 1)))
							.addTransform(new SimpleColumn(AN_MASS_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), AN_MASS_IN_COL) + 1)))
							.addTransform(new SimpleColumn(AN_MASS_UPPER_95_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), AN_MASS_UPPER_95_IN_COL) + 1)))
							.addTransform(new SimpleColumn(AN_YIELD_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), AN_YIELD_IN_COL) + 1)))
							.buildFilterStage())
				.buildFilter());
		steps.add(renameColsStep);
		
		//drop columns
		List<Column> finalColList = new ArrayList<>();
		List<Column> allCols = renameColsStep.getExpectedColumns().getColumns();
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_QW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_FLOW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, QW_CONSTIT_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, WY_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, MOD_TYPE_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, AN_MASS_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, AN_MASS_LOWER_95_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, AN_MASS_UPPER_95_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, AN_CONC_FLOW_WEIGHTED_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, AN_YIELD_OUT_COL)));
		
		ColumnGrouping finalCols = new ColumnGrouping(finalColList);
		FilterStep removeUnusedColsStep = new FilterStep(new NudeFilterBuilder(finalCols)
				.addFilterStage(new FilterStageBuilder(finalCols)
						.buildFilterStage())
				.buildFilter());
		steps.add(removeUnusedColsStep);

		return steps;
	}
	
	private List<PlanStep> getMayLoadSteps(final List<PlanStep> prevSteps) {
		List<PlanStep> steps = new ArrayList<>();

		//rename columns to specified headers
		ColumnGrouping originals = prevSteps.get(prevSteps.size()-1).getExpectedColumns();
		FilterStep renameColsStep = new FilterStep(new NudeFilterBuilder(originals)
						.addFilterStage(new FilterStageBuilder(originals)
							.addTransform(new SimpleColumn(SITE_QW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_QW_ID_IN_COL) + 1)))
							.addTransform(new SimpleColumn(SITE_FLOW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_FLOW_ID_IN_COL) + 1)))
							.addTransform(new SimpleColumn(WY_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), WY_IN_COL) + 1)))
							.addTransform(new SimpleColumn(MONTH_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), MONTH_IN_COL) + 1)))
							.addTransform(new SimpleColumn(QW_CONSTIT_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), QW_CONSTIT_IN_COL) + 1)))
							.addTransform(new SimpleColumn(MOD_TYPE_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), MOD_TYPE_IN_COL) + 1)))
							.addTransform(new SimpleColumn(MON_MASS_LOWER_95_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), MON_MASS_LOWER_95_IN_COL) + 1)))
							.addTransform(new SimpleColumn(MON_MASS_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), MON_MASS_IN_COL) + 1)))
							.addTransform(new SimpleColumn(MON_MASS_UPPER_95_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), MON_MASS_UPPER_95_IN_COL) + 1)))
							.buildFilterStage())
				.buildFilter());
		steps.add(renameColsStep);
		
		//drop constit and modtype columns
		List<Column> finalColList = new ArrayList<>();
		List<Column> allCols = renameColsStep.getExpectedColumns().getColumns();
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_QW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_FLOW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, QW_CONSTIT_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, WY_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, MONTH_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, MOD_TYPE_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, MON_MASS_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, MON_MASS_LOWER_95_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, MON_MASS_UPPER_95_OUT_COL)));
		
		ColumnGrouping finalCols = new ColumnGrouping(finalColList);
		FilterStep removeUnusedColsStep = new FilterStep(new NudeFilterBuilder(finalCols)
				.addFilterStage(new FilterStageBuilder(finalCols)
						.buildFilterStage())
				.buildFilter());
		steps.add(removeUnusedColsStep);
		
		return steps;
	}

	private List<PlanStep> getAnnualFlowSteps(final List<PlanStep> prevSteps) {
		List<PlanStep> steps = new ArrayList<>();
		
		//rename columns to specified headers
		ColumnGrouping originals = prevSteps.get(prevSteps.size()-1).getExpectedColumns();
		FilterStep renameColsStep = new FilterStep(new NudeFilterBuilder(originals)
						.addFilterStage(new FilterStageBuilder(originals)
							.addTransform(new SimpleColumn(SITE_QW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_QW_ID_IN_COL) + 1)))
							.addTransform(new SimpleColumn(SITE_FLOW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_FLOW_ID_IN_COL) + 1)))	
							.addTransform(new SimpleColumn(WY_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), WY_IN_COL) + 1)))
							.addTransform(new SimpleColumn(FLOW_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), FLOW_IN_COL) + 1)))
							.buildFilterStage())
				.buildFilter());
		steps.add(renameColsStep);

		//drop constit and modtype columns
		List<Column> finalColList = new ArrayList<>();
		List<Column> allCols = renameColsStep.getExpectedColumns().getColumns();
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_QW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_FLOW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, WY_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, FLOW_OUT_COL)));
		
		ColumnGrouping finalCols = new ColumnGrouping(finalColList);
		FilterStep removeUnusedColsStep = new FilterStep(new NudeFilterBuilder(finalCols)
				.addFilterStage(new FilterStageBuilder(finalCols)
						.buildFilterStage())
				.buildFilter());
		steps.add(removeUnusedColsStep);

		return steps;
	}

	private List<PlanStep> getMonthlyFlowSteps(final List<PlanStep> prevSteps) {
		List<PlanStep> steps = new ArrayList<>();
		
		//rename columns to specified headers
		ColumnGrouping originals = prevSteps.get(prevSteps.size()-1).getExpectedColumns();
		FilterStep renameColsStep = new FilterStep(new NudeFilterBuilder(originals)
						.addFilterStage(new FilterStageBuilder(originals)
							.addTransform(new SimpleColumn(SITE_QW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_QW_ID_IN_COL) + 1)))
							.addTransform(new SimpleColumn(SITE_FLOW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_FLOW_ID_IN_COL) + 1)))	
							.addTransform(new SimpleColumn(WY_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), WY_IN_COL) + 1)))
							.addTransform(new SimpleColumn(MONTH_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), MONTH_IN_COL) + 1)))
							.addTransform(new SimpleColumn(MON_FLOW_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), MON_FLOW_IN_COL) + 1)))
							.buildFilterStage())
				.buildFilter());
		steps.add(renameColsStep);

		//drop constit and modtype columns
		List<Column> finalColList = new ArrayList<>();
		List<Column> allCols = renameColsStep.getExpectedColumns().getColumns();
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_QW_ID_IN_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_FLOW_ID_IN_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, WY_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, MONTH_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, MON_FLOW_OUT_COL)));
		
		ColumnGrouping finalCols = new ColumnGrouping(finalColList);
		FilterStep removeUnusedColsStep = new FilterStep(new NudeFilterBuilder(finalCols)
				.addFilterStage(new FilterStageBuilder(finalCols)
						.buildFilterStage())
				.buildFilter());
		steps.add(removeUnusedColsStep);

		return steps;
	}
	
	private List<PlanStep> getDailyFlowSteps(final List<PlanStep> prevSteps) {
		List<PlanStep> steps = new ArrayList<>();
		
		//rename columns to specified headers
		ColumnGrouping originals = prevSteps.get(prevSteps.size()-1).getExpectedColumns();
		FilterStep renameColsStep = new FilterStep(new NudeFilterBuilder(originals)
						.addFilterStage(new FilterStageBuilder(originals)
							.addTransform(new SimpleColumn(SITE_QW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_QW_ID_IN_COL) + 1)))
							.addTransform(new SimpleColumn(SITE_FLOW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_FLOW_ID_IN_COL) + 1)))
							.addTransform(new SimpleColumn(DATE_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), DATE_IN_COL) + 1)))
							.addTransform(new SimpleColumn(FLOW_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), FLOW_IN_COL) + 1)))
							.addTransform(new SimpleColumn(WY_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), WY_IN_COL) + 1)))
							.buildFilterStage())
				.buildFilter());
		steps.add(renameColsStep);
		
		//drop constit and modtype columns
		List<Column> finalColList = new ArrayList<>();
		List<Column> allCols = renameColsStep.getExpectedColumns().getColumns();
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_QW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_FLOW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, DATE_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, WY_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, FLOW_OUT_COL)));
		
		ColumnGrouping finalCols = new ColumnGrouping(finalColList);
		FilterStep removeUnusedColsStep = new FilterStep(new NudeFilterBuilder(finalCols)
				.addFilterStage(new FilterStageBuilder(finalCols)
						.buildFilterStage())
				.buildFilter());
		steps.add(removeUnusedColsStep);
		
		//convert dates to WY and day
		steps.add(new FilterStep(new NudeFilterBuilder(finalCols)
				.addFilterStage(new FilterStageBuilder(finalCols)
				.addTransform(finalColList.get(indexOfCol(finalColList, DATE_OUT_COL)), new ToDayDateTransform(finalColList.get(indexOfCol(finalColList, DATE_OUT_COL))))
				.buildFilterStage())
		.buildFilter()));

		return steps;
	}

	private List<PlanStep> getDiscreteQwSteps(final List<PlanStep> prevSteps) {
		List<PlanStep> steps = new ArrayList<>();
		//rename columns to specified headers
		//Not sure if any renaming is necessary until missing columns are available 
		ColumnGrouping originals = prevSteps.get(prevSteps.size()-1).getExpectedColumns();
		FilterStep renameColsStep = new FilterStep(new NudeFilterBuilder(originals)
			.addFilterStage(new FilterStageBuilder(originals)
			.addTransform(new SimpleColumn(SITE_QW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_QW_ID_IN_COL) + 1)))
			.addTransform(new SimpleColumn(SITE_FLOW_ID_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), SITE_FLOW_ID_IN_COL) + 1)))
			.addTransform(new SimpleColumn(QW_CONSTIT_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), QW_CONSTIT_IN_COL) + 1)))
			.addTransform(new SimpleColumn(DATE_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), DATE_IN_COL) + 1)))
			.addTransform(new SimpleColumn(WY_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), WY_IN_COL) + 1)))
			.addTransform(new SimpleColumn(QW_CONCENTRATION_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), QW_CONCENTRATION_IN_COL) + 1)))
			.addTransform(new SimpleColumn(REMARK_OUT_COL), new ColumnAlias(originals.get(indexOfCol(originals.getColumns(), REMARK_IN_COL) + 1)))
			.buildFilterStage())
			.buildFilter());
		steps.add(renameColsStep);
		
		//missing cols commented out until available 
		List<Column> finalColList = new ArrayList<>();
		List<Column> allCols = renameColsStep.getExpectedColumns().getColumns();
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_QW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, SITE_FLOW_ID_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, QW_CONSTIT_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, DATE_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, WY_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, QW_CONCENTRATION_OUT_COL)));
		finalColList.add(allCols.get(indexOfCol(allCols, REMARK_OUT_COL)));
		
		ColumnGrouping finalCols = new ColumnGrouping(finalColList);
		FilterStep removeUnusedColsStep = new FilterStep(new NudeFilterBuilder(finalCols)
				.addFilterStage(new FilterStageBuilder(finalCols)
				.buildFilterStage())
				.buildFilter());
		steps.add(removeUnusedColsStep);
		
		//convert dates to WY and day
		steps.add(new FilterStep(new NudeFilterBuilder(finalCols)
				.addFilterStage(new FilterStageBuilder(finalCols)
				.addTransform(finalColList.get(indexOfCol(finalColList, DATE_OUT_COL)), new ToDayDateTransform(finalColList.get(indexOfCol(finalColList, DATE_OUT_COL))))
				.buildFilterStage())
		.buildFilter()));
		
		return steps;
	}
	
	/**
	 * Helper function to get the index of a column with the given name
	 */
	private int indexOfCol(List<Column> cols, String colName) {
		int index = -1;
		for(int i = 0; i < cols.size(); i++) {
			if(cols.get(i).getName().equals(colName)) {
				index = i;
				break;
			}
		}
		return index;
	}

	private MyBatisConnector buildConnector(DownloadType type,
			SimpleFeatureCollection siteFeatures,
			List<String> constituent,
			String startDateTime,
			String endDateTime) throws IOException {
		MyBatisConnector connector = null;
		List<String> sites = SiteInformationService.getStationIdsFromFeatureCollection(siteFeatures);
		switch(this.type) {
			case annualLoad:
				AloadsService aloadsService = new AloadsService();
				aloadsService.setSiteQwId(sites);
				aloadsService.setConstit(constituent);
				aloadsService.setStartDate(startDateTime);
				aloadsService.setEndDate(endDateTime);
				connector = new AloadsConnector(aloadsService);
				break;
			case mayLoad:
				MloadsService mloadsService = new MloadsService();
				mloadsService.setSiteQwId(sites);
				mloadsService.setConstit(constituent);
				mloadsService.setStartDate(startDateTime);
				mloadsService.setEndDate(endDateTime);
				connector = new MloadsConnector(mloadsService);
				break;
			case annualFlow:
				AflowService aflowService = new AflowService();
				aflowService.setSiteQwId(sites);
				aflowService.setStartDate(startDateTime);
				aflowService.setEndDate(endDateTime);
				connector = new AflowConnector(aflowService);
				break;
			case mayFlow:
				MflowService mflowService = new MflowService();
				mflowService.setSiteQwId(sites);
				mflowService.setStartDate(startDateTime);
				mflowService.setEndDate(endDateTime);
				connector = new MflowConnector(mflowService);
				break;
			case dailyFlow:
				DflowService dflowService = new DflowService();
				dflowService.setSiteQwId(sites);
				dflowService.setStartDate(startDateTime);
				dflowService.setEndDate(endDateTime);
				connector = new DflowConnector(dflowService);
				break;
			case sampleConcentrations:
				DiscqwService discqwService = new DiscqwService();
				discqwService.setSiteQwId(sites);
				discqwService.setConstit(constituent);
				discqwService.setStartDate(startDateTime);
				discqwService.setEndDate(endDateTime);
				connector = new DiscqwConnector(discqwService);
				break;
			default: //nothing
		}
		return connector;
	}
}
