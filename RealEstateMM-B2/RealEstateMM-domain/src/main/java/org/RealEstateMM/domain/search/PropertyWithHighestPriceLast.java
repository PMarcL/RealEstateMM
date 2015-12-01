package org.RealEstateMM.domain.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.RealEstateMM.domain.property.Property;

public class PropertyWithHighestPriceLast implements PropertyOrderingStrategy {

	@Override
	public List<Property> getOrderedProperties(List<Property> properties) {
		Comparator<Property> highestPriceLast = Comparator.comparing(Property::getPrice);
		Collections.sort(properties, highestPriceLast);
		return properties;
	}
}
