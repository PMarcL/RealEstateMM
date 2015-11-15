package org.RealEstateMM.services.property;

import java.util.List;

import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.user.UnauthorizedAccessException;

public class PropertyServiceSecurity implements PropertyServiceHandler {

	private PropertyServiceHandler service;
	private UserAuthorizations authorizations;

	public PropertyServiceSecurity(PropertyServiceHandler service, UserAuthorizations authorizations) {
		this.authorizations = authorizations;
		this.service = service;
	}

	@Override
	public void uploadProperty(String pseudo, PropertyDTO propertyInfos) throws UnauthorizedAccessException {
		validateUserAccess(pseudo, AccessLevel.SELLER);
		service.uploadProperty(pseudo, propertyInfos);
	}

	private void validateUserAccess(String pseudo, AccessLevel... accessLevels) throws UnauthorizedAccessException {
		if (!(authorizations.isUserAuthorized(pseudo, accessLevels))) {
			throw new UnauthorizedAccessException();
		}
	}

	@Override
	public List<PropertyDTO> getAllProperties(String pseudo) throws UnauthorizedAccessException {
		validateUserAccess(pseudo, AccessLevel.SELLER);
		return service.getAllProperties(pseudo);
	}

	@Override
	public List<PropertyDTO> getOrderedProperties(String pseudo, PropertySearchFilter orderBy)
			throws UnauthorizedAccessException {
		validateUserAccess(pseudo, AccessLevel.BUYER);
		return service.getOrderedProperties(pseudo, orderBy);
	}

	@Override
	public void editPropertyFeatures(String pseudo, PropertyDTO propertyDTO) throws UnauthorizedAccessException {
		validateUserAccess(pseudo, AccessLevel.SELLER);
		service.editPropertyFeatures(pseudo, propertyDTO);
	}

	@Override
	public List<PropertyDTO> getPropertiesFromOwner(String owner) {
		// TODO Auto-generated method stub
		return null;
	}

}
