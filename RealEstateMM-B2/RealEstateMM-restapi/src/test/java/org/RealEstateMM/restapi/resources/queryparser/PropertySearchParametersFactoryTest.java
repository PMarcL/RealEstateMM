package org.RealEstateMM.restapi.resources.queryparser;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.RealEstateMM.restapi.resources.queryparser.PropertySearchParametersFactory;
import org.RealEstateMM.services.search.InvalidSearchParameterException;
import org.RealEstateMM.services.search.dtos.SearchDTO;
import org.junit.Before;
import org.junit.Test;

public class PropertySearchParametersFactoryTest {
	private final String ORDER_BY = "orderBy";
	private final String PROPERTY_TYPES = "propertyTypes";
	private final String MIN_NUM_BEDROOMS = "minNumBedrooms";
	private final String MIN_NUM_BATHROOMS = "minNumBathrooms";
	private final String ORDER_BY_VAL = "recently_uploaded_first";
	private final List<String> PROPERTY_TYPES_VAL = new ArrayList<String>();
	private final String MIN_NUM_BEDROOMS_VAL = "2";
	private final String MIN_NUM_BATHROOMS_VAL = "3";
	private final int DEFAULT_MIN_NUMBER = 0;
	private final String INVALID_NUMBER = "hello";

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

		verify(searchParam, times(4)).getQueryParameters();
		verify(queryParams).getFirst(ORDER_BY);
		verify(queryParams).get(PROPERTY_TYPES);
		verify(queryParams).getFirst(MIN_NUM_BEDROOMS);
		verify(queryParams).getFirst(MIN_NUM_BATHROOMS);
	}

	@Test
	public void givenAUriInfoWhenGetSearchParametersThenReturnsDTOWithCorrectInfo() throws Exception {
		SearchDTO result = factory.getSearchParametersDTO(searchParam);

		assertEquals(ORDER_BY_VAL, result.getOrderBy());
		assertEquals(PROPERTY_TYPES_VAL, result.getPropertyTypes());
		assertEquals(Integer.parseInt(MIN_NUM_BEDROOMS_VAL), result.getMinNumBedrooms());
		assertEquals(Integer.parseInt(MIN_NUM_BATHROOMS_VAL), result.getMinNumBathrooms());
	}

	@Test(expected = InvalidSearchParameterException.class)
	public void givenUriInfoWithInvalidNumberOfBedroomsWhenGetSearchParametersThenThrowsInvalidParameterException()
			throws Exception {
		given(queryParams.getFirst(MIN_NUM_BEDROOMS)).willReturn(INVALID_NUMBER);
		factory.getSearchParametersDTO(searchParam);
	}

	@Test(expected = InvalidSearchParameterException.class)
	public void givenUriInfoWithInvalidNumberOfBathroomsWhenGetSearchParametersThenThrowsInvalidParameterException()
			throws Exception {
		given(queryParams.getFirst(MIN_NUM_BATHROOMS)).willReturn(INVALID_NUMBER);
		factory.getSearchParametersDTO(searchParam);
	}

	@Test
	public void givenUrinInfoWithNoNumOfBedroomsWhenGetSearchParametersThenMinNumOfBedroomsIsZero() throws Exception {
		given(queryParams.getFirst(MIN_NUM_BEDROOMS)).willReturn(null);
		SearchDTO result = factory.getSearchParametersDTO(searchParam);
		assertEquals(DEFAULT_MIN_NUMBER, result.getMinNumBedrooms());
	}

	@Test
	public void givenUrinInfoWithNoNumOfBathroomsWhenGetSearchParametersThenMinNumOfBathroomsIsZero() throws Exception {
		given(queryParams.getFirst(MIN_NUM_BATHROOMS)).willReturn(null);
		SearchDTO result = factory.getSearchParametersDTO(searchParam);
		assertEquals(DEFAULT_MIN_NUMBER, result.getMinNumBathrooms());
	}

	@Test
	public void givenUriInfoWithNoOrderingParamWhenGetSearchParametersThenReturnsSearchParamWithNoOrdering()
			throws Exception {
		given(queryParams.getFirst(ORDER_BY)).willReturn(null);
		SearchDTO result = factory.getSearchParametersDTO(searchParam);
		assertEquals(PropertyOrderingType.NO_ORDERING.toString(), result.getOrderBy());
	}

	@Test
	public void givenUriInfoWithNoPropertyTypeWhenGetSearchParametersThenReturnEmptyPropertyTypesToFilterList()
			throws Exception {
		given(queryParams.get(PROPERTY_TYPES)).willReturn(null);
		SearchDTO result = factory.getSearchParametersDTO(searchParam);
		assertNotNull(result.getPropertyTypes());
	}

	private void configureUriInfoMock() {
		given(searchParam.getQueryParameters()).willReturn(queryParams);
		given(queryParams.getFirst(ORDER_BY)).willReturn(ORDER_BY_VAL);
		given(queryParams.get(PROPERTY_TYPES)).willReturn(PROPERTY_TYPES_VAL);
		given(queryParams.getFirst(MIN_NUM_BEDROOMS)).willReturn(MIN_NUM_BEDROOMS_VAL);
		given(queryParams.getFirst(MIN_NUM_BATHROOMS)).willReturn(MIN_NUM_BATHROOMS_VAL);
	}
}
