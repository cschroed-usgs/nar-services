package gov.usgs.cida.nar.mybatis.model;

import gov.usgs.cida.nar.domain.PesticideType;
import java.util.HashMap;
import java.util.Map;


public class MostCommonPesticides {
	private String herbicide;
	private String nonHerbicide;
	
	public Map<PesticideType, String> getTypesToNames(){
		Map<PesticideType, String> typesToNames = new HashMap<>();
		typesToNames.put(PesticideType.HERBICIDE, herbicide);
		typesToNames.put(PesticideType.NON_HERBICIDE, nonHerbicide);
		return typesToNames;
	}
	
	/**
	 * @return the herbicide
	 */
	public String getHerbicide() {
		return herbicide;
	}

	/**
	 * @param herbicide the herbicide to set
	 */
	public void setHerbicide(String herbicide) {
		this.herbicide = herbicide;
	}

	/**
	 * @return the nonHerbicide
	 */
	public String getNonHerbicide() {
		return nonHerbicide;
	}

	/**
	 * @param nonHerbicide the nonHerbicide to set
	 */
	public void setNonHerbicide(String nonHerbicide) {
		this.nonHerbicide = nonHerbicide;
	}
}
