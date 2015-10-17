package org.RealEstateMM.persistence.xml.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;

public class XmlPropertyRepository implements PropertyRepository {

	private XmlMarshaller marshaller;
	private XmlPropertyAssembler propertyAssembler;
	private XmlPropertyCollection propertyCache;

	public XmlPropertyRepository(XmlMarshaller marshaller, XmlPropertyAssembler propertyAssembler) {
		this.marshaller = marshaller;
		this.propertyAssembler = propertyAssembler;

		loadProperties();
	}

	private void loadProperties() {
		try {
			propertyCache = marshaller.unmarshal(XmlPropertyCollection.class);
		} catch (EmptyXmlFileException ex) {
			propertyCache = new XmlPropertyCollection();
		}
	}

	@Override
	public void add(Property newProperty) {
		XmlProperty xmlProperty = propertyAssembler.fromProperty(newProperty);
		propertyCache.add(xmlProperty);
		marshaller.marshal(XmlPropertyCollection.class, propertyCache);
	}

	@Override
	public ArrayList<Property> getAllProperties() {
		ArrayList<Property> properties = new ArrayList<Property>();
		List<XmlProperty> xmlProperties = propertyCache.getProperties();

		for (XmlProperty xmlProperty : xmlProperties) {
			properties.add(propertyAssembler.toProperty(xmlProperty));
		}
		return properties;
	}

	@Override
	public Optional<Property> getPropertyAtAddress(PropertyAddress address) {
		ArrayList<Property> properties = this.getAllProperties();

		for (Property property : properties) {
			if (property.getAddress().isEquals(address)) {
				return Optional.of(property);
			}
		}
		return Optional.empty();
	}

	@Override
	public void updateProperty(Property property) {
		propertyCache.removePropertyAtAddress(property.getAddress().streetAddress, property.getAddress().city);
		XmlProperty xmlProperty = propertyAssembler.fromProperty(property);
		propertyCache.add(xmlProperty);
		marshaller.marshal(XmlPropertyCollection.class, propertyCache);
	}

	@Override
	public ArrayList<Property> getPropertiesFromOwner(String owner) {
		ArrayList<Property> properties = this.getAllProperties();
		ArrayList<Property> propertiesOfOwner = new ArrayList<Property>();

		for (Property property : properties) {
			if (property.getOwner().equals(owner)) {
				propertiesOfOwner.add(property);
			}
		}
		return propertiesOfOwner;
	}

}
