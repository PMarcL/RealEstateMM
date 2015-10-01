package org.RealEstateMM.domain.property;

import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class Property {

	public final String propertyType;
	public final PropertyAddress propertyAddress;
	public final double propertyPrice;
	public final String propertyOwner;
	public final String propertyStatus;

	public Property(String type, PropertyAddress address, double price, String ownerUsername, String status) {
		this.propertyType = type;
		this.propertyAddress = address;
		this.propertyPrice = price;
		this.propertyOwner = ownerUsername;
		this.propertyStatus = status;
	}

	public boolean isEquals(Property property) {
		// TODO complete this method and its tests
		if (property == null) {
			return false;
		}

		boolean areEquals = this.propertyAddress.isEquals(property.propertyAddress);
		areEquals &= this.propertyOwner.equals(property.propertyOwner);
		return areEquals;
	}

	@Override
	public String toString() {
		return "Property [propertyType=" + propertyType + ", propertyAddress=" + propertyAddress + ", propertyPrice="
				+ propertyPrice + ", propertyOwner=" + propertyOwner + ", propertyStatus=" + propertyStatus + "]";
	}

}
