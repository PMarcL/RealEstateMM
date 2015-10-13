package org.RealEstateMM.domain.property.informations;

public enum PropertyStatus {
	ONSALE, SOLD;

	private static final String ONSALE_DESC = "on sale";
	private static final String SOLD_DESC = "sold";

	public static PropertyStatus getStatusFromString(String description) {
		if (description.equals(ONSALE_DESC)) {
			return PropertyStatus.ONSALE;
		} else {
			return PropertyStatus.SOLD;
		}
	}

	public static String getStringFromStatus(PropertyStatus status) {
		if (status == PropertyStatus.ONSALE) {
			return ONSALE_DESC;
		} else {
			return SOLD_DESC;
		}
	}

	public static boolean isValidStatusDescription(String description) {
		if (description.equals(ONSALE_DESC) || description.equals(SOLD_DESC)) {
			return true;
		}
		return false;
	}
}
