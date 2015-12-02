package org.RealEstateMM.domain.property;

import java.util.Calendar;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;

public class Properties {

	private PropertyRepository repository;

	public Properties(PropertyRepository repository) {
		this.repository = repository;
	}

	public void addProperty(Property property) {
		property.setCreationDate(Calendar.getInstance().getTime());
		repository.add(property);
	}

	public void editPropertyFeatures(Property property, PropertyFeatures propertyFeatures) {
		property.updateFeatures(propertyFeatures);
		repository.updateProperty(property);
	}
}
