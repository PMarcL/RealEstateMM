package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.PropertyService;
import org.RealEstateMM.services.dtos.property.PropertyAddressDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;

public class PropertyServiceAntiCorruption {

	private PropertyAddressValidator informationValidator;
	private PropertyService uploadService;

	public PropertyServiceAntiCorruption(PropertyService service, PropertyAddressValidator validator) {
		this.informationValidator = validator;
		this.uploadService = service;
	}

	public void upload(PropertyDTO propertyInfos) {
		validatePropertyAddress(propertyInfos.getPropertyAddressDTO());
		uploadService.uploadProperty(propertyInfos);
	}

	private void validatePropertyAddress(PropertyAddressDTO addressInfos) {
		if (!informationValidator.zipCodeIsValid(addressInfos.getZipCode())) {
			throw new InvalidPropertyInformationException(addressInfos.getZipCode());
		}
	}

	public void editProperty(PropertyFeaturesDTO features) {

	}
}
