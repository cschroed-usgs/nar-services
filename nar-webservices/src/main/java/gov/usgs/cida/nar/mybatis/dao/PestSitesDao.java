package gov.usgs.cida.nar.mybatis.dao;

import gov.usgs.cida.nar.domain.ComparisonCategorization;
import gov.usgs.cida.nar.domain.ComparisonCategory;
import gov.usgs.cida.nar.domain.Pesticide;
import gov.usgs.cida.nar.domain.PesticideBuilder;
import gov.usgs.cida.nar.mybatis.model.PestSites;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gov.usgs.cida.nar.mybatis.dao.BaseDao.QUERY_PACKAGE;
import static gov.usgs.cida.nar.mybatis.dao.BaseDao.SITE_QW;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class PestSitesDao extends BaseDao {

	private static final Logger log = LoggerFactory.getLogger(PestSitesDao.class);
	
	public PestSitesDao(){
		super();
	}
	
	public PestSitesDao(SqlSessionFactory factory){
		super(factory);
	}
	
	public List<PestSites> getPestSites(List<String> siteQwId) {
		List<PestSites> result = null;
		
		Map<String, Object> params = new HashMap<>(3);
		params.put(SITE_QW, siteQwId);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			result = session.selectList(QUERY_PACKAGE + ".PestSitesMapper.getPestSites", params);
		}
		
		return result;
	}

	public List<Pesticide> getMostFrequentlyDetectedPesticides(String siteQwId) {
		List<PesticideBuilder> pestBuilders = null;
		List<Pesticide> pesticides = new ArrayList<>();
		Map<String, Object> params = new HashMap<>(3);
		params.put(SITE_QW, siteQwId);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			pestBuilders = session.selectList(QUERY_PACKAGE + ".PestSitesMapper.getMostFrequentlyDetectedPesticides", params);
		}
		if(null == pestBuilders || pestBuilders.isEmpty()){
			//return empty list
		} else if(2 == pestBuilders.size()) { 
			for(PesticideBuilder builder : pestBuilders){
				Pesticide pest = builder.build();
				pesticides.add(pest);
			}
		} else {
			throw new RuntimeException("Duplicate records exist for a single site. There should only be one record per site.");
		}
		return pesticides;
	}

	protected ComparisonCategorization getComparisonCategorizationForColumnName(String columnName){
		log.debug("Attempting to map column name '" + columnName + "' to a reasonable ComparisonCategorization");
		ComparisonCategorization result = null;
		String strComparisonCategory = columnName.substring(0, 2).toUpperCase();
		String strComparisonOrder = columnName.substring(2);
		ComparisonCategory category = null;
		switch(strComparisonCategory){
			case "AQ":
				category = ComparisonCategory.AQUATIC_LIFE;
				break;
			case "HH":
				category = ComparisonCategory.HUMAN_HEALTH;
				break;
			default:
				throw new IllegalArgumentException("Could not map the substring '" + strComparisonCategory + "' of the string '" + columnName + "' to a value of " + ComparisonCategory.class.getSimpleName());
		}
		
		int order;
		try{
			order = Integer.parseInt(strComparisonOrder);
		} catch(NumberFormatException e){
			throw new IllegalArgumentException("Could not map the substring '" + strComparisonOrder + "' of the string '" + columnName + "' to a valid integer");
		}
		result = new ComparisonCategorization(category, order);
		return result;
	}
	/**
	 * 
	 * @param siteQwId a list of site id
	 * @return a Map of String constituent names to a comparison Categorization
	 */
	public Map<String, ComparisonCategorization> getPesticidesCloseToBenchmarks(List<String> siteQwId) {
		Map<String, ComparisonCategorization> methodResult= new HashMap<>();
		Map<String, String> queryResult;//column name to name of pesticide
		Map<String, Object> params = new HashMap<>(3);
		params.put(SITE_QW, siteQwId);
		
		log.debug("querying db for pesticides close to benchmarks for site '" + siteQwId + "'.");
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			queryResult = session.selectOne(QUERY_PACKAGE + ".PestSitesMapper.getPesticidesCloseToBenchmarks", params);
		}
		
		log.debug("processing db query results for pesticides close to benchmarks for site '" + siteQwId + "'.");
		
		for(Map.Entry<String, String> entry : queryResult.entrySet()){
			String columnName = entry.getKey();
			String pesticide = entry.getValue();
			ComparisonCategorization comparisonCategorization = getComparisonCategorizationForColumnName(columnName);
			methodResult.put(pesticide, comparisonCategorization);
		}
		return methodResult;
		
	}
	
}
