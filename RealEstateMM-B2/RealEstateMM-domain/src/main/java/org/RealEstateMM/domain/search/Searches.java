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

	public List<Property> executeSearch(SearchDTO search) {
		return searchEngine.executeSearch(search);
	}

	public void save(SearchDTO search, String pseudonym) {
		repository.addSearch(search, pseudonym);
	}

	public List<String> findSearchesForUser(String pseudonym) {
		List<SearchDTO> savedSearches = repository.getSearchesForUser(pseudonym);
		ArrayList<String> result = new ArrayList<>();
		savedSearches.stream().forEach(s -> result.add(s.getName()));
		return result;
	}

	public void deleteSearch(String pseudonym, String searchName) {
		repository.remove(pseudonym, searchName);
	}

	public SearchDTO getSearch(String pseudonym, String searchName) throws SearchNotFoundException {
		return repository.getSearchWithNameForUser(pseudonym, searchName);
	}

}
