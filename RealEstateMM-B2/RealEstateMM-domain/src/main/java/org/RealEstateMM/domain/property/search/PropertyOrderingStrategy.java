package org.RealEstateMM.domain.property.search;

import java.util.ArrayList;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;

public interface PropertyOrderingStrategy {

	public ArrayList<Property> getOrderedProperties(PropertyRepository propertyRepository);

}