package org.RealEstateMM.domain.property.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;

public class PropertyWithHighestPriceLast implements PropertyOrderingStrategy {

	@Override
	public ArrayList<Property> getOrderedProperties(PropertyRepository propertyRepository) {
		ArrayList<Property> properties = propertyRepository.getAll();
		Comparator<Property> highestPriceLast = Comparator.comparing(Property::getPrice);

		Collections.sort(properties, highestPriceLast);

		return properties;
	}
}