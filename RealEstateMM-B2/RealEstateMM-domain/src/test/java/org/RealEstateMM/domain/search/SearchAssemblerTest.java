package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.search.SearchAssembler;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.criterias.SearchCriteriaFactory;

import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

public class SearchAssemblerTest {
	private static final String SEARCH_NAME = "Google me that!";
	private static final int NO_MIN_ROOM_NUMBER = 0;
	private static final int MINIMUM_ROOM_NUMBER = 3;
	private static final String INVALID_ORDERING_VALUE = "cannot order on this!";
	private static final String VALID_ORDERING_VALUE = "recently_uploaded_first";
	private static final int MINIMUM_PRICE = 500;
	private static final int MAXIMUM_PRICE = 10000;
	private static final int NO_PRICE = 0;
	private static final PropertyOrderingType VALID_ORDERING_TYPE = PropertyOrderingType.RECENTLY_UPLOADED_FIRST;
	private static final PropertyType PROPERTY_TYPE_1 = PropertyType.COMMERCIAL;
	private static final PropertyType PROPERTY_TYPE_2 = PropertyType.FARM;

	@Captor
	private ArgumentCaptor<List<SearchCriteria>> captor;

	private SearchDTO searchDto;
	private SearchCriteria propertyTypeCriteria;
	private SearchCriteria minBedroomCriteria;
	private SearchCriteria minBathroomCriteria;
	private SearchCriteria maxPriceCriteria;
	private SearchCriteria minPriceCriteria;
	private SearchCriteriaFactory criteriaFactory;
	private SearchFactory searchFactory;
	private SearchAssembler assembler;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		searchDto = mock(SearchDTO.class);
		given(searchDto.getName()).willReturn(SEARCH_NAME);
		given(searchDto.getOrderBy()).willReturn(VALID_ORDERING_VALUE);
		given(searchDto.getPropertyTypes()).willReturn(validPropertyTypeList());
		given(searchDto.getMinNumBedrooms()).willReturn(MINIMUM_ROOM_NUMBER);
		given(searchDto.getMinNumBathrooms()).willReturn(MINIMUM_ROOM_NUMBER);
		given(searchDto.getMaxPrice()).willReturn(MAXIMUM_PRICE);
		given(searchDto.getMinPrice()).willReturn(MINIMUM_PRICE);
		criteriaFactory = mock(SearchCriteriaFactory.class);
		propertyTypeCriteria = mock(SearchCriteria.class);
		minBathroomCriteria = mock(SearchCriteria.class);
		minBedroomCriteria = mock(SearchCriteria.class);
		maxPriceCriteria = mock(SearchCriteria.class);
		given(criteriaFactory.createPropertyTypeCriteria(any())).willReturn(propertyTypeCriteria);
		given(criteriaFactory.createMinimumBathroomNumberCriteria(MINIMUM_ROOM_NUMBER)).willReturn(minBathroomCriteria);
		given(criteriaFactory.createMinimumBedroomNumberCriteria(MINIMUM_ROOM_NUMBER)).willReturn(minBedroomCriteria);
		given(criteriaFactory.createMaximumPriceCriteria(MAXIMUM_PRICE)).willReturn(maxPriceCriteria);
		given(criteriaFactory.createMaximumPriceCriteria(MINIMUM_PRICE)).willReturn(minPriceCriteria);
		searchFactory = mock(SearchFactory.class);

		assembler = new SearchAssembler(criteriaFactory, searchFactory);
	}

	@Test
	public void givenValidSearchOrderingValueInDtoWhenCreateSearchFromDtoShouldCreateSearchWithCorrespondingOrderingType() {
		assembler.fromDTO(searchDto);
		verify(searchFactory).createSearch(eq(VALID_ORDERING_TYPE), any());
	}

	@Test
	public void givenInvalidSearchOrderingValueInDtoWhenCreateSearchFromDtoShouldCreateSearchWithNorOrdering() {
		given(searchDto.getOrderBy()).willReturn(INVALID_ORDERING_VALUE);
		assembler.fromDTO(searchDto);
		verify(searchFactory).createSearch(eq(PropertyOrderingType.NO_ORDERING), any());
	}

	@Test
	public void givenValidPropertyTypeListInDtoWhenCreateSearchFromDtoShouldCreatePropertyTypeCriteriaWithCorrespondingPropertyType() {
		assembler.fromDTO(searchDto);
		verify(criteriaFactory)
				.createPropertyTypeCriteria(argThat(containsPropertyType(PROPERTY_TYPE_1, PROPERTY_TYPE_2)));
	}

	@Test
	public void givenInvalidPropertyTypeInDtoWhenCreateSearchFromDtoShouldOmmitInvalidPropertyTypeForCriteriaCreation() {
		given(searchDto.getPropertyTypes()).willReturn(invalidPropertyTypeList());
		assembler.fromDTO(searchDto);
		verify(criteriaFactory).createPropertyTypeCriteria(argThat(containsPropertyType(PROPERTY_TYPE_1)));
	}

	@Test
	public void givenNoNeedToFilterPropertyTypesWhenCreateSearchShouldNotCreateSearchWithPropertyTypeCriteria() {
		given(searchDto.getPropertyTypes()).willReturn(null);
		assembler.fromDTO(searchDto);
		verify(criteriaFactory, never()).createPropertyTypeCriteria(any());
	}

	@Test
	public void givenValidPropertyTypeListInDtoWhenCreateSearchShouldCreateSearchWithPropertyTypeCriteria() {
		assembler.fromDTO(searchDto);
		verifyCriteriasContains(propertyTypeCriteria);
	}

	@Test
	public void givenMinimumNumberOfBedroomsWhenCreateSearchShouldCreateSearchWithMinimumBedroomNumberCriteria() {
		assembler.fromDTO(searchDto);
		verifyCriteriasContains(minBedroomCriteria);
	}

	@Test
	public void givenNoMinimumNumberOfBedroomsWhenCreateSearchShouldNotcreateMinimumBedroomCriteria() {
		given(searchDto.getMinNumBedrooms()).willReturn(NO_MIN_ROOM_NUMBER);
		assembler.fromDTO(searchDto);
		verifyCriteriasDoesNotContains(minBedroomCriteria);
	}

	@Test
	public void givenMinimumNumberOfBathroomsWhenCreateSearchShouldCreateMinimumBathroomNumberCriteria() {
		assembler.fromDTO(searchDto);
		verifyCriteriasContains(minBathroomCriteria);
	}

	@Test
	public void givenNoMinimumNumberOfBathroomsWhenCreateSearchShouldNotCreateMinimumBathroomCriteria() {
		given(searchDto.getMinNumBathrooms()).willReturn(NO_MIN_ROOM_NUMBER);
		assembler.fromDTO(searchDto);
		verifyCriteriasDoesNotContains(minBathroomCriteria);
	}

	@Test
	public void givenMaximumPriceWhenCreateSearchShouldCreateMaximumPriceCriteria() {
		assembler.fromDTO(searchDto);
		verifyCriteriasContains(maxPriceCriteria);
	}

	@Test
	public void givenMinimumPriceWhenCreateSearchShouldCreateMinimumPriceCriteria() {
		assembler.fromDTO(searchDto);
		verifyCriteriasContains(minPriceCriteria);
	}

	@Test
	public void givenNoMinimumPriceWhenCreateSearchShouldNotcreateMinimumPriceCriteria() {
		given(searchDto.getMinPrice()).willReturn(NO_PRICE);
		assembler.fromDTO(searchDto);
		verifyCriteriasDoesNotContains(minPriceCriteria);
	}

	@Test
	public void givenNoMaximumPriceWhenCreateSearchShouldNotcreateMaximumPriceCriteria() {
		given(searchDto.getMaxPrice()).willReturn(NO_PRICE);
		assembler.fromDTO(searchDto);
		verifyCriteriasDoesNotContains(maxPriceCriteria);
	}

	@Test
	public void whenCreateSearchReturnCreatedSearch() {
		Search search = mock(Search.class);
		given(searchFactory.createSearch(any(), any())).willReturn(search);

		Search result = assembler.fromDTO(searchDto);

		assertSame(search, result);
	}

	private void verifyCriteriasContains(SearchCriteria criteria) {
		List<SearchCriteria> criterias = captureCriterias();
		assertTrue(criterias.contains(criteria));
	}

	private List<SearchCriteria> captureCriterias() {
		verify(searchFactory).createSearch(any(), captor.capture());
		List<SearchCriteria> criterias = captor.getValue();
		return criterias;
	}

	private void verifyCriteriasDoesNotContains(SearchCriteria criteria) {
		List<SearchCriteria> criterias = captureCriterias();
		assertFalse(criterias.contains(criteria));
	}

	private List<String> invalidPropertyTypeList() {
		ArrayList<String> result = new ArrayList<>();
		result.add(PROPERTY_TYPE_1.toString());
		result.add("invalid property type");
		return result;
	}

	private ArgumentMatcher<List<PropertyType>> containsPropertyType(PropertyType... propertyTypes) {
		return new ArgumentMatcher<List<PropertyType>>() {

			@SuppressWarnings("unchecked")
			@Override
			public boolean matches(Object argument) {
				List<PropertyType> argList = (List<PropertyType>) argument;
				for (PropertyType propType : propertyTypes) {
					if (!argList.contains(propType)) {
						return false;
					}
				}
				return true;
			}
		};
	}

	private List<String> validPropertyTypeList() {
		ArrayList<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add(PROPERTY_TYPE_1.toString());
		propertyTypes.add(PROPERTY_TYPE_2.toString());
		return propertyTypes;
	}
}
