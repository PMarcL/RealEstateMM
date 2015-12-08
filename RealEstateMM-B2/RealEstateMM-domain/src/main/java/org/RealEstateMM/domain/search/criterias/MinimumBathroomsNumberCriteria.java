package org.RealEstateMM.domain.search.criterias;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;

public class MinimumBathroomsNumberCriteria implements SearchCriteria {

	private int minNumberOfBathrooms;

	public MinimumBathroomsNumberCriteria(int minNumberOfBathrooms) {
		this.minNumberOfBathrooms = minNumberOfBathrooms;
	}

	@Override
	public List<Property> filterProperties(List<Property> properties) {
		return properties.stream().filter(p -> p.hasAtLeastNBathrooms(minNumberOfBathrooms))
				.collect(Collectors.toList());
	}

}
