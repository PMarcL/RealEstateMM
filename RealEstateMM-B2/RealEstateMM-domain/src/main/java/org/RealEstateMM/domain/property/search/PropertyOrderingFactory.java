package org.RealEstateMM.domain.property.search;

public class PropertyOrderingFactory {

	public PropertyOrderingStrategy getOrderingStrategy(PropertySearchParameters searchParameter) {
		switch (searchParameter) {
		case RECENTLY_UPLOADED_FIRST:
			return new PropertyRecentlyUploadedFirst();
		case RECENTLY_UPLOADED_LAST:
			return new PropertyRecentlyUploadedLast();
		case HIGHEST_PRICE_FIRST:
			return new PropertyWithHighestPriceFirst();
		case HIGHEST_PRICE_LAST:
			return new PropertyWithHighestPriceLast();
		}
		return null;
	}
}
