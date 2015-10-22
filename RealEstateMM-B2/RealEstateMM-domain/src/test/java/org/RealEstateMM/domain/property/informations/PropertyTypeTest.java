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
		PropertyType type = PropertyType.getTypeFromString(HOUSE);
		assertEquals(PropertyType.HOUSE, type);

		type = PropertyType.getTypeFromString(MULTIPLEX);
		assertEquals(PropertyType.MULTIPLEX, type);

		type = PropertyType.getTypeFromString(COMMERCIAL);
		assertEquals(PropertyType.COMMERCIAL, type);

		type = PropertyType.getTypeFromString(LAND);
		assertEquals(PropertyType.LAND, type);

		type = PropertyType.getTypeFromString(FARM);
		assertEquals(PropertyType.FARM, type);
	}

	@Test
	public void givenAPropertyTypeWhenConvertToStringThenReturnsCorrectString() {
		String description = PropertyType.getStringFromType(PropertyType.HOUSE);
		assertEquals(HOUSE, description);

		description = PropertyType.getStringFromType(PropertyType.COMMERCIAL);
		assertEquals(COMMERCIAL, description);

		description = PropertyType.getStringFromType(PropertyType.FARM);
		assertEquals(FARM, description);

		description = PropertyType.getStringFromType(PropertyType.LAND);
		assertEquals(LAND, description);

		description = PropertyType.getStringFromType(PropertyType.MULTIPLEX);
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
