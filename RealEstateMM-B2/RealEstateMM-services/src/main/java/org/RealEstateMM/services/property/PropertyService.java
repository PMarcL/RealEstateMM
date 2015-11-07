package org.RealEstateMM.services.property;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;

import java.util.ArrayList;
import java.util.Optional;

public class PropertyService implements PropertyServiceHandler {

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

	@Override
	public void uploadProperty(PropertyDTO propertyInfos) {
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		propertyRepository.add(newProperty);
	}

	@Override
	public ArrayList<PropertyDTO> getAllProperties() {
		ArrayList<Property> properties = propertyRepository.getAllProperties();
		return buildDTOsFromProperties(properties);
	}

	@Override
	public void editPropertyFeatures(PropertyDTO propertyDTO) {
		Property property = getPropertyWithDTO(propertyDTO);
		PropertyFeatures features = propertyAssembler.getFeaturesFromDTO(propertyDTO);

		property.updateFeatures(features);
		propertyRepository.updateProperty(property);
	}

	private Property getPropertyWithDTO(PropertyDTO propertyDTO) {
		PropertyAddress address = propertyAssembler.getPropertyAddressFromDTO(propertyDTO);
		Optional<Property> property = propertyRepository.getPropertyAtAddress(address);
		return property.get();
	}

	@Override
	public ArrayList<PropertyDTO> getPropertiesFromOwner(String owner) {
		ArrayList<Property> properties = propertyRepository.getPropertiesFromOwner(owner);
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
