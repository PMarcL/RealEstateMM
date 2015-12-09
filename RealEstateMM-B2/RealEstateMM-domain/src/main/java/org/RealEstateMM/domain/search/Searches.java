package org.RealEstateMM.domain.search;

import java.util.ArrayList;
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

	public List<String> findSearchesForUser(String pseudonym) {
		List<SearchDescription> savedSearches = repository.findSearchesForUser(pseudonym);
		ArrayList<String> result = new ArrayList<>();
		savedSearches.stream().forEach(s -> result.add(s.getName()));
		return result;
	}

	public void deleteSearch(String pseudonym, String searchName) {
		repository.remove(pseudonym, searchName);
	}

	public SearchDescription getSearch(String pseudonym, String searchName) {
		return repository.getSearchWithNameForUser(pseudonym, searchName);
	}

}
