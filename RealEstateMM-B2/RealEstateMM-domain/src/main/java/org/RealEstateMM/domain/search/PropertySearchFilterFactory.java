package org.RealEstateMM.domain.search;


public class PropertySearchFilterFactory {

	public PropertySearchFilterStrategy getSearchFilterStrategy(PropertySearchParameters searchParam) {
		PropertySearchFilterComposite filterComposite = new PropertySearchFilterComposite();

		if (searchParam.hasPropertyTypesToFilter()) {
			PropertySearchFilterStrategy typeFilter = new PropertyFilterByTypes(searchParam.getPropertyTypesToFilter());
			filterComposite.add(typeFilter);
		}

		if (searchParam.hasMinNumOfBedrooms()) {
			PropertySearchFilterStrategy bedroomsNumFilter = new PropertyFilterByBedroomsNumber(
					searchParam.getMinNumberOfBedrooms());
			filterComposite.add(bedroomsNumFilter);
		}

		return filterComposite;
	}

}
