package org.RealEstateMM.persistence.xml.property;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class XmlPropertyAssembler {

	public XmlProperty fromProperty(Property property) {
		XmlProperty newProperty = new XmlProperty();
		PropertyAddress propertyAddress = property.getAddress();
		PropertyFeatures propertyFeatures = property.getFeatures();

		newProperty.setType(PropertyType.getStringFromType(property.getType()));
		newProperty.setStreetAddress(propertyAddress.streetAddress);
		newProperty.setCityAddress(propertyAddress.city);
		newProperty.setProvinceAddress(propertyAddress.province);
		newProperty.setZipCodeAddress(propertyAddress.zipCode);
		newProperty.setPrice(String.valueOf(property.getPrice()));
		newProperty.setOwnerUserName(property.getOwner());
		newProperty.setStatus(PropertyStatus.getStringFromStatus(property.getPropertyStatus()));
		
		newProperty.setNumberOfBathrooms(String.valueOf(propertyFeatures.numberOfBathrooms));
		newProperty.setNumberOfBedrooms(String.valueOf(propertyFeatures.numberOfBedrooms));
		newProperty.setTotalNumberOfRooms(String.valueOf(propertyFeatures.totalNumberOfRooms));
		newProperty.setNumberOfLevel(String.valueOf(propertyFeatures.numberOfLevel));
		newProperty.setLotDimension(String.valueOf(propertyFeatures.lotDimension));
		newProperty.setYearOfConstruction(String.valueOf(propertyFeatures.yearOfConstruction));
		newProperty.setLivingSpaceArea(String.valueOf(propertyFeatures.livingSpaceArea));
		newProperty.setBackyardDirection(String.valueOf(propertyFeatures.backyardDirection));

		return newProperty;
	}

	public Property toProperty(XmlProperty xmlProperty) {
		PropertyAddress propertyAddress = new PropertyAddress(xmlProperty.getStreetAddress(),
				xmlProperty.getCityAddress(), xmlProperty.getProvinceAddress(), xmlProperty.getZipCodeAddress());
		PropertyType type = PropertyType.getTypeFromString(xmlProperty.getType());
		PropertyStatus status = PropertyStatus.getStatusFromString(xmlProperty.getStatus());
		
		PropertyFeatures propertyFeatures = new PropertyFeatures(Integer.parseInt(xmlProperty.getNumberOfBathrooms()),
																 Integer.parseInt(xmlProperty.getNumberOfBedrooms()), 
																 Integer.parseInt(xmlProperty.getTotalNumberOfRooms()),
																 Integer.parseInt(xmlProperty.getNumberOfLevel()), 
																 Double.parseDouble(xmlProperty.getLotDimension()),
																 Integer.parseInt(xmlProperty.getYearOfConstruction()), 
																 Double.parseDouble(xmlProperty.getLivingSpaceArea()), 
																 xmlProperty.getBackyardDirection());

		Property property = new Property(type, propertyAddress, Double.parseDouble(xmlProperty.getPrice()),
				xmlProperty.getOwnerUserName(), status);
		property.updateFeatures(propertyFeatures);
		return property;
	}
}
