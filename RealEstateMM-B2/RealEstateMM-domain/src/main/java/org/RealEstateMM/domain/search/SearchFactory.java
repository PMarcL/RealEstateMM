package org.RealEstateMM.domain.search;

import java.util.List;

import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingStrategy;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingStrategyFactory;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;

public class SearchFactory {

	private PropertyOrderingStrategyFactory orderingFactory;

	public SearchFactory(PropertyOrderingStrategyFactory orderingStrategyFactory) {
		this.orderingFactory = orderingStrategyFactory;
	}

	public Search createSearch(PropertyOrderingType orderBy, List<SearchCriteria> criterias) {
		PropertyOrderingStrategy orderByStrategy = orderingFactory.createOrderingStrategy(orderBy);
		return new Search(criterias, orderByStrategy);
	}

}
