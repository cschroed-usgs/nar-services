package gov.usgs.cida.nar.mybatis.model;

import java.util.Date;
import java.util.Objects;


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

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + Objects.hashCode(this.parmCd);
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.concentration) ^ (Double.doubleToLongBits(this.concentration) >>> 32));
		hash = 23 * hash + Objects.hashCode(this.remark);
		hash = 23 * hash + Objects.hashCode(this.siteQwId);
		hash = 23 * hash + Objects.hashCode(this.datetime);
		hash = 23 * hash + Objects.hashCode(this.date);
		hash = 23 * hash + this.wy;
		hash = 23 * hash + Objects.hashCode(this.constit);
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.acuteFish) ^ (Double.doubleToLongBits(this.acuteFish) >>> 32));
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.acuteInvert) ^ (Double.doubleToLongBits(this.acuteInvert) >>> 32));
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.chronicFish) ^ (Double.doubleToLongBits(this.chronicFish) >>> 32));
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.chronicInvert) ^ (Double.doubleToLongBits(this.chronicInvert) >>> 32));
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.plant) ^ (Double.doubleToLongBits(this.plant) >>> 32));
		hash = 23 * hash + Objects.hashCode(this.planttype);
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.hh) ^ (Double.doubleToLongBits(this.hh) >>> 32));
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.hhChronic) ^ (Double.doubleToLongBits(this.hhChronic) >>> 32));
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.hhAcute) ^ (Double.doubleToLongBits(this.hhAcute) >>> 32));
		hash = 23 * hash + (int) (Double.doubleToLongBits(this.lrl) ^ (Double.doubleToLongBits(this.lrl) >>> 32));
		hash = 23 * hash + Objects.hashCode(this.methCd);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PesticideSample other = (PesticideSample) obj;
		if (Double.doubleToLongBits(this.concentration) != Double.doubleToLongBits(other.concentration)) {
			return false;
		}
		if (this.wy != other.wy) {
			return false;
		}
		if (Double.doubleToLongBits(this.acuteFish) != Double.doubleToLongBits(other.acuteFish)) {
			return false;
		}
		if (Double.doubleToLongBits(this.acuteInvert) != Double.doubleToLongBits(other.acuteInvert)) {
			return false;
		}
		if (Double.doubleToLongBits(this.chronicFish) != Double.doubleToLongBits(other.chronicFish)) {
			return false;
		}
		if (Double.doubleToLongBits(this.chronicInvert) != Double.doubleToLongBits(other.chronicInvert)) {
			return false;
		}
		if (Double.doubleToLongBits(this.plant) != Double.doubleToLongBits(other.plant)) {
			return false;
		}
		if (Double.doubleToLongBits(this.hh) != Double.doubleToLongBits(other.hh)) {
			return false;
		}
		if (Double.doubleToLongBits(this.hhChronic) != Double.doubleToLongBits(other.hhChronic)) {
			return false;
		}
		if (Double.doubleToLongBits(this.hhAcute) != Double.doubleToLongBits(other.hhAcute)) {
			return false;
		}
		if (Double.doubleToLongBits(this.lrl) != Double.doubleToLongBits(other.lrl)) {
			return false;
		}
		if (!Objects.equals(this.parmCd, other.parmCd)) {
			return false;
		}
		if (!Objects.equals(this.remark, other.remark)) {
			return false;
		}
		if (!Objects.equals(this.siteQwId, other.siteQwId)) {
			return false;
		}
		if (!Objects.equals(this.datetime, other.datetime)) {
			return false;
		}
		if (!Objects.equals(this.constit, other.constit)) {
			return false;
		}
		if (!Objects.equals(this.planttype, other.planttype)) {
			return false;
		}
		if (!Objects.equals(this.methCd, other.methCd)) {
			return false;
		}
		if (!Objects.equals(this.date, other.date)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PesticideSample{" + "parmCd=" + parmCd + ", concentration=" + concentration + ", remark=" + remark + ", siteQwId=" + siteQwId + ", datetime=" + datetime + ", date=" + date + ", wy=" + wy + ", constit=" + constit + ", acuteFish=" + acuteFish + ", acuteInvert=" + acuteInvert + ", chronicFish=" + chronicFish + ", chronicInvert=" + chronicInvert + ", plant=" + plant + ", planttype=" + planttype + ", hh=" + hh + ", hhChronic=" + hhChronic + ", hhAcute=" + hhAcute + ", lrl=" + lrl + ", methCd=" + methCd + '}';
	}

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
