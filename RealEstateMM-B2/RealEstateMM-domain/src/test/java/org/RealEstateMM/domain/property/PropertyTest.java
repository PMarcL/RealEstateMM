package org.RealEstateMM.domain.property;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PropertyTest {

	private Property property;

	private static double A_PROPERTY_PRICE = 200000.00;
	private static String A_PROPERTY_STATUS = "residential";
	private static String OWNER_USERNAME = "bob";
	private static String A_PROPERTY_TYPE = "for sale";

	@Mock
	private PropertyAddress propertyAddress;
	@Mock
	private PropertyAddress propertyAddress2;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		property = new Property(A_PROPERTY_TYPE, propertyAddress, A_PROPERTY_PRICE, OWNER_USERNAME, A_PROPERTY_STATUS);
	}

	@Test
	public void comparingTwoEqualsPropertyShouldReturnTrue() {
		Property sameProperty = property;

		when(property.propertyAddress.isEquals(sameProperty.propertyAddress)).thenReturn(true);

		assertTrue(property.isEquals(sameProperty));
	}

	@Test
	public void comparingTwoNotEqualPropertyShouldReturnFalse() {
		Property differentProperty = new Property(A_PROPERTY_TYPE, propertyAddress2, A_PROPERTY_PRICE, OWNER_USERNAME,
				A_PROPERTY_STATUS);

		when(property.propertyAddress.isEquals(differentProperty.propertyAddress)).thenReturn(false);

		assertFalse(property.isEquals(differentProperty));
	}

}
