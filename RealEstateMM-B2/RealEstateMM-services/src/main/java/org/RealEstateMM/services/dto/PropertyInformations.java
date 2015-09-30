package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.user.User;

public class PropertyInformations {
	

//	private PropertyType propertyType;
	private String propertyType;
	private PropertyAddress propertyAddress;
	private double propertyPrice;
	private User propertyOwner;
	//private PropertyStatus propertyStatus; //TODO find a better name;
	private String propertyStatus;
	
	private PropertyAddressInformationsAssembler addressAssembler;
	private UserInformationsAssembler ownerAssembler;

	public PropertyInformations(){
		
	}
	
	public PropertyInformations(String propertyType, PropertyAddressInformations propertyAddressInfos, double propertyPrice, UserInformations propertyOwnerInfo, String propertyStatus){
		addressAssembler = new PropertyAddressInformationsAssembler();
		ownerAssembler = new UserInformationsAssembler();
		
		this.setPropertyType(propertyType);
		this.setPropertyAddress(propertyAddressInfos); //TODO coder l'assignation des deux DTO
		this.setPropertyPrice(propertyPrice);
		this.setPropertyOwner(propertyOwnerInfo);
		this.setPropertyStatus(propertyStatus);
	}

//	public PropertyType getPropertyType() {
//		return propertyType;
//	}

//	public void setPropertyType(PropertyType propertyType) {
//		this.propertyType = propertyType;
//	}
	public String  getPropertyType(){
		return propertyType;
	}
	
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public PropertyAddress getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(PropertyAddressInformations propertyAddressInfo) {
		this.propertyAddress = addressAssembler.fromDTO(propertyAddressInfo);
	}
	
	public void setPropertyAddress(PropertyAddress propertyAddress){
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

	public void setPropertyOwner(UserInformations propertyOwnerInfos) {
		this.propertyOwner = ownerAssembler.fromDTO(propertyOwnerInfos);
	}
	public void setPropertyOwner(User owner){
		this.propertyOwner = owner;
	}

	public String getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
	
}
