package org.RealEstateMM.domain.property;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyFilter {

	private PropertyRepository propertyRepository;

	public PropertyFilter(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	public ArrayList<Property> getPropertiesSoldThisYear() {
		ArrayList<Property> properties = propertyRepository.getAll();
		List<Property> filteredList = properties.stream().filter(p -> p.isSoldThisYear()).collect(Collectors.toList());
		return new ArrayList<>(filteredList);
	}

}
