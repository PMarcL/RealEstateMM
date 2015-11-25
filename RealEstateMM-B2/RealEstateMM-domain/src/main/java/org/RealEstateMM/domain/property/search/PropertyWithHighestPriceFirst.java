package org.RealEstateMM.domain.property.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.RealEstateMM.domain.property.Property;

public class PropertyWithHighestPriceFirst implements PropertyOrderingStrategy {

	@Override
	public List<Property> getOrderedProperties(List<Property> properties) {
		Comparator<Property> highestPriceFirst = Comparator.comparing(Property::getPrice).reversed();
		Collections.sort(properties, highestPriceFirst);
		return properties;
	}
}
