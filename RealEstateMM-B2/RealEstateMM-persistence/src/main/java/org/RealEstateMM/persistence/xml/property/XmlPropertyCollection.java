package org.RealEstateMM.persistence.xml.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "properties")
public class XmlPropertyCollection {

	private List<XmlProperty> properties;
	
	public XmlPropertyCollection(){
		properties = new ArrayList<XmlProperty>();
	}
	
	public boolean contains(String streetAddress, String cityAddress) {
		Optional<XmlProperty> foundProperty = find(streetAddress, cityAddress);
		return foundProperty.isPresent();
	}

	private Optional<XmlProperty> find(String streetAddress, String cityAddress) {
		for (XmlProperty property : properties) {
			if ((property.getSteetAddress().equals(streetAddress) && property.getCityAddress().equals(cityAddress))) {
				return Optional.of(property);
			}
		}
		return Optional.empty();
	}

	public void add(XmlProperty newProperty) {
		properties.add(newProperty);
	}

	public XmlProperty getProperty(String streetAddress, String cityAddress) {
		Optional<XmlProperty> foundProperty = find(streetAddress, cityAddress);
		return foundProperty.get();
	}

	public List<XmlProperty> getProperties() {
		return properties;
	}

	@XmlElement(name = "property")
	public void setProperties(List<XmlProperty> properties) {
		this.properties = properties;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
