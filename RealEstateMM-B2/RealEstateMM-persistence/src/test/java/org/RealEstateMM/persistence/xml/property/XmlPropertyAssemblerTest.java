package org.RealEstateMM.persistence.xml.property;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.persistence.xml.property.XmlProperty;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.junit.Before;
import org.junit.Test;

public class XmlPropertyAssemblerTest {

	private final PropertyType A_TYPE = PropertyType.HOUSE;
	private final Double A_PRICE = 200000.0;
	private final Double DELTA = 0.001;
	private final String A_OWNER_NAME = "Joe";
	private final PropertyStatus A_STATUS = PropertyStatus.ONSALE;
	private final String A_STREETADDRESS = "123, FakeStreet";
	private final String A_CITY = "Gotham";
	private final String A_PROVINCE = "Quebec";
	private final String A_ZIPCODE = "G6V0A9";

	private XmlPropertyAssembler assembler;
	private XmlProperty xmlProperty;
	private Property property;

	@Before
	public void init() {
		assembler = new XmlPropertyAssembler();
	}

	@Test
	public void givenAPropertyWhenAssemblingXmlPropertyThenXmlPropertyHasIdenticalFields() {
		createProperty();
		XmlProperty result = assembler.fromProperty(property);

		assertEquals(A_TYPE, PropertyType.getTypeFromString(result.getType()));
		assertEquals(A_PRICE, Double.parseDouble(result.getPrice()), DELTA);
		assertEquals(A_OWNER_NAME, result.getOwnerUserName());
		assertEquals(A_STATUS, PropertyStatus.getStatusFromString(result.getStatus()));
		assertEquals(A_STREETADDRESS, result.getStreetAddress());
		assertEquals(A_CITY, result.getCityAddress());
		assertEquals(A_PROVINCE, result.getProvinceAddress());
		assertEquals(A_ZIPCODE, result.getZipCodeAddress());
	}

	@Test
	public void givenAXmlPropertyWhenAssemblingPropertyFromXmlPropertyThenPropertyHasIdenticalFields() {
		createXmlProperty();
		Property result = assembler.toProperty(xmlProperty);
		PropertyAddress resultAddress = result.getAddress();

		assertEquals(A_TYPE, result.getType());
		assertEquals(A_PRICE, result.getPrice(), DELTA);
		assertEquals(A_OWNER_NAME, result.getOwner());
		assertEquals(A_STATUS, result.getPropertyStatus());
		assertEquals(A_STREETADDRESS, resultAddress.streetAddress);
		assertEquals(A_CITY, resultAddress.city);
		assertEquals(A_PROVINCE, resultAddress.province);
		assertEquals(A_ZIPCODE, resultAddress.zipCode);
	}

	private void createProperty() {
		PropertyAddress address = new PropertyAddress(A_STREETADDRESS, A_CITY, A_PROVINCE, A_ZIPCODE);
		property = new Property(A_TYPE, address, A_PRICE, A_OWNER_NAME, A_STATUS);
	}

	private void createXmlProperty() {
		xmlProperty = new XmlProperty();
		xmlProperty.setType(PropertyType.getStringFromType(A_TYPE));
		xmlProperty.setPrice(String.valueOf(A_PRICE));
		xmlProperty.setOwnerUserName(A_OWNER_NAME);
		xmlProperty.setStatus(PropertyStatus.getStringFromStatus(A_STATUS));
		xmlProperty.setStreetAddress(A_STREETADDRESS);
		xmlProperty.setCityAddress(A_CITY);
		xmlProperty.setProvinceAddress(A_PROVINCE);
		xmlProperty.setZipCodeAddress(A_ZIPCODE);
	}
}
