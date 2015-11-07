package org.RealEstateMM.services.property;

import java.util.ArrayList;

import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyFeaturesDTO;

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
		if (!informationValidator.priceIsValid(propertyInfos.getPropertyPrice())) {
			throw new InvalidPropertyInformationException("Property Price");
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
		verifyYearOfConstructionValidity(features);
		service.editPropertyFeatures(propertyDTO);
	}

	private void verifyNumberOfRoomsValidity(PropertyFeaturesDTO features) {
		if (!informationValidator.numberOfRoomsIsValid(features.getNumberOfBathrooms())) {
			throw new InvalidPropertyInformationException("Number of Bathrooms");
		}
		if (!informationValidator.numberOfRoomsIsValid(features.getNumberOfBedrooms())) {
			throw new InvalidPropertyInformationException("Number of Bedrooms");
		}
		if (!informationValidator.totalNumberOfRoomsIsValid(features.getNumberOfBathrooms(),
				features.getNumberOfBedrooms(), features.getTotalNumberOfRooms())) {
			throw new InvalidPropertyInformationException("Total number of Rooms");
		}
	}

	private void verifyYearOfConstructionValidity(PropertyFeaturesDTO features) {
		if (!informationValidator.yearOfConstructionIsValid(features.getYearOfConstruction())) {
			throw new InvalidPropertyInformationException("Year of Construction");
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
