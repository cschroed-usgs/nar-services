package gov.usgs.cida.nar.domain;


public class Herbicide extends Pesticide {
	public static final String TYPE = "HERBICIDE";
	private final String name;
	public Herbicide(String name){
		this.name = name;
	}
	
	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getName() {
		return name;
	}

}