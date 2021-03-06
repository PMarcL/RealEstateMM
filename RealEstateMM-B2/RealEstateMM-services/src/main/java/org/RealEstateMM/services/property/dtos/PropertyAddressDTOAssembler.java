package org.RealEstateMM.services.property.dtos;

import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class PropertyAddressDTOAssembler {

	public PropertyAddressDTO toDTO(PropertyAddress address) {
		PropertyAddressDTO dto = new PropertyAddressDTO();

		dto.setStreetAddress(address.streetAddress);
		dto.setCity(address.city);
		dto.setProvince(address.province);
		dto.setZipCode(address.zipCode);

		return dto;
	}

	public PropertyAddress fromDTO(PropertyAddressDTO dto) {
		return new PropertyAddress(dto.getStreetAddress(), dto.getCity(), dto.getProvince(), dto.getZipCode());
	}
}
