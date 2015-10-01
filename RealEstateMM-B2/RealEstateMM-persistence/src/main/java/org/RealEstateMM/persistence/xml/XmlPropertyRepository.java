package org.RealEstateMM.persistence.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyAddress;
import org.RealEstateMM.domain.property.PropertyRepository;

public class XmlPropertyRepository extends PropertyRepository {

	private XmlMarshaller marshaller;
	private XmlPropertyAssembler propertyAssembler;
	private XmlPropertyCollection propertyCache;

	public XmlPropertyRepository(XmlMarshaller marshaller, XmlPropertyAssembler propertyAssembler) {
		this.marshaller = marshaller;
		this.propertyAssembler = propertyAssembler;

		loadProperty();
	}

	private void loadProperty() {
		try {
			propertyCache = marshaller.unmarshal(XmlPropertyCollection.class);
		} catch (EmptyXmlFileException ex) {
			propertyCache = new XmlPropertyCollection();
		}
	}

	@Override
	public Optional<Property> getPropertyAtAddress(String streetAddress, String cityAddress) {
		if (isPropertyAbsentFromCache(streetAddress, cityAddress)){
			return Optional.empty();
		}
		XmlProperty xmlProperty = propertyCache.getProperty(streetAddress, cityAddress);
		Property property = propertyAssembler.toProperty(xmlProperty);
		return Optional.of(property);
	}

	private boolean isPropertyAbsentFromCache(String streetAddress, String cityAddress) {
		return !propertyCache.contains(streetAddress, cityAddress);
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
	public boolean contains(String streetAddress, String cityAddress){
		return propertyCache.contains(streetAddress, cityAddress);
	}

}
