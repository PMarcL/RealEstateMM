package org.RealEstateMM.services.property;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertyInformationsValidator {

	private final Pattern ZIP_CODE_PATTERN = Pattern.compile("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$");

	public PropertyInformationsValidator() {

	}

	public boolean zipCodeIsValid(String zipCode) {
		Matcher matcher = ZIP_CODE_PATTERN.matcher(zipCode);
		return matcher.matches();
	}

	public boolean propertyTypeIsValid(String type) {
		return PropertyType.isValidPropertyTypeDescription(type);
	}

	public boolean propertyStatusIsValid(String status) {
		return PropertyStatus.isValidStatusDescription(status);
	}

	public boolean numberOfRoomsIsValid(int numberOfRooms) {
		if (numberOfRooms >= 0) {
			return true;
		}
		return false;
	}

}
