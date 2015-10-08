package org.RealEstateMM.services.dtos.property;

import java.util.Optional;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.servicelocator.ServiceLocator;

public class PropertyDTOAssembler {

	private PropertyAddressDTOAssembler addressAssembler;
	private UserRepository userRepository;

	public PropertyDTOAssembler() {
		this.addressAssembler = new PropertyAddressDTOAssembler();
		this.userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
	}

	public PropertyDTOAssembler(PropertyAddressDTOAssembler addressAssembler,
			UserRepository userRepository) {
		this.addressAssembler = addressAssembler;
		this.userRepository = userRepository;
	}

	public PropertyDTO toDTO(Property property) {
		PropertyDTO dto = new PropertyDTO();
		PropertyAddressDTO addressDTO = addressAssembler.toDTO(property.propertyAddress);

		dto.setPropertyType(property.propertyType);
		dto.setPropertyAddressInformations(addressDTO);
		dto.setPropertyPrice(property.propertyPrice);
		dto.setPropertyOwner(property.propertyOwner);
		dto.setPropertyStatus(property.propertyStatus);
		return dto;
	}

	public Property fromDTO(PropertyDTO propertyInfos) {
		PropertyAddress address = addressAssembler.fromDTO(propertyInfos.getPropertyAddressInformations());
		Optional<User> owner = userRepository.getUserWithPseudonym(propertyInfos.getPropertyOwner());

		return new Property(propertyInfos.getPropertyType(), address, propertyInfos.getPropertyPrice(), owner.get().getPseudonym(),
				propertyInfos.getPropertyStatus());
	}
}
