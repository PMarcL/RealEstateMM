package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.jersey.responses.statistics.NumberOfActiveUserResponse;
import org.RealEstateMM.jersey.responses.statistics.NumberOfPropertiesSoldThisYearResponse;
import org.RealEstateMM.services.statistics.StatisticService;
import org.junit.Before;
import org.junit.Test;

public class StatisticResourceTest {

	private static final int NUMBER_OF_PROPERTIES_SOLD_THIS_YEAR = 1;
	private static final int NUMBER_OF_ACTIVE_BUYER = 2;
	private static final int NUMBER_OF_ACTIVE_SELLER = 3;
	private StatisticResource statisticResource;

	@Before
	public void setUp()  {
		StatisticService statisticService = mock(StatisticService.class);
		given(statisticService.getNumberOfPropertiesSoldThisYear()).willReturn(NUMBER_OF_PROPERTIES_SOLD_THIS_YEAR);
		given(statisticService.getNumberOfActiveSeller()).willReturn(NUMBER_OF_ACTIVE_SELLER);
		given(statisticService.getNumberOfActiveBuyer()).willReturn(NUMBER_OF_ACTIVE_BUYER);

		statisticResource = new StatisticResource(statisticService);
	}

	@Test
	public void whenGetNumberOfPropertiesSoldThisYearThenReturnsResponseStatusOK() {
		Response actual = statisticResource.getNumberOfPropertiesSoldThisYear();
		assertEquals(Status.OK, actual.getStatusInfo());
	}

	@Test
	public void whenGetNumberOfPropertiesSoldThisYearThenReturnsNumberOfPropertiesSOldThisYearResponse() {
		Response actual = statisticResource.getNumberOfPropertiesSoldThisYear();
		NumberOfPropertiesSoldThisYearResponse expected = new NumberOfPropertiesSoldThisYearResponse(
				NUMBER_OF_PROPERTIES_SOLD_THIS_YEAR);
		assertEquals(expected, actual.getEntity());
	}

	@Test
	public void whenGetNumberOfActiveUserThenReturnsNumberOfActiveUserResponse() {
		Response actual = statisticResource.getNumberOfActiveUser();
		NumberOfActiveUserResponse expected = new NumberOfActiveUserResponse(NUMBER_OF_ACTIVE_SELLER,
				NUMBER_OF_ACTIVE_BUYER);
		assertEquals(expected, actual.getEntity());
	}

}
