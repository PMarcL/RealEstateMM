package org.RealEstateMM.domain.property.search;

import java.util.ArrayList;
import java.util.Collections;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;

public class PropertyRecentlyUploadedFirst implements PropertyOrderingStrategy {

	@Override
	public ArrayList<Property> getOrderedProperties(PropertyRepository propertyRepository) {
		ArrayList<Property> properties = propertyRepository.getAllProperties();
		Collections.reverse(properties);

		return properties;
	}

}
