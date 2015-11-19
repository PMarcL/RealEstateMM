package org.RealEstateMM.services.property;

import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

public class PropertyServiceSecurity implements PropertyServiceHandler {

	private PropertyServiceHandler service;
	private UserAuthorizations authorizations;

	public PropertyServiceSecurity(PropertyServiceHandler service, UserAuthorizations authorizations) {
		this.authorizations = authorizations;
		this.service = service;
	}

	@Override
	public void uploadProperty(String pseudo, PropertyDTO propertyInfos)
			throws ForbiddenAccessException, InvalidPropertyInformationException {
		validateUserAccess(pseudo, AccessLevel.SELLER);
		service.uploadProperty(pseudo, propertyInfos);
	}

	private void validateUserAccess(String pseudo, AccessLevel... accessLevels) throws ForbiddenAccessException {
		authorizations.validateUserAuthorizations(pseudo, accessLevels);
	}

	@Override
	public void editPropertyFeatures(String pseudo, PropertyDTO propertyDTO)
			throws ForbiddenAccessException, InvalidPropertyInformationException {
		validateUserAccess(pseudo, AccessLevel.SELLER);
		service.editPropertyFeatures(pseudo, propertyDTO);
	}

}
