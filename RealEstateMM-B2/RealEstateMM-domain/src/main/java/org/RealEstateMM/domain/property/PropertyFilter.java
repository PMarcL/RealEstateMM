package org.RealEstateMM.domain.property;

import java.util.ArrayList;
import java.util.Calendar;

public class PropertyFilter {

	private PropertyRepository propertyRepository;

	public PropertyFilter(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	public ArrayList<Property> getNumberOfNumberOfPropertiesSoldThisYear() {
		ArrayList<Property> properties = propertyRepository.getAllProperties();
		return null; // return properties.stream().filter(p ->
						// isSoldThisYear(p)).;
	}

	private boolean isSoldThisYear(Property property) {
		Calendar now = Calendar.getInstance();
		Calendar soldDate = Calendar.getInstance();
		soldDate.setTime(property.getSaleDate());
		return soldDate.get(Calendar.YEAR) == now.get(Calendar.YEAR);
	}

}
