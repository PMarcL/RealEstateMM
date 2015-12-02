package org.RealEstateMM.domain.search;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;

public class PropertySearchFilterComposite implements PropertySearchFilterStrategy {

	private ArrayList<PropertySearchFilterStrategy> filters;

	public PropertySearchFilterComposite() {
		filters = new ArrayList<PropertySearchFilterStrategy>();
	}

	@Override
	public List<Property> getFilteredProperties(List<Property> properties) {
		for (PropertySearchFilterStrategy filter : filters) {
			properties = filter.getFilteredProperties(properties);
		}
		return properties;
	}

	public void add(PropertySearchFilterStrategy filter) {
		filters.add(filter);
	}

	public PropertySearchFilterStrategy get(int i) {
		return filters.get(i);
	}
}
