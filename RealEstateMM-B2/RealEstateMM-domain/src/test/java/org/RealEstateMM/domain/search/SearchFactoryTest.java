package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.search.Search;
import org.RealEstateMM.domain.search.SearchDescription;
import org.RealEstateMM.domain.search.SearchFactory;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingStrategyFactory;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.junit.Before;
import org.junit.Test;

public class SearchFactoryTest {
	private static final PropertyOrderingType ORDER_BY = PropertyOrderingType.NO_ORDERING;
	private static final List<SearchCriteria> CRITERIAS = new ArrayList<>();

	private PropertyOrderingStrategyFactory orderingStrategyFactory;
	private SearchDescription searchDescription;

	private SearchFactory factory;

	@Before
	public void setup() {
		searchDescription = mock(SearchDescription.class);
		given(searchDescription.getCriterias()).willReturn(CRITERIAS);
		given(searchDescription.getOrderBy()).willReturn(ORDER_BY);
		orderingStrategyFactory = mock(PropertyOrderingStrategyFactory.class);
		factory = new SearchFactory(orderingStrategyFactory);
	}

	@Test
	public void whenCreateSearchShouldCreateOrderingStrategy() {
		factory.createSearch(searchDescription);
		verify(orderingStrategyFactory).createOrderingStrategy(ORDER_BY);
	}

	@Test
	public void whenCreateSearchShouldReturnSearchInstance() {
		Search result = factory.createSearch(searchDescription);
		assertTrue(result instanceof Search);
	}
}
