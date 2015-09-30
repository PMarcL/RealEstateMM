package org.RealEstateMM.domain.property;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.user.User;

public class Property {

	public final String propertyType;
	public final PropertyAddress propertyAddress;
	public final double propertyPrice;
	public final User propertyOwner;
	public final String propertyStatus;

	public Property(String type, PropertyAddress address, double price, User owner, String status) {
		this.propertyType = type;
		this.propertyAddress = address;
		this.propertyPrice = price;
		this.propertyOwner = owner;
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
}
