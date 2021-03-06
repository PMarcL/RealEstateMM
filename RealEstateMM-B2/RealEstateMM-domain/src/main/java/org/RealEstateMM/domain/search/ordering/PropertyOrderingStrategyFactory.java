package org.RealEstateMM.domain.search.ordering;

public class PropertyOrderingStrategyFactory {

	public PropertyOrderingStrategy createOrderingStrategy(PropertyOrderingType orderingType) {
		switch (orderingType) {
		case RECENTLY_UPLOADED_FIRST:
			return new DescendingAddedDateOrdering();
		case RECENTLY_UPLOADED_LAST:
			return new AscendingAddedDateOrdering();
		case HIGHEST_PRICE_FIRST:
			return new DescendingPriceOrdering();
		case HIGHEST_PRICE_LAST:
			return new AscendingPriceOrdering();
		case NO_ORDERING:
			return new DescendingAddedDateOrdering();
		}
		return null;
	}
}
