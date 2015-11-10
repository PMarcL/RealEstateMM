package org.RealEstateMM.domain.property.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;

public class PropertyWithHighestPriceFirst implements PropertyOrderingStrategy {

	@Override
	public ArrayList<Property> getOrderedProperties(PropertyRepository propertyRepository) {
		ArrayList<Property> properties = propertyRepository.getAllProperties();
		Comparator<Property> highestPriceFirst = Comparator.comparing(Property::getPrice).reversed();

		Collections.sort(properties, highestPriceFirst);

		return properties;
	}
}
