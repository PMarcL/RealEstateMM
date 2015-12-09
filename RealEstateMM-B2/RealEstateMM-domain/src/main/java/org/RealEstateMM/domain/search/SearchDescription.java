package org.RealEstateMM.domain.search;

import java.util.List;

import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;

public class SearchDescription {

	private String name;
	private PropertyOrderingType orderBy;
	private List<SearchCriteria> criterias;

	public SearchDescription(String name, PropertyOrderingType orderBy, List<SearchCriteria> criterias) {
		this.name = name;
		this.orderBy = orderBy;
		this.criterias = criterias;
	}

	public PropertyOrderingType getOrderBy() {
		return orderBy;
	}

	public String getName() {
		return name;
	}

	public List<SearchCriteria> getCriterias() {
		return criterias;
	}

}
