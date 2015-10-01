package org.RealEstateMM.services;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.services.dtos.property.PropertyAddressAssembler;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyAssembler;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

import com.google.gson.Gson;

public class PropertyService {

	private PropertyRepository propertyRepository;
	private PropertyAssembler propertyAssembler;

	public PropertyService() {
		propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		propertyAssembler = new PropertyAssembler();
	}

	public PropertyService(PropertyRepository propertyRepository, PropertyAssembler propertyAssembler,
			PropertyAddressAssembler addressAssembler) {
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
	
	public String getAllProperties()
	{
		Gson gson = new Gson();

		String json = gson.toJson(propertyRepository.getAllProperties());
		return json;
	}
}
