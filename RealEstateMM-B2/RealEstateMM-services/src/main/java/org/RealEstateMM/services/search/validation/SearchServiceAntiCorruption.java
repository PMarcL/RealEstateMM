package org.RealEstateMM.services.search.validation;

import java.util.List;

import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.validation.PropertyInformationsValidator;
import org.RealEstateMM.services.search.InvalidSearchParameterException;
import org.RealEstateMM.services.search.SearchServiceHandler;

public class SearchServiceAntiCorruption implements SearchServiceHandler {

	private SearchServiceHandler service;
	private PropertyInformationsValidator validator;

	public SearchServiceAntiCorruption(SearchServiceHandler service, PropertyInformationsValidator validator) {
		this.service = service;
		this.validator = validator;
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
	public List<PropertyDTO> getOrderedProperties(String pseudo, String orderBy)
			throws ForbiddenAccessException, InvalidSearchParameterException {
		return service.getOrderedProperties(pseudo, orderBy);
	}

	@Override
	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO address)
			throws ForbiddenAccessException, PropertyNotFoundException, InvalidPropertyInformationException {
		validatePropertyAddress(address);
		return service.getPropertyAtAddress(pseudo, address);
	}

	private void validatePropertyAddress(PropertyAddressDTO addressInfos) throws InvalidPropertyInformationException {
		if (!validator.zipCodeIsValid(addressInfos.getZipCode())) {
			throw new InvalidPropertyInformationException("Zip code");
		}
	}
}
