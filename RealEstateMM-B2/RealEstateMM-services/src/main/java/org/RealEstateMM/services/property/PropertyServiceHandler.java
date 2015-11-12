package org.RealEstateMM.services.property;

import java.util.List;

import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

public interface PropertyServiceHandler {

	public void uploadProperty(String pseudo, PropertyDTO propertyInfos);

	public List<PropertyDTO> getAllProperties(String pseudo);

	public List<PropertyDTO> getOrderedProperties(String pseudo, PropertySearchFilter orderBy);

	public void editPropertyFeatures(String pseudo, PropertyDTO propertyDTO);

	public List<PropertyDTO> getPropertiesFromOwner(String owner);
}
