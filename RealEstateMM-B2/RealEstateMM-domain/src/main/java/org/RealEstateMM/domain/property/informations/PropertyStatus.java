package org.RealEstateMM.domain.property.informations;

public enum PropertyStatus {
	ON_SALE, SOLD;

	@Override
	public String toString() {
		return super.toString().replace("_", " ").toLowerCase();
	}

	public static PropertyStatus fromString(String value) {
		return PropertyStatus.valueOf(value.replace(" ", "_").toUpperCase());
	}

	public static boolean isValidStatusDescription(String value) {
		try {
			PropertyStatus.fromString(value);
		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;
	}
}
