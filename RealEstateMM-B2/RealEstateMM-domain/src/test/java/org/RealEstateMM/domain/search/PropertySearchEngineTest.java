package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class PropertySearchEngineTest {
	private final String OWNER = "owner90";
	private final PropertyOrderingParameters ORDERING_PARAM = PropertyOrderingParameters.RECENTLY_UPLOADED_FIRST;
	private final ArrayList<Property> PROPERTIES = new ArrayList<Property>();

	private PropertyOrderingFactory orderingFactory;
	private PropertyOrderingStrategy orderingStrategy;
	private PropertySearchParameters searchParams;
	private PropertySearchFilterFactory filterFactory;
	private PropertySearchFilterStrategy filterStrategy;
	private PropertyRepository repository;
	private Property property;
	private PropertyAddress address;

	private PropertySearchEngine searchEngine;

	@Before
	public void setup() {

		repository = mock(PropertyRepository.class);
		orderingFactory = mock(PropertyOrderingFactory.class);
		filterFactory = mock(PropertySearchFilterFactory.class);
		searchEngine = new PropertySearchEngine(repository, orderingFactory, filterFactory);

		address = mock(PropertyAddress.class);
		property = mock(Property.class);
		given(repository.getPropertyAtAddress(address)).willReturn(Optional.of(property));

		searchParams = mock(PropertySearchParameters.class);
		orderingStrategy = mock(PropertyOrderingStrategy.class);
		filterStrategy = mock(PropertySearchFilterStrategy.class);
		given(searchParams.getOrderingParam()).willReturn(ORDERING_PARAM);
		given(orderingFactory.getOrderingStrategy(searchParams)).willReturn(orderingStrategy);
		given(filterFactory.getSearchFilterStrategy(searchParams)).willReturn(filterStrategy);
	}

	@Test
	public void givenAnOwnerNameWhenGetPropertiesFromOwnerThenUsesRepository() {
		searchEngine.getPropertiesFromOwner(OWNER);
		verify(repository).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenAnOwnerNameWhenGetPropertiesFromOwnerThenReturnsPropertiesFromRepository() {
		given(repository.getPropertiesFromOwner(OWNER)).willReturn(PROPERTIES);
		List<Property> result = searchEngine.getPropertiesFromOwner(OWNER);
		assertEquals(PROPERTIES, result);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesRepository() throws Exception {
		searchEngine.getPropertyAtAddress(address);
		verify(repository).getPropertyAtAddress(address);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenReturnsPropertyGottenFromRepository() throws Exception {
		Property result = searchEngine.getPropertyAtAddress(address);
		assertEquals(property, result);
	}

	@Test(expected = PropertyNotFoundException.class)
	public void givenAnAddressWhenGetPropertyAtAddressThenThrowsExceptionIfNoPropertyIsFound() throws Exception {
		given(repository.getPropertyAtAddress(address)).willReturn(Optional.empty());
		searchEngine.getPropertyAtAddress(address);
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenUsesFactoryToGetOrderingStrategy() {
		searchEngine.getPropertiesSearchResults(searchParams);
		verify(orderingFactory).getOrderingStrategy(searchParams);
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenUsesFactoryToGetPropertyFilter() {
		searchEngine.getPropertiesSearchResults(searchParams);
		verify(filterFactory).getSearchFilterStrategy(searchParams);
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenUsesRepositoryToGetAllProperties() {
		searchEngine.getPropertiesSearchResults(searchParams);
		verify(repository).getAll();
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenUsesFilteringAndOrderingStrategyToGetProperties() {
		searchEngine.getPropertiesSearchResults(searchParams);

		InOrder inOrder = inOrder(filterStrategy, orderingStrategy);
		inOrder.verify(filterStrategy).getFilteredProperties(PROPERTIES);
		inOrder.verify(orderingStrategy).getOrderedProperties(PROPERTIES);
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenReturnsOrderedProperties() {
		ArrayList<Property> orderedProperties = new ArrayList<Property>();
		given(orderingStrategy.getOrderedProperties(PROPERTIES)).willReturn(orderedProperties);

		List<Property> returnedProperties = searchEngine.getPropertiesSearchResults(searchParams);

		assertEquals(orderedProperties, returnedProperties);
	}
}
