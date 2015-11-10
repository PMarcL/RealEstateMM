package org.RealEstateMM.domain.property.onsale;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.junit.Before;
import org.junit.Test;

public class NumberOfOnSalePropertiesTest {

	private OnSaleProperties onSaleProperties;
	private NumberOfOnSaleProperties onSaleCount;
	private Property firstProperty;

	private static final int RETURNED_NUMBER_OF_HOUSES = 1;

	@Before
	public void Setup() {
		onSaleProperties = mock(OnSaleProperties.class);
		firstProperty = mock(Property.class);

	}

	@Test
	public void givenNoPropertiesGetMapTypeNumberOfPropertiesShouldHaveAllZeroValues() {
		given(onSaleProperties.findOnSalePropertiesByType(any(PropertyType.class)))
				.willReturn(new ArrayList<Property>());
		onSaleCount = new NumberOfOnSaleProperties(onSaleProperties);

		HashMap<String, Integer> mapOnSaleByType = onSaleCount.getMapTypeNumberOfProperties();

		assertEquals(PropertyType.values().length, mapOnSaleByType.values().stream().filter(p -> p == 0).count());
	}

	@Test
	public void givenAOnSaleHousegetMapTypeNumberOfPropertiesShouldMapToZerosAndAOneForHouses() {
		ArrayList<Property> properties = new ArrayList<Property>();
		given(firstProperty.getType()).willReturn(PropertyType.HOUSE);
		properties.add(firstProperty);
		given(onSaleProperties.findOnSalePropertiesByType(PropertyType.HOUSE)).willReturn(properties);
		onSaleCount = new NumberOfOnSaleProperties(onSaleProperties);

		HashMap<String, Integer> mapOnSaleByType = onSaleCount.getMapTypeNumberOfProperties();

		assertEquals(RETURNED_NUMBER_OF_HOUSES,
				mapOnSaleByType.get(PropertyType.getStringFromType(PropertyType.HOUSE)).intValue());
		assertEquals(PropertyType.values().length - 1, mapOnSaleByType.values().stream().filter(p -> p == 0).count());
	}

}
