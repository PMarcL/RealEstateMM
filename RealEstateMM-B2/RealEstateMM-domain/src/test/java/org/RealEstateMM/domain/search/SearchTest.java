package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingStrategy;
import org.junit.Before;
import org.junit.Test;

public class SearchTest {
	private final List<Property> ALL_PROPERTIES = new ArrayList<>();
	private final List<Property> FILTERED_PROPERTIES = new ArrayList<>();
	private final List<Property> SORTED_PROPERTIES = new ArrayList<>();

	private List<SearchCriteria> criterias;
	private SearchCriteria criteria1;
	private SearchCriteria criteria2;
	private PropertyOrderingStrategy orderBy;

	private Search search;

	@Before
	public void setup() {
		criterias = new ArrayList<>();
		criteria1 = mock(SearchCriteria.class);
		given(criteria1.filterProperties(ALL_PROPERTIES)).willReturn(FILTERED_PROPERTIES);
		criteria2 = mock(SearchCriteria.class);
		given(criteria2.filterProperties(any())).willReturn(FILTERED_PROPERTIES);
		criterias.add(criteria1);
		criterias.add(criteria2);
		orderBy = mock(PropertyOrderingStrategy.class);
		given(orderBy.sort(FILTERED_PROPERTIES)).willReturn(SORTED_PROPERTIES);

		search = new Search(criterias, orderBy);
	}

	@Test
	public void whenExecuteSearchShouldApplyEachSearchCriteriaWithResultOfPreviousCriteria() {
		search.execute(ALL_PROPERTIES);

		verify(criteria1).filterProperties(ALL_PROPERTIES);
		verify(criteria2).filterProperties(FILTERED_PROPERTIES);
	}

	@Test
	public void whenExecuteSearchShouldReturnOrderedResultsFromLastCriteria() {
		List<Property> results = search.execute(ALL_PROPERTIES);
		assertSame(SORTED_PROPERTIES, results);
	}
}
