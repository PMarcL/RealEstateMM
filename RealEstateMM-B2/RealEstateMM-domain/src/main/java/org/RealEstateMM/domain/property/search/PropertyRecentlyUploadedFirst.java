package org.RealEstateMM.domain.property.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.RealEstateMM.domain.property.Property;

public class PropertyRecentlyUploadedFirst implements PropertyOrderingStrategy {

	@Override
	public List<Property> getOrderedProperties(List<Property> properties) {
		Comparator<Property> mostRecentPropertyFirst = Comparator.comparing(Property::getCreationDate).reversed();
		Collections.sort(properties, mostRecentPropertyFirst);
		return properties;
	}
}
