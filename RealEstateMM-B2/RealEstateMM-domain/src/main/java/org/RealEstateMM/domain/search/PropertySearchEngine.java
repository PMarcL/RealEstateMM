package org.RealEstateMM.domain.search;

import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class PropertySearchEngine {

	private PropertyRepository repository;
	private PropertyOrderingFactory factory;

	public PropertySearchEngine(PropertyRepository respository, PropertyOrderingFactory factory) {
		this.repository = respository;
		this.factory = factory;
	}

	public List<Property> getAllProperties() {
		return repository.getAll();
	}

	public List<Property> getPropertiesFromOwner(String owner) {
		return repository.getPropertiesFromOwner(owner);
	}

	public List<Property> getOrderedProperties(PropertySearchParameters searchParameter) {
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParameter);
		return strategy.getOrderedProperties(repository);
	}

	public Property getPropertyAtAddress(PropertyAddress address) throws PropertyNotFoundException {
		Optional<Property> property = repository.getPropertyAtAddress(address);
		if (!property.isPresent()) {
			throw new PropertyNotFoundException();
		}
		return property.get();
	}
}
