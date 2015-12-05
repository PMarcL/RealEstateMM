package org.RealEstateMM.domain.search.criterias;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.filters.PropertyTypeFilter;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.search.PropertyCriteria;

public class PropertyTypeCriteria implements SearchCriteria {

	// TODO
	private PropertyTypeFilter propertyTypeFilter;
	private List<PropertyType> typesToFilter;

	public PropertyTypeCriteria(List<PropertyType> typesToFilter) {
		this.propertyTypeFilter = propertyTypeFilter;
		this.typesToFilter = typesToFilter;
	}

	// @Override
	// public List<Property> getFilteredProperties(List<Property> properties) {
	// List<Property> filteredProperties = new ArrayList<Property>();
	// for (PropertyType type : typesToFilter) {
	// filteredProperties.addAll(propertyTypeFilter.filter(properties, type));
	// }
	// return filteredProperties;
	// }
}
