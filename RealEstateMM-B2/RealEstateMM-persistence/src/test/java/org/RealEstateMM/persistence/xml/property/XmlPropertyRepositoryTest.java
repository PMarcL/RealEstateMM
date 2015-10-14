package org.RealEstateMM.persistence.xml.property;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.property.XmlProperty;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.RealEstateMM.persistence.xml.property.XmlPropertyCollection;
import org.RealEstateMM.persistence.xml.property.XmlPropertyRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class XmlPropertyRepositoryTest {

	private static final String A_STREET_ADDRESS = "123 fakestreet";
	private static final String A_STREET_CITTY = "Gotham";

	private XmlPropertyRepository repository;
	private XmlMarshaller marshaller;
	private XmlPropertyCollection propertyCollection;
	private XmlPropertyAssembler assembler;
	private XmlProperty xmlProperty;
	private PropertyAddress address;
	private Property property;

	@Before
	public void setup() {
		setupMocks();
		addPropertyWithAddress(A_STREET_ADDRESS, A_STREET_CITTY);

		repository = new XmlPropertyRepository(marshaller, assembler);
	}

	@Test
	public void givenEmptyXmlFileWhenCreatedShouldInitializeEmptyPropertyCollection() {
		given(marshaller.unmarshal(XmlPropertyCollection.class)).willThrow(new EmptyXmlFileException());
		repository = new XmlPropertyRepository(marshaller, assembler);
	}

	@Test
	public void givenANewRepositoryWhenAddedShoudAddToPropertyCollectionBeforeMarshalling() {
		Property property = mock(Property.class);

		repository.add(property);

		InOrder inOrder = inOrder(propertyCollection, marshaller);
		inOrder.verify(propertyCollection).add(xmlProperty);
		inOrder.verify(marshaller).marshal(XmlPropertyCollection.class, propertyCollection);
	}
	
	@Test
	public void givenExistingPropertyAddressWhenRetreivingItWithAddressShouldReturnCorrespondingProperty() {
		Property assembledProperty = mock(Property.class);
		given(assembler.toProperty(xmlProperty)).willReturn(assembledProperty);
		
		Optional<Property> returnedProperty = repository.getPropertyAtAddress(address);
		
		// TODO assertEquals(assembledProperty, returnedProperty.get());
	}

	@Test
	public void givenNonExistingPropertyAddressWhenRetreivingItWhithAddressShouldReturnEmptyResult() {
		Optional<Property> returnedProperty = repository.getPropertyAtAddress(address);

		assertFalse(returnedProperty.isPresent());
	}

	private void addPropertyWithAddress(String streetAddress, String cityAddress) {
		given(propertyCollection.contains(streetAddress, cityAddress)).willReturn(true);
		given(propertyCollection.getProperty(streetAddress, cityAddress)).willReturn(xmlProperty);
	}

	private void setupMocks() {
		xmlProperty = mock(XmlProperty.class);
		assembler = mock(XmlPropertyAssembler.class);
		given(assembler.fromProperty(any(Property.class))).willReturn(xmlProperty);
		propertyCollection = mock(XmlPropertyCollection.class);
		marshaller = mock(XmlMarshaller.class);
		given(marshaller.unmarshal(XmlPropertyCollection.class)).willReturn(propertyCollection);
		property = mock(Property.class);
	}
}
