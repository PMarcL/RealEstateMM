package org.RealEstateMM.services.property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertyOrderingFactory;
import org.RealEstateMM.domain.property.search.PropertyOrderingStrategy;
import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;

public class PropertyService implements PropertyServiceHandler {

	private PropertyRepository propertyRepository;
	private PropertyDTOAssembler propertyAssembler;
	private PropertyOrderingFactory orderingFactory;

	public PropertyService() {
		propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		propertyAssembler = new PropertyDTOAssembler();
		orderingFactory = new PropertyOrderingFactory();
	}

	public PropertyService(PropertyRepository propertyRepository, PropertyDTOAssembler propertyAssembler,
			PropertyOrderingFactory orderingFactory) {
		this.propertyRepository = propertyRepository;
		this.propertyAssembler = propertyAssembler;
		this.orderingFactory = orderingFactory;
	}

	@Override
	public void uploadProperty(String owner, PropertyDTO propertyInfos) {
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		newProperty.setCreationDate(Calendar.getInstance().getTime());
		propertyRepository.add(newProperty);
	}

	@Override
	public List<PropertyDTO> getAllProperties(String pseudo) {
		ArrayList<Property> properties = propertyRepository.getAll();
		return buildDTOsFromProperties(properties);
	}

	@Override
	public void editPropertyFeatures(String owner, PropertyDTO propertyDTO) {
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
	public List<PropertyDTO> getPropertiesFromOwner(String owner) {
		List<Property> properties = propertyRepository.getPropertiesFromOwner(owner);
		return buildDTOsFromProperties(properties);
	}

	private List<PropertyDTO> buildDTOsFromProperties(List<Property> properties) {
		ArrayList<PropertyDTO> propertiesDTO = new ArrayList<PropertyDTO>();
		for (Property property : properties) {
			PropertyDTO dto = propertyAssembler.toDTO(property);
			propertiesDTO.add(dto);
		}
		return propertiesDTO;
	}

	@Override
	public List<PropertyDTO> getOrderedProperties(String pseudo, PropertySearchFilter orderBy) {
		PropertyOrderingStrategy orderingStrategy = orderingFactory.getOrderingStrategy(orderBy
				.getParsedSearchParameter());
		ArrayList<Property> properties = orderingStrategy.getOrderedProperties(propertyRepository);
		return buildDTOsFromProperties(properties);
	}
}
