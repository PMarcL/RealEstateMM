package org.RealEstateMM.domain.property.informations;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyTypeTest {
	private final String HOUSE = "house";
	private final String MULTIPLEX = "multiplex";
	private final String COMMERCIAL = "commercial";
	private final String LAND = "land";
	private final String FARM = "farm";
	private final String INVALID_TYPE = "bob";

	@Test
	public void givenAValidPropertyTypeDescriptionWhenConvertToEnumThenReturnsTheGoodEnum() {
		PropertyType type = PropertyType.fromString(HOUSE);
		assertEquals(PropertyType.HOUSE, type);

		type = PropertyType.fromString(MULTIPLEX);
		assertEquals(PropertyType.MULTIPLEX, type);

		type = PropertyType.fromString(COMMERCIAL);
		assertEquals(PropertyType.COMMERCIAL, type);

		type = PropertyType.fromString(LAND);
		assertEquals(PropertyType.LAND, type);

		type = PropertyType.fromString(FARM);
		assertEquals(PropertyType.FARM, type);
	}

	@Test
	public void givenAPropertyTypeWhenConvertToStringThenReturnsCorrectString() {
		String description = PropertyType.HOUSE.toString();
		assertEquals(HOUSE, description);

		description = PropertyType.COMMERCIAL.toString();
		assertEquals(COMMERCIAL, description);

		description = PropertyType.FARM.toString();
		assertEquals(FARM, description);

		description = PropertyType.LAND.toString();
		assertEquals(LAND, description);

		description = PropertyType.MULTIPLEX.toString();
		assertEquals(MULTIPLEX, description);
	}

	@Test
	public void givenAValidPropertyTypeDescriptionWhenCheckTypeValidityReturnsTrue() {
		assertTrue(PropertyType.isValidPropertyTypeDescription(COMMERCIAL));
		assertTrue(PropertyType.isValidPropertyTypeDescription(HOUSE));
		assertTrue(PropertyType.isValidPropertyTypeDescription(MULTIPLEX));
		assertTrue(PropertyType.isValidPropertyTypeDescription(FARM));
		assertTrue(PropertyType.isValidPropertyTypeDescription(LAND));
	}

	@Test
	public void givenAnInvalidPropertyTypeDescriptionWhenCheckTypeValidityReturnsFalse() {
		assertFalse(PropertyType.isValidPropertyTypeDescription(INVALID_TYPE));
	}
}
