package org.RealEstateMM.domain.property.informations;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyStatusTest {

	private final String ONSALE = "on sale";
	private final String SOLD = "sold";

	@Test
	public void givenAStatusDescriptionWhenConvertToEnumReturnsGoodEnum() {
		PropertyStatus status = PropertyStatus.getStatusFromString(ONSALE);
		assertEquals(PropertyStatus.ONSALE, status);

		status = PropertyStatus.getStatusFromString(SOLD);
		assertEquals(PropertyStatus.SOLD, status);
	}

	@Test
	public void givenAStatusWhenConvertToStringReturnsCorrectString() {
		String description = PropertyStatus.getStringFromStatus(PropertyStatus.ONSALE);
		assertEquals(ONSALE, description);

		description = PropertyStatus.getStringFromStatus(PropertyStatus.SOLD);
		assertEquals(SOLD, description);
	}
}
