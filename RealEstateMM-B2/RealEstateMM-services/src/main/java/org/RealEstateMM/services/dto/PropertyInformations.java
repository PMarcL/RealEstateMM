package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.user.User;

public class PropertyInformations {
	
	private PropertyType propertyType;
	private PropertyAddress propertyAddress;
	private double propertyPrice;
	private User propertyOwner;
	private PropertyStatus propertyStatus; //TODO find a better name;

	public PropertyInformations(){
		
	}
	
	public PropertyInformations(PropertyType propertyType, PropertyAddress propertyAddress, double propertyPrice, User propertyOwner, PropertyStatus propertyStatus){
		this.setPropertyType(propertyType);
		this.setPropertyAddress(propertyAddress);
		this.setPropertyPrice(propertyPrice);
		this.setPropertyOwner(propertyOwner);
		this.setPropertyStatus(propertyStatus);
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public PropertyAddress getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(PropertyAddress propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public double getPropertyPrice() {
		return propertyPrice;
	}

	public void setPropertyPrice(double propertyPrice) {
		this.propertyPrice = propertyPrice;
	}

	public User getPropertyOwner() {
		return propertyOwner;
	}

	public void setPropertyOwner(User propertyOwner) {
		this.propertyOwner = propertyOwner;
	}

	public PropertyStatus getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(PropertyStatus propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
}
