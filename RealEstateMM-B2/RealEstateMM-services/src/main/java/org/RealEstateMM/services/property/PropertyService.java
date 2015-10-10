package org.RealEstateMM.services.property;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTOAssembler;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTOAssembler;

import java.util.ArrayList;
import java.util.Optional;

public class PropertyService implements PropertyServiceHandler {

	private PropertyRepository propertyRepository;
	private PropertyDTOAssembler propertyAssembler;
	private PropertyFeaturesDTOAssembler featuresAssembler;

	public PropertyService() {
		propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		propertyAssembler = new PropertyDTOAssembler();
		featuresAssembler = new PropertyFeaturesDTOAssembler();
	}

	public PropertyService(PropertyRepository propertyRepository, PropertyDTOAssembler propertyAssembler,
			PropertyFeaturesDTOAssembler featuresAssembler) {
		this.propertyRepository = propertyRepository;
		this.propertyAssembler = propertyAssembler;
		this.featuresAssembler = featuresAssembler;
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

	private ArrayList<PropertyDTO> buildDTOsFromProperties(ArrayList<Property> properties) {
		ArrayList<PropertyDTO> propertiesDTO = new ArrayList<PropertyDTO>();
		for (Property property : properties) {
			PropertyDTO dto = propertyAssembler.toDTO(property);
			propertiesDTO.add(dto);
		}
		return propertiesDTO;
	}

	@Override
	public void editPropertyFeatures(PropertyFeaturesDTO featuresDTO) {
		PropertyFeatures features = featuresAssembler.fromDTO(featuresDTO);
		Optional<Property> property = getPropertyFromPropertyFeatures(featuresDTO);

		property.get().updateFeatures(features);
		propertyRepository.updateProperty(property.get());
	}

	private Optional<Property> getPropertyFromPropertyFeatures(PropertyFeaturesDTO featuresDTO) {
		PropertyAddress address = featuresAssembler.getAddressFromDTO(featuresDTO);
		return propertyRepository.getPropertyAtAddress(address);
	}
}
