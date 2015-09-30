package org.RealEstateMM.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.user.User;


public class InMemoryPropertyRepository implements org.RealEstateMM.domain.property.PropertyRepository{
	
	private Map<String, Property> properties;
	
	public InMemoryPropertyRepository(){
		properties = new HashMap<String, Property>();
	}
	
	@Override
	public Optional<Property> getPropertyAtAddress(PropertyAddress propertyAddress){
		if(!propertyExist(propertyAddress)){
			return Optional.empty();
		}
		return Optional.of(properties.get(propertyAddress.toString()));
	}
	
	private boolean propertyExist(PropertyAddress propertyAddress){
		return properties.containsKey(propertyAddress.toString());
	}
	
	@Override
	public void add(Property property){
//		if(propertyExist(property.propertyAddress)){
//			throw new PropertyAlreadyAddedException();
//		}
		//properties.put("Hey", property);
		properties.put(property.propertyAddress.toString(), property);
	}

	public int getSize() {
		return properties.size();
	}
}
