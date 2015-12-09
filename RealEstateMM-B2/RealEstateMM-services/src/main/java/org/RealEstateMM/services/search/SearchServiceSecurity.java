package org.RealEstateMM.services.search;

import java.util.List;

import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.search.dtos.SearchDTO;

public class SearchServiceSecurity implements SearchServiceHandler {

	private UserAuthorizations authorizations;
	private SearchServiceHandler service;

	public SearchServiceSecurity(SearchServiceHandler service, UserAuthorizations authorizations) {
		this.service = service;
		this.authorizations = authorizations;
	}

	@Override
	public List<PropertyDTO> executeSearch(String pseudo, SearchDTO searchParam) throws ForbiddenAccessException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		return service.executeSearch(pseudo, searchParam);
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

	@Override
	public void saveSearch(String pseudo, SearchDTO search) throws ForbiddenAccessException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		service.saveSearch(pseudo, search);
	}

	private void validateUserAccess(String pseudo, AccessLevel... accessLevels) throws ForbiddenAccessException {
		authorizations.validateUserAuthorizations(pseudo, accessLevels);
	}

	@Override
	public List<String> getSavedSearchesForUser(String pseudo) throws ForbiddenAccessException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		return service.getSavedSearchesForUser(pseudo);
	}

	@Override
	public void deleteSearch(String pseudo, String searchName) throws ForbiddenAccessException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		service.deleteSearch(pseudo, searchName);
	}

	@Override
	public SearchDTO getSearch(String pseudo, String searchName) throws ForbiddenAccessException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		return service.getSearch(pseudo, searchName);
	}

}
