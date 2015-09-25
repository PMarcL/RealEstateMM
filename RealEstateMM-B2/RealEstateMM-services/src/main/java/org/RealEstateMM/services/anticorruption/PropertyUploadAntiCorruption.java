package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.PropertyUploadService;
import org.RealEstateMM.services.dto.PropertyAddressInformations;
import org.RealEstateMM.services.dto.PropertyInformations;

public class PropertyUploadAntiCorruption {

	private PropertyAddressValidator informationValidator;
	private PropertyUploadService uploadService;
	
	private PropertyUploadAntiCorruption(PropertyUploadService service, PropertyAddressValidator validator){
		this.informationValidator = validator;
		this.uploadService = service;
	}
	
	public void upload(PropertyInformations propertyInfos, PropertyAddressInformations propertyAddressInfos){
		validatePropertyAddress(propertyAddressInfos);
		uploadService.uploadProperty(propertyInfos, propertyAddressInfos);
	}

	private void validatePropertyAddress(PropertyAddressInformations propertyAddressInfos) {
		if(!informationValidator.zipCodeIsValid(propertyAddressInfos.getZipCode())){
			throw new InvalidZipCodeFormatException(propertyAddressInfos.getZipCode());
		}
	}
}
