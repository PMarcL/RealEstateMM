package org.RealEstateMM.domain.search;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.filters.PropertyTypeFilter;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertyFilterByTypes implements PropertySearchFilterStrategy {

	private PropertyTypeFilter propertyTypeFilter;
	private ArrayList<PropertyType> typesToFilter;

	public PropertyFilterByTypes(ArrayList<PropertyType> typesToFilter, PropertyTypeFilter propertyTypeFilter) {
		this.propertyTypeFilter = propertyTypeFilter;
		this.typesToFilter = typesToFilter;
	}

	@Override
	public List<Property> getFilteredProperties(List<Property> properties) {
		List<Property> filteredProperties = new ArrayList<Property>();
		for (PropertyType type : typesToFilter) {
			filteredProperties.addAll(propertyTypeFilter.filter(properties, type));
		}
		return filteredProperties;
	}
}
