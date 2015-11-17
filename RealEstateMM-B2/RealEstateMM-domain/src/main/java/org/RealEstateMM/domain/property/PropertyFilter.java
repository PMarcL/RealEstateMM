package org.RealEstateMM.domain.property;

import java.util.Collection;
import java.util.stream.Collectors;

public class PropertyFilter {

	public Collection<Property> getPropertiesSoldThisYear(Collection<Property> properties) {
		return properties.stream().filter(p -> p.isSoldThisYear()).collect(Collectors.toList());
	}

}
