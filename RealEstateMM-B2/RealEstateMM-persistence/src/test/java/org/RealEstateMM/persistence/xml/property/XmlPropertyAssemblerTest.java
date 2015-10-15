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

	private static final int A_NUMBER_OF_ROOMS = 2;
	private static final int A_NUMBER_OF_DEBROOMS = 3;
	private static final int A_TOTAL_NUMBER_OF_ROOMS = 7;
	private static final int A_NUMBER_OF_LEVEL = 2;
	private static final double A_LOT_DIMENSION = 2000.00;
	private static final int A_YEAR_OF_CONSTRUCTION = 2000;
	private static final double A_LIVING_SPACE_AREA = 1000.00;
	private static final String A_BACKYARD_DIRECTION = "WEST";
	
	private static final PropertyType A_TYPE = PropertyType.HOUSE;
	private static final Double A_PRICE = 200000.0;
	private static final Double DELTA = 0.001;
	private static final String A_OWNER_NAME = "Joe";
	private static final PropertyStatus A_STATUS = PropertyStatus.ONSALE;
	private static final String A_STREETADDRESS = "123, FakeStreet";
	private static final String A_CITY = "Gotham";
	private static final String A_PROVINCE = "Quebec";
	private static final String A_ZIPCODE = "G6V0A9";

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
		
		xmlProperty.setNumberOfBathrooms(Integer.toString(A_NUMBER_OF_ROOMS));
		xmlProperty.setNumberOfBedrooms(Integer.toString(A_NUMBER_OF_DEBROOMS));
		xmlProperty.setTotalNumberOfRooms(Integer.toString(A_TOTAL_NUMBER_OF_ROOMS));
		xmlProperty.setNumberOfLevel(Integer.toString(A_NUMBER_OF_LEVEL));
		xmlProperty.setLotDimension(Double.toString(A_LOT_DIMENSION));
		xmlProperty.setYearOfConstruction(Integer.toString(A_YEAR_OF_CONSTRUCTION));
		xmlProperty.setLivingSpaceArea(Double.toString(A_LIVING_SPACE_AREA));
		xmlProperty.setBackyardDirection(A_BACKYARD_DIRECTION);
	}
}
