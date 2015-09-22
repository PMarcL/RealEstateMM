package org.RealEstateMM.domain.property.informations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyAddress {
	
	public final String streetNumber;
	public final String streetName;
	public final String city;
	public final String province;
	public final String zipCode;
	
	private final Pattern ZIP_CODE_PATTERN = Pattern.compile("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$");

	public PropertyAddress(String streetNumber, String streetName, String city, String province, String zipCode)
	{
		if(!zipCodeIsValid(zipCode))
		{
			throw new InvalidZipCodeFormatException(zipCode);
		}
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.city = city;
		this.province = province;
		this.zipCode = zipCode;
	}
	
	private boolean zipCodeIsValid(String zipCode)
	{
		Matcher matcher = ZIP_CODE_PATTERN.matcher(zipCode);
		return matcher.matches();
	}
	
	@Override
	public String toString()
	{
		String formattedAddress = this.streetNumber + " " + this.streetName + ", " + this.city + ", " + this.province + ", " + this.zipCode;
		return formattedAddress;
	}

	public boolean isEquals(PropertyAddress propertyAddress) {
		if (propertyAddress == null){
			return false;
		}
		
		boolean areEquals = this.streetNumber.equals(propertyAddress.streetNumber);
		areEquals &= this.streetName.equals(propertyAddress.streetName);
		areEquals &= this.city.equals(propertyAddress.city);
		areEquals &= this.province.equals(propertyAddress.province);
		areEquals &= this.zipCode.equals(propertyAddress.zipCode);
		return areEquals;
	}
	
	
}
