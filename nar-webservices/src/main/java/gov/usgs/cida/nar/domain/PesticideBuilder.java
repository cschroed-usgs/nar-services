package gov.usgs.cida.nar.domain;


public class PesticideBuilder {
	private String type;
	private String name;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public Pesticide build() {
		Pesticide pest;
		switch (this.type) {
			case Herbicide.TYPE:
				pest = new Herbicide(this.name);
				break;
			case NonHerbicide.TYPE:
				pest = new NonHerbicide(this.name);
				break;
			default:
				throw new IllegalArgumentException("No pesticides available of type '" + this.type + "'.");
		}
		return pest;
	}
}
