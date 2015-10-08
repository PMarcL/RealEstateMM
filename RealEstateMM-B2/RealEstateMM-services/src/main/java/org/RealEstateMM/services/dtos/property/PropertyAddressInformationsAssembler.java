package org.RealEstateMM.services.dtos.property;

import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class PropertyAddressInformationsAssembler {

	public PropertyAddressInformations toDTO(PropertyAddress address) {
		PropertyAddressInformations dto = new PropertyAddressInformations();

		dto.setStreetAddress(address.streetAddress);
		dto.setCity(address.city);
		dto.setProvince(address.province);
		dto.setZipCode(address.zipCode);

		return dto;
	}

	public PropertyAddress fromDTO(PropertyAddressInformations dto) {
		return new PropertyAddress(dto.getStreetAddress(), dto.getCity(), dto.getProvince(), dto.getZipCode());
	}
}