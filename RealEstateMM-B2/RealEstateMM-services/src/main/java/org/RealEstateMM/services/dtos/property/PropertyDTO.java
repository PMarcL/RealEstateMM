package org.RealEstateMM.services.dtos.property;

public class PropertyDTO {

	private String propertyType;
	private PropertyAddressDTO propertyAddressDTO;
	private double propertyPrice;
	private String propertyOwner;
	private String propertyStatus;

	public PropertyDTO() {
	}

	public PropertyDTO(String propertyType, PropertyAddressDTO propertyAddressDTO, double propertyPrice,
			String ownerPseudonym, String propertyStatus) {

		this.setPropertyType(propertyType);
		this.setPropertyAddressDTO(propertyAddressDTO);
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

	public PropertyAddressDTO getPropertyAddressDTO() {
		return propertyAddressDTO;
	}

	public void setPropertyAddressDTO(PropertyAddressDTO propertyAddress) {
		this.propertyAddressDTO = propertyAddress;
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
