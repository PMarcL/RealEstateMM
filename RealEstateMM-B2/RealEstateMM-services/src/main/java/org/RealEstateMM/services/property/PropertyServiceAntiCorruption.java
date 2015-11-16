package org.RealEstateMM.services.property;

import java.util.List;

import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyFeaturesDTO;
import org.RealEstateMM.services.user.ForbiddenAccessException;

public class PropertyServiceAntiCorruption implements PropertyServiceHandler {

	private PropertyInformationsValidator informationValidator;
	private PropertyServiceHandler service;

	public PropertyServiceAntiCorruption(PropertyServiceHandler service, PropertyInformationsValidator validator) {
		this.informationValidator = validator;
		this.service = service;
	}

	@Override
	public void uploadProperty(String owner, PropertyDTO propertyInfos) throws ForbiddenAccessException {
		validatePropertyInformations(propertyInfos);
		validatePropertyAddress(propertyInfos.getPropertyAddress());
		service.uploadProperty(owner, propertyInfos);
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
	public void editPropertyFeatures(String owner, PropertyDTO propertyDTO) throws ForbiddenAccessException {
		PropertyFeaturesDTO features = propertyDTO.getPropertyFeatures();
		verifyNumberOfRoomsValidity(features);
		verifyYearOfConstructionValidity(features);
		service.editPropertyFeatures(owner, propertyDTO);
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
	public List<PropertyDTO> getAllProperties(String pseudo) throws ForbiddenAccessException {
		return service.getAllProperties(pseudo);
	}

	@Override
	public List<PropertyDTO> getPropertiesFromOwner(String owner) throws ForbiddenAccessException {
		return service.getPropertiesFromOwner(owner);
	}

	@Override
	public List<PropertyDTO> getOrderedProperties(String pseudo, String orderBy) throws ForbiddenAccessException {
		return service.getOrderedProperties(pseudo, orderBy);
	}
}
