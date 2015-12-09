package org.RealEstateMM.domain.search;

import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class SearchEngine {

	private PropertyRepository repository;

	public SearchEngine(PropertyRepository respository) {
		this.repository = respository;
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

	public List<Property> executeSearch(Search search) {
		return search.execute(repository.getAll());
	}

	public void save(Search search) {
		// TODO Auto-generated method stub

	}
}
