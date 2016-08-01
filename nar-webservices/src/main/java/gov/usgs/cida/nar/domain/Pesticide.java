package gov.usgs.cida.nar.domain;

import com.google.common.base.Objects;
import org.apache.commons.lang.builder.HashCodeBuilder;


public abstract class Pesticide {
	
	protected static final String PREFIX = "PESTICIDE";
	protected static final String DELIM = "/";
	abstract public String getType();
	abstract public String getName();
	
	public final String getFullName(){
		return PREFIX + DELIM + getType() + DELIM + getName();
	}
	
	@Override
	public boolean equals(Object obj){
		boolean equal = true;
		if(obj == null){
			equal = false;
		} else if (!this.getClass().equals(obj.getClass())){
			equal = false;
		} else {
			final Pesticide other = (Pesticide)obj;
			equal = 
				Objects.equal(this.getName(), other.getName())
				&& Objects.equal(this.getType(), other.getType())
				;
		}
		
		return equal;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(this.getName())
			.append(this.getType());
		
		return hcb.toHashCode();
	}
}