package org.RealEstateMM.domain.search;

import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;

public class Search {

	private List<SearchCriteria> searchCriterias;
	private PropertyOrderingStrategy orderByStrategy;

	public Search(List<SearchCriteria> searchCriterias, PropertyOrderingStrategy orderByStrategy) {
		this.searchCriterias = searchCriterias;
		this.orderByStrategy = orderByStrategy;
	}

	public List<Property> execute(List<Property> properties) {
		List<Property> searchResult = properties;
		for (SearchCriteria criteria : searchCriterias) {
			searchResult = criteria.filterProperties(searchResult);
		}
		return orderByStrategy.sort(searchResult);
	}

}
