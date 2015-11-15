package org.RealEstateMM.services.property;

import java.util.List;

import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.user.UnauthorizedAccessException;

public interface PropertyServiceHandler {

	public void uploadProperty(String pseudo, PropertyDTO propertyInfos) throws UnauthorizedAccessException;

	public List<PropertyDTO> getAllProperties(String pseudo) throws UnauthorizedAccessException;

	public List<PropertyDTO> getOrderedProperties(String pseudo, PropertySearchFilter orderBy)
			throws UnauthorizedAccessException;

	public void editPropertyFeatures(String pseudo, PropertyDTO propertyDTO) throws UnauthorizedAccessException;

	public List<PropertyDTO> getPropertiesFromOwner(String owner);
}
