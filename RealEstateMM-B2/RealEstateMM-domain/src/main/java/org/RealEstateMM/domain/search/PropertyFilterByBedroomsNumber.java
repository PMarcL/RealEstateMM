package org.RealEstateMM.domain.search;

import java.util.List;

import org.RealEstateMM.domain.property.Property;

public class PropertyFilterByBedroomsNumber implements PropertySearchFilterStrategy {

	public PropertyFilterByBedroomsNumber(int minNumberOfBedrooms) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Property> getFilteredProperties(List<Property> properties) {
		return properties;
	}

}
