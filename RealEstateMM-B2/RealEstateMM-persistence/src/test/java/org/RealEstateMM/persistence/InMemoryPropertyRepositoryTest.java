package org.RealEstateMM.persistence;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.junit.Before;
import org.junit.Test;

public class InMemoryPropertyRepositoryTest {

	private final String ZIPCODE = "G6P7H7";

	private InMemoryPropertyRepository repository;

	private Property property;

	@Before
	public void init() {
		property = mock(Property.class);
		repository = new InMemoryPropertyRepository();

		given(property.getZipCode()).willReturn(ZIPCODE);
	}

	@Test
	public void givenANewRepositoryWhenGetSizeThenReturnsZero() {
		assertEquals(0, repository.getSize());
	}

	@Test
	public void givenARepositoryWhenAddPropertyThenSizeIncreases() {
		int originalSize = repository.getSize();
		repository.add(property);
		assertEquals(originalSize + 1, repository.getSize());
	}

	@Test
	public void givenARepositoryWhenAddPropertyThenPropertyIsInRepository() {
		repository.add(property);
		Optional<Property> result = repository.getPropertyWithZipCode(ZIPCODE);
		assertEquals(property, result.get());
	}

	@Test
	public void givenARepositoryWithPropertiesWhenGetAllPropertiesThenReturnsArrayListWithSameNumberOfProperties() {
		repository.add(property);
		ArrayList<Property> properties = repository.getAllProperties();
		assertEquals(repository.getSize(), properties.size());
	}

	@Test
	public void givenARepositoryContainingAPropertyWhenUpdatePropertyThenSizeHasNotChanged() {
		repository.add(property);
		int originalSize = repository.getSize();

		repository.updateProperty(property);

		assertEquals(originalSize, repository.getSize());
	}

	@Test
	public void givenARepositoryContainingAPropertyWhenUpdatePropertyThenUpdatedPropertyIsInRepository() {
		repository.add(property);
		Property updatedProperty = mock(Property.class);
		given(updatedProperty.getZipCode()).willReturn(ZIPCODE);

		repository.updateProperty(updatedProperty);

		assertEquals(updatedProperty, repository.getPropertyWithZipCode(ZIPCODE).get());
	}
}
