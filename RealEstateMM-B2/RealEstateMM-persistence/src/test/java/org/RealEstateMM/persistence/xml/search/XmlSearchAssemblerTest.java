package org.RealEstateMM.persistence.xml.search;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.search.SearchDTO;
import org.junit.Before;
import org.junit.Test;

public class XmlSearchAssemblerTest {
	private final String ORDER_BY = "highestPriceFirst";
	private final String NAME = "mySearch";
	private final String PSEUDO = "pseudo";
	private final int MAX_PRICE = 50000;
	private final int MIN_PRICE = 100;
	private final int NUM_BATHROOMS = 2;
	private final int NUM_BEDROOMS = 4;
	private final List<String> PROPERTY_TYPES = new ArrayList<String>();

	private SearchDTO searchDto;
	private XmlSearchDescription xmlSearch;
	private XmlSearchAssembler assembler;

	@Before
	public void setup() {
		assembler = new XmlSearchAssembler();

		initializeSearchDTO();
		initializeXmlSearchDescription();
	}

	@Test
	public void givenASearchDescriptionAndPseudonymWhenFromSearchDTOThenReturnsXmlSearchDescriptionWithSameFields() {
		XmlSearchDescription result = assembler.fromSearchDTO(searchDto, PSEUDO);
		assertEquals(MAX_PRICE, result.getMaxPrice());
		assertEquals(MIN_PRICE, result.getMinPrice());
		assertEquals(NUM_BATHROOMS, result.getMinNumBathrooms());
		assertEquals(NUM_BEDROOMS, result.getMinNumBedrooms());
		assertEquals(NAME, result.getName());
		assertEquals(ORDER_BY, result.getOrderBy());
		assertEquals(PROPERTY_TYPES, result.getPropertyTypes());
		assertEquals(PSEUDO, result.getPseudonym());
	}

	@Test
	public void givenAXmlSearchDescriptionWhenToSearchDTOThenReturnsSearchDTOWithSameFields() {
		SearchDTO result = assembler.toSearchDTO(xmlSearch);
		assertEquals(MAX_PRICE, result.getMaxPrice());
		assertEquals(MIN_PRICE, result.getMinPrice());
		assertEquals(NUM_BATHROOMS, result.getMinNumBathrooms());
		assertEquals(NUM_BEDROOMS, result.getMinNumBedrooms());
		assertEquals(NAME, result.getName());
		assertEquals(ORDER_BY, result.getOrderBy());
		assertEquals(PROPERTY_TYPES, result.getPropertyTypes());
	}

	private void initializeSearchDTO() {
		searchDto = new SearchDTO();
		searchDto.setMaxPrice(MAX_PRICE);
		searchDto.setMinNumBathrooms(NUM_BATHROOMS);
		searchDto.setMinNumBedrooms(NUM_BEDROOMS);
		searchDto.setMinPrice(MIN_PRICE);
		searchDto.setName(NAME);
		searchDto.setOrderBy(ORDER_BY);
		searchDto.setPropertyTypes(PROPERTY_TYPES);
	}

	private void initializeXmlSearchDescription() {
		xmlSearch = new XmlSearchDescription();
		xmlSearch.setMaxPrice(MAX_PRICE);
		xmlSearch.setMinNumBathrooms(NUM_BATHROOMS);
		xmlSearch.setMinNumBedrooms(NUM_BEDROOMS);
		xmlSearch.setMinPrice(MIN_PRICE);
		xmlSearch.setName(NAME);
		xmlSearch.setOrderBy(ORDER_BY);
		xmlSearch.setPropertyTypes(PROPERTY_TYPES);
		xmlSearch.setPseudonym(PSEUDO);
	}
}
