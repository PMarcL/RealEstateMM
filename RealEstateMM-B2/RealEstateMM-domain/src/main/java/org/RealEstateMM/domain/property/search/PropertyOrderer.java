package org.RealEstateMM.domain.property.search;

import java.util.ArrayList;
import java.util.Collections;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;

public class PropertyOrderer {
	//TODO Léandre: Peut-être changer le nom de la class/des fonctions.
	
	private PropertyRepository propertyRepository;

	public PropertyOrderer(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	public ArrayList<Property> OrderBy(PropertySearchParameters searchParameter) {
		if(searchParameter == PropertySearchParameters.RECENTLY_UPLOADED_FIRST){
			return getPropertiesOrderedByRecentlyUploadedFirst();
		}
		
		return propertyRepository.getAllProperties(); //Default is recently uploaded last
	}

	private ArrayList<Property> getPropertiesOrderedByRecentlyUploadedFirst() {
		ArrayList<Property> properties = propertyRepository.getAllProperties();
		Collections.reverse(properties);
		
		return properties;
	}

}
