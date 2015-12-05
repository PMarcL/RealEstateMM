package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.junit.Before;
import org.junit.Test;

public class SearchFactoryTest {
	private static PropertyOrderingType ORDER_BY = PropertyOrderingType.NO_ORDERING;

	private PropertyOrderingStrategyFactory orderingStrategyFactory;
	private List<SearchCriteria> criterias;
	private SearchFactory factory;

	@Before
	public void setup() {
		orderingStrategyFactory = mock(PropertyOrderingStrategyFactory.class);
		criterias = new ArrayList<>();
		factory = new SearchFactory(orderingStrategyFactory);
	}

	@Test
	public void whenCreateSearchShouldCreateOrderingStrategy() {
		factory.createSearch(ORDER_BY, criterias);
		verify(orderingStrategyFactory).createOrderingStrategy(ORDER_BY);
	}

	@Test
	public void whenCreateSearchShouldReturnSearchInstance() {
		Search result = factory.createSearch(ORDER_BY, criterias);
		assertTrue(result instanceof Search);
	}
}
