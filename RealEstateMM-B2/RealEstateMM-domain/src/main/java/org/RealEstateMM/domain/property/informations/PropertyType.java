package org.RealEstateMM.domain.property.informations;

public enum PropertyType {

	HOUSE, MULTIPLEX, COMMERCIAL, LAND, FARM;

	private static final String HOUSE_DESC = "house";
	private static final String MULTIPLEX_DESC = "multiplex";
	private static final String COMMERCIAL_DESC = "commercial";
	private static final String LAND_DESC = "land";
	private static final String FARM_DESC = "farm";

	public static PropertyType getTypeFromString(String description) {
		if (description.equals(HOUSE_DESC)) {
			return PropertyType.HOUSE;
		} else if (description.equals(MULTIPLEX_DESC)) {
			return PropertyType.MULTIPLEX;
		} else if (description.equals(COMMERCIAL_DESC)) {
			return PropertyType.COMMERCIAL;
		} else if (description.equals(LAND_DESC)) {
			return PropertyType.LAND;
		} else {
			return PropertyType.FARM;
		}
	}

	public static String getStringFromType(PropertyType type) {
		if (type == PropertyType.HOUSE) {
			return HOUSE_DESC;
		} else if (type == PropertyType.COMMERCIAL) {
			return COMMERCIAL_DESC;
		} else if (type == PropertyType.FARM) {
			return FARM_DESC;
		} else if (type == PropertyType.LAND) {
			return LAND_DESC;
		} else {
			return MULTIPLEX_DESC;
		}
	}
}
