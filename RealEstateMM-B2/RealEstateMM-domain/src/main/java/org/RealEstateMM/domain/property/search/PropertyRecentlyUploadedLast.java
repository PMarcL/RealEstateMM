package org.RealEstateMM.domain.property.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;

public class PropertyRecentlyUploadedLast implements PropertyOrderingStrategy {

	@Override
	public ArrayList<Property> getOrderedProperties(PropertyRepository propertyRepository) {
		ArrayList<Property> properties = propertyRepository.getAllProperties();
		Comparator<Property> mostRecentPropertyFirst = Comparator.comparing(Property::getCreationDate);

		Collections.sort(properties, mostRecentPropertyFirst);

		return properties;
	}
}
