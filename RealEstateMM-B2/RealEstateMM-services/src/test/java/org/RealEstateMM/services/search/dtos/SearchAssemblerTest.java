package org.RealEstateMM.services.search.dtos;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.search.SearchDescription;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.criterias.SearchCriteriaFactory;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

public class SearchAssemblerTest {
	private static final String SEARCH_NAME = "Google me that!";
	private static final int NO_MIN_ROOM_NUMBER = 0;
	private static final int MINIMUM_ROOM_NUMBER = 3;
	private static final String INVALID_ORDERING_VALUE = "cannot order on this!";
	private static final String VALID_ORDERING_VALUE = "recently_uploaded_first";
	private static final PropertyOrderingType VALID_ORDERING_TYPE = PropertyOrderingType.RECENTLY_UPLOADED_FIRST;
	private static final PropertyType PROPERTY_TYPE_1 = PropertyType.COMMERCIAL;
	private static final PropertyType PROPERTY_TYPE_2 = PropertyType.FARM;

	private SearchDTO searchDto;
	private SearchCriteria propertyTypeCriteria;
	private SearchCriteria minBedroomCriteria;
	private SearchCriteria minBathroomCriteria;
	private SearchCriteriaFactory criteriaFactory;
	private SearchAssembler assembler;

	@Before
	public void setup() {
		searchDto = mock(SearchDTO.class);
		given(searchDto.getName()).willReturn(SEARCH_NAME);
		given(searchDto.getOrderBy()).willReturn(VALID_ORDERING_VALUE);
		given(searchDto.getPropertyTypes()).willReturn(validPropertyTypeList());
		given(searchDto.getMinNumBedrooms()).willReturn(MINIMUM_ROOM_NUMBER);
		given(searchDto.getMinNumBathrooms()).willReturn(MINIMUM_ROOM_NUMBER);
		criteriaFactory = mock(SearchCriteriaFactory.class);
		propertyTypeCriteria = mock(SearchCriteria.class);
		minBathroomCriteria = mock(SearchCriteria.class);
		minBedroomCriteria = mock(SearchCriteria.class);
		given(criteriaFactory.createPropertyTypeCriteria(any())).willReturn(propertyTypeCriteria);
		given(criteriaFactory.createMinimumBathroomNumberCriteria(MINIMUM_ROOM_NUMBER)).willReturn(minBathroomCriteria);
		given(criteriaFactory.createMinimumBedroomNumberCriteria(MINIMUM_ROOM_NUMBER)).willReturn(minBedroomCriteria);

		assembler = new SearchAssembler(criteriaFactory);
	}

	@Test
	public void givenSearchNameWhenCreateSearchFromDtoShouldReturnsDescriptionWithCorrespondingName() {
		SearchDescription result = assembler.fromDTO(searchDto);
		assertEquals(SEARCH_NAME, result.getName());
	}

	@Test
	public void givenValidSearchOrderingValueInDtoWhenCreateSearchDescriptionFromDtoShouldReturnsDescriptionWithCorrespondingOrderingType() {
		SearchDescription result = assembler.fromDTO(searchDto);
		assertEquals(VALID_ORDERING_TYPE, result.getOrderBy());
	}

	@Test
	public void givenInvalidSearchOrderingValueInDtoWhenCreateSearchFromDtoShouldCreateSearchWithNorOrdering() {
		given(searchDto.getOrderBy()).willReturn(INVALID_ORDERING_VALUE);
		SearchDescription result = assembler.fromDTO(searchDto);
		assertEquals(PropertyOrderingType.NO_ORDERING, result.getOrderBy());
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
		SearchDescription result = assembler.fromDTO(searchDto);
		assertTrue(result.getCriterias().contains(propertyTypeCriteria));
	}

	@Test
	public void givenMinimumNumberOfBedroomsWhenCreateSearchShouldCreateMinimumBedroomNumberCriteria() {
		SearchDescription result = assembler.fromDTO(searchDto);
		assertTrue(result.getCriterias().contains(minBedroomCriteria));
	}

	@Test
	public void givenNoMinimumNumberOfBedroomsWhenCreateSearchShouldNotcreateMinimumBedroomCriteria() {
		given(searchDto.getMinNumBedrooms()).willReturn(NO_MIN_ROOM_NUMBER);
		SearchDescription result = assembler.fromDTO(searchDto);
		assertFalse(result.getCriterias().contains(minBedroomCriteria));
	}

	@Test
	public void givenMinimumNumberOfBathroomsWhenCreateSearchShouldCreateMinimumBathroomNumberCriteria() {
		SearchDescription result = assembler.fromDTO(searchDto);
		assertTrue(result.getCriterias().contains(minBathroomCriteria));
	}

	@Test
	public void givenNoMinimumNumberOfBathroomsWhenCreateSearchShouldNotCreateMinimumBathroomCriteria() {
		given(searchDto.getMinNumBathrooms()).willReturn(NO_MIN_ROOM_NUMBER);
		SearchDescription result = assembler.fromDTO(searchDto);
		assertFalse(result.getCriterias().contains(minBathroomCriteria));
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
