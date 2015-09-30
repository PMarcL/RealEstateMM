package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.PropertyUploadService;
import org.RealEstateMM.services.dtos.property.PropertyAddressInformations;
import org.RealEstateMM.services.dtos.property.PropertyInformations;

public class PropertyUploadAntiCorruption {

	private PropertyAddressValidator informationValidator;
	private PropertyUploadService uploadService;

	public PropertyUploadAntiCorruption(PropertyUploadService service, PropertyAddressValidator validator) {
		this.informationValidator = validator;
		this.uploadService = service;
	}

	public void upload(PropertyInformations propertyInfos) {
		validatePropertyAddress(propertyInfos.getPropertyAddressInformations());
		uploadService.uploadProperty(propertyInfos);
	}

	private void validatePropertyAddress(PropertyAddressInformations addressInfos) {
		if (!informationValidator.zipCodeIsValid(addressInfos.getZipCode())) {
			throw new InvalidZipCodeFormatException(addressInfos.getZipCode());
		}
	}
}
