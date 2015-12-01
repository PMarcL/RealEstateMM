package org.RealEstateMM.services.property.dtos;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.search.PropertyOrderingParameters;
import org.RealEstateMM.domain.search.PropertySearchParameters;
import org.RealEstateMM.services.property.PropertyOrderingParametersParser;
import org.junit.Before;
import org.junit.Test;

public class PropertySearchParametersDTOAssemblerTest {
	private final String ORDER_BY = "recently_uploaded_last";
	private final PropertyOrderingParameters ORDER_BY_PARAM = PropertyOrderingParameters.RECENTLY_UPLOADED_LAST;
	private final int MIN_NUM_OF_BEDROOMS = 2;

	private List<String> propertyTypes;
	private PropertyOrderingParametersParser orderingParamParser;
	private PropertySearchParametersDTO searchParamDTO;
	private PropertySearchParametersDTOAssembler assembler;

	@Before
	public void setup() throws Exception {
		orderingParamParser = mock(PropertyOrderingParametersParser.class);
		given(orderingParamParser.getParsedSearchParameter(ORDER_BY)).willReturn(ORDER_BY_PARAM);
		assembler = new PropertySearchParametersDTOAssembler(orderingParamParser);

		searchParamDTO = mock(PropertySearchParametersDTO.class);
		configureSearchParamDTOMock();
	}

	@Test
	public void givenASearchParametersDTOWhenFromDTOThenUsesPropertyOrderingParamParser() throws Exception {
		assembler.fromDTO(searchParamDTO);
		verify(orderingParamParser).getParsedSearchParameter(ORDER_BY);
	}

	@Test
	public void givenASearchParametersDTOWhenFromDTOThenReturnsSearchParams() throws Exception {
		PropertySearchParameters result = assembler.fromDTO(searchParamDTO);
		assertEquals(MIN_NUM_OF_BEDROOMS, result.getMinNumberOfBedrooms());
		assertEquals(ORDER_BY_PARAM, result.getOrderingParam());
		assertEquals(PropertyType.HOUSE, result.getPropertyTypesToFilter().get(0));
	}

	private void configureSearchParamDTOMock() {
		propertyTypes = new ArrayList<String>();
		propertyTypes.add(PropertyType.getStringFromType(PropertyType.HOUSE));
		given(searchParamDTO.getOrderBy()).willReturn(ORDER_BY);
		given(searchParamDTO.getMinNumBedrooms()).willReturn(MIN_NUM_OF_BEDROOMS);
		given(searchParamDTO.getPropertyTypes()).willReturn(propertyTypes);
	}
}
