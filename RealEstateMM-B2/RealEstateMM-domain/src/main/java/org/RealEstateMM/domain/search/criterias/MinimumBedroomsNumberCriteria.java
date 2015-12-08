package org.RealEstateMM.domain.search.criterias;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;

public class MinimumBedroomsNumberCriteria implements SearchCriteria {

	private int minNumberOfBedrooms;

	public MinimumBedroomsNumberCriteria(int minNumberOfBedrooms) {
		this.minNumberOfBedrooms = minNumberOfBedrooms;
	}

	@Override
	public List<Property> filterProperties(List<Property> properties) {
		return properties.stream().filter(p -> p.hasAtLeastNBedrooms(minNumberOfBedrooms)).collect(Collectors.toList());
	}

}
