package org.RealEstateMM.domain.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;

public class PropertyRecentlyUploadedLast implements PropertyOrderingStrategy {

	@Override
	public ArrayList<Property> getOrderedProperties(PropertyRepository propertyRepository) {
		ArrayList<Property> properties = propertyRepository.getAll();
		Comparator<Property> mostRecentPropertyLast = Comparator.comparing(Property::getCreationDate);

		Collections.sort(properties, mostRecentPropertyLast);

		return properties;
	}
}
