package org.RealEstateMM.domain.property;

import static org.mockito.Mockito.*;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.junit.Before;

public class PropertyTest {

	private Property property;

	private static double A_PROPERTY_PRICE = 200000.00;
	private static PropertyStatus A_PROPERTY_STATUS = PropertyStatus.ONSALE;
	private static String OWNER_USERNAME = "bob";
	private static PropertyType A_PROPERTY_TYPE = PropertyType.HOUSE;

	private PropertyAddress propertyAddress;

	@Before
	public void setup() {
		propertyAddress = mock(PropertyAddress.class);

		property = new Property(A_PROPERTY_TYPE, propertyAddress, A_PROPERTY_PRICE, OWNER_USERNAME, A_PROPERTY_STATUS);
	}

}
