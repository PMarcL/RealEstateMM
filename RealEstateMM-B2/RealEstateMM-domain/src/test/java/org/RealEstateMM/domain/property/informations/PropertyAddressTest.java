package org.RealEstateMM.domain.property.informations;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.junit.Before;
import org.junit.Test;

public class PropertyAddressTest {

	private PropertyAddress address;

	private static String AN_ADDRESS_STREET_NAME = "123 FakeStreet";
	private static String AN_ADDRESS_CITY_NAME = "Pleasant Town";
	private static String AN_ADDRESS_PROVINCE_NAME = "Québec";
	private static String AN_ADDRESS_ZIP_CODE = "H0H0H0";

	private static String AN_OTHER_STREET_ADDRESS = "456 boom boom town";
	private static String AN_OTHER_ADDRESS_CITY_NAME = "Pleasanter Town";

	@Before
	public void setup() {
		address = new PropertyAddress(AN_ADDRESS_STREET_NAME, AN_ADDRESS_CITY_NAME, AN_ADDRESS_PROVINCE_NAME,
				AN_ADDRESS_ZIP_CODE);
	}

	@Test
	public void comparingTwoEqualsPropertyAddressShouldReturnTrue() {
		PropertyAddress address2 = new PropertyAddress(AN_ADDRESS_STREET_NAME, AN_ADDRESS_CITY_NAME,
				AN_ADDRESS_PROVINCE_NAME, AN_ADDRESS_ZIP_CODE);

		assertTrue(address.isEquals(address2));
	}

	@Test
	public void comparingTwoNotEqualsPropertyAddressShouldReturnFalse() {
		PropertyAddress address2 = new PropertyAddress(AN_OTHER_STREET_ADDRESS, AN_OTHER_ADDRESS_CITY_NAME,
				AN_ADDRESS_PROVINCE_NAME, AN_ADDRESS_ZIP_CODE);

		assertFalse(address.isEquals(address2));
	}
}
