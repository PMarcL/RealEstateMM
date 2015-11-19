package org.RealEstateMM.services.property;

import java.util.List;

import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

public class PropertyServiceSecurity implements PropertyServiceHandler {

	private PropertyServiceHandler service;
	private UserAuthorizations authorizations;

	public PropertyServiceSecurity(PropertyServiceHandler service, UserAuthorizations authorizations) {
		this.authorizations = authorizations;
		this.service = service;
	}

	@Override
	public void uploadProperty(String pseudo, PropertyDTO propertyInfos) throws ForbiddenAccessException {
		validateUserAccess(pseudo, AccessLevel.SELLER);
		service.uploadProperty(pseudo, propertyInfos);
	}

	private void validateUserAccess(String pseudo, AccessLevel... accessLevels) throws ForbiddenAccessException {
		authorizations.validateUserAuthorizations(pseudo, accessLevels);
	}

	@Override
	public List<PropertyDTO> getAllProperties(String pseudo) throws ForbiddenAccessException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		return service.getAllProperties(pseudo);
	}

	@Override
	public List<PropertyDTO> getOrderedProperties(String pseudo, String orderBy)
			throws ForbiddenAccessException, InvalidSearchParameterException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		return service.getOrderedProperties(pseudo, orderBy);
	}

	@Override
	public void editPropertyFeatures(String pseudo, PropertyDTO propertyDTO) throws ForbiddenAccessException {
		validateUserAccess(pseudo, AccessLevel.SELLER);
		service.editPropertyFeatures(pseudo, propertyDTO);
	}

	@Override
	public List<PropertyDTO> getPropertiesFromOwner(String owner) throws ForbiddenAccessException {
		validateUserAccess(owner, AccessLevel.SELLER);
		return service.getPropertiesFromOwner(owner);
	}

	@Override
	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO address)
			throws ForbiddenAccessException, PropertyNotFoundException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		return service.getPropertyAtAddress(pseudo, address);
	}

}
