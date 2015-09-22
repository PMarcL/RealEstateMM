package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertyInformationsAssemblerTest {
	private PropertyType A_PROPERTY_TYPE = PropertyType.RESIDENTIAL;
	private double A_PRICE = 200000.00;
	private PropertyStatus A_PROPERTY_STATUS = PropertyStatus.ONSALE;
	
	private PropertyAddress propertyAddress;
	private User owner;
	private Property property;
	
	private PropertyInformations propertyInfos;
	private PropertyInformationsAssembler assembler;
	

	@Before
	public void init(){
		assembler = new PropertyInformationsAssembler();
		property = new Property(A_PROPERTY_TYPE, propertyAddress, A_PRICE, owner, A_PROPERTY_STATUS);
	}
	
	@Test
	public void givenAPropertyInformationsWhenBuildToDTOThenReturnedDTOShouldHaveTheSameInformations(){
		propertyInfos = assembler.toDTO(property);
		
		assertEquals(propertyInfos.getPropertyType(), A_PROPERTY_TYPE);
		assertEquals(propertyInfos.getPropertyAddress(), propertyAddress);
		assertEquals(String.valueOf(propertyInfos.getPropertyPrice()), String.valueOf(A_PRICE)); //assertEquals(double, double) is deprecated;
		assertEquals(propertyInfos.getPropertyOwner(), owner);
		assertEquals(propertyInfos.getPropertyStatus(), A_PROPERTY_STATUS);
	}
	
	@Test
	public void givenAPropertyDTOWhenBuildingFromDTOThenPropertyShouldHaveTheSameInformations(){
		PropertyInformations dto = assembler.toDTO(property);
		Property result = assembler.fromDTO(dto);
		
		assertEquals(result.propertyType, dto.getPropertyType());
		assertEquals(result.propertyAddress, dto.getPropertyAddress());
		assertEquals(String.valueOf(result.propertyPrice), String.valueOf(dto.getPropertyPrice())); //assertEquals(double, double) is deprecated;
		assertEquals(result.propertyOwner, dto.getPropertyOwner());
		assertEquals(result.propertyStatus, dto.getPropertyStatus());
	}
}
