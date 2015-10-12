package org.RealEstateMM.persistence.xml.property;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class XmlPropertyAssembler {

	public XmlProperty fromProperty(Property property) {
		XmlProperty newProperty = new XmlProperty();
		PropertyAddress propertyAddress = property.getAddress();

		newProperty.setType(PropertyType.getStringFromType(property.getType()));
		newProperty.setStreetAddress(propertyAddress.streetAddress);
		newProperty.setCityAddress(propertyAddress.city);
		newProperty.setProvinceAddress(propertyAddress.province);
		newProperty.setZipCodeAddress(propertyAddress.zipCode);
		newProperty.setPrice(String.valueOf(property.getPrice()));
		newProperty.setOwnerUserName(property.getOwner());
		newProperty.setStatus(PropertyStatus.getStringFromStatus(property.getPropertyStatus()));

		return newProperty;
	}

	public Property toProperty(XmlProperty xmlProperty) {
		PropertyAddress propertyAddress = new PropertyAddress(xmlProperty.getSteetAddress(),
				xmlProperty.getCityAddress(), xmlProperty.getProvinceAddress(), xmlProperty.getZipCodeAddress());
		PropertyType type = PropertyType.getTypeFromString(xmlProperty.getType());
		PropertyStatus status = PropertyStatus.getStatusFromString(xmlProperty.getStatus());

		Property property = new Property(type, propertyAddress, Double.parseDouble(xmlProperty.getPrice()),
				xmlProperty.getOwnerUserName(), status);
		return property;
	}
}
