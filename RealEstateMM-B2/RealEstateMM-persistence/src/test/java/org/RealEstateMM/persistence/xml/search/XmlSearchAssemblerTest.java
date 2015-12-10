package org.RealEstateMM.persistence.xml.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.search.SearchDescription;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.RealEstateMM.persistence.xml.user.XmlSearchCriteria;
import org.junit.Before;
import org.junit.Test;

public class XmlSearchAssemblerTest {
	private static final PropertyOrderingType ORDER_BY = PropertyOrderingType.NO_ORDERING;
	private static final String SEARCH_NAME = "GoogleThis";
	private static final String PSEUDONYM = "bobby134";

	private SearchDescription search;
	private SearchCriteria searchCriteria;
	private XmlSearchAssembler searchAssembler;
	private List<SearchCriteria> criterias;
	private XmlSearchDescription xmlSearchDescription;
	private XmlSearchCriteriaAssembler criteriaAssembler;
	private List<XmlSearchCriteria> xmlCriterias;
	private XmlSearchCriteria xmlCriteria;

	@Before
	public void setup() {
		criteriaAssembler = mock(XmlSearchCriteriaAssembler.class);
		searchCriteria = mock(SearchCriteria.class);
		criterias = new ArrayList<>();
		criterias.add(searchCriteria);
		criterias.add(searchCriteria);
		createSearchDescription();
		createXmlSearchDescription();

		searchAssembler = new XmlSearchAssembler(criteriaAssembler);
	}

	@Test
	public void whenAssembleFromSearchDescritionThenResultShouldHaveSameName() {
		XmlSearchDescription result = searchAssembler.fromSearchDescription(search, PSEUDONYM);
		assertEquals(SEARCH_NAME, result.getName());
	}

	@Test
	public void whenAssembleFromSearchDescriptionThenResultShouldHaveCorrespondingOrderingType() {
		XmlSearchDescription result = searchAssembler.fromSearchDescription(search, PSEUDONYM);
		assertEquals(ORDER_BY.toString(), result.getOrderBy());
	}

	@Test
	public void whenAssembleFromSearchDescriptionThenResultShouldHavePseudonym() {
		XmlSearchDescription result = searchAssembler.fromSearchDescription(search, PSEUDONYM);
		assertEquals(PSEUDONYM, result.getPseudonym());
	}

	@Test
	public void whenAssembleFromSearchDescriptionThenResultShouldContainsSearchCriterias() {
		XmlSearchDescription result = searchAssembler.fromSearchDescription(search, PSEUDONYM);
		assertEquals(criterias.size(), result.getSearchCriterias().size());
	}

	@Test
	public void whenAssembleToSearchDescriptionThenSearchNameShouldBeTheSame() {
		SearchDescription result = searchAssembler.toSearchDescription(xmlSearchDescription);
		assertEquals(SEARCH_NAME, result.getName());
	}

	@Test
	public void whenAssembleToSearchDescriptionThenOrderByShouldBeCorresponding() {
		SearchDescription result = searchAssembler.toSearchDescription(xmlSearchDescription);
		assertSame(ORDER_BY, result.getOrderBy());
	}

	@Test
	public void whenAssembleToSearchDescriptionThenShouldContainsSearchCriterias() {
		SearchDescription result = searchAssembler.toSearchDescription(xmlSearchDescription);
		assertEquals(xmlCriterias.size(), result.getCriterias().size());
	}

	private void createXmlSearchDescription() {
		xmlSearchDescription = mock(XmlSearchDescription.class);
		given(xmlSearchDescription.getName()).willReturn(SEARCH_NAME);
		given(xmlSearchDescription.getOrderBy()).willReturn(ORDER_BY.toString());
		given(criteriaAssembler.fromXmlSearchCriterias(any())).willReturn(mock(SearchCriteria.class));
		xmlCriterias = new ArrayList<>();
		xmlCriteria = mock(XmlSearchCriteria.class);
		xmlCriterias.add(xmlCriteria);
		xmlCriterias.add(xmlCriteria);
		given(xmlSearchDescription.getSearchCriterias()).willReturn(xmlCriterias);
	}

	private void createSearchDescription() {
		search = mock(SearchDescription.class);
		given(search.getName()).willReturn(SEARCH_NAME);
		given(search.getOrderBy()).willReturn(ORDER_BY);
		given(search.getCriterias()).willReturn(criterias);
		given(criteriaAssembler.toXmlSearchCriteria(any())).willReturn(mock(XmlSearchCriteria.class));
	}
}
