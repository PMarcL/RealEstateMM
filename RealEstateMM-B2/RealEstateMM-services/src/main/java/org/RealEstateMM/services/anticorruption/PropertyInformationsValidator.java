package org.RealEstateMM.services.anticorruption;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyInformationsValidator {

	private final Pattern ZIP_CODE_PATTERN = Pattern.compile("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$");

	@SuppressWarnings("serial")
	private final ArrayList<String> VALID_PROPERTY_TYPES = new ArrayList<String>() {
		{
			add("house");
			add("multiplex");
			add("commercial");
			add("farm");
			add("land");
		}
	};

	@SuppressWarnings("serial")
	private final ArrayList<String> VALID_PROPERTY_STATUS = new ArrayList<String>() {
		{
			add("on sale");
			add("sold");
		}
	};

	public PropertyInformationsValidator() {

	}

	public boolean zipCodeIsValid(String zipCode) {
		Matcher matcher = ZIP_CODE_PATTERN.matcher(zipCode);
		return matcher.matches();
	}

	public boolean propertyTypeIsValid(String type) {
		return VALID_PROPERTY_TYPES.contains(type);
	}

	public boolean propertyStatusIsValid(String status) {
		return VALID_PROPERTY_STATUS.contains(status);
	}
}
