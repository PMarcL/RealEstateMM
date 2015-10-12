package org.RealEstateMM.services.property;

import java.util.ArrayList;

import org.RealEstateMM.services.dtos.property.PropertyDTO;

public interface PropertyServiceHandler {

	public void uploadProperty(PropertyDTO propertyInfos);

	public ArrayList<PropertyDTO> getAllProperties();

	public void editPropertyFeatures(PropertyDTO propertyDTO);

	public ArrayList<PropertyDTO> getPropertiesFromOwner(String owner);
}
