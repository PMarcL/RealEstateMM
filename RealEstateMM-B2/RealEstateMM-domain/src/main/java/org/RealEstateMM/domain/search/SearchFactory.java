package org.RealEstateMM.domain.search;

import java.util.List;

import org.RealEstateMM.domain.search.criterias.SearchCriteria;

public class SearchFactory {

	private PropertyOrderingStrategyFactory orderingFactory;

	public SearchFactory(PropertyOrderingStrategyFactory orderingStrategyFactory) {
		this.orderingFactory = orderingStrategyFactory;
	}

	public Search createSearch(PropertyOrderingType orderBy, List<SearchCriteria> searchCriterias) {
		PropertyOrderingStrategy orderByStrategy = orderingFactory.createOrderingStrategy(orderBy);
		return new Search(searchCriterias, orderByStrategy);
	}

}
