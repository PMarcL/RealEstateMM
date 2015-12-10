package org.RealEstateMM.domain.property.informations;

public enum PropertyType {
	HOUSE, MULTIPLEX, COMMERCIAL, LAND, FARM;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

	public static PropertyType fromString(String value) {
		return PropertyType.valueOf(value.toUpperCase());
	}

	public static boolean isValidPropertyTypeDescription(String value) {
		try {
			PropertyType.fromString(value);
		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;
	}
}
