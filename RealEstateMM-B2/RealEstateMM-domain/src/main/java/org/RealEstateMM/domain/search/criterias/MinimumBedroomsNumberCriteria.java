package org.RealEstateMM.domain.search.criterias;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.search.PropertyCriteria;

public class MinimumBedroomsNumberCriteria implements SearchCriteria {

	private int minNumberOfBedrooms;
	// TODO

	public MinimumBedroomsNumberCriteria(int minNumberOfBedrooms) {
		this.minNumberOfBedrooms = minNumberOfBedrooms;
	}

	// @Override
	// public List<Property> getFilteredProperties(List<Property> properties) {
	// return properties.stream().filter(p ->
	// p.hasAtLeastNBedrooms(minNumberOfBedrooms)).collect(Collectors.toList());
	// }

}
