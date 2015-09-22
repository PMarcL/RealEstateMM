package org.RealEstateMM.services;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.services.dto.PropertyInformations;
import org.RealEstateMM.services.dto.PropertyInformationsAssembler;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class PropertyUploadService { //TODO Change class name
	
	private PropertyRepository propertyRepository;
	private PropertyInformationsAssembler assembler;

	public PropertyUploadService() {
		propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		assembler = new PropertyInformationsAssembler();
	}

	public PropertyUploadService(PropertyRepository propertyRepository, PropertyInformationsAssembler assembler) {
		this.propertyRepository = propertyRepository;
		this.assembler = assembler;
	}
	
	public void uploadProperty(PropertyInformations propertyInfos){
		Property newProperty = assembler.fromDTO(propertyInfos);
		propertyRepository.add(newProperty);
	}
	
	public void editPropertyInfos(Property property){
		//TODO Implement
	}
}
