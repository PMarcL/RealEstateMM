package org.RealEstateMM.services.dtos.property;

import java.util.Optional;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyAddress;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class PropertyAssembler {

	private PropertyAddressAssembler addressAssembler;
	private UserRepository userRepository;

	public PropertyAssembler() {
		this.addressAssembler = new PropertyAddressAssembler();
		this.userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
	}

	public PropertyAssembler(PropertyAddressAssembler addressAssembler,
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
