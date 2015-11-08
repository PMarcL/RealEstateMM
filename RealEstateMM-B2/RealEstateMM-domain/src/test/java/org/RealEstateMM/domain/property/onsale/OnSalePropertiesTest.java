package org.RealEstateMM.domain.property.onsale;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.junit.Before;
import org.junit.Test;

public class OnSalePropertiesTest {

	private PropertyRepository propertyRepo;
	private OnSaleProperties onSaleProperties;
	private Property firstProperty;
	private Property secondProperty;
	
	private static final int FIRST_INDEX =0;

	@Before
	public void setup() {
		propertyRepo = mock(PropertyRepository.class);
	}

	@Test
	public void givenNoOnSalePropertiesThenGetOnSalePropertiesByTypeShouldReturnEmptyList() {
		given(propertyRepo.getAllProperties()).willReturn(new ArrayList<Property>());
		onSaleProperties = new OnSaleProperties(propertyRepo);

		List<Property> onSaleHouses = onSaleProperties.findOnSalePropertiesByType(PropertyType.HOUSE);

		assertEquals(0, onSaleHouses.size());
	}
	
	@Test
	public void givenOneOnSaleHouseGetOnSalePropertiesByHouseShouldReturnIt(){
		ArrayList<Property> allProperties = new ArrayList<Property>();
		firstProperty = mock(Property.class);
		given(firstProperty.getPropertyStatus()).willReturn(PropertyStatus.ONSALE);
		given(firstProperty.getType()).willReturn(PropertyType.HOUSE);
		allProperties.add(firstProperty);
		given(propertyRepo.getAllProperties()).willReturn(allProperties);
		onSaleProperties = new OnSaleProperties(propertyRepo);
		
		List<Property> onSaleHouses = onSaleProperties.findOnSalePropertiesByType(PropertyType.HOUSE);
		
		assertEquals(firstProperty, onSaleHouses.get(FIRST_INDEX));
	}

	
	@Test
	public void givenOneSoldHouseGetOnSalePropertiesByHouseShouldReturnAnEmptyList(){
		ArrayList<Property> allProperties = new ArrayList<Property>();
		firstProperty = mock(Property.class);
		given(firstProperty.getPropertyStatus()).willReturn(PropertyStatus.SOLD);
		given(firstProperty.getType()).willReturn(PropertyType.HOUSE);
		allProperties.add(firstProperty);
		given(propertyRepo.getAllProperties()).willReturn(allProperties);
		onSaleProperties = new OnSaleProperties(propertyRepo);
		
		List<Property> onSaleHouses = onSaleProperties.findOnSalePropertiesByType(PropertyType.HOUSE);
		
		assertEquals(0, onSaleHouses.size());
	}
	
	@Test
	public void givenOneOnSaleHouseGetOnSalePropertiesByFarmShouldReturnAnEmptyList(){
		ArrayList<Property> allProperties = new ArrayList<Property>();
		firstProperty = mock(Property.class);
		given(firstProperty.getPropertyStatus()).willReturn(PropertyStatus.ONSALE);
		given(firstProperty.getType()).willReturn(PropertyType.FARM);
		allProperties.add(firstProperty);
		given(propertyRepo.getAllProperties()).willReturn(allProperties);
		onSaleProperties = new OnSaleProperties(propertyRepo);
		
		List<Property> onSaleHouses = onSaleProperties.findOnSalePropertiesByType(PropertyType.HOUSE);
		
		assertEquals(0, onSaleHouses.size());
	}
	
	@Test
	public void givenTwoOnSaleMultiplexGetOnSalePropertiesByMultiplexShouldReturnBoth(){
		ArrayList<Property> allProperties = new ArrayList<Property>();
		firstProperty = mock(Property.class);
		secondProperty = mock(Property.class);
		given(firstProperty.getPropertyStatus()).willReturn(PropertyStatus.ONSALE);
		given(firstProperty.getType()).willReturn(PropertyType.MULTIPLEX);
		given(secondProperty.getPropertyStatus()).willReturn(PropertyStatus.ONSALE);
		given(secondProperty.getType()).willReturn(PropertyType.MULTIPLEX);
		allProperties.add(firstProperty);
		allProperties.add(secondProperty);
		given(propertyRepo.getAllProperties()).willReturn(allProperties);
		onSaleProperties = new OnSaleProperties(propertyRepo);
		
		List<Property> onSaleMultiplex = onSaleProperties.findOnSalePropertiesByType(PropertyType.MULTIPLEX);
		
		assertEquals(2, onSaleMultiplex.size());
	}
	
	@Test
	public void givenTwoPropertiesAndOnlyOneOnSalegetOnSalePropertiesByTypeShouldReturnOnlyTheOnSaleOne(){
		ArrayList<Property> allProperties = new ArrayList<Property>();
		firstProperty = mock(Property.class);
		secondProperty = mock(Property.class);
		given(firstProperty.getPropertyStatus()).willReturn(PropertyStatus.ONSALE);
		given(firstProperty.getType()).willReturn(PropertyType.MULTIPLEX);
		given(secondProperty.getPropertyStatus()).willReturn(PropertyStatus.SOLD);
		given(secondProperty.getType()).willReturn(PropertyType.MULTIPLEX);
		allProperties.add(firstProperty);
		allProperties.add(secondProperty);
		given(propertyRepo.getAllProperties()).willReturn(allProperties);
		onSaleProperties = new OnSaleProperties(propertyRepo);
		
		List<Property> onSaleMultiplex = onSaleProperties.findOnSalePropertiesByType(PropertyType.MULTIPLEX);
		
		assertEquals(1, onSaleMultiplex.size());
		assertEquals(firstProperty, onSaleMultiplex.get(FIRST_INDEX));
	}
}
