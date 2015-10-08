package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.PropertyService;
import org.RealEstateMM.services.dtos.property.PropertyAddressDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;

public class PropertyServiceAntiCorruption {

	private PropertyInformationsValidator informationValidator;
	private PropertyService service;

	public PropertyServiceAntiCorruption(PropertyService service, PropertyInformationsValidator validator) {
		this.informationValidator = validator;
		this.service = service;
	}

	public void upload(PropertyDTO propertyInfos) {
		validatePropertyInformations(propertyInfos);
		validatePropertyAddress(propertyInfos.getPropertyAddressDTO());
		service.uploadProperty(propertyInfos);
	}

	private void validatePropertyInformations(PropertyDTO propertyInfos) {
		if (!informationValidator.propertyTypeIsValid(propertyInfos.getPropertyType())) {
			throw new InvalidPropertyInformationException("Property Type");
		}
		if (!informationValidator.propertyStatusIsValid(propertyInfos.getPropertyStatus())) {
			throw new InvalidPropertyInformationException("Property Status");
		}
	}

	private void validatePropertyAddress(PropertyAddressDTO addressInfos) {
		if (!informationValidator.zipCodeIsValid(addressInfos.getZipCode())) {
			throw new InvalidPropertyInformationException("Zip code");
		}
	}

	public void editProperty(PropertyFeaturesDTO features, String zipCode) {
		service.editPropertyFeatures(features, zipCode);
	}
}
