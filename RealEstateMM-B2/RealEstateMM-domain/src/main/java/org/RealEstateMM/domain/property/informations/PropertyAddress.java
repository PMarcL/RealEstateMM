package org.RealEstateMM.domain.property.informations;


public class PropertyAddress {
	
	public final String streetNumber;
	public final String streetName;
	public final String city;
	public final String province;
	public final String zipCode;
	

	public PropertyAddress(String streetNumber, String streetName, String city, String province, String zipCode)
	{
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.city = city;
		this.province = province;
		this.zipCode = zipCode;
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
