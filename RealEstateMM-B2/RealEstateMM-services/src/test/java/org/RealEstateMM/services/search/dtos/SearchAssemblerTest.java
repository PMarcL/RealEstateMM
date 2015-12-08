package org.RealEstateMM.services.search.dtos;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.search.Search;
import org.RealEstateMM.domain.search.SearchFactory;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.criterias.SearchCriteriaFactory;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

public class SearchAssemblerTest {
	private static final int NO_MIN_ROOM_NUMBER = 0;
	private static final int MINIMUM_ROOM_NUMBER = 3;
	private static final String INVALID_ORDERING_VALUE = "cannot order on this!";
	private static final String VALID_ORDERING_VALUE = "recently_uploaded_first";
	private static final PropertyOrderingType VALID_ORDERING_TYPE = PropertyOrderingType.RECENTLY_UPLOADED_FIRST;
	private static final PropertyType PROPERTY_TYPE_1 = PropertyType.COMMERCIAL;
	private static final PropertyType PROPERTY_TYPE_2 = PropertyType.FARM;

	private SearchDTO searchDto;
	private SearchFactory searchFactory;
	private SearchCriteriaFactory criteriaFactory;
	private SearchAssembler assembler;

	@Before
	public void setup() {
		searchDto = mock(SearchDTO.class);
		given(searchDto.getOrderBy()).willReturn(VALID_ORDERING_VALUE);
		given(searchDto.getPropertyTypes()).willReturn(validPropertyTypeList());
		given(searchDto.getMinNumBedrooms()).willReturn(MINIMUM_ROOM_NUMBER);
		given(searchDto.getMinNumBathrooms()).willReturn(MINIMUM_ROOM_NUMBER);
		searchFactory = mock(SearchFactory.class);
		criteriaFactory = mock(SearchCriteriaFactory.class);

		assembler = new SearchAssembler(searchFactory, criteriaFactory);
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
	public void givenValidPropertyTypeListIndDtoWhenCreateSearchShouldCreateSearchWithPropertyTypeCriteria() {
		SearchCriteria criteria = mock(SearchCriteria.class);
		given(criteriaFactory.createPropertyTypeCriteria(any())).willReturn(criteria);

		assembler.fromDTO(searchDto);

		verify(searchFactory).createSearch(any(), argThat(containsCriteria(criteria)));
	}

	@Test
	public void givenMinimumNumberOfBedroomsWhenCreateSearchShouldCreateMinimumBedroomNumberCriteria() {
		assembler.fromDTO(searchDto);
		verify(criteriaFactory).createMinimumBedroomNumberCriteria(MINIMUM_ROOM_NUMBER);
	}

	@Test
	public void givenNoMinimumNumberOfBedroomsWhenCreateSearchShouldNotcreateMinimumBedroomCriteria() {
		given(searchDto.getMinNumBedrooms()).willReturn(NO_MIN_ROOM_NUMBER);
		assembler.fromDTO(searchDto);
		verify(criteriaFactory, never()).createMinimumBedroomNumberCriteria(anyInt());
	}

	@Test
	public void givenMinimumNumberOfBedroogWhenCreateSearchShouldCreateSearchWithBedroomNumberCriteria() {
		SearchCriteria criteria = mock(SearchCriteria.class);
		given(criteriaFactory.createMinimumBedroomNumberCriteria(anyInt())).willReturn(criteria);

		assembler.fromDTO(searchDto);

		verify(searchFactory).createSearch(any(), argThat(containsCriteria(criteria)));
	}

	@Test
	public void givenMinimumNumberOfBathroomsWhenCreateSearchShouldCreateMinimumBathroomNumberCriteria() {
		assembler.fromDTO(searchDto);
		verify(criteriaFactory).createMinimumBathroomNumberCriteria(MINIMUM_ROOM_NUMBER);
	}

	@Test
	public void givenNoMinimumNumberOfBathroomsWhenCreateSearchShouldNotCreateMinimumBathroomCriteria() {
		given(searchDto.getMinNumBathrooms()).willReturn(NO_MIN_ROOM_NUMBER);
		assembler.fromDTO(searchDto);
		verify(criteriaFactory, never()).createMinimumBathroomNumberCriteria(anyInt());
	}

	@Test
	public void givenMinimumNumberOfBathroomWhenCreateSearchShouldCreateSearchWithBathroomNumberCriteria() {
		SearchCriteria criteria = mock(SearchCriteria.class);
		given(criteriaFactory.createMinimumBathroomNumberCriteria(anyInt())).willReturn(criteria);

		assembler.fromDTO(searchDto);

		verify(searchFactory).createSearch(any(), argThat(containsCriteria(criteria)));
	}

	@Test
	public void whenCreateSearchShouldReturnSearchFromFactory() {
		Search search = mock(Search.class);
		given(searchFactory.createSearch(any(), any())).willReturn(search);

		Search result = assembler.fromDTO(searchDto);

		assertSame(search, result);
	}

	private List<String> invalidPropertyTypeList() {
		ArrayList<String> result = new ArrayList<>();
		result.add(PROPERTY_TYPE_1.toString());
		result.add("invalid property type");
		return result;
	}

	private ArgumentMatcher<List<SearchCriteria>> containsCriteria(SearchCriteria criteria) {
		return new ArgumentMatcher<List<SearchCriteria>>() {

			@SuppressWarnings("unchecked")
			@Override
			public boolean matches(Object argument) {
				List<SearchCriteria> criterias = (List<SearchCriteria>) argument;
				return criterias.contains(criteria);
			}
		};
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
