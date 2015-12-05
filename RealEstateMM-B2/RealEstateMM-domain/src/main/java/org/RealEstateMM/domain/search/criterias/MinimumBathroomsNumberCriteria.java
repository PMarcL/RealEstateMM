package org.RealEstateMM.domain.search.criterias;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.search.PropertyCriteria;

public class MinimumBathroomsNumberCriteria implements SearchCriteria {

	private int minNumberOfBathrooms;

	// TODO
	public MinimumBathroomsNumberCriteria(int minNumberOfBathrooms) {
		this.minNumberOfBathrooms = minNumberOfBathrooms;
	}

	// @Override
	// public List<Property> getFilteredProperties(List<Property> properties) {
	// return properties.stream().filter(p ->
	// p.hasAtLeastNBathrooms(minNumberOfBathrooms))
	// .collect(Collectors.toList());
	// }

}
