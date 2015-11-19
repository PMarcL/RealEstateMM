package org.RealEstateMM.services.property;

import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

public interface PropertyServiceHandler {

	public void uploadProperty(String pseudo, PropertyDTO propertyInfos)
			throws ForbiddenAccessException, InvalidPropertyInformationException;

	public void editPropertyFeatures(String pseudo, PropertyDTO propertyDTO)
			throws ForbiddenAccessException, InvalidPropertyInformationException;

}
