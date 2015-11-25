package org.RealEstateMM.domain.property.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.RealEstateMM.domain.property.Property;

public class PropertyRecentlyUploadedLast implements PropertyOrderingStrategy {

	@Override
	public List<Property> getOrderedProperties(List<Property> properties) {
		Comparator<Property> mostRecentPropertyLast = Comparator.comparing(Property::getCreationDate);
		Collections.sort(properties, mostRecentPropertyLast);
		return properties;
	}
}
