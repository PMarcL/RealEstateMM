package org.RealEstateMM.domain.property.onsale;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.junit.Before;
import org.junit.Test;

public class SellersWithOnSalePropertyTest {
	
	private PropertyRepository repository;
	private Property firstProperty;
	private Property secondProperty;
	
	private static final String PROPERTY_OWNER = "bob";
	private static final String SECOND_OWNER = "Banana guy";

	@Before
	public void setup(){
		repository = mock(PropertyRepository.class);
	}
	
	@Test
	public void givenAnEmptyRepositoryNumberOfSellerShouldBeZero(){
		given(repository.getAll()).willReturn(new ArrayList<Property>());
		SellersWithOnSaleProperty sellers = new SellersWithOnSaleProperty(repository);
		
		int numberOfSellers = sellers.findNumberOfSellerWithOnSaleProperty();
		
		assertEquals(0, numberOfSellers);
	}
	
	@Test
	public void givenARepositoryWithOneOnSalePropertyThenNumberOfSellerShouldReturnOne(){
		ArrayList<Property> allProperties = new ArrayList<Property>();
		firstProperty = mock(Property.class);
		given(firstProperty.getStatus()).willReturn(PropertyStatus.ON_SALE);
		given(firstProperty.getOwner()).willReturn(PROPERTY_OWNER);
		allProperties.add(firstProperty);
		given(repository.getAll()).willReturn(allProperties);
		SellersWithOnSaleProperty sellers = new SellersWithOnSaleProperty(repository);
		
		int numberOfSellers = sellers.findNumberOfSellerWithOnSaleProperty();
		
		assertEquals(1, numberOfSellers);
	}
	
	@Test
	public void givenArepositoryWithTwoPropertiesWithSameOwnerThenNumberOfSellerShouldReturnOne(){
		ArrayList<Property> allProperties = new ArrayList<Property>();
		firstProperty = mock(Property.class);
		secondProperty = mock(Property.class);
		given(firstProperty.getStatus()).willReturn(PropertyStatus.ON_SALE);
		given(firstProperty.getOwner()).willReturn(PROPERTY_OWNER);
		given(secondProperty.getStatus()).willReturn(PropertyStatus.ON_SALE);
		given(secondProperty.getOwner()).willReturn(PROPERTY_OWNER);
		allProperties.add(firstProperty);
		allProperties.add(secondProperty);
		given(repository.getAll()).willReturn(allProperties);
		SellersWithOnSaleProperty sellers = new SellersWithOnSaleProperty(repository);
		
		int numberOfSellers = sellers.findNumberOfSellerWithOnSaleProperty();
		
		assertEquals(1, numberOfSellers);
	}
	
	
	@Test
	public void givenArepositoryWithTwoPropertiesWithDifferentOwnersThenNumberOfSellerShouldReturnTwo(){
		ArrayList<Property> allProperties = new ArrayList<Property>();
		firstProperty = mock(Property.class);
		secondProperty = mock(Property.class);
		given(firstProperty.getStatus()).willReturn(PropertyStatus.ON_SALE);
		given(firstProperty.getOwner()).willReturn(PROPERTY_OWNER);
		given(secondProperty.getStatus()).willReturn(PropertyStatus.ON_SALE);
		given(secondProperty.getOwner()).willReturn(SECOND_OWNER);
		allProperties.add(firstProperty);
		allProperties.add(secondProperty);
		given(repository.getAll()).willReturn(allProperties);
		SellersWithOnSaleProperty sellers = new SellersWithOnSaleProperty(repository);
		
		int numberOfSellers = sellers.findNumberOfSellerWithOnSaleProperty();
		
		assertEquals(2, numberOfSellers);
	}
	
	@Test
	public void givenARepositoryWithASoldPropertyThenNumberOfSellerShouldReturnZero(){
		ArrayList<Property> allProperties = new ArrayList<Property>();
		firstProperty = mock(Property.class);
		given(firstProperty.getStatus()).willReturn(PropertyStatus.SOLD);
		given(firstProperty.getOwner()).willReturn(PROPERTY_OWNER);
		allProperties.add(firstProperty);
		given(repository.getAll()).willReturn(allProperties);
		SellersWithOnSaleProperty sellers = new SellersWithOnSaleProperty(repository);
		
		int numberOfSellers = sellers.findNumberOfSellerWithOnSaleProperty();
		
		assertEquals(0, numberOfSellers);
	}
}
