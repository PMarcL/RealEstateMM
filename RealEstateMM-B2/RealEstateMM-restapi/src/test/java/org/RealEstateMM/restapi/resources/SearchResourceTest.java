package org.RealEstateMM.restapi.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.search.dtos.SearchDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchResourceTest {
	private final String TOKEN = "aToken";

	private SearchDTO searchParams;
	private SessionService sessionService;
	private SearchResource searchResource;

	@Before
	public void setup() {
		sessionService = mock(SessionService.class);
		ServiceLocator.getInstance().registerService(SessionService.class, sessionService);
		searchResource = new SearchResource();

		searchParams = mock(SearchDTO.class);
	}

	@After
	public void tearDown() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void givenATokenWhenGetSearchThenValidateToken() throws Exception {
		searchResource.getSearch(TOKEN);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenAnInvalidTokenWhenGetSearchThenReturnsUnauthorizedStatusCode() throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response result = searchResource.getSearch(TOKEN);
		assertEquals(Status.UNAUTHORIZED, result.getStatusInfo());
	}

	@Test
	public void givenATokenWhenSaveSearchThenValidateToken() throws Exception {
		searchResource.saveSearch(TOKEN, searchParams);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenAnInvalidTokenWhenSaveSearchThenReturnsUnauthorizedStatusCode() throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response result = searchResource.saveSearch(TOKEN, searchParams);
		assertEquals(Status.UNAUTHORIZED, result.getStatusInfo());
	}

	@Test
	public void givenATokenWhenEditSearchThenValidateToken() throws Exception {
		searchResource.editSearch(TOKEN, searchParams);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenAnInvalidTokenWhenEditSearchThenReturnsUnauthorizedStatusCode() throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response result = searchResource.editSearch(TOKEN, searchParams);
		assertEquals(Status.UNAUTHORIZED, result.getStatusInfo());
	}

	@Test
	public void givenATokenWhenDeleteSearchThenValidateToken() throws Exception {
		searchResource.deleteSearch(TOKEN, searchParams);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenAnInvalidTokenWhendeleteSearchThenReturnsUnauthorizedStatusCode() throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response result = searchResource.deleteSearch(TOKEN, searchParams);
		assertEquals(Status.UNAUTHORIZED, result.getStatusInfo());
	}
}
