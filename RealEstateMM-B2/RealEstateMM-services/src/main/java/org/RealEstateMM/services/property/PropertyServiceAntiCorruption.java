package org.RealEstateMM.services.property;

import java.util.ArrayList;

import org.RealEstateMM.services.dtos.property.PropertyAddressDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;

public class PropertyServiceAntiCorruption implements PropertyServiceHandler {

	private PropertyInformationsValidator informationValidator;
	private PropertyServiceHandler service;

	public PropertyServiceAntiCorruption(PropertyServiceHandler service, PropertyInformationsValidator validator) {
		this.informationValidator = validator;
		this.service = service;
	}

	@Override
	public void uploadProperty(PropertyDTO propertyInfos) {
		validatePropertyInformations(propertyInfos);
		validatePropertyAddress(propertyInfos.getPropertyAddress());
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

	@Override
	public void editPropertyFeatures(PropertyDTO propertyDTO) {
		PropertyFeaturesDTO features = propertyDTO.getPropertyFeatures();
		verifyNumberOfRoomsValidity(features);
		service.editPropertyFeatures(propertyDTO);
	}

	private void verifyNumberOfRoomsValidity(PropertyFeaturesDTO features) {
		if (!informationValidator.numberOfRoomsIsValid(features.getNumberOfBathrooms())) {
			throw new InvalidPropertyInformationException("Number of Bathrooms");
		}
		if (!informationValidator.numberOfRoomsIsValid(features.getNumberOfBedrooms())) {
			throw new InvalidPropertyInformationException("Number of Bedrooms");
		}
		if (!informationValidator.numberOfRoomsIsValid(features.getTotalNumberOfRooms())) {
			throw new InvalidPropertyInformationException("Total number of Rooms");
		}
	}

	@Override
	public ArrayList<PropertyDTO> getAllProperties() {
		return service.getAllProperties();
	}

	@Override
	public ArrayList<PropertyDTO> getPropertiesFromOwner(String owner) {
		return service.getPropertiesFromOwner(owner);
	}
}
