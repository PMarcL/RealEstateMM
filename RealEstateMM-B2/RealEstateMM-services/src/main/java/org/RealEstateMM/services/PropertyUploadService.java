package org.RealEstateMM.services;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.services.dtos.property.PropertyAddressInformations;
import org.RealEstateMM.services.dtos.property.PropertyAddressInformationsAssembler;
import org.RealEstateMM.services.dtos.property.PropertyInformations;
import org.RealEstateMM.services.dtos.property.PropertyInformationsAssembler;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class PropertyUploadService { //TODO Change class name
	
	private PropertyRepository propertyRepository;
	private PropertyInformationsAssembler propertyAssembler;
	//private PropertyAddressInformationsAssembler addressAssembler;

	public PropertyUploadService() {
		propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		propertyAssembler = new PropertyInformationsAssembler();
		//addressAssembler  = new PropertyAddressInformationsAssembler();
	}

	public PropertyUploadService(PropertyRepository propertyRepository, PropertyInformationsAssembler propertyAssembler, PropertyAddressInformationsAssembler addressAssembler) {
		this.propertyRepository = propertyRepository;
		this.propertyAssembler = propertyAssembler;
		//this.addressAssembler = addressAssembler;
	}
	
	public void uploadProperty(PropertyInformations propertyInfos){
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		propertyRepository.add(newProperty);
	}
	
	public void editPropertyInfos(Property property){
		//TODO Implement
	}
}
