package gov.usgs.cida.nar.domain;

import java.util.Objects;


public class ComparisonCategorization {
	private ComparisonCategory category;//human health, aquatic life, etc.
	private int order;//1-based index. 1 for closest to benchmark, 2 for 2nd-closest to bencmark, etc.
	public static final int ORDER_INDEX_BASE = 1;
	public ComparisonCategorization(ComparisonCategory category, int order) {
		this.category = category;
		this.setOrder(order);
	}

	public ComparisonCategorization() {}


	@Override
	public String toString() {
		return "ComparisonCategorization{" + "category=" + category + ", order=" + order + '}';
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.category);
		hash = 89 * hash + this.order;
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
		final ComparisonCategorization other = (ComparisonCategorization) obj;
		if (this.order != other.order) {
			return false;
		}
		if (this.category != other.category) {
			return false;
		}
		return true;
	}

	/**
	 * @return the category
	 */
	public ComparisonCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(ComparisonCategory category) {
		this.category = category;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		if(ORDER_INDEX_BASE > order){
			throw new IllegalArgumentException(order + " is to0 small a value, only orders with value >= " + ORDER_INDEX_BASE + " are valid");
		}
		this.order = order;
	}
	
}
