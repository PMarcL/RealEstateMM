package org.RealEstateMM.persistence.xml.property;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyAddress;
import org.RealEstateMM.persistence.xml.property.XmlProperty;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.junit.Before;
import org.junit.Test;

public class XmlPropertyAssemblerTest {

	private XmlPropertyAssembler assembler;
	private XmlProperty xmlProperty;
	private Property property;

	private static final String A_TYPE = "type1";
	private static final String A_PRICE = "200000.0";
	private static final String A_OWNER_NAME = "Joe";
	private static final String A_STATUS = "on sale";
	private static final String A_STREETADDRESS = "123, FakeStreet";
	private static final String A_CITY = "Gotham";
	private static final String A_PROVINCE = "Quebec";
	private static final String A_ZIPCODE = "G6V0A9";

	@Before
	public void init() {
		assembler = new XmlPropertyAssembler();
	}

	@Test
	public void givenAPropertyWhenAssemblingXmlPropertyThenXmlPropertyHasIdenticalFields() {
		createProperty();

		XmlProperty result = assembler.fromProperty(property);

		assertEquals(A_TYPE, result.getType());
		assertEquals(A_PRICE, result.getPrice());
		assertEquals(A_OWNER_NAME, result.getOwnerUserName());
		assertEquals(A_STATUS, result.getStatus());
		assertEquals(A_STREETADDRESS, result.getSteetAddress());
		assertEquals(A_CITY, result.getCityAddress());
		assertEquals(A_PROVINCE, result.getProvinceAddress());
		assertEquals(A_ZIPCODE, result.getZipCodeAddress());
	}

	@Test
	public void givenAXmlPropertyWhenAssemblingPropertyFromXmlPropertyThenPropertyHasIdenticalFields() {
		createXmlProperty();
		Property result = assembler.toProperty(xmlProperty);
		PropertyAddress resultAddress = result.propertyAddress;

		assertEquals(A_TYPE, result.propertyType);
		assertEquals(A_PRICE, String.valueOf(result.propertyPrice));
		assertEquals(A_OWNER_NAME, result.propertyOwner);
		assertEquals(A_STATUS, result.propertyStatus);
		assertEquals(A_STREETADDRESS, resultAddress.streetAddress);
		assertEquals(A_CITY, resultAddress.city);
		assertEquals(A_PROVINCE, resultAddress.province);
		assertEquals(A_ZIPCODE, resultAddress.zipCode);
	}

	private void createProperty() {
		PropertyAddress address = new PropertyAddress(A_STREETADDRESS, A_CITY, A_PROVINCE, A_ZIPCODE);
		property = new Property(A_TYPE, address, Double.parseDouble(A_PRICE), A_OWNER_NAME, A_STATUS);
	}

	private void createXmlProperty() {
		xmlProperty = new XmlProperty();
		xmlProperty.setType(A_TYPE);
		xmlProperty.setPrice(String.valueOf(A_PRICE));
		xmlProperty.setOwnerUserName(A_OWNER_NAME);
		xmlProperty.setStatus(A_STATUS);
		xmlProperty.setStreetAddress(A_STREETADDRESS);
		xmlProperty.setCityAddress(A_CITY);
		xmlProperty.setProvinceAddress(A_PROVINCE);
		xmlProperty.setZipCodeAddress(A_ZIPCODE);
	}
}
