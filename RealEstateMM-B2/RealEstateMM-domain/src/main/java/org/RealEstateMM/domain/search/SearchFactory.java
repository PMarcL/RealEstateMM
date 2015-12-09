package org.RealEstateMM.domain.search;

import org.RealEstateMM.domain.search.ordering.PropertyOrderingStrategy;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingStrategyFactory;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;

public class SearchFactory {

	private PropertyOrderingStrategyFactory orderingFactory;

	public SearchFactory(PropertyOrderingStrategyFactory orderingStrategyFactory) {
		this.orderingFactory = orderingStrategyFactory;
	}

	public Search createSearch(SearchDescription searchDescription) {
		PropertyOrderingType orderBy = searchDescription.getOrderBy();
		PropertyOrderingStrategy orderByStrategy = orderingFactory.createOrderingStrategy(orderBy);
		return new Search(searchDescription.getCriterias(), orderByStrategy);
	}

}
