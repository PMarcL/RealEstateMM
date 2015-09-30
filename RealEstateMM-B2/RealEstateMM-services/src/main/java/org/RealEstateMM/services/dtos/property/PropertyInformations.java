package org.RealEstateMM.services.dtos.property;

public class PropertyInformations {

	private String propertyType;
	private PropertyAddressInformations propertyAddress;
	private double propertyPrice;
	private String propertyOwner;
	private String propertyStatus;

	public PropertyInformations() {
	}

	public PropertyInformations(String propertyType, PropertyAddressInformations propertyAddressInfos,
			double propertyPrice, String ownerPseudonym, String propertyStatus) {

		this.setPropertyType(propertyType);
		this.setPropertyAddressInformations(propertyAddressInfos);
		this.setPropertyPrice(propertyPrice);
		this.setPropertyOwner(ownerPseudonym);
		this.setPropertyStatus(propertyStatus);
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public PropertyAddressInformations getPropertyAddressInformations() {
		return propertyAddress;
	}

	public void setPropertyAddressInformations(PropertyAddressInformations propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public double getPropertyPrice() {
		return propertyPrice;
	}

	public void setPropertyPrice(double propertyPrice) {
		this.propertyPrice = propertyPrice;
	}

	public String getPropertyOwner() {
		return propertyOwner;
	}

	public void setPropertyOwner(String owner) {
		this.propertyOwner = owner;
	}

	public String getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}

}
