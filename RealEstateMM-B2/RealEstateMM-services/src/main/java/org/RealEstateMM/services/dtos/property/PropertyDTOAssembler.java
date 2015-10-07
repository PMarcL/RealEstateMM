package org.RealEstateMM.services.dtos.property;

import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class PropertyDTOAssembler {

	private PropertyAddressDTOAssembler addressAssembler;
	private UserRepository userRepository;

	public PropertyDTOAssembler() {
		this.addressAssembler = new PropertyAddressDTOAssembler();
		this.userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
	}

	public PropertyDTOAssembler(PropertyAddressDTOAssembler addressAssembler, UserRepository userRepository) {
		this.addressAssembler = addressAssembler;
		this.userRepository = userRepository;
	}

	public PropertyDTO toDTO(Property property) {
		PropertyDTO dto = new PropertyDTO();
		PropertyAddressDTO addressDTO = addressAssembler.toDTO(property.getPropertyAddress());

		dto.setPropertyType(PropertyType.getStringFromType(property.getPropertyType()));
		dto.setPropertyAddressDTO(addressDTO);
		dto.setPropertyPrice(property.getPropertyPrice());
		dto.setPropertyOwner(property.getPropertyOwner());
		dto.setPropertyStatus(PropertyStatus.getStringFromStatus(property.getPropertyStatus()));
		return dto;
	}

	public Property fromDTO(PropertyDTO propertyDTO) {
		PropertyAddress address = addressAssembler.fromDTO(propertyDTO.getPropertyAddressDTO());
		Optional<User> owner = userRepository.getUserWithPseudonym(propertyDTO.getPropertyOwner());
		PropertyType type = PropertyType.getTypeFromString(propertyDTO.getPropertyType());
		PropertyStatus status = PropertyStatus.getStatusFromString(propertyDTO.getPropertyStatus());

		return new Property(type, address, propertyDTO.getPropertyPrice(), owner.get().getPseudonym(), status);
	}
}
