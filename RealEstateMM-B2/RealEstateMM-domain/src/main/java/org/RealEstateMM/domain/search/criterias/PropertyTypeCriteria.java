package org.RealEstateMM.domain.search.criterias;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertyTypeCriteria implements SearchCriteria {

	private List<PropertyType> searchedTypes;

	public PropertyTypeCriteria(List<PropertyType> searchedTypes) {
		this.searchedTypes = searchedTypes;
	}

	@Override
	public List<Property> filterProperties(List<Property> properties) {
		List<Property> result = new ArrayList<>();
		for (Property prop : properties) {
			if (hasSearchedType(prop)) {
				result.add(prop);
			}
		}

		return result;
	}

	private boolean hasSearchedType(Property prop) {
		boolean hasType = searchedTypes.isEmpty();
		for (PropertyType type : searchedTypes) {
			if (prop.hasType(type)) {
				hasType = true;
			}
		}

		return hasType;
	}
}
