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
		
		for (Property property : properties){
			if (property.getAddress().isEquals(address)){
				return Optional.of(property);
			}
		}
		return Optional.empty();
	}

	private boolean isPropertyAbsentFromCache(String streetAddress, String cityAddress) {
		return !propertyCache.contains(streetAddress, cityAddress);
	}

	@Override
	public void updateProperty(Property property) {
		// TODO Auto-generated method stub
		if (getPropertyAtAddress(property.getAddress()).isPresent()){
			Property propertyFromRepository = getPropertyAtAddress(property.getAddress()).get();
			propertyFromRepository.updateFeatures(property.getFeatures());
			//TODO finish
			//Probably need to modify assembler and the marshaller to rewrite XML tags.
		}
	}

	@Override
	public ArrayList<Property> getPropertiesFromOwner(String owner) {
		ArrayList<Property> properties = this.getAllProperties();
		ArrayList<Property> propertiesOfOwner = new ArrayList<Property>();
		
		for (Property property : properties){
			if (property.getOwner().equals(owner)){
				propertiesOfOwner.add(property);
			}
		}
		return propertiesOfOwner;
	}

}
