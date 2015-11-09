package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.jersey.responses.NumberOfPropertiesSoldThisYearResponse;
import org.RealEstateMM.services.statistics.StatisticService;
import org.junit.Before;
import org.junit.Test;

public class StatisticResourceTest {

	private static final int NUMBER_OF_PROPERTIES_SOLD_THIS_YEAR = 222222;
	private StatisticResource statisticResource;

	@Before
	public void setUp() throws Exception {
		StatisticService statisticService = mock(StatisticService.class);
		given(statisticService.getNumberOfPropertiesSoldThisYear()).willReturn(NUMBER_OF_PROPERTIES_SOLD_THIS_YEAR);

		statisticResource = new StatisticResource(statisticService);
	}

	@Test
	public void whenGetNumberOfPropertiesSoldThisYearThenReturnsResponseStatusOK() {
		Response actual = statisticResource.getNumberOfPropertiesSoldThisYear();
		assertEquals(Status.OK, actual.getStatusInfo());
	}

	@Test
	public void whenGetNumberOfPropertiesSoldThisYearThenReturnsJsonFormattedResponse() {
		Response actual = statisticResource.getNumberOfPropertiesSoldThisYear();
		NumberOfPropertiesSoldThisYearResponse expected = new NumberOfPropertiesSoldThisYearResponse(
				NUMBER_OF_PROPERTIES_SOLD_THIS_YEAR);
		assertEquals(expected, actual.getEntity());
	}

}
