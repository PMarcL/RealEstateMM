package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.PropertyService;
import org.RealEstateMM.services.dtos.property.PropertyAddressInformations;
import org.RealEstateMM.services.dtos.property.PropertyInformations;

public class PropertyServiceAntiCorruption {

	private PropertyAddressValidator informationValidator;
	private PropertyService uploadService;

	public PropertyServiceAntiCorruption(PropertyService service, PropertyAddressValidator validator) {
		this.informationValidator = validator;
		this.uploadService = service;
	}

	public void upload(PropertyInformations propertyInfos) {
		validatePropertyAddress(propertyInfos.getPropertyAddressInformations());
		uploadService.uploadProperty(propertyInfos);
	}

	private void validatePropertyAddress(PropertyAddressInformations addressInfos) {
		if (!informationValidator.zipCodeIsValid(addressInfos.getZipCode())) {
			throw new InvalidPropertyInformationException(addressInfos.getZipCode());
		}
	}
}