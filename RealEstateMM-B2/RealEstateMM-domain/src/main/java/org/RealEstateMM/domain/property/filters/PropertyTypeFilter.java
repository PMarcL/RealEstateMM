package org.RealEstateMM.domain.property.filters;

import java.util.Collection;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertyTypeFilter {

	public Collection<Property> filter(Collection<Property> properties, PropertyType type) {
		return properties.stream().filter(p -> p.hasType(type)).collect(Collectors.toList());
	}

}
