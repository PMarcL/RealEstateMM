package org.RealEstateMM.services.property.dtos;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertyDTOAssembler {

	private PropertyAddressDTOAssembler addressAssembler;
	private PropertyFeaturesDTOAssembler featuresAssembler;

	public PropertyDTOAssembler() {
		this.addressAssembler = new PropertyAddressDTOAssembler();
		this.featuresAssembler = new PropertyFeaturesDTOAssembler();
	}

	public PropertyDTOAssembler(PropertyAddressDTOAssembler addressAssembler,
			PropertyFeaturesDTOAssembler featuresAssembler) {
		this.addressAssembler = addressAssembler;
		this.featuresAssembler = featuresAssembler;
	}

	public PropertyDTO toDTO(Property property) {
		PropertyDTO dto = new PropertyDTO();
		PropertyAddressDTO addressDTO = addressAssembler.toDTO(property.getAddress());
		PropertyFeaturesDTO featuresDTO = featuresAssembler.toDTO(property.getFeatures());

		dto.setPropertyType(PropertyType.getStringFromType(property.getType()));
		dto.setPropertyAddress(addressDTO);
		dto.setPropertyFeatures(featuresDTO);
		dto.setPropertyPrice(property.getPrice());
		dto.setPropertyOwner(property.getOwner());
		dto.setPropertyStatus(PropertyStatus.getStringFromStatus(property.getStatus()));
		return dto;
	}

	public Property fromDTO(PropertyDTO propertyDTO) {
		PropertyAddress address = addressAssembler.fromDTO(propertyDTO.getPropertyAddress());
		PropertyType type = PropertyType.getTypeFromString(propertyDTO.getPropertyType());
		PropertyStatus status = PropertyStatus.getStatusFromString(propertyDTO.getPropertyStatus());

		return new Property(type, address, propertyDTO.getPropertyPrice(), propertyDTO.getPropertyOwner(), status);
	}

	public PropertyFeatures getFeaturesFromDTO(PropertyDTO propertyDTO) {
		return featuresAssembler.fromDTO(propertyDTO.getPropertyFeatures());
	}

	public PropertyAddress getAddressFromDTO(PropertyAddressDTO addressDTO) {
		return addressAssembler.fromDTO(addressDTO);
	}
}
