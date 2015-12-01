package org.RealEstateMM.restapi.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.RealEstateMM.domain.search.PropertyOrderingParameters;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.search.InvalidSearchParameterException;
import org.junit.Before;
import org.junit.Test;

public class PropertySearchParametersFactoryTest {
	private final String ORDER_BY = "orderBy";
	private final String PROPERTY_TYPES = "propertyTypes";
	private final String MIN_NUM_BEDROOMS = "minNumBedrooms";
	private final String ORDER_BY_VAL = "recently_uploaded_first";
	private final List<String> PROPERTY_TYPES_VAL = new ArrayList<String>();
	private final String MIN_NUM_BEDROOMS_VAL = "2";
	private final String INVALID_MIN_NUM_BEDROOMS_VAL = "hello";

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
	public void givenAUriInfoWhenGetSearchParametersThenGetInfoFromUriInfo() throws Exception {
		factory.getSearchParametersDTO(searchParam);

		verify(searchParam, times(3)).getQueryParameters();
		verify(queryParams).getFirst(ORDER_BY);
		verify(queryParams).get(PROPERTY_TYPES);
		verify(queryParams).getFirst(MIN_NUM_BEDROOMS);
	}

	@Test
	public void givenAUriInfoWhenGetSearchParametersThenReturnsDTOWithCorrectInfo() throws Exception {
		PropertySearchParametersDTO result = factory.getSearchParametersDTO(searchParam);

		assertEquals(ORDER_BY_VAL, result.getOrderBy());
		assertEquals(PROPERTY_TYPES_VAL, result.getPropertyTypes());
		assertEquals(Integer.parseInt(MIN_NUM_BEDROOMS_VAL), result.getMinNumBedrooms());
	}

	@Test(expected = InvalidSearchParameterException.class)
	public void givenUriInfoWithInvalidNumberWhenGetSearchParametersThenThrowsInvalidParameterException()
			throws Exception {
		given(queryParams.getFirst(MIN_NUM_BEDROOMS)).willReturn(INVALID_MIN_NUM_BEDROOMS_VAL);
		factory.getSearchParametersDTO(searchParam);
	}

	@Test
	public void givenUriInfoWithNoOrderingParamWhenGetSearchParametersThenReturnsSearchParamWithNoOrdering()
			throws Exception {
		given(queryParams.getFirst(ORDER_BY)).willReturn(null);
		PropertySearchParametersDTO result = factory.getSearchParametersDTO(searchParam);
		assertEquals(PropertyOrderingParameters.NO_ORDERING.toString(), result.getOrderBy());
	}

	private void configureUriInfoMock() {
		given(searchParam.getQueryParameters()).willReturn(queryParams);
		given(queryParams.getFirst(ORDER_BY)).willReturn(ORDER_BY_VAL);
		given(queryParams.get(PROPERTY_TYPES)).willReturn(PROPERTY_TYPES_VAL);
		given(queryParams.getFirst(MIN_NUM_BEDROOMS)).willReturn(MIN_NUM_BEDROOMS_VAL);
	}
}
