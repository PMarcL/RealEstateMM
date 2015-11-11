package org.RealEstateMM.services.property;

import java.util.List;

import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

public interface PropertyServiceHandler {

	public void uploadProperty(PropertyDTO propertyInfos);

	public List<PropertyDTO> getAllProperties();

	public List<PropertyDTO> getOrderedProperties(PropertySearchFilter orderBy);

	public void editPropertyFeatures(PropertyDTO propertyDTO);

	public List<PropertyDTO> getPropertiesFromOwner(String owner);
}
