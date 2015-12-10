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

	public Search createSearch(PropertyOrderingType validOrderingType, List<SearchCriteria> criterias) {
		// PropertyOrderingType orderBy = validOrderingType.getOrderBy();
		// PropertyOrderingStrategy orderByStrategy =
		// orderingFactory.createOrderingStrategy(orderBy);
		// return new Search(validOrderingType.getCriterias(), orderByStrategy);
		// TODO
		return null;
	}

}
