package org.RealEstateMM.domain.search;

import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class SearchEngine {

	private PropertyRepository propertyRepository;
	private SearchFactory searchFactory;

	public SearchEngine(SearchFactory searchFactory, PropertyRepository respository) {
		this.searchFactory = searchFactory;
		this.propertyRepository = respository;
	}

	public List<Property> getPropertiesFromOwner(String owner) {
		return propertyRepository.getPropertiesFromOwner(owner);
	}

	public Property getPropertyAtAddress(PropertyAddress address) throws PropertyNotFoundException {
		Optional<Property> property = propertyRepository.getPropertyAtAddress(address);
		if (!property.isPresent()) {
			throw new PropertyNotFoundException();
		}
		return property.get();
	}

	public List<Property> executeSearch(SearchDTO search) {
		// Search search = searchFactory.createSearch(search);
		// return search.execute(propertyRepository.getAll());
		// TODO
		return null;
	}
}
