package org.RealEstateMM.domain.property.search;

public class PropertyOrderingFactory {

	public PropertyOrderingStrategy getOrderingStrategy(PropertySearchParameters recentlyUploadedFirst) {
		switch (recentlyUploadedFirst) {
		case RECENTLY_UPLOADED_FIRST:
			return new PropertyRecentlyUploadedFirst();
		case RECENTLY_UPLOADED_LAST:
			break;
		}
		return null;
	}

}
