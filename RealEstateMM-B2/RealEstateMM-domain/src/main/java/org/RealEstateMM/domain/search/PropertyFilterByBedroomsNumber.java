package org.RealEstateMM.domain.search;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;

public class PropertyFilterByBedroomsNumber implements PropertySearchFilterStrategy {

	private int minNumberOfBedrooms;

	public PropertyFilterByBedroomsNumber(int minNumberOfBedrooms) {
		this.minNumberOfBedrooms = minNumberOfBedrooms;
	}

	@Override
	public List<Property> getFilteredProperties(List<Property> properties) {
		return properties.stream().filter(p -> p.hasAtLeastNBedrooms(minNumberOfBedrooms)).collect(Collectors.toList());
	}

}
