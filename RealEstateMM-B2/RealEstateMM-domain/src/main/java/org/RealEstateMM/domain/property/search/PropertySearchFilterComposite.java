package org.RealEstateMM.domain.property.search;

import java.util.List;

import org.RealEstateMM.domain.property.Property;

public class PropertySearchFilterComposite implements PropertySearchFilterStrategy {

	@Override
	public List<Property> getFilteredProperties(List<Property> properties) {
		return null;
	}

	public void add(PropertySearchFilterStrategy typeFilter) {
		// TODO Auto-generated method stub

	}
}
