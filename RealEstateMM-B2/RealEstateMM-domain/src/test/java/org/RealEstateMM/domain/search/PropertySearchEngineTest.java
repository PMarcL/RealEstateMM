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

public class PropertySearchEngineTest {
	private final String OWNER = "owner90";
	private final PropertySearchParameters SEARCH_PARAM = PropertySearchParameters.RECENTLY_UPLOADED_FIRST;

	private PropertyRepository repository;
	private Property property;
	private PropertyOrderingFactory factory;
	private PropertyOrderingStrategy orderingStrategy;
	private PropertyAddress address;

	private PropertySearchEngine search;

	@Before
	public void setup() {
		repository = mock(PropertyRepository.class);
		factory = mock(PropertyOrderingFactory.class);
		property = mock(Property.class);
		orderingStrategy = mock(PropertyOrderingStrategy.class);
		address = mock(PropertyAddress.class);
		given(factory.getOrderingStrategy(SEARCH_PARAM)).willReturn(orderingStrategy);
		given(repository.getPropertyAtAddress(address)).willReturn(Optional.of(property));

		search = new PropertySearchEngine(repository, factory);
	}

	@Test
	public void whenGetAllPropertiesThenUsesRepositoryToGetAllProperties() {
		search.getAllProperties();
		verify(repository).getAll();
	}

	@Test
	public void whenGetAllPropertiesThenReturnsRepositoryContent() {
		ArrayList<Property> allProperties = new ArrayList<Property>();
		given(repository.getAll()).willReturn(allProperties);

		List<Property> returnedProperties = search.getAllProperties();

		assertEquals(allProperties, returnedProperties);
	}

	@Test
	public void givenAnOwnerWhenGetPropertyFromOwnerThenUsesRepositoryToGetProperties() {
		search.getPropertiesFromOwner(OWNER);
		verify(repository).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenAnOwnerWhenGetPropertyFromOwnerThenReturnsRepositoryContent() {
		ArrayList<Property> ownersProperties = new ArrayList<Property>();
		given(repository.getPropertiesFromOwner(OWNER)).willReturn(ownersProperties);

		List<Property> returnedProperties = search.getPropertiesFromOwner(OWNER);

		assertEquals(ownersProperties, returnedProperties);
	}

	@Test
	public void givenAPseudoAndPropertyFilterWhenGetOrderedPropertiesThenUsesFactoryToGetOrderingStrategy() {
		search.getOrderedProperties(SEARCH_PARAM);
		verify(factory).getOrderingStrategy(SEARCH_PARAM);
	}

	@Test
	public void givenPseudoAndPropertyFilterWhenGetOrderedPropertiesThenUsesOrderingStrategyToGetProperties() {
		search.getOrderedProperties(SEARCH_PARAM);
		verify(orderingStrategy).getOrderedProperties(repository);
	}

	@Test
	public void givenPseudoAndPropertyFilterWhenGetOrderedPropertiesThenReturnsOrderingStrategyProperties() {
		ArrayList<Property> orderedProperties = new ArrayList<Property>();
		given(orderingStrategy.getOrderedProperties(repository)).willReturn(orderedProperties);

		List<Property> returnedProperties = search.getOrderedProperties(SEARCH_PARAM);

		assertEquals(orderedProperties, returnedProperties);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesRepository() throws Exception {
		search.getPropertyAtAddress(address);
		verify(repository).getPropertyAtAddress(address);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenReturnsPropertyGottenFromRepository() throws Exception {
		Property result = search.getPropertyAtAddress(address);
		assertEquals(property, result);
	}

	@Test(expected = PropertyNotFoundException.class)
	public void givenAnAddressWhenGetPropertyAtAddressThenThrowsExceptionIfNoPropertyIsFound() throws Exception {
		given(repository.getPropertyAtAddress(address)).willReturn(Optional.empty());
		search.getPropertyAtAddress(address);
	}
}
