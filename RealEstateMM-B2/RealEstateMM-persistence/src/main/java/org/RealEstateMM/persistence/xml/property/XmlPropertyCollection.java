package org.RealEstateMM.persistence.xml.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "properties")
public class XmlPropertyCollection {

	private List<XmlUser> properties;

	public XmlPropertyCollection() {
		properties = new ArrayList<XmlUser>();
	}

	public boolean contains(String streetAddress, String cityAddress) {
		Optional<XmlUser> foundProperty = find(streetAddress, cityAddress);
		return foundProperty.isPresent();
	}

	private Optional<XmlUser> find(String streetAddress, String cityAddress) {
		for (XmlUser property : properties) {
			if ((property.getStreetAddress().equals(streetAddress) && property.getCityAddress().equals(cityAddress))) {
				return Optional.of(property);
			}
		}
		return Optional.empty();
	}

	public void add(XmlUser newProperty) {
		properties.add(newProperty);
	}

	public void removePropertyAtAddress(String streetAddress, String cityAddress) {
		Optional<XmlUser> property = find(streetAddress, cityAddress);
		properties.remove(property.get());
	}

	public XmlUser getProperty(String streetAddress, String cityAddress) {
		Optional<XmlUser> foundProperty = find(streetAddress, cityAddress);
		return foundProperty.get();
	}

	public List<XmlUser> getProperties() {
		return properties;
	}

	@XmlElement(name = "property")
	public void setProperties(List<XmlUser> properties) {
		this.properties = properties;
	}

}
