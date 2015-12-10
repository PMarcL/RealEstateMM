package org.RealEstateMM.domain.property.informations;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyStatusTest {

	private final String ON_SALE = "on sale";
	private final String SOLD = "sold";
	private final String INVALID_STATUS = "invalid";

	@Test
	public void givenAStatusDescriptionWhenConvertToEnumReturnsGoodEnum() {
		PropertyStatus status = PropertyStatus.fromString(ON_SALE);
		assertEquals(PropertyStatus.ON_SALE, status);

		status = PropertyStatus.fromString(SOLD);
		assertEquals(PropertyStatus.SOLD, status);
	}

	@Test
	public void givenAStatusWhenConvertToStringReturnsCorrectString() {
		String description = PropertyStatus.ON_SALE.toString();
		assertEquals(ON_SALE, description);

		description = PropertyStatus.SOLD.toString();
		assertEquals(SOLD, description);
	}

	@Test
	public void givenAValidStatusDescriptionWhenCheckStatusValidityReturnsTrue() {
		assertTrue(PropertyStatus.isValidStatusDescription(ON_SALE));
		assertTrue(PropertyStatus.isValidStatusDescription(SOLD));
	}

	@Test
	public void givenAnInvalidStatusDescriptionWhenCheckStatusValidityReturnsFalse() {
		assertFalse(PropertyStatus.isValidStatusDescription(INVALID_STATUS));
	}
}
