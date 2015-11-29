package org.RealEstateMM.domain.property.filters;

import java.util.Collection;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;

public class PropertiesSoldThisYearFilter {

	public Collection<Property> getPropertiesSoldThisYear(Collection<Property> properties) {
		return properties.stream().filter(p -> p.isSoldThisYear()).collect(Collectors.toList());
	}

}
