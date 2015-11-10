package org.RealEstateMM.domain.property.informations;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyStatusTest {

	private final String ONSALE = "on sale";
	private final String SOLD = "sold";
	private final String INVALID_STATUS = "invalid";

	@Test
	public void givenAStatusDescriptionWhenConvertToEnumReturnsGoodEnum() {
		PropertyStatus status = PropertyStatus.getStatusFromString(ONSALE);
		assertEquals(PropertyStatus.ON_SALE, status);

		status = PropertyStatus.getStatusFromString(SOLD);
		assertEquals(PropertyStatus.SOLD, status);
	}

	@Test
	public void givenAStatusWhenConvertToStringReturnsCorrectString() {
		String description = PropertyStatus.getStringFromStatus(PropertyStatus.ON_SALE);
		assertEquals(ONSALE, description);

		description = PropertyStatus.getStringFromStatus(PropertyStatus.SOLD);
		assertEquals(SOLD, description);
	}

	@Test
	public void givenAValidStatusDescriptionWhenCheckStatusValidityReturnsTrue() {
		assertTrue(PropertyStatus.isValidStatusDescription(ONSALE));
		assertTrue(PropertyStatus.isValidStatusDescription(SOLD));
	}

	@Test
	public void givenAnInvalidStatusDescriptionWhenCheckStatusValidityReturnsFalse() {
		assertFalse(PropertyStatus.isValidStatusDescription(INVALID_STATUS));
	}
}
