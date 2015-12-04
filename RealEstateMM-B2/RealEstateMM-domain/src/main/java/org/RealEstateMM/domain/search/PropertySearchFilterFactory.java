package org.RealEstateMM.domain.search;

import org.RealEstateMM.domain.property.filters.PropertyFilterFactory;

public class PropertySearchFilterFactory {

	private PropertyFilterFactory propertyFilterFactory;

	public PropertySearchFilterFactory(PropertyFilterFactory propertyFilterFactory) {
		this.propertyFilterFactory = propertyFilterFactory;
	}

	public PropertySearchFilterStrategy getSearchFilterStrategy(PropertySearchParameters searchParam) {
		PropertySearchFilterComposite filterComposite = new PropertySearchFilterComposite();

		if (searchParam.hasPropertyTypesToFilter()) {
			PropertySearchFilterStrategy typeFilter = new PropertyFilterByTypes(searchParam.getPropertyTypesToFilter(),
					propertyFilterFactory.createPropertyTypeFilter());
			filterComposite.add(typeFilter);
		}

		if (searchParam.hasMinNumOfBedrooms()) {
			PropertySearchFilterStrategy bedroomsNumFilter = new PropertyFilterByBedroomsNumber(
					searchParam.getMinNumberOfBedrooms());
			filterComposite.add(bedroomsNumFilter);
		}

		if (searchParam.hasMinNumOfBathrooms()) {
			PropertySearchFilterStrategy bathroomsNumFilter = new PropertyFilterByBathroomsNumber(
					searchParam.getMinNumberOfBathrooms());
			filterComposite.add(bathroomsNumFilter);
		}

		return filterComposite;
	}

}
