package org.RealEstateMM.services.dtos.property;

import java.util.Optional;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class PropertyInformationsAssembler {

	private PropertyAddressInformationsAssembler addressAssembler;
	private UserRepository userRepository;

	public PropertyInformationsAssembler() {
		this.addressAssembler = new PropertyAddressInformationsAssembler();
		this.userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
	}

	public PropertyInformationsAssembler(PropertyAddressInformationsAssembler addressAssembler,
			UserRepository userRepository) {
		this.addressAssembler = addressAssembler;
		this.userRepository = userRepository;
	}

	public PropertyInformations toDTO(Property property) {
		PropertyInformations dto = new PropertyInformations();
		PropertyAddressInformations addressDTO = addressAssembler.toDTO(property.propertyAddress);

		dto.setPropertyType(property.propertyType);
		dto.setPropertyAddressInformations(addressDTO);
		dto.setPropertyPrice(property.propertyPrice);
		dto.setPropertyOwner(property.propertyOwner);
		dto.setPropertyStatus(property.propertyStatus);
		return dto;
	}

	public Property fromDTO(PropertyInformations propertyInfos) {
		PropertyAddress address = addressAssembler.fromDTO(propertyInfos.getPropertyAddressInformations());
		Optional<User> owner = userRepository.getUserWithPseudonym(propertyInfos.getPropertyOwner());

		return new Property(propertyInfos.getPropertyType(), address, propertyInfos.getPropertyPrice(), owner.get().getPseudonym(),
				propertyInfos.getPropertyStatus());
	}
}
