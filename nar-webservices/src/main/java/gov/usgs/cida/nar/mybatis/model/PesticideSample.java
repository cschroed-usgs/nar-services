package gov.usgs.cida.nar.mybatis.model;

import java.util.Date;


public class PesticideSample extends NARData{
	private String parmCd;
	private double concentration;
	private String remark;
	private String siteQwId;
	private String datetime;
	private Date date;
	private int wy;
	private String constit;
	private double acuteFish;
	private double acuteInvert;
	private double chronicFish;
	private double chronicInvert;
	private double plant;
	private String planttype;
	private double hh;
	private double hhChronic;
	private double hhAcute;
	private double lrl;
	private String methCd;

	/**
	 * @return the parmCd
	 */
	public String getParmCd() {
		return parmCd;
	}

	/**
	 * @param parmCd the parmCd to set
	 */
	public void setParmCd(String parmCd) {
		this.parmCd = parmCd;
	}

	/**
	 * @return the concentration
	 */
	public double getConcentration() {
		return concentration;
	}

	/**
	 * @param concentration the concentration to set
	 */
	public void setConcentration(double concentration) {
		this.concentration = concentration;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the siteQwId
	 */
	public String getSiteQwId() {
		return siteQwId;
	}

	/**
	 * @param siteQwId the siteQwId to set
	 */
	public void setSiteQwId(String siteQwId) {
		this.siteQwId = siteQwId;
	}

	/**
	 * @return the datetime
	 */
	public String getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the wy
	 */
	public int getWy() {
		return wy;
	}

	/**
	 * @param wy the wy to set
	 */
	public void setWy(int wy) {
		this.wy = wy;
	}

	/**
	 * @return the constit
	 */
	public String getConstit() {
		return constit;
	}

	/**
	 * @param constit the constit to set
	 */
	public void setConstit(String constit) {
		this.constit = constit;
	}

	/**
	 * @return the acuteFish
	 */
	public double getAcuteFish() {
		return acuteFish;
	}

	/**
	 * @param acuteFish the acuteFish to set
	 */
	public void setAcuteFish(double acuteFish) {
		this.acuteFish = acuteFish;
	}

	/**
	 * @return the acuteInvert
	 */
	public double getAcuteInvert() {
		return acuteInvert;
	}

	/**
	 * @param acuteInvert the acuteInvert to set
	 */
	public void setAcuteInvert(double acuteInvert) {
		this.acuteInvert = acuteInvert;
	}

	/**
	 * @return the chronicFish
	 */
	public double getChronicFish() {
		return chronicFish;
	}

	/**
	 * @param chronicFish the chronicFish to set
	 */
	public void setChronicFish(double chronicFish) {
		this.chronicFish = chronicFish;
	}

	/**
	 * @return the chronicInvert
	 */
	public double getChronicInvert() {
		return chronicInvert;
	}

	/**
	 * @param chronicInvert the chronicInvert to set
	 */
	public void setChronicInvert(double chronicInvert) {
		this.chronicInvert = chronicInvert;
	}

	/**
	 * @return the plant
	 */
	public double getPlant() {
		return plant;
	}

	/**
	 * @param plant the plant to set
	 */
	public void setPlant(double plant) {
		this.plant = plant;
	}

	/**
	 * @return the planttype
	 */
	public String getPlanttype() {
		return planttype;
	}

	/**
	 * @param planttype the planttype to set
	 */
	public void setPlanttype(String planttype) {
		this.planttype = planttype;
	}

	/**
	 * @return the hh
	 */
	public double getHh() {
		return hh;
	}

	/**
	 * @param hh the hh to set
	 */
	public void setHh(double hh) {
		this.hh = hh;
	}

	/**
	 * @return the hhChronic
	 */
	public double getHhChronic() {
		return hhChronic;
	}

	/**
	 * @param hhChronic the hhChronic to set
	 */
	public void setHhChronic(double hhChronic) {
		this.hhChronic = hhChronic;
	}

	/**
	 * @return the hhAcute
	 */
	public double getHhAcute() {
		return hhAcute;
	}

	/**
	 * @param hhAcute the hhAcute to set
	 */
	public void setHhAcute(double hhAcute) {
		this.hhAcute = hhAcute;
	}

	/**
	 * @return the lrl
	 */
	public double getLrl() {
		return lrl;
	}

	/**
	 * @param lrl the lrl to set
	 */
	public void setLrl(double lrl) {
		this.lrl = lrl;
	}

	/**
	 * @return the methCd
	 */
	public String getMethCd() {
		return methCd;
	}

	/**
	 * @param methCd the methCd to set
	 */
	public void setMethCd(String methCd) {
		this.methCd = methCd;
	}
}
