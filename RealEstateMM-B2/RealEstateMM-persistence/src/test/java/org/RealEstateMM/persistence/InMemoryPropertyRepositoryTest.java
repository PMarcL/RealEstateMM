package org.RealEstateMM.persistence;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.property.Property;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InMemoryPropertyRepositoryTest {

	private InMemoryPropertyRepository repository;
	
	@Mock
	private Property property;
	
	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		repository = new InMemoryPropertyRepository();
	}
	
	@Test
	public void aNewPropertyRepositoryShouldBeEmpty()
	{
		assertEquals(0, repository.getSize());
	}
	
//	@Test
//	public void givenANewRepositoryWhenAddinAPropertyThenTheSizeShouldIncrementAccordingly()
//	{
//		Mockito.when(property.propertyAddress.toString()).thenReturn(Mockito.anyString());
//		int initialSize = repository.getSize();
//		repository.add(property);
//		
//		assertEquals(initialSize + 1, repository.getSize());
//	}	
//	
//	@Test(expected = PropertyAlreadyAddedException.class)
//	public void givenAnEmptyRepositoryWhenAddingTwiceTheSamePropertyThenAPropertyAlreadyAddedExceptionShouldBeThrown()
//	{
//		Mockito.when(property.propertyAddress.toString()).thenReturn("A value");
//		repository.add(property);
//		repository.add(property);
//	}
	//TODO Fix this.
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
