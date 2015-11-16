package org.RealEstateMM.services.property;

import java.util.List;

import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.user.ForbiddenAccessException;

public interface PropertyServiceHandler {

	public void uploadProperty(String pseudo, PropertyDTO propertyInfos) throws ForbiddenAccessException;

	public List<PropertyDTO> getAllProperties(String pseudo) throws ForbiddenAccessException;

	public List<PropertyDTO> getOrderedProperties(String pseudo, PropertySearchFilter orderBy)
			throws ForbiddenAccessException;

	public void editPropertyFeatures(String pseudo, PropertyDTO propertyDTO) throws ForbiddenAccessException;

	public List<PropertyDTO> getPropertiesFromOwner(String owner);
}
