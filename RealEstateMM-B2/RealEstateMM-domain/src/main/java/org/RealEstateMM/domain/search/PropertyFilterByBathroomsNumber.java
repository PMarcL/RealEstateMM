package org.RealEstateMM.domain.search;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;

public class PropertyFilterByBathroomsNumber implements PropertySearchFilterStrategy {

	private int minNumberOfBathrooms;

	public PropertyFilterByBathroomsNumber(int minNumberOfBathrooms) {
		this.minNumberOfBathrooms = minNumberOfBathrooms;
	}

	@Override
	public List<Property> getFilteredProperties(List<Property> properties) {
		return properties.stream().filter(p -> p.hasAtLeastNBathrooms(minNumberOfBathrooms))
				.collect(Collectors.toList());
	}

}
