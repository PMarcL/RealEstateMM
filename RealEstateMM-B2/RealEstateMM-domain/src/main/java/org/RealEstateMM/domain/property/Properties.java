package org.RealEstateMM.domain.property;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertyOrderingFactory;
import org.RealEstateMM.domain.property.search.PropertyOrderingStrategy;
import org.RealEstateMM.domain.property.search.PropertyOrderingParameters;
import org.RealEstateMM.domain.property.search.PropertySearchParameters;

public class Properties {

	private PropertyRepository repository;
	private PropertyOrderingFactory factory;

	public Properties(PropertyRepository repository, PropertyOrderingFactory factory) {
		this.repository = repository;
		this.factory = factory;
	}

	public void addProperty(Property property) {
		property.setCreationDate(Calendar.getInstance().getTime());
		repository.add(property);
	}

	public List<Property> getAllProperties() {
		return repository.getAll();
	}

	public void editPropertyFeatures(Property property, PropertyFeatures propertyFeatures) {
		property.updateFeatures(propertyFeatures);
		repository.updateProperty(property);
	}

	public List<Property> getPropertiesFromOwner(String owner) {
		return repository.getPropertiesFromOwner(owner);
	}

	public List<Property> getPropertiesSearchResults(PropertySearchParameters searchParameter) {
		// PropertyOrderingStrategy orderingStrategy =
		// factory.getOrderingStrategy(searchParameter);
		// List<Property> properties = repository.getAll();
		// return orderingStrategy.getOrderedProperties(properties);
		return null;
	}

	public Property getPropertyAtAddress(PropertyAddress address) throws PropertyNotFoundException {
		Optional<Property> property = repository.getPropertyAtAddress(address);
		if (!property.isPresent()) {
			throw new PropertyNotFoundException();
		}
		return property.get();
	}

}
