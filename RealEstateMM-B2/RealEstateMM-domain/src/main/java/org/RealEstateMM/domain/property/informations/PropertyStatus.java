package org.RealEstateMM.domain.property.informations;

public enum PropertyStatus {
	ON_SALE, SOLD;

	private static final String ON_SALE_DESC = "on sale";
	private static final String SOLD_DESC = "sold";

	public static PropertyStatus getStatusFromString(String description) {
		if (description.equals(ON_SALE_DESC)) {
			return PropertyStatus.ON_SALE;
		} else {
			return PropertyStatus.SOLD;
		}
	}

	public static String getStringFromStatus(PropertyStatus status) {
		if (status == PropertyStatus.ON_SALE) {
			return ON_SALE_DESC;
		} else {
			return SOLD_DESC;
		}
	}

	public static boolean isValidStatusDescription(String description) {
		if (description.equals(ON_SALE_DESC) || description.equals(SOLD_DESC)) {
			return true;
		}
		return false;
	}
}
