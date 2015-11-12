package org.RealEstateMM.domain.property;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InOrder;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertyOrderingFactory;
import org.RealEstateMM.domain.property.search.PropertyOrderingStrategy;
import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.domain.property.search.PropertySearchParameters;
import org.junit.Before;
import org.junit.Test;

public class PropertiesTest {

	private final String OWNER = "owner90";
	private final PropertySearchParameters SEARCH_PARAM = PropertySearchParameters.RECENTLY_UPLOADED_FIRST;

	private Property property;
	private PropertyRepository repository;
	private PropertyFeatures propertyFeatures;
	private PropertySearchFilter filter;
	private PropertyOrderingFactory factory;
	private PropertyOrderingStrategy orderingStrategy;

	private Properties properties;

	@Before
	public void setup() {
		repository = mock(PropertyRepository.class);
		factory = mock(PropertyOrderingFactory.class);
		properties = new Properties(repository, factory);

		property = mock(Property.class);
		propertyFeatures = mock(PropertyFeatures.class);
		orderingStrategy = mock(PropertyOrderingStrategy.class);
		filter = mock(PropertySearchFilter.class);
		given(filter.getParsedSearchParameter()).willReturn(SEARCH_PARAM);
		given(factory.getOrderingStrategy(SEARCH_PARAM)).willReturn(orderingStrategy);
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
	public void givenAPseudoAndPropertyFilterWhenGetOrderedPropertiesThenUsesFactoryToGetOrderingStrategy() {
		properties.getOrderedProperties(filter);
		verify(factory).getOrderingStrategy(SEARCH_PARAM);
	}

	@Test
	public void givenPseudoAndPropertyFilterWhenGetOrderedPropertiesThenUsesOrderingStrategyToGetProperties() {
		properties.getOrderedProperties(filter);
		verify(orderingStrategy).getOrderedProperties(repository);
	}

	@Test
	public void givenPseudoAndPropertyFilterWhenGetOrderedPropertiesThenReturnsOrderingStrategyProperties() {
		ArrayList<Property> orderedProperties = new ArrayList<Property>();
		given(orderingStrategy.getOrderedProperties(repository)).willReturn(orderedProperties);

		List<Property> returnedProperties = properties.getOrderedProperties(filter);

		assertEquals(orderedProperties, returnedProperties);
	}
}
