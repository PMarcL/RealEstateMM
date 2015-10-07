package org.RealEstateMM.domain.property;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class Property {

	private PropertyType propertyType;
	private PropertyAddress propertyAddress;
	private double propertyPrice;
	private String propertyOwner;
	private PropertyStatus propertyStatus;

	public Property(PropertyType type, PropertyAddress address, double price, String ownerUsername,
			PropertyStatus status) {

		propertyType = type;
		propertyAddress = address;
		propertyPrice = price;
		propertyOwner = ownerUsername;
		propertyStatus = status;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public PropertyAddress getPropertyAddress() {
		return propertyAddress;
	}

	public double getPropertyPrice() {
		return propertyPrice;
	}

	public String getPropertyOwner() {
		return propertyOwner;
	}

	public PropertyStatus getPropertyStatus() {
		return propertyStatus;
	}

}
