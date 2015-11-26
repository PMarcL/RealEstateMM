package org.RealEstateMM.domain.property;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertyOrderingFactory;
import org.RealEstateMM.domain.property.search.PropertyOrderingStrategy;
import org.RealEstateMM.domain.property.search.PropertyOrderingParameters;
import org.RealEstateMM.domain.property.search.PropertySearchFilterFactory;
import org.RealEstateMM.domain.property.search.PropertySearchFilterStrategy;
import org.RealEstateMM.domain.property.search.PropertySearchParameters;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class PropertiesTest {

	private final String OWNER = "owner90";
	private final PropertyOrderingParameters ORDERING_PARAM = PropertyOrderingParameters.RECENTLY_UPLOADED_FIRST;
	private ArrayList<Property> PROPERTIES = new ArrayList<Property>();

	private Property property;
	private PropertyRepository repository;
	private PropertyFeatures propertyFeatures;
	private PropertyAddress address;
	private PropertyOrderingFactory orderingFactory;
	private PropertyOrderingStrategy orderingStrategy;
	private PropertySearchParameters searchParams;
	private PropertySearchFilterFactory filterFactory;
	private PropertySearchFilterStrategy filterStrategy;

	private Properties properties;

	@Before
	public void setup() {
		repository = mock(PropertyRepository.class);
		orderingFactory = mock(PropertyOrderingFactory.class);
		filterFactory = mock(PropertySearchFilterFactory.class);
		properties = new Properties(repository, orderingFactory, filterFactory);

		property = mock(Property.class);
		address = mock(PropertyAddress.class);
		propertyFeatures = mock(PropertyFeatures.class);
		orderingStrategy = mock(PropertyOrderingStrategy.class);
		searchParams = mock(PropertySearchParameters.class);
		filterStrategy = mock(PropertySearchFilterStrategy.class);
		given(searchParams.getOrderingParam()).willReturn(ORDERING_PARAM);
		given(orderingFactory.getOrderingStrategy(ORDERING_PARAM)).willReturn(orderingStrategy);
		given(filterFactory.getSearchFilterStrategy(searchParams)).willReturn(filterStrategy);
		given(repository.getPropertyAtAddress(address)).willReturn(Optional.of(property));
		given(repository.getAll()).willReturn(PROPERTIES);
	}

	@Test
	public void givenAPropertyWhenAddPropertyThenAddPropertyToRepository() {
		properties.addProperty(property);
		verify(repository).add(property);
	}

	@Test
	public void givenAPropertyWhenAddPropertyThenSetCreationDateBeforeUsingRepository() {
		properties.addProperty(property);

		InOrder inOrder = inOrder(property, repository);
		inOrder.verify(property).setCreationDate(anyObject());
		inOrder.verify(repository).add(property);
	}

	@Test
	public void whenGetAllPropertiesThenUsesRepositoryToGetAllProperties() {
		properties.getAllProperties();
		verify(repository).getAll();
	}

	@Test
	public void whenGetAllPropertiesThenReturnsRepositoryContent() {
		ArrayList<Property> allProperties = new ArrayList<Property>();
		given(repository.getAll()).willReturn(allProperties);

		List<Property> returnedProperties = properties.getAllProperties();

		assertEquals(allProperties, returnedProperties);
	}

	@Test
	public void givenAPropertyAndPropertyFeaturesWhenEditPropertyFeaturesThenUpdatesFeatures() {
		properties.editPropertyFeatures(property, propertyFeatures);
		verify(property).updateFeatures(propertyFeatures);
	}

	@Test
	public void givenPropertyAndFeaturesWhenEditPropertyFeaturesThenUpdatesRepoAfterUpdatingFeatures() {
		properties.editPropertyFeatures(property, propertyFeatures);

		InOrder inOrder = inOrder(property, repository);
		inOrder.verify(property).updateFeatures(propertyFeatures);
		inOrder.verify(repository).updateProperty(property);
	}

	@Test
	public void givenAnOwnerWhenGetPropertyFromOwnerThenUsesRepositoryToGetProperties() {
		properties.getPropertiesFromOwner(OWNER);
		verify(repository).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenAnOwnerWhenGetPropertyFromOwnerThenReturnsRepositoryContent() {
		ArrayList<Property> ownersProperties = new ArrayList<Property>();
		given(repository.getPropertiesFromOwner(OWNER)).willReturn(ownersProperties);

		List<Property> returnedProperties = properties.getPropertiesFromOwner(OWNER);

		assertEquals(ownersProperties, returnedProperties);
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenUsesFactoryToGetOrderingStrategy() {
		properties.getPropertiesSearchResults(searchParams);
		verify(orderingFactory).getOrderingStrategy(ORDERING_PARAM);
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenUsesFactoryToGetPropertyFilter() {
		properties.getPropertiesSearchResults(searchParams);
		verify(filterFactory).getSearchFilterStrategy(searchParams);
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenUsesRepositoryToGetAllProperties() {
		properties.getPropertiesSearchResults(searchParams);
		verify(repository).getAll();
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenUsesFilteringAndOrderingStrategyToGetProperties() {
		properties.getPropertiesSearchResults(searchParams);

		InOrder inOrder = inOrder(filterStrategy, orderingStrategy);
		inOrder.verify(filterStrategy).getFilteredProperties(PROPERTIES);
		inOrder.verify(orderingStrategy).getOrderedProperties(PROPERTIES);
	}

	@Test
	public void givenPropertySearchParametersWhenGetPropertiesSearchResultsThenReturnsOrderedProperties() {
		ArrayList<Property> orderedProperties = new ArrayList<Property>();
		given(orderingStrategy.getOrderedProperties(PROPERTIES)).willReturn(orderedProperties);

		List<Property> returnedProperties = properties.getPropertiesSearchResults(searchParams);

		assertEquals(orderedProperties, returnedProperties);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesRepository() throws Exception {
		properties.getPropertyAtAddress(address);
		verify(repository).getPropertyAtAddress(address);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenReturnsPropertyGottenFromRepository() throws Exception {
		Property result = properties.getPropertyAtAddress(address);
		assertEquals(property, result);
	}

	@Test(expected = PropertyNotFoundException.class)
	public void givenAnAddressWhenGetPropertyAtAddressThenThrowsExceptionIfNoPropertyIsFound() throws Exception {
		given(repository.getPropertyAtAddress(address)).willReturn(Optional.empty());
		properties.getPropertyAtAddress(address);
	}
}
