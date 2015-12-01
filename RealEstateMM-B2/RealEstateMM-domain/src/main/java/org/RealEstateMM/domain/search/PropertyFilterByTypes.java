package org.RealEstateMM.domain.search;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertyFilterByTypes implements PropertySearchFilterStrategy {

	public PropertyFilterByTypes(ArrayList<PropertyType> propertyTypesToFilter) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Property> getFilteredProperties(List<Property> properties) {
		return properties;
	}
}
