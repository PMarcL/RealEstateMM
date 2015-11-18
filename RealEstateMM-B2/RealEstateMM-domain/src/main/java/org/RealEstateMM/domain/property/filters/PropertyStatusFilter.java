package org.RealEstateMM.domain.property.filters;

import java.util.Collection;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyStatus;

public class PropertyStatusFilter {

	public Collection<Property> filter(Collection<Property> properties, PropertyStatus status) {
		return properties.stream().filter(p -> p.getStatus() == status).collect(Collectors.toList());
	}
}
