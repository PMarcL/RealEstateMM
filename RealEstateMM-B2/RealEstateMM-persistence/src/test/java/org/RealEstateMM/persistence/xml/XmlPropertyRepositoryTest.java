package org.RealEstateMM.persistence.xml;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
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

	@Before
	public void init(){
		setupMocks();
		addPropertyWithAddress(A_STREET_ADDRESS, A_STREET_CITTY);
		
		repository = new XmlPropertyRepository(marshaller, assembler);
	}
	
	@Test
	public void unmardhallPropertyCollectionWhenCreated(){
		assertTrue(repository.contains(A_STREET_ADDRESS, A_STREET_CITTY));
	}
	
	@Test
	public void givenEmptyXmlFileWhenCreatedShouldInitializeEmptyPropertyCollection(){
		given(marshaller.unmarshal(XmlPropertyCollection.class)).willThrow(new EmptyXmlFileException());
		repository = new XmlPropertyRepository(marshaller, assembler);
		assertFalse(repository.contains(A_STREET_ADDRESS, A_STREET_CITTY));
	}
	
	@Test
	public void givenANewRepositoryWhenAddedShoudAddToPropertyCollectionBeforeMarshalling(){
		Property property = mock(Property.class);
		
		repository.add(property);
		
		InOrder inOrder = inOrder(propertyCollection, marshaller);
		inOrder.verify(propertyCollection).add(xmlProperty);
		inOrder.verify(marshaller).marshal(XmlPropertyCollection.class, propertyCollection);
	}
	
	@Test
	public void givenExistingPropertyAddressWhenRetreivingItWithAddressShouldReturnCorrespondingProperty(){
		Property assembledProperty = mock(Property.class);
		given(assembler.toProperty(xmlProperty)).willReturn(assembledProperty);
	
		Optional<Property> returnedProperty = repository.getPropertyAtAddress(A_STREET_ADDRESS, A_STREET_CITTY);
		
		assertSame(assembledProperty, returnedProperty.get());
	}
	
	@Test
	public void givenNonExistingPropertyAddressWhenRetreivingItWhithAddressShouldReturnEmptyResult(){
		final String NON_EXISTING_STREET_ADDRESS = "666 Highway to Hell";
		final String NON_EXISTING_CITY_ADRESS = "Hell";
		
		Optional<Property> returnedProperty = repository.getPropertyAtAddress(NON_EXISTING_STREET_ADDRESS, NON_EXISTING_CITY_ADRESS);
		
		assertFalse(returnedProperty.isPresent());
	}
	
	private void addPropertyWithAddress(String streetAddress, String cityAddress){
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
	}
}
