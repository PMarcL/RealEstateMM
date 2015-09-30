package org.RealEstateMM.domain.property;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;



public class PropertyTest {
	
	private Property property;

	private static double A_PROPERTY_PRICE = 200000.00;
//	private static PropertyStatus A_PROPERTY_STATUS = PropertyStatus.ONSALE;
//	private static PropertyType A_PROPERTY_TYPE = PropertyType.RESIDENTIAL;
	private static String A_PROPERTY_STATUS = "residential";
	private static String A_PROPERTY_TYPE = "for sale";
	
	@Mock
	private PropertyAddress propertyAddress;
	@Mock
	private PropertyAddress propertyAddress2;
	@Mock
	private User owner;
	
	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		
		property = new Property(A_PROPERTY_TYPE, propertyAddress, A_PROPERTY_PRICE, owner, A_PROPERTY_STATUS);
	}
	
	@Test
	public void comparingTwoEqualsPropertyShouldReturnTrue()
	{
		Property sameProperty = property;
		
		when(property.propertyAddress.isEquals(sameProperty.propertyAddress)).thenReturn(true);
		when(property.propertyOwner.isEquals(sameProperty.propertyOwner)).thenReturn(true);
		
		assertTrue(property.isEquals(sameProperty));
	}
	
	@Test
	public void comparingTwoNotEqualPropertyShouldReturnFalse(){
		Property differentProperty = new Property(A_PROPERTY_TYPE, propertyAddress2, A_PROPERTY_PRICE, owner, A_PROPERTY_STATUS);
		
		when(property.propertyAddress.isEquals(differentProperty.propertyAddress)).thenReturn(false);
		
		assertFalse(property.isEquals(differentProperty));
	}
	
}
