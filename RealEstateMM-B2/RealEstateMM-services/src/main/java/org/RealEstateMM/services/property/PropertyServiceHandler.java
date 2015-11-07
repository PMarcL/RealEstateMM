package org.RealEstateMM.services.property;

import java.util.ArrayList;

import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.domain.property.search.PropertySearchFilter;

public interface PropertyServiceHandler {

	public void uploadProperty(PropertyDTO propertyInfos);

	public ArrayList<PropertyDTO> getAllProperties();

	public ArrayList<PropertyDTO> getOrderedProperties(PropertySearchFilter orderBy);

	public void editPropertyFeatures(PropertyDTO propertyDTO);

	public ArrayList<PropertyDTO> getPropertiesFromOwner(String owner);
}
