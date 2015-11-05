package org.RealEstateMM.services.property;

import java.util.ArrayList;

import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.services.dtos.property.PropertyDTO;

public interface PropertyServiceHandler {

	public void uploadProperty(PropertyDTO propertyInfos);

	public ArrayList<PropertyDTO> getAllProperties();

	public ArrayList<PropertyDTO> getOrderedProperties(PropertySearchFilter orderBy);

	public void editPropertyFeatures(PropertyDTO propertyDTO);

	public ArrayList<PropertyDTO> getPropertiesFromOwner(String owner);
}
