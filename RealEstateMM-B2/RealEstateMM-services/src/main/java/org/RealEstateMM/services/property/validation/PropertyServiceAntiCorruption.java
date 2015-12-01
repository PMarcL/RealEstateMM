package org.RealEstateMM.services.property.validation;

import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
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
	public void uploadProperty(String owner, PropertyDTO propertyInfos) throws ForbiddenAccessException,
			InvalidPropertyInformationException {
		validatePropertyInformations(propertyInfos);
		validatePropertyAddress(propertyInfos.getPropertyAddress());
		service.uploadProperty(owner, propertyInfos);
	}

	private void validatePropertyInformations(PropertyDTO propertyInfos) throws InvalidPropertyInformationException {
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

	private void validatePropertyAddress(PropertyAddressDTO addressInfos) throws InvalidPropertyInformationException {
		if (!informationValidator.zipCodeIsValid(addressInfos.getZipCode())) {
			throw new InvalidPropertyInformationException("Zip code");
		}
	}

	@Override
	public void editPropertyFeatures(String owner, PropertyDTO propertyDTO) throws ForbiddenAccessException,
			InvalidPropertyInformationException {
		PropertyFeaturesDTO features = propertyDTO.getPropertyFeatures();
		verifyNumberOfRoomsValidity(features);
		verifyYearOfConstructionValidity(features);
		service.editPropertyFeatures(owner, propertyDTO);
	}

	private void verifyNumberOfRoomsValidity(PropertyFeaturesDTO features) throws InvalidPropertyInformationException {
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

	private void verifyYearOfConstructionValidity(PropertyFeaturesDTO features)
			throws InvalidPropertyInformationException {
		if (!informationValidator.yearOfConstructionIsValid(features.getYearOfConstruction())) {
			throw new InvalidPropertyInformationException("Year of Construction");
		}
	}
}
