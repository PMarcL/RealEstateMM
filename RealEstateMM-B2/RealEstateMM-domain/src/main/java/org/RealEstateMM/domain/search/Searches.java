package org.RealEstateMM.domain.search;

import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class Searches {

	private SearchEngine searchEngine;
	private SearchRepository repository;

	public Searches(SearchEngine searchEngine, SearchRepository repository) {
		this.searchEngine = searchEngine;
		this.repository = repository;
	}

	public List<Property> getPropertiesFromOwner(String owner) {
		return searchEngine.getPropertiesFromOwner(owner);
	}

	public Property getPropertyAtAddress(PropertyAddress address) throws PropertyNotFoundException {
		return searchEngine.getPropertyAtAddress(address);
	}

	public List<Property> executeSearch(SearchDescription searchDescription) {
		return searchEngine.executeSearch(searchDescription);
	}

	public void save(SearchDescription searchDescription, String pseudonym) {
		repository.persist(searchDescription, pseudonym);
	}

}
