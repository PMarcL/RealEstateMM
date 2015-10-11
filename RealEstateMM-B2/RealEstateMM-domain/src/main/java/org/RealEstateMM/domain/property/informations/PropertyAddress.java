package org.RealEstateMM.domain.property.informations;

public class PropertyAddress {

	public final String streetAddress;
	public final String city;
	public final String province;
	public final String zipCode;

	public PropertyAddress(String streetAddress, String city, String province, String zipCode) {
		this.streetAddress = streetAddress;
		this.city = city;
		this.province = province;
		this.zipCode = zipCode;
	}

	public boolean isEquals(PropertyAddress propertyAddress) {
		if (propertyAddress == null) {
			return false;
		}

		boolean areEquals = this.streetAddress.equals(propertyAddress.streetAddress);
		areEquals &= this.city.equals(propertyAddress.city);
		areEquals &= this.province.equals(propertyAddress.province);
		areEquals &= this.zipCode.equals(propertyAddress.zipCode);
		return areEquals;
	}

}
