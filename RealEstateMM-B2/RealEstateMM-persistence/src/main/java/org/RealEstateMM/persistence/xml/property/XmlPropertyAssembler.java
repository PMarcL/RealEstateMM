package org.RealEstateMM.persistence.xml.property;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyAddress;

public class XmlPropertyAssembler {

	public XmlProperty fromProperty(Property property) {
		XmlProperty newProperty = new XmlProperty();
		PropertyAddress propertyAddress = property.propertyAddress;

		newProperty.setType(property.propertyType);
		newProperty.setStreetAddress(propertyAddress.streetAddress);
		newProperty.setCityAddress(propertyAddress.city);
		newProperty.setProvinceAddress(propertyAddress.province);
		newProperty.setZipCodeAddress(propertyAddress.zipCode);
		newProperty.setPrice(String.valueOf(property.propertyPrice));
		newProperty.setOwnerUserName(property.propertyOwner);
		newProperty.setStatus(property.propertyStatus);

		return newProperty;
	}

	public Property toProperty(XmlProperty xmlProperty) {
		PropertyAddress propertyAddress = new PropertyAddress(xmlProperty.getSteetAddress(),
				xmlProperty.getCityAddress(), xmlProperty.getProvinceAddress(), xmlProperty.getZipCodeAddress());
		Property property = new Property(xmlProperty.getType(), propertyAddress,
				Double.parseDouble(xmlProperty.getPrice()), xmlProperty.getOwnerUserName(), xmlProperty.getStatus());
		return property;
	}
}
