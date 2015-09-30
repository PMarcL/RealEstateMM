package org.RealEstateMM.services.anticorruption;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyAddressValidator {
	
	private final Pattern ZIP_CODE_PATTERN = Pattern.compile("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$");
	
	public PropertyAddressValidator(){
		
	}

	public boolean zipCodeIsValid(String zipCode) {
		Matcher matcher = ZIP_CODE_PATTERN.matcher(zipCode);
		return matcher.matches();
	}

}
