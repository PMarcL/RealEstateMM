package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.junit.Before;
import org.junit.Test;

public class PropertySearchParametersFactoryTest {
	private final String ORDER_BY = "orderBy";
	private final String PROPERTY_TYPES = "propertyTypes";
	private final String MIN_NUM_BEDROOMS = "minNumBedrooms";
	private final String ORDER_BY_VAL = "recently_uploaded_first";
	private final List<String> PROPERTY_TYPES_VAL = new ArrayList<String>();
	private final String MIN_NUM_BEDROOMS_VAL = "2";

	private UriInfo searchParam;
	private MultivaluedMap<String, String> queryParams;
	private PropertySearchParametersFactory factory;

	@Before
	@SuppressWarnings("unchecked")
	public void setup() {
		factory = new PropertySearchParametersFactory();

		searchParam = mock(UriInfo.class);
		queryParams = mock(MultivaluedMap.class);
		configureUriInfoMock();
	}

	@Test
	public void givenAUriInfoWhenGetSearchParametersThenGetInfoFromUriInfo() {
		factory.getSearchParametersDTO(searchParam);

		verify(searchParam, times(3)).getQueryParameters();
		verify(queryParams).getFirst(ORDER_BY);
		verify(queryParams).get(PROPERTY_TYPES);
		verify(queryParams).getFirst(MIN_NUM_BEDROOMS);
	}

	@Test
	public void givenAUriInfoWhenGetSearchParametersThenReturnsDTOWithCorrectInfo() {
		PropertySearchParametersDTO result = factory.getSearchParametersDTO(searchParam);

		assertEquals(ORDER_BY_VAL, result.getOrderBy());
		assertEquals(PROPERTY_TYPES_VAL, result.getPropertyTypes());
		assertEquals(Integer.parseInt(MIN_NUM_BEDROOMS_VAL), result.getMinNumBedrooms());
	}

	private void configureUriInfoMock() {
		given(searchParam.getQueryParameters()).willReturn(queryParams);
		given(queryParams.getFirst(ORDER_BY)).willReturn(ORDER_BY_VAL);
		given(queryParams.get(PROPERTY_TYPES)).willReturn(PROPERTY_TYPES_VAL);
		given(queryParams.getFirst(MIN_NUM_BEDROOMS)).willReturn(MIN_NUM_BEDROOMS_VAL);
	}
}
