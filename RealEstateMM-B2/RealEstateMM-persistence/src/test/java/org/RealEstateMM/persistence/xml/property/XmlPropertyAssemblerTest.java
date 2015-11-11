package org.RealEstateMM.persistence.xml.property;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.junit.Before;
import org.junit.Test;

public class XmlPropertyAssemblerTest {

	private final int A_NUMBER_OF_ROOMS = 2;
	private final int A_NUMBER_OF_DEBROOMS = 3;
	private final int A_TOTAL_NUMBER_OF_ROOMS = 7;
	private final int A_NUMBER_OF_LEVEL = 2;
	private final double A_LOT_DIMENSION = 2000.00;
	private final int A_YEAR_OF_CONSTRUCTION = 2000;
	private final double A_LIVING_SPACE_AREA = 1000.00;
	private final String A_BACKYARD_DIRECTION = "WEST";

	private final PropertyType A_TYPE = PropertyType.HOUSE;
	private final Double A_PRICE = 200000.0;
	private final Double DELTA = 0.001;
	private final String A_OWNER_NAME = "Joe";
	private final PropertyStatus A_STATUS = PropertyStatus.ON_SALE;
	private final String A_STREETADDRESS = "123, FakeStreet";
	private final String A_CITY = "Gotham";
	private final String A_PROVINCE = "Quebec";
	private final String A_ZIPCODE = "G6V0A9";
	private final String A_CREATION_DATE = "2015-10-31-01:30:05";
	private final String A_SALE_DATE = "2015-10-31-01:30:05";

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
		assertEquals(A_CREATION_DATE, result.getCreationDate());
		assertEquals(A_SALE_DATE, result.getSaleDate());
	}

	private void createProperty() {
		PropertyAddress address = new PropertyAddress(A_STREETADDRESS, A_CITY, A_PROVINCE, A_ZIPCODE);
		property = new Property(A_TYPE, address, A_PRICE, A_OWNER_NAME, A_STATUS);
		try {
			property.setCreationDate(DateUtil.parseDate(A_CREATION_DATE));
			property.setSaleDate(DateUtil.parseDate(A_SALE_DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenAXmlPropertyWhenAssemblingPropertyFromXmlPropertyThenPropertyHasIdenticalFields() {
		createXmlProperty();
		Property result = assembler.toProperty(xmlProperty);
		PropertyAddress resultAddress = result.getAddress();

		assertEquals(A_TYPE, result.getType());
		assertEquals(A_PRICE, result.getPrice(), DELTA);
		assertEquals(A_OWNER_NAME, result.getOwner());
		assertEquals(A_STATUS, result.getStatus());
		assertEquals(A_STREETADDRESS, resultAddress.streetAddress);
		assertEquals(A_CITY, resultAddress.city);
		assertEquals(A_PROVINCE, resultAddress.province);
		assertEquals(A_ZIPCODE, resultAddress.zipCode);
		assertEquals(A_CREATION_DATE, DateUtil.formatDate(result.getCreationDate()));
		assertEquals(A_SALE_DATE, DateUtil.formatDate(result.getSaleDate()));
	}

	private void createXmlProperty() {
		xmlProperty = new XmlProperty();
		xmlProperty.setType(PropertyType.getStringFromType(A_TYPE));
		xmlProperty.setPrice(String.valueOf(A_PRICE));
		xmlProperty.setOwnerUserName(A_OWNER_NAME);
		xmlProperty.setCreationDate(A_CREATION_DATE);
		xmlProperty.setSaleDate(A_SALE_DATE);
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
