package org.RealEstateMM.services.property.dtos;

public class PropertyDTO {

	private String propertyType;
	private PropertyAddressDTO propertyAddress;
	private PropertyFeaturesDTO propertyFeatures;
	private double propertyPrice;
	private String propertyOwner;
	private String propertyStatus;

	public PropertyDTO() {
	}

	public PropertyDTO(String propertyType, PropertyAddressDTO propertyAddressDTO,
			PropertyFeaturesDTO propertyFeaturesDTO, double propertyPrice, String ownerPseudonym, String propertyStatus) {
		this.setPropertyType(propertyType);
		this.setPropertyAddress(propertyAddressDTO);
		this.setPropertyFeatures(propertyFeaturesDTO);
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

	public PropertyAddressDTO getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(PropertyAddressDTO propertyAddress) {
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

	public PropertyFeaturesDTO getPropertyFeatures() {
		return propertyFeatures;
	}

	public void setPropertyFeatures(PropertyFeaturesDTO propertyFeaturesDTO) {
		this.propertyFeatures = propertyFeaturesDTO;
	}
}
