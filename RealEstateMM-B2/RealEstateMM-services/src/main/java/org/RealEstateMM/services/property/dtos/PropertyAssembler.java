package org.RealEstateMM.services.property.dtos;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertyAssembler {

	private PropertyAddressDTOAssembler addressAssembler;
	private PropertyFeaturesDTOAssembler featuresAssembler;

	public PropertyAssembler() {
		this.addressAssembler = new PropertyAddressDTOAssembler();
		this.featuresAssembler = new PropertyFeaturesDTOAssembler();
	}

	public PropertyAssembler(PropertyAddressDTOAssembler addressAssembler,
			PropertyFeaturesDTOAssembler featuresAssembler) {
		this.addressAssembler = addressAssembler;
		this.featuresAssembler = featuresAssembler;
	}

	public PropertyDTO toDTO(Property property) {
		PropertyDTO dto = new PropertyDTO();
		PropertyAddressDTO addressDTO = addressAssembler.toDTO(property.getAddress());
		PropertyFeaturesDTO featuresDTO = featuresAssembler.toDTO(property.getFeatures());

		dto.setPropertyType(property.getType().toString());
		dto.setPropertyAddress(addressDTO);
		dto.setPropertyFeatures(featuresDTO);
		dto.setPropertyPrice(property.getPrice());
		dto.setPropertyOwner(property.getOwner());
		dto.setPropertyStatus(property.getStatus().toString());
		return dto;
	}

	public Property fromDTO(PropertyDTO propertyDTO) {
		PropertyAddress address = addressAssembler.fromDTO(propertyDTO.getPropertyAddress());
		PropertyType type = PropertyType.fromString(propertyDTO.getPropertyType());
		PropertyStatus status = PropertyStatus.fromString(propertyDTO.getPropertyStatus());

		return new Property(type, address, propertyDTO.getPropertyPrice(), propertyDTO.getPropertyOwner(), status);
	}

	public PropertyFeatures getFeaturesFromDTO(PropertyDTO propertyDTO) {
		return featuresAssembler.fromDTO(propertyDTO.getPropertyFeatures());
	}

	public PropertyAddress getAddressFromDTO(PropertyAddressDTO addressDTO) {
		return addressAssembler.fromDTO(addressDTO);
	}
}
