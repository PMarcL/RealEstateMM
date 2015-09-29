package org.RealEstateMM.domain.property;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.user.User;

public class Property {
	
	//public final PropertyType propertyType;
	public final String propertyType;
	public final PropertyAddress propertyAddress;
	public final double propertyPrice;
	public final User propertyOwner;
	//public PropertyStatus propertyStatus; //TODO find a better name;
	public final String propertyStatus;
	
	public Property(String type, PropertyAddress address, double price, User owner, String status)
	{
		this.propertyType = type;
		this.propertyAddress = address;
		this.propertyPrice = price;
		this.propertyOwner = owner;
		this.propertyStatus = status;
	}

	
	public boolean isEquals(Property property)
	{
		if(property == null){
			return false;
		}
		
		boolean areEquals = this.propertyAddress.isEquals(property.propertyAddress);
		areEquals &= this.propertyOwner.isEquals(property.propertyOwner);
		return areEquals;
	}
}
