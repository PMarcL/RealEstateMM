package org.RealEstateMM.restapi.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.search.SearchDTO;
import org.RealEstateMM.domain.search.SearchNotFoundException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.search.SearchServiceHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchResourceTest {
	private final String PSEUDO = "aPseudo";
	private final String TOKEN = "aToken";
	private final String SEARCH_NAME = "SearchName";

	private SearchDTO searchDTO;
	private SessionService sessionService;
	private SearchServiceHandler searchService;
	private SearchResource searchResource;

	@Before
	public void setup() throws Exception {
		sessionService = mock(SessionService.class);
		given(sessionService.validate(TOKEN)).willReturn(PSEUDO);
		ServiceLocator.getInstance().registerService(SessionService.class, sessionService);
		searchService = mock(SearchServiceHandler.class);
		ServiceLocator.getInstance().registerService(SearchServiceHandler.class, searchService);

		searchResource = new SearchResource();

		searchDTO = mock(SearchDTO.class);
	}

	@After
	public void tearDown() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void givenATokenWhenGetSearchesThenValidateToken() throws Exception {
		searchResource.getSearches(TOKEN);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenAnInvalidTokenWhenGetSearchesThenReturnsUnauthorizedStatusCode() throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response result = searchResource.getSearches(TOKEN);
		assertEquals(Status.UNAUTHORIZED, result.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenGetSearchesThenUsesSearchService() throws Exception {
		searchResource.getSearches(TOKEN);
		verify(searchService).getSavedSearchesForUser(PSEUDO);
	}

	@Test
	public void givenAValidTokenWhenGetSearchesThenReturnsForbiddenStatusIfUserDoesntHaveAccess() throws Exception {
		doThrow(ForbiddenAccessException.class).when(searchService).getSavedSearchesForUser(PSEUDO);
		Response result = searchResource.getSearches(TOKEN);
		assertEquals(Status.FORBIDDEN, result.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenGetSearchesThenReturnsSearchesNames() throws Exception {
		List<String> searches = new ArrayList<String>();
		given(searchService.getSavedSearchesForUser(PSEUDO)).willReturn(searches);

		Response result = searchResource.getSearches(TOKEN);

		assertEquals(searches, result.getEntity());
	}

	@Test
	public void givenATokenWhenGetSearchThenValidateToken() throws Exception {
		searchResource.getSearch(TOKEN, SEARCH_NAME);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenAnInvalidTokenWhenGetSearchThenReturnsUnauthorizedStatusCode() throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response result = searchResource.getSearch(TOKEN, SEARCH_NAME);
		assertEquals(Status.UNAUTHORIZED, result.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenGetSearchThenUsesSearchService() throws Exception {
		searchResource.getSearch(TOKEN, SEARCH_NAME);
		verify(searchService).getSearch(PSEUDO, SEARCH_NAME);
	}

	@Test
	public void givenAValidTokenWhenGetSearchThenReturnsForbiddenStatusIfUserDoesntHaveAccess() throws Exception {
		doThrow(ForbiddenAccessException.class).when(searchService).getSearch(PSEUDO, SEARCH_NAME);
		Response result = searchResource.getSearch(TOKEN, SEARCH_NAME);
		assertEquals(Status.FORBIDDEN, result.getStatusInfo());
	}

	@Test
	public void givenAValidTokenAndSearchNameWhenGetSearchThenReturnsSearchDTO() throws Exception {
		given(searchService.getSearch(PSEUDO, SEARCH_NAME)).willReturn(searchDTO);
		Response result = searchResource.getSearch(TOKEN, SEARCH_NAME);
		assertEquals(searchDTO, result.getEntity());
	}

	@Test
	public void givenAValidTokenAndInvalidSearchNameWhenGetSEarchThenReturnsNotFoundStatus() throws Exception {
		doThrow(SearchNotFoundException.class).when(searchService).getSearch(PSEUDO, SEARCH_NAME);
		Response result = searchResource.getSearch(TOKEN, SEARCH_NAME);
		assertEquals(Status.NOT_FOUND, result.getStatusInfo());
	}

	@Test
	public void givenATokenWhenSaveSearchThenValidateToken() throws Exception {
		searchResource.saveSearch(TOKEN, searchDTO);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenAnInvalidTokenWhenSaveSearchThenReturnsUnauthorizedStatusCode() throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response result = searchResource.saveSearch(TOKEN, searchDTO);
		assertEquals(Status.UNAUTHORIZED, result.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenSaveSearchThenUsesService() throws Exception {
		searchResource.saveSearch(TOKEN, searchDTO);
		verify(searchService).saveSearch(PSEUDO, searchDTO);
	}

	@Test
	public void givenAValidTokenWhenSaveSearchThenReturnsForbiddenStatusIfUserDoesntHaveAccess() throws Exception {
		doThrow(ForbiddenAccessException.class).when(searchService).saveSearch(PSEUDO, searchDTO);
		Response result = searchResource.saveSearch(TOKEN, searchDTO);
		assertEquals(Status.FORBIDDEN, result.getStatusInfo());
	}

	@Test
	public void givenAValidTokenAndSearchDtoWhenSaveSearchReturnsStatusOK() throws Exception {
		Response result = searchResource.saveSearch(TOKEN, searchDTO);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenATokenWhenDeleteSearchThenValidateToken() throws Exception {
		searchResource.deleteSearch(TOKEN, SEARCH_NAME);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenAnInvalidTokenWhendeleteSearchThenReturnsUnauthorizedStatusCode() throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response result = searchResource.deleteSearch(TOKEN, SEARCH_NAME);
		assertEquals(Status.UNAUTHORIZED, result.getStatusInfo());
	}

	@Test
	public void givenATokenAndSearchNameWhenDeleteSearchThenUsesService() throws Exception {
		searchResource.deleteSearch(TOKEN, SEARCH_NAME);
		verify(searchService).deleteSearch(PSEUDO, SEARCH_NAME);
	}

	@Test
	public void givenATokenAndSearchNameWhenDeleteSearchThenReturnsForbiddenStatusIfUserDoesntHaveAccess()
			throws Exception {
		doThrow(ForbiddenAccessException.class).when(searchService).deleteSearch(PSEUDO, SEARCH_NAME);
		Response result = searchResource.deleteSearch(TOKEN, SEARCH_NAME);
		assertEquals(Status.FORBIDDEN, result.getStatusInfo());
	}

	@Test
	public void givenATokenAndSearchNameWhenDeleteSearchThenReturnsStatusOk() throws Exception {
		Response result = searchResource.deleteSearch(TOKEN, SEARCH_NAME);
		assertEquals(Status.OK, result.getStatusInfo());
	}
}
