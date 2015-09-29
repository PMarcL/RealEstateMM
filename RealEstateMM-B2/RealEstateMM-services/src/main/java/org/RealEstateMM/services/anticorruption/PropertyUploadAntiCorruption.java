package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.PropertyUploadService;
import org.RealEstateMM.services.dto.PropertyAddressInformations;
import org.RealEstateMM.services.dto.PropertyInformations;

public class PropertyUploadAntiCorruption {

	private PropertyAddressValidator informationValidator;
	private PropertyUploadService uploadService;
	
	public PropertyUploadAntiCorruption(PropertyUploadService service, PropertyAddressValidator validator){
		this.informationValidator = validator;
		this.uploadService = service;
	}
	
	public void upload(PropertyInformations propertyInfos){
		validatePropertyAddress(propertyInfos);
		uploadService.uploadProperty(propertyInfos);
	}

	private void validatePropertyAddress(PropertyInformations propertyInfos) {
		if(!informationValidator.zipCodeIsValid(propertyInfos.getPropertyAddress().zipCode)){ //TODO Train wreck
			throw new InvalidZipCodeFormatException(propertyInfos.getPropertyAddress().zipCode);
		}
	}
}
