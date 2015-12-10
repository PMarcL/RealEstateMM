package org.RealEstateMM.restapi.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.search.SearchServiceHandler;
import org.RealEstateMM.services.search.dtos.SearchDTO;

@Path("/search")
public class SearchResource {

	private SessionService sessionService;
	private SearchServiceHandler searchService;

	public SearchResource() {
		sessionService = ServiceLocator.getInstance().getService(SessionService.class);
		searchService = ServiceLocator.getInstance().getService(SearchServiceHandler.class);
	}

	@GET
	@Path("{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearches(@PathParam("token") String token) {
		try {
			String pseudo = sessionService.validate(token);
			List<String> searches = searchService.getSavedSearchesForUser(pseudo);
			return Response.ok(Status.OK).entity(searches).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("{token}/{searchName}")
	public Response getSearch(@PathParam("token") String token, @PathParam("searchName") String searchName) {
		try {
			String pseudo = sessionService.validate(token);
			SearchDTO dto = searchService.getSearch(pseudo, searchName);
			return Response.ok(Status.OK).entity(dto).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveSearch(@PathParam("token") String token, SearchDTO searchParams) {
		try {
			String pseudo = sessionService.validate(token);
			searchService.saveSearch(pseudo, searchParams);
			return Response.ok(Status.OK).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("{token}/{searchName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteSearch(@PathParam("token") String token, @PathParam("searchName") String searchName) {
		try {
			String pseudo = sessionService.validate(token);
			searchService.deleteSearch(pseudo, searchName);
			return Response.ok(Status.OK).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}
}
