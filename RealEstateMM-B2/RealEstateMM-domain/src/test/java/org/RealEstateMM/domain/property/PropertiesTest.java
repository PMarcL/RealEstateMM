package org.RealEstateMM.domain.property;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class PropertiesTest {

	private Property property;
	private PropertyRepository repository;
	private PropertyFeatures propertyFeatures;

	private Properties properties;

	@Before
	public void setup() {
		repository = mock(PropertyRepository.class);
		properties = new Properties(repository);

		property = mock(Property.class);
		propertyFeatures = mock(PropertyFeatures.class);
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

}
