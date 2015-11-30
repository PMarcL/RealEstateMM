package org.RealEstateMM.restapi.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.restapi.resources.StatisticResource;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.statistics.StatisticService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StatisticResourceTest {

	private static final int NUMBER_OF_PROPERTIES_SOLD_THIS_YEAR = 1;
	private static final int NUMBER_OF_ACTIVE_BUYER = 2;
	private static final int NUMBER_OF_ACTIVE_SELLER = 3;

	private StatisticResource statisticResource;

	@Before
	public void setUp() {
		StatisticService statisticService = mock(StatisticService.class);
		given(statisticService.getNumberOfPropertiesSoldThisYear()).willReturn(NUMBER_OF_PROPERTIES_SOLD_THIS_YEAR);
		given(statisticService.getNumberOfActiveSeller()).willReturn(NUMBER_OF_ACTIVE_SELLER);
		given(statisticService.getNumberOfActiveBuyer()).willReturn(NUMBER_OF_ACTIVE_BUYER);

		ServiceLocator.getInstance().registerService(StatisticService.class, statisticService);

		statisticResource = new StatisticResource();
	}

	@After
	public void tearDown() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void whenGetNumberOfPropertiesSoldThisYearThenReturnsResponseStatusOK() {
		Response actual = statisticResource.getNumberOfPropertiesSoldThisYear();
		assertEquals(Status.OK, actual.getStatusInfo());
	}

	@Test
	public void whenGetNumberOfPropertiesSoldThisYearThenReturnsNumberOfPropertiesSoldThisYearResponse() {
		Response actual = statisticResource.getNumberOfPropertiesSoldThisYear();

		Map<String, String> expected = new HashMap<String, String>();
		expected.put("numberOfPropertiesSoldThisYear", "" + NUMBER_OF_PROPERTIES_SOLD_THIS_YEAR);

		assertEquals(expected, actual.getEntity());
	}

	@Test
	public void whenGetNumberOfActiveUserThenReturnsResponseStatusOK() {
		Response actual = statisticResource.getNumberOfActiveUser();
		assertEquals(Status.OK, actual.getStatusInfo());
	}

	@Test
	public void whenGetNumberOfActiveUserThenReturnsNumberOfActiveUserResponse() {
		Response actual = statisticResource.getNumberOfActiveUser();

		Map<String, String> expected = new HashMap<String, String>();
		expected.put("numberOfActiveSeller", "" + NUMBER_OF_ACTIVE_SELLER);
		expected.put("numberOfActiveBuyer", "" + NUMBER_OF_ACTIVE_BUYER);

		assertEquals(expected, actual.getEntity());
	}

}
