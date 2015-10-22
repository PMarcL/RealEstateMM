package org.RealEstateMM.persistence.xml.property;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.XmlMarshallingException;
import org.RealEstateMM.persistence.xml.property.XmlProperty;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.RealEstateMM.persistence.xml.property.XmlPropertyCollection;
import org.RealEstateMM.persistence.xml.property.XmlPropertyRepository;
import org.RealEstateMM.persistence.xml.user.XmlUserCollection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class XmlPropertyRepositoryTest {

	private XmlMarshaller marshaller;
	private XmlPropertyCollection propertyCollection;
	private XmlPropertyAssembler assembler;
	private XmlProperty xmlProperty;
	private PropertyAddress address;

	private XmlPropertyRepository repository;

	@Before
	public void setup() {
		setupMocks();

		repository = new XmlPropertyRepository(marshaller, assembler);
	}

	@Test
	public void givenEmptyXmlFileWhenCreatedThenInitializeEmptyPropertyCollection() {
		given(marshaller.unmarshal(XmlPropertyCollection.class)).willThrow(new EmptyXmlFileException());
		repository = new XmlPropertyRepository(marshaller, assembler);
	}

	@Test
	public void givenXmlFileWithNoPropertyWhenCreatedThenInitializeEmptyPropertyCollection() {
		given(marshaller.unmarshal(XmlUserCollection.class)).willThrow(new XmlMarshallingException(null));
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
	public void givenNonExistingPropertyAddressWhenRetreivingItWhithAddressShouldReturnEmptyResult() {
		Optional<Property> returnedProperty = repository.getPropertyAtAddress(address);

		assertFalse(returnedProperty.isPresent());
	}

	private void setupMocks() {
		xmlProperty = mock(XmlProperty.class);
		assembler = mock(XmlPropertyAssembler.class);
		given(assembler.fromProperty(any(Property.class))).willReturn(xmlProperty);
		propertyCollection = mock(XmlPropertyCollection.class);
		marshaller = mock(XmlMarshaller.class);
		given(marshaller.unmarshal(XmlPropertyCollection.class)).willReturn(propertyCollection);
	}
}
