package gov.usgs.cida.nar.domain;

/**
 * Represents a non-herbicide. For Example, fungicide, insecticide.
  */
public class NonHerbicide extends Pesticide{
	public static final String TYPE = "NON_HERBICIDE";
	private final String name;
	
	public NonHerbicide(String name){
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