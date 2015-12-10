package org.RealEstateMM.persistence.xml.property;

import java.text.ParseException;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.persistence.xml.InvalidXmlFileException;
import org.RealEstateMM.persistence.xml.util.DateUtil;

public class XmlPropertyAssembler {

	public XmlProperty fromProperty(Property property) {
		XmlProperty newProperty = new XmlProperty();
		PropertyAddress propertyAddress = property.getAddress();
		PropertyFeatures propertyFeatures = property.getFeatures();

		newProperty.setType(property.getType().toString());
		newProperty.setCreationDate(DateUtil.formatDate(property.getCreationDate()));
		newProperty.setSaleDate(DateUtil.formatDate(property.getSaleDate()));
		newProperty.setStreetAddress(propertyAddress.streetAddress);
		newProperty.setCityAddress(propertyAddress.city);
		newProperty.setProvinceAddress(propertyAddress.province);
		newProperty.setZipCodeAddress(propertyAddress.zipCode);
		newProperty.setPrice(String.valueOf(property.getPrice()));
		newProperty.setOwnerUserName(property.getOwner());
		newProperty.setStatus(property.getStatus().toString());

		newProperty.setNumberOfBathrooms(String.valueOf(propertyFeatures.numberOfBathrooms));
		newProperty.setNumberOfBedrooms(String.valueOf(propertyFeatures.numberOfBedrooms));
		newProperty.setTotalNumberOfRooms(String.valueOf(propertyFeatures.totalNumberOfRooms));
		newProperty.setNumberOfLevel(String.valueOf(propertyFeatures.numberOfLevel));
		newProperty.setLotDimension(String.valueOf(propertyFeatures.lotDimension));
		newProperty.setYearOfConstruction(String.valueOf(propertyFeatures.yearOfConstruction));
		newProperty.setLivingSpaceArea(String.valueOf(propertyFeatures.livingSpaceArea));
		newProperty.setBackyardDirection(propertyFeatures.backyardDirection);
		newProperty.setDescription(propertyFeatures.description);

		return newProperty;
	}

	public Property toProperty(XmlProperty xmlProperty) {
		PropertyType type = PropertyType.fromString(xmlProperty.getType());
		PropertyStatus status = PropertyStatus.fromString(xmlProperty.getStatus());

		PropertyAddress propertyAddress = createPropertyAddress(xmlProperty);
		PropertyFeatures propertyFeatures = createPropertyFeatures(xmlProperty);

		Property property = new Property(type, propertyAddress, Double.parseDouble(xmlProperty.getPrice()),
				xmlProperty.getOwnerUserName(), status);

		property.updateFeatures(propertyFeatures);
		setPropertyCreationDate(xmlProperty, property);
		setPropertySaleDate(xmlProperty, property);

		return property;
	}

	private void setPropertySaleDate(XmlProperty xmlProperty, Property property) {
		try {
			property.setSaleDate(DateUtil.parseDate(xmlProperty.getSaleDate()));
		} catch (ParseException e) {
			throw new InvalidXmlFileException();
		}
	}

	private void setPropertyCreationDate(XmlProperty xmlProperty, Property property) {
		try {
			property.setCreationDate(DateUtil.parseDate(xmlProperty.getCreationDate()));
		} catch (ParseException e) {
			throw new InvalidXmlFileException();
		}
	}

	private PropertyAddress createPropertyAddress(XmlProperty xmlProperty) {
		PropertyAddress propertyAddress = new PropertyAddress(xmlProperty.getStreetAddress(),
				xmlProperty.getCityAddress(), xmlProperty.getProvinceAddress(), xmlProperty.getZipCodeAddress());
		return propertyAddress;
	}

	private PropertyFeatures createPropertyFeatures(XmlProperty xmlProperty) {
		PropertyFeatures propertyFeatures = new PropertyFeatures(Integer.parseInt(xmlProperty.getNumberOfBathrooms()),
				Integer.parseInt(xmlProperty.getNumberOfBedrooms()),
				Integer.parseInt(xmlProperty.getTotalNumberOfRooms()), Integer.parseInt(xmlProperty.getNumberOfLevel()),
				Double.parseDouble(xmlProperty.getLotDimension()),
				Integer.parseInt(xmlProperty.getYearOfConstruction()),
				Double.parseDouble(xmlProperty.getLivingSpaceArea()), xmlProperty.getBackyardDirection(),
				xmlProperty.getDescription());
		return propertyFeatures;
	}

}
