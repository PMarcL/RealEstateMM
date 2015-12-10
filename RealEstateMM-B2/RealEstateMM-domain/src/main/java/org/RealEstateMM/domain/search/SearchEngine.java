package org.RealEstateMM.domain.search;

import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class SearchEngine {

	private PropertyRepository propertyRepository;
	private SearchAssembler searchAssembler;

	public SearchEngine(SearchAssembler searchAssembler, PropertyRepository respository) {
		this.searchAssembler = searchAssembler;
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

	public List<Property> executeSearch(SearchDTO searchDTO) {
		Search search = searchAssembler.fromDTO(searchDTO);
		return search.execute(propertyRepository.getAll());
	}
}
