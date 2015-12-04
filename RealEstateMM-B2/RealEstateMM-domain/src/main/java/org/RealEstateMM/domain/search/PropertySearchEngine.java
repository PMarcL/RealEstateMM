package org.RealEstateMM.domain.search;

import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class PropertySearchEngine {

	private PropertyRepository repository;
	private PropertyOrderingFactory orderingFactory;
	private PropertySearchFilterFactory filterFactory;

	public PropertySearchEngine(PropertyRepository respository, PropertyOrderingFactory orderingFactory,
			PropertySearchFilterFactory filterFactory) {
		this.repository = respository;
		this.orderingFactory = orderingFactory;
		this.filterFactory = filterFactory;
	}

	public List<Property> getPropertiesFromOwner(String owner) {
		return repository.getPropertiesFromOwner(owner);
	}

	public Property getPropertyAtAddress(PropertyAddress address) throws PropertyNotFoundException {
		Optional<Property> property = repository.getPropertyAtAddress(address);
		if (!property.isPresent()) {
			throw new PropertyNotFoundException();
		}
		return property.get();
	}

	public List<Property> getPropertiesSearchResults(PropertySearchParameters searchParams) {
		PropertyOrderingStrategy orderingStrategy = orderingFactory.getOrderingStrategy(searchParams);
		PropertySearchFilterStrategy filteringStrategy = filterFactory.getSearchFilterStrategy(searchParams);

		List<Property> properties = repository.getAll();
		properties = filteringStrategy.getFilteredProperties(properties);
		return orderingStrategy.getOrderedProperties(properties);
	}
}
