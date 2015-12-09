package org.RealEstateMM.domain.search.criterias;

import java.util.List;

import org.RealEstateMM.domain.property.informations.PropertyType;

public class SearchCriteriaFactory {

	public SearchCriteria createPropertyTypeCriteria(List<PropertyType> acceptedPropertyTypes) {
		return new PropertyTypeCriteria(acceptedPropertyTypes);
	}

	public SearchCriteria createMinimumBedroomNumberCriteria(int minimum) {
		return new MinimumBedroomsNumberCriteria(minimum);
	}

	public SearchCriteria createMinimumBathroomNumberCriteria(int minimum) {
		return new MinimumBathroomsNumberCriteria(minimum);
	}

	public SearchCriteria createMinimumPriceCriteria(int minimumPrice) {
		return new MinimumPriceCriteria(minimumPrice);
	}

	public SearchCriteria createMaximumPriceCriteria(int maximumPrice) {
		return new MaximumPriceCriteria(maximumPrice);
	}

}
