package org.RealEstateMM.services.search;

import java.util.List;

import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

public class SearchServiceSecurity implements SearchServiceHandler {

	private UserAuthorizations authorizations;
	private SearchServiceHandler service;

	public SearchServiceSecurity(SearchServiceHandler service, UserAuthorizations authorizations) {
		this.service = service;
		this.authorizations = authorizations;
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
	public List<PropertyDTO> getPropertiesFromOwner(String owner) throws ForbiddenAccessException {
		validateUserAccess(owner, AccessLevel.SELLER);
		return service.getPropertiesFromOwner(owner);
	}

	@Override
	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO address)
			throws ForbiddenAccessException, PropertyNotFoundException, InvalidPropertyInformationException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		return service.getPropertyAtAddress(pseudo, address);
	}

	private void validateUserAccess(String pseudo, AccessLevel... accessLevels) throws ForbiddenAccessException {
		authorizations.validateUserAuthorizations(pseudo, accessLevels);
	}

}
