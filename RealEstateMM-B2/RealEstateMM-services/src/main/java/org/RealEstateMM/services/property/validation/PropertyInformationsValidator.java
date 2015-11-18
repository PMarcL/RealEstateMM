package org.RealEstateMM.services.property.validation;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertyInformationsValidator {

	private final Pattern ZIP_CODE_PATTERN = Pattern.compile("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$");
	private final int CONSTRUCTION_YEAR_OF_OLDEST_BUILDING_IN_CANADA = 1637; // According
																				// to
																				// wikipedia.org
	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

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

	public boolean totalNumberOfRoomsIsValid(int numberOfBathrooms, int numberOfBedrooms, int totalNumberOfRooms) {
		numberOfRoomsIsValid(numberOfBathrooms);
		numberOfRoomsIsValid(numberOfBedrooms);
		numberOfRoomsIsValid(totalNumberOfRooms);
		if (totalNumberOfRooms >= (numberOfBathrooms + numberOfBedrooms)) {
			return true;
		}
		return false;
	}

	public boolean yearOfConstructionIsValid(int yearOfConstruction) {
		if (yearOfConstruction < CONSTRUCTION_YEAR_OF_OLDEST_BUILDING_IN_CANADA || yearOfConstruction > currentYear) {
			return false;
		}
		return true;
	}

	public boolean priceIsValid(double price) {
		if (price > 0.0) {
			return true;
		}
		return false;
	}

}
