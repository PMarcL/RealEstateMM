package org.RealEstateMM.services;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTOAssembler;
import org.RealEstateMM.services.servicelocator.ServiceLocator;
import java.util.ArrayList;

public class PropertyService {

	private PropertyRepository propertyRepository;
	private PropertyDTOAssembler propertyAssembler;

	public PropertyService() {
		propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		propertyAssembler = new PropertyDTOAssembler();
	}

	public PropertyService(PropertyRepository propertyRepository, PropertyDTOAssembler propertyAssembler) {
		this.propertyRepository = propertyRepository;
		this.propertyAssembler = propertyAssembler;
	}

	public void uploadProperty(PropertyDTO propertyInfos) {
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		propertyRepository.add(newProperty);
	}

	public void editPropertyInfos(Property property) {
		// TODO Implement
	}

	public ArrayList<PropertyDTO> getAllProperties() {
		ArrayList<Property> properties = propertyRepository.getAllProperties();
		return buildDTOsFromProperties(properties);
	}

	private ArrayList<PropertyDTO> buildDTOsFromProperties(ArrayList<Property> properties) {
		ArrayList<PropertyDTO> propertiesDTO = new ArrayList<PropertyDTO>();
		for (Property property : properties) {
			PropertyDTO dto = propertyAssembler.toDTO(property);
			propertiesDTO.add(dto);
		}
		return propertiesDTO;
	}
}
