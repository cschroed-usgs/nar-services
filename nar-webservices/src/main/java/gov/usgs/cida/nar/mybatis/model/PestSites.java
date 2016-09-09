package gov.usgs.cida.nar.mybatis.model;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class PestSites extends NARData {

	private String siteQwId;
	private String siteQwName;
	private String siteFlowId;
	private String siteFlowName;
	private String siteAbb;
	private double da;
	private String siteType;
	private String state;
	private double minSamp;
	private String herbSamp;
	private String nonherbSamp;
	private String aq1;
	private String aq2;
	private String aq3;
	private double percHh;
	private double percAq;
	private double percHh3;
	private double percAq3;
	private double percHhOld;
	private double percAqOld;
	private int nNew;
	private int npestNewHh;
	private int n3;
	private int npest3Hh;
	private double nOldAve;
	private int nYrs;
	private int nOldHh;
	private int nNewAq;
	private int nAq3;
	private int nOldAq;
	private int nHhOld;
	private int nAqOld;
	private String pestNewExceedHh;
	private String pestNewExceedAq;
	private String pest3ExceedHh;
	private String pest3ExceedAq;
	private String pestOldExceedHh;
	private String pestOldExceedAq;
	private String hh1;
	private String hh2;
	private String hh3;
	private String ndet1Pname;
	private double perc1New;
	private double perc13;
	private double perc1Old;
	private double perc1NewCom;
	private double perc13Com;
	private double perc1OldCom;
	private int nsamp1New;
	private double nsamp13;
	private double nsamp1Old;
	private String ndet2Pname;
	private double perc2New;
	private double perc23;
	private double perc2Old;
	private double perc2NewCom;
	private double perc23Com;
	private double perc2OldCom;
	private int nsamp2New;
	private double nsamp23;
	private double nsamp2Old;
	private String ndet3Pname;
	private double perc3New;
	private double perc33;
	private double perc3Old;
	private double perc3NewCom;
	private double perc33Com;
	private double perc3OldCom;
	private int nsamp3New;
	private double nsamp33;
	private double nsamp3Old;
	private String ndet4Pname;
	private double perc4New;
	private double perc43;
	private double perc4Old;
	private double perc4NewCom;
	private double perc43Com;
	private double perc4OldCom;
	private int nsamp4New;
	private double nsamp43;
	private double nsamp4Old;
	private String ndet5Pname;
	private double perc5New;
	private double perc53;
	private double perc5Old;
	private double perc5NewCom;
	private double perc53Com;
	private double perc5OldCom;
	private int nsamp5New;
	private double nsamp53;
	private double nsamp5Old;
	private String ndet6Pname;
	private double perc6New;
	private double perc63;
	private double perc6Old;
	private double perc6NewCom;
	private double perc63Com;
	private double perc6OldCom;
	private int nsamp6New;
	private double nsamp63;
	private double nsamp6Old;
	private String ndet7Pname;
	private double perc7New;
	private double perc73;
	private double perc7Old;
	private double perc7NewCom;
	private double perc73Com;
	private double perc7OldCom;
	private int nsamp7New;
	private double nsamp73;
	private double nsamp7Old;
	private String ndet8Pname;
	private double perc8New;
	private double perc83;
	private double perc8Old;
	private double perc8NewCom;
	private double perc83Com;
	private double perc8OldCom;
	private int nsamp8New;
	private double nsamp83;
	private double nsamp8Old;
	private String ndet9Pname;
	private double perc9New;
	private double perc93;
	private double perc9Old;
	private double perc9NewCom;
	private double perc93Com;
	private double perc9OldCom;
	private int nsamp9New;
	private double nsamp93;
	private double nsamp9Old;
	private String ndet10Pname;
	private double perc10New;
	private double perc103;
	private double perc10Old;
	private double perc10NewCom;
	private double perc103Com;
	private double perc10OldCom;
	private int nsamp10New;
	private double nsamp103;
	private double nsamp10Old;
	private int npestNew;
	private int npest3;
	private int ndetsNew;
	private int ndets3;
	private int ndetsHh;
	private int ndetsAq;
	private int nHh;
	private int nAq;
	private int ndetsHh3;
	private int ndetsAq3;
	private int nHh3;
	private double nNuts;
	private double nNutsDet;
	private double nNutsDetHh;
	private double nNutsDetAq;
	private int nNutHh;
	private String plot;
	private int npest3Aq;

	public String getSiteQwId() {
		return siteQwId;
	}

	public void setSiteQwId(String siteQwId) {
		this.siteQwId = siteQwId;
	}

	public String getSiteQwName() {
		return siteQwName;
	}

	public void setSiteQwName(String siteQwName) {
		this.siteQwName = siteQwName;
	}

	public String getSiteFlowId() {
		return siteFlowId;
	}

	public void setSiteFlowId(String siteFlowId) {
		this.siteFlowId = siteFlowId;
	}

	public String getSiteFlowName() {
		return siteFlowName;
	}

	public void setSiteFlowName(String siteFlowName) {
		this.siteFlowName = siteFlowName;
	}

	public String getSiteAbb() {
		return siteAbb;
	}

	public void setSiteAbb(String siteAbb) {
		this.siteAbb = siteAbb;
	}

	public double getDa() {
		return da;
	}

	public void setDa(double da) {
		this.da = da;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getMinSamp() {
		return minSamp;
	}

	public void setMinSamp(double minSamp) {
		this.minSamp = minSamp;
	}

	public String getHerbSamp() {
		return herbSamp;
	}

	public void setHerbSamp(String herbSamp) {
		this.herbSamp = herbSamp;
	}

	public String getNonherbSamp() {
		return nonherbSamp;
	}

	public void setNonherbSamp(String nonherbSamp) {
		this.nonherbSamp = nonherbSamp;
	}

	public String getAq1() {
		return aq1;
	}

	public void setAq1(String aq1) {
		this.aq1 = aq1;
	}

	public String getAq2() {
		return aq2;
	}

	public void setAq2(String aq2) {
		this.aq2 = aq2;
	}

	public String getAq3() {
		return aq3;
	}

	public void setAq3(String aq3) {
		this.aq3 = aq3;
	}

	public double getPercHh() {
		return percHh;
	}

	public void setPercHh(double percHh) {
		this.percHh = percHh;
	}

	public double getPercAq() {
		return percAq;
	}

	public void setPercAq(double percAq) {
		this.percAq = percAq;
	}

	public double getPercHh3() {
		return percHh3;
	}

	public void setPercHh3(double percHh3) {
		this.percHh3 = percHh3;
	}

	public double getPercAq3() {
		return percAq3;
	}

	public void setPercAq3(double percAq3) {
		this.percAq3 = percAq3;
	}

	public double getPercHhOld() {
		return percHhOld;
	}

	public void setPercHhOld(double percHhOld) {
		this.percHhOld = percHhOld;
	}

	public double getPercAqOld() {
		return percAqOld;
	}

	public void setPercAqOld(double percAqOld) {
		this.percAqOld = percAqOld;
	}

	public int getnNew() {
		return nNew;
	}

	public void setnNew(int nNew) {
		this.nNew = nNew;
	}

	public int getNpestNewHh() {
		return npestNewHh;
	}

	public void setNpestNewHh(int npestNewHh) {
		this.npestNewHh = npestNewHh;
	}

	public int getN3() {
		return n3;
	}

	public void setN3(int n3) {
		this.n3 = n3;
	}

	public int getNpest3Hh() {
		return npest3Hh;
	}

	public void setNpest3Hh(int npest3Hh) {
		this.npest3Hh = npest3Hh;
	}

	public double getnOldAve() {
		return nOldAve;
	}

	public void setnOldAve(double nOldAve) {
		this.nOldAve = nOldAve;
	}

	public int getnYrs() {
		return nYrs;
	}

	public void setnYrs(int nYrs) {
		this.nYrs = nYrs;
	}

	public int getnOldHh() {
		return nOldHh;
	}

	public void setnOldHh(int nOldHh) {
		this.nOldHh = nOldHh;
	}

	public int getnNewAq() {
		return nNewAq;
	}

	public void setnNewAq(int nNewAq) {
		this.nNewAq = nNewAq;
	}

	public int getnAq3() {
		return nAq3;
	}

	public void setnAq3(int nAq3) {
		this.nAq3 = nAq3;
	}

	public int getnOldAq() {
		return nOldAq;
	}

	public void setnOldAq(int nOldAq) {
		this.nOldAq = nOldAq;
	}

	public int getnHhOld() {
		return nHhOld;
	}

	public void setnHhOld(int nHhOld) {
		this.nHhOld = nHhOld;
	}

	public int getnAqOld() {
		return nAqOld;
	}

	public void setnAqOld(int nAqOld) {
		this.nAqOld = nAqOld;
	}

	public String getPestNewExceedHh() {
		return pestNewExceedHh;
	}

	public void setPestNewExceedHh(String pestNewExceedHh) {
		this.pestNewExceedHh = pestNewExceedHh;
	}

	public String getPestNewExceedAq() {
		return pestNewExceedAq;
	}

	public void setPestNewExceedAq(String pestNewExceedAq) {
		this.pestNewExceedAq = pestNewExceedAq;
	}

	public String getPest3ExceedHh() {
		return pest3ExceedHh;
	}

	public void setPest3ExceedHh(String pest3ExceedHh) {
		this.pest3ExceedHh = pest3ExceedHh;
	}

	public String getPest3ExceedAq() {
		return pest3ExceedAq;
	}

	public void setPest3ExceedAq(String pest3ExceedAq) {
		this.pest3ExceedAq = pest3ExceedAq;
	}

	public String getPestOldExceedHh() {
		return pestOldExceedHh;
	}

	public void setPestOldExceedHh(String pestOldExceedHh) {
		this.pestOldExceedHh = pestOldExceedHh;
	}

	public String getPestOldExceedAq() {
		return pestOldExceedAq;
	}

	public void setPestOldExceedAq(String pestOldExceedAq) {
		this.pestOldExceedAq = pestOldExceedAq;
	}

	public String getHh1() {
		return hh1;
	}

	public void setHh1(String hh1) {
		this.hh1 = hh1;
	}

	public String getHh2() {
		return hh2;
	}

	public void setHh2(String hh2) {
		this.hh2 = hh2;
	}

	public String getHh3() {
		return hh3;
	}

	public void setHh3(String hh3) {
		this.hh3 = hh3;
	}

	public String getNdet1Pname() {
		return ndet1Pname;
	}

	public void setNdet1Pname(String ndet1Pname) {
		this.ndet1Pname = ndet1Pname;
	}

	public double getPerc1New() {
		return perc1New;
	}

	public void setPerc1New(double perc1New) {
		this.perc1New = perc1New;
	}

	public double getPerc13() {
		return perc13;
	}

	public void setPerc13(double perc13) {
		this.perc13 = perc13;
	}

	public double getPerc1Old() {
		return perc1Old;
	}

	public void setPerc1Old(double perc1Old) {
		this.perc1Old = perc1Old;
	}

	public double getPerc1NewCom() {
		return perc1NewCom;
	}

	public void setPerc1NewCom(double perc1NewCom) {
		this.perc1NewCom = perc1NewCom;
	}

	public double getPerc13Com() {
		return perc13Com;
	}

	public void setPerc13Com(double perc13Com) {
		this.perc13Com = perc13Com;
	}

	public double getPerc1OldCom() {
		return perc1OldCom;
	}

	public void setPerc1OldCom(double perc1OldCom) {
		this.perc1OldCom = perc1OldCom;
	}

	public int getNsamp1New() {
		return nsamp1New;
	}

	public void setNsamp1New(int nsamp1New) {
		this.nsamp1New = nsamp1New;
	}

	public double getNsamp13() {
		return nsamp13;
	}

	public void setNsamp13(int nsamp13) {
		this.nsamp13 = nsamp13;
	}

	public double getNsamp1Old() {
		return nsamp1Old;
	}

	public void setNsamp1Old(double nsamp1Old) {
		this.nsamp1Old = nsamp1Old;
	}

	public String getNdet2Pname() {
		return ndet2Pname;
	}

	public void setNdet2Pname(String ndet2Pname) {
		this.ndet2Pname = ndet2Pname;
	}

	public double getPerc2New() {
		return perc2New;
	}

	public void setPerc2New(double perc2New) {
		this.perc2New = perc2New;
	}

	public double getPerc23() {
		return perc23;
	}

	public void setPerc23(double perc23) {
		this.perc23 = perc23;
	}

	public double getPerc2Old() {
		return perc2Old;
	}

	public void setPerc2Old(double perc2Old) {
		this.perc2Old = perc2Old;
	}

	public double getPerc2NewCom() {
		return perc2NewCom;
	}

	public void setPerc2NewCom(double perc2NewCom) {
		this.perc2NewCom = perc2NewCom;
	}

	public double getPerc23Com() {
		return perc23Com;
	}

	public void setPerc23Com(double perc23Com) {
		this.perc23Com = perc23Com;
	}

	public double getPerc2OldCom() {
		return perc2OldCom;
	}

	public void setPerc2OldCom(double perc2OldCom) {
		this.perc2OldCom = perc2OldCom;
	}

	public int getNsamp2New() {
		return nsamp2New;
	}

	public void setNsamp2New(int nsamp2New) {
		this.nsamp2New = nsamp2New;
	}

	public double getNsamp23() {
		return nsamp23;
	}

	public void setNsamp23(int nsamp23) {
		this.nsamp23 = nsamp23;
	}

	public double getNsamp2Old() {
		return nsamp2Old;
	}

	public void setNsamp2Old(double nsamp2Old) {
		this.nsamp2Old = nsamp2Old;
	}

	public String getNdet3Pname() {
		return ndet3Pname;
	}

	public void setNdet3Pname(String ndet3Pname) {
		this.ndet3Pname = ndet3Pname;
	}

	public double getPerc3New() {
		return perc3New;
	}

	public void setPerc3New(double perc3New) {
		this.perc3New = perc3New;
	}

	public double getPerc33() {
		return perc33;
	}

	public void setPerc33(double perc33) {
		this.perc33 = perc33;
	}

	public double getPerc3Old() {
		return perc3Old;
	}

	public void setPerc3Old(double perc3Old) {
		this.perc3Old = perc3Old;
	}

	public double getPerc3NewCom() {
		return perc3NewCom;
	}

	public void setPerc3NewCom(double perc3NewCom) {
		this.perc3NewCom = perc3NewCom;
	}

	public double getPerc33Com() {
		return perc33Com;
	}

	public void setPerc33Com(double perc33Com) {
		this.perc33Com = perc33Com;
	}

	public double getPerc3OldCom() {
		return perc3OldCom;
	}

	public void setPerc3OldCom(double perc3OldCom) {
		this.perc3OldCom = perc3OldCom;
	}

	public int getNsamp3New() {
		return nsamp3New;
	}

	public void setNsamp3New(int nsamp3New) {
		this.nsamp3New = nsamp3New;
	}

	public double getNsamp33() {
		return nsamp33;
	}

	public void setNsamp33(int nsamp33) {
		this.nsamp33 = nsamp33;
	}

	public double getNsamp3Old() {
		return nsamp3Old;
	}

	public void setNsamp3Old(double nsamp3Old) {
		this.nsamp3Old = nsamp3Old;
	}

	public String getNdet4Pname() {
		return ndet4Pname;
	}

	public void setNdet4Pname(String ndet4Pname) {
		this.ndet4Pname = ndet4Pname;
	}

	public double getPerc4New() {
		return perc4New;
	}

	public void setPerc4New(double perc4New) {
		this.perc4New = perc4New;
	}

	public double getPerc43() {
		return perc43;
	}

	public void setPerc43(double perc43) {
		this.perc43 = perc43;
	}

	public double getPerc4Old() {
		return perc4Old;
	}

	public void setPerc4Old(double perc4Old) {
		this.perc4Old = perc4Old;
	}

	public double getPerc4NewCom() {
		return perc4NewCom;
	}

	public void setPerc4NewCom(double perc4NewCom) {
		this.perc4NewCom = perc4NewCom;
	}

	public double getPerc43Com() {
		return perc43Com;
	}

	public void setPerc43Com(double perc43Com) {
		this.perc43Com = perc43Com;
	}

	public double getPerc4OldCom() {
		return perc4OldCom;
	}

	public void setPerc4OldCom(double perc4OldCom) {
		this.perc4OldCom = perc4OldCom;
	}

	public int getNsamp4New() {
		return nsamp4New;
	}

	public void setNsamp4New(int nsamp4New) {
		this.nsamp4New = nsamp4New;
	}

	public double getNsamp43() {
		return nsamp43;
	}

	public void setNsamp43(int nsamp43) {
		this.nsamp43 = nsamp43;
	}

	public double getNsamp4Old() {
		return nsamp4Old;
	}

	public void setNsamp4Old(double nsamp4Old) {
		this.nsamp4Old = nsamp4Old;
	}

	public String getNdet5Pname() {
		return ndet5Pname;
	}

	public void setNdet5Pname(String ndet5Pname) {
		this.ndet5Pname = ndet5Pname;
	}

	public double getPerc5New() {
		return perc5New;
	}

	public void setPerc5New(double perc5New) {
		this.perc5New = perc5New;
	}

	public double getPerc53() {
		return perc53;
	}

	public void setPerc53(double perc53) {
		this.perc53 = perc53;
	}

	public double getPerc5Old() {
		return perc5Old;
	}

	public void setPerc5Old(double perc5Old) {
		this.perc5Old = perc5Old;
	}

	public double getPerc5NewCom() {
		return perc5NewCom;
	}

	public void setPerc5NewCom(double perc5NewCom) {
		this.perc5NewCom = perc5NewCom;
	}

	public double getPerc53Com() {
		return perc53Com;
	}

	public void setPerc53Com(double perc53Com) {
		this.perc53Com = perc53Com;
	}

	public double getPerc5OldCom() {
		return perc5OldCom;
	}

	public void setPerc5OldCom(double perc5OldCom) {
		this.perc5OldCom = perc5OldCom;
	}

	public int getNsamp5New() {
		return nsamp5New;
	}

	public void setNsamp5New(int nsamp5New) {
		this.nsamp5New = nsamp5New;
	}

	public double getNsamp53() {
		return nsamp53;
	}

	public void setNsamp53(int nsamp53) {
		this.nsamp53 = nsamp53;
	}

	public double getNsamp5Old() {
		return nsamp5Old;
	}

	public void setNsamp5Old(double nsamp5Old) {
		this.nsamp5Old = nsamp5Old;
	}

	public String getNdet6Pname() {
		return ndet6Pname;
	}

	public void setNdet6Pname(String ndet6Pname) {
		this.ndet6Pname = ndet6Pname;
	}

	public double getPerc6New() {
		return perc6New;
	}

	public void setPerc6New(double perc6New) {
		this.perc6New = perc6New;
	}

	public double getPerc63() {
		return perc63;
	}

	public void setPerc63(double perc63) {
		this.perc63 = perc63;
	}

	public double getPerc6Old() {
		return perc6Old;
	}

	public void setPerc6Old(double perc6Old) {
		this.perc6Old = perc6Old;
	}

	public double getPerc6NewCom() {
		return perc6NewCom;
	}

	public void setPerc6NewCom(double perc6NewCom) {
		this.perc6NewCom = perc6NewCom;
	}

	public double getPerc63Com() {
		return perc63Com;
	}

	public void setPerc63Com(double perc63Com) {
		this.perc63Com = perc63Com;
	}

	public double getPerc6OldCom() {
		return perc6OldCom;
	}

	public void setPerc6OldCom(double perc6OldCom) {
		this.perc6OldCom = perc6OldCom;
	}

	public int getNsamp6New() {
		return nsamp6New;
	}

	public void setNsamp6New(int nsamp6New) {
		this.nsamp6New = nsamp6New;
	}

	public double getNsamp63() {
		return nsamp63;
	}

	public void setNsamp63(int nsamp63) {
		this.nsamp63 = nsamp63;
	}

	public double getNsamp6Old() {
		return nsamp6Old;
	}

	public void setNsamp6Old(double nsamp6Old) {
		this.nsamp6Old = nsamp6Old;
	}

	public String getNdet7Pname() {
		return ndet7Pname;
	}

	public void setNdet7Pname(String ndet7Pname) {
		this.ndet7Pname = ndet7Pname;
	}

	public double getPerc7New() {
		return perc7New;
	}

	public void setPerc7New(double perc7New) {
		this.perc7New = perc7New;
	}

	public double getPerc73() {
		return perc73;
	}

	public void setPerc73(double perc73) {
		this.perc73 = perc73;
	}

	public double getPerc7Old() {
		return perc7Old;
	}

	public void setPerc7Old(double perc7Old) {
		this.perc7Old = perc7Old;
	}

	public double getPerc7NewCom() {
		return perc7NewCom;
	}

	public void setPerc7NewCom(double perc7NewCom) {
		this.perc7NewCom = perc7NewCom;
	}

	public double getPerc73Com() {
		return perc73Com;
	}

	public void setPerc73Com(double perc73Com) {
		this.perc73Com = perc73Com;
	}

	public double getPerc7OldCom() {
		return perc7OldCom;
	}

	public void setPerc7OldCom(double perc7OldCom) {
		this.perc7OldCom = perc7OldCom;
	}

	public int getNsamp7New() {
		return nsamp7New;
	}

	public void setNsamp7New(int nsamp7New) {
		this.nsamp7New = nsamp7New;
	}

	public double getNsamp73() {
		return nsamp73;
	}

	public void setNsamp73(int nsamp73) {
		this.nsamp73 = nsamp73;
	}

	public double getNsamp7Old() {
		return nsamp7Old;
	}

	public void setNsamp7Old(double nsamp7Old) {
		this.nsamp7Old = nsamp7Old;
	}

	public String getNdet8Pname() {
		return ndet8Pname;
	}

	public void setNdet8Pname(String ndet8Pname) {
		this.ndet8Pname = ndet8Pname;
	}

	public double getPerc8New() {
		return perc8New;
	}

	public void setPerc8New(double perc8New) {
		this.perc8New = perc8New;
	}

	public double getPerc83() {
		return perc83;
	}

	public void setPerc83(double perc83) {
		this.perc83 = perc83;
	}

	public double getPerc8Old() {
		return perc8Old;
	}

	public void setPerc8Old(double perc8Old) {
		this.perc8Old = perc8Old;
	}

	public double getPerc8NewCom() {
		return perc8NewCom;
	}

	public void setPerc8NewCom(double perc8NewCom) {
		this.perc8NewCom = perc8NewCom;
	}

	public double getPerc83Com() {
		return perc83Com;
	}

	public void setPerc83Com(double perc83Com) {
		this.perc83Com = perc83Com;
	}

	public double getPerc8OldCom() {
		return perc8OldCom;
	}

	public void setPerc8OldCom(double perc8OldCom) {
		this.perc8OldCom = perc8OldCom;
	}

	public int getNsamp8New() {
		return nsamp8New;
	}

	public void setNsamp8New(int nsamp8New) {
		this.nsamp8New = nsamp8New;
	}

	public double getNsamp83() {
		return nsamp83;
	}

	public void setNsamp83(int nsamp83) {
		this.nsamp83 = nsamp83;
	}

	public double getNsamp8Old() {
		return nsamp8Old;
	}

	public void setNsamp8Old(double nsamp8Old) {
		this.nsamp8Old = nsamp8Old;
	}

	public String getNdet9Pname() {
		return ndet9Pname;
	}

	public void setNdet9Pname(String ndet9Pname) {
		this.ndet9Pname = ndet9Pname;
	}

	public double getPerc9New() {
		return perc9New;
	}

	public void setPerc9New(double perc9New) {
		this.perc9New = perc9New;
	}

	public double getPerc93() {
		return perc93;
	}

	public void setPerc93(double perc93) {
		this.perc93 = perc93;
	}

	public double getPerc9Old() {
		return perc9Old;
	}

	public void setPerc9Old(double perc9Old) {
		this.perc9Old = perc9Old;
	}

	public double getPerc9NewCom() {
		return perc9NewCom;
	}

	public void setPerc9NewCom(double perc9NewCom) {
		this.perc9NewCom = perc9NewCom;
	}

	public double getPerc93Com() {
		return perc93Com;
	}

	public void setPerc93Com(double perc93Com) {
		this.perc93Com = perc93Com;
	}

	public double getPerc9OldCom() {
		return perc9OldCom;
	}

	public void setPerc9OldCom(double perc9OldCom) {
		this.perc9OldCom = perc9OldCom;
	}

	public int getNsamp9New() {
		return nsamp9New;
	}

	public void setNsamp9New(int nsamp9New) {
		this.nsamp9New = nsamp9New;
	}

	public double getNsamp93() {
		return nsamp93;
	}

	public void setNsamp93(int nsamp93) {
		this.nsamp93 = nsamp93;
	}

	public double getNsamp9Old() {
		return nsamp9Old;
	}

	public void setNsamp9Old(double nsamp9Old) {
		this.nsamp9Old = nsamp9Old;
	}

	public String getNdet10Pname() {
		return ndet10Pname;
	}

	public void setNdet10Pname(String ndet10Pname) {
		this.ndet10Pname = ndet10Pname;
	}

	public double getPerc10New() {
		return perc10New;
	}

	public void setPerc10New(double perc10New) {
		this.perc10New = perc10New;
	}

	public double getPerc103() {
		return perc103;
	}

	public void setPerc103(double perc103) {
		this.perc103 = perc103;
	}

	public double getPerc10Old() {
		return perc10Old;
	}

	public void setPerc10Old(double perc10Old) {
		this.perc10Old = perc10Old;
	}

	public double getPerc10NewCom() {
		return perc10NewCom;
	}

	public void setPerc10NewCom(double perc10NewCom) {
		this.perc10NewCom = perc10NewCom;
	}

	public double getPerc103Com() {
		return perc103Com;
	}

	public void setPerc103Com(double perc103Com) {
		this.perc103Com = perc103Com;
	}

	public double getPerc10OldCom() {
		return perc10OldCom;
	}

	public void setPerc10OldCom(double perc10OldCom) {
		this.perc10OldCom = perc10OldCom;
	}

	public int getNsamp10New() {
		return nsamp10New;
	}

	public void setNsamp10New(int nsamp10New) {
		this.nsamp10New = nsamp10New;
	}

	public double getNsamp103() {
		return nsamp103;
	}

	public void setNsamp103(int nsamp103) {
		this.nsamp103 = nsamp103;
	}

	public double getNsamp10Old() {
		return nsamp10Old;
	}

	public void setNsamp10Old(double nsamp10Old) {
		this.nsamp10Old = nsamp10Old;
	}

	public int getNpestNew() {
		return npestNew;
	}

	public void setNpestNew(int npestNew) {
		this.npestNew = npestNew;
	}

	public int getNpest3() {
		return npest3;
	}

	public void setNpest3(int npest3) {
		this.npest3 = npest3;
	}

	public int getNdetsNew() {
		return ndetsNew;
	}

	public void setNdetsNew(int ndetsNew) {
		this.ndetsNew = ndetsNew;
	}

	public int getNdets3() {
		return ndets3;
	}

	public void setNdets3(int ndets3) {
		this.ndets3 = ndets3;
	}

	public int getNdetsHh() {
		return ndetsHh;
	}

	public void setNdetsHh(int ndetsHh) {
		this.ndetsHh = ndetsHh;
	}

	public int getNdetsAq() {
		return ndetsAq;
	}

	public void setNdetsAq(int ndetsAq) {
		this.ndetsAq = ndetsAq;
	}

	public int getnHh() {
		return nHh;
	}

	public void setnHh(int nHh) {
		this.nHh = nHh;
	}

	public int getnAq() {
		return nAq;
	}

	public void setnAq(int nAq) {
		this.nAq = nAq;
	}

	public int getNdetsHh3() {
		return ndetsHh3;
	}

	public void setNdetsHh3(int ndetsHh3) {
		this.ndetsHh3 = ndetsHh3;
	}

	public int getNdetsAq3() {
		return ndetsAq3;
	}

	public void setNdetsAq3(int ndetsAq3) {
		this.ndetsAq3 = ndetsAq3;
	}

	public int getnHh3() {
		return nHh3;
	}

	public void setnHh3(int nHh3) {
		this.nHh3 = nHh3;
	}

	public double getnNuts() {
		return nNuts;
	}

	public void setnNuts(double nNuts) {
		this.nNuts = nNuts;
	}

	public double getnNutsDet() {
		return nNutsDet;
	}

	public void setnNutsDet(double nNutsDet) {
		this.nNutsDet = nNutsDet;
	}

	public double getnNutsDetHh() {
		return nNutsDetHh;
	}

	public void setnNutsDetHh(double nNutsDetHh) {
		this.nNutsDetHh = nNutsDetHh;
	}

	public double getnNutsDetAq() {
		return nNutsDetAq;
	}

	public void setnNutsDetAq(double nNutsDetAq) {
		this.nNutsDetAq = nNutsDetAq;
	}

	public int getnNutHh() {
		return nNutHh;
	}

	public void setnNutHh(int nNutHh) {
		this.nNutHh = nNutHh;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public int getNpest3Aq() {
		return npest3Aq;
	}

	public void setNpest3Aq(int npest3Aq) {
		this.npest3Aq = npest3Aq;
	}

}
