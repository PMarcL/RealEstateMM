package org.RealEstateMM.restapi.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.InvalidSearchParameterException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.property.validation.InvalidPropertyInformationException;
import org.RealEstateMM.services.user.ForbiddenAccessException;

@Path("/property")
public class PropertyResource {

	private PropertyServiceHandler propertyService;
	private SessionService sessionService;
	private PropertySearchParametersFactory searchParamFactory;

	public PropertyResource() {
		propertyService = ServiceLocator.getInstance().getService(PropertyServiceHandler.class);
		sessionService = ServiceLocator.getInstance().getService(SessionService.class);
		searchParamFactory = new PropertySearchParametersFactory();
	}

	public PropertyResource(PropertyServiceHandler service, SessionService sessionService,
			PropertySearchParametersFactory searchParamFactory) {
		this.propertyService = service;
		this.sessionService = sessionService;
		this.searchParamFactory = searchParamFactory;
	}

	@GET
	@Path("{token}/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchProperties(@PathParam("token") String token, @Context UriInfo searchParam) {
		try {
			String pseudo = sessionService.validate(token);
			PropertySearchParametersDTO searchParamDTO = searchParamFactory.getSearchParametersDTO(searchParam);
			List<PropertyDTO> properties = propertyService.getPropertiesSearchResult(pseudo, searchParamDTO);
			return Response.ok(Status.OK).entity(properties).build();
		} catch (InvalidSearchParameterException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/atAddress/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPropertyAtAddress(@PathParam("token") String token, PropertyAddressDTO address) {
		try {
			String pseudo = sessionService.validate(token);
			PropertyDTO propertyDTO = propertyService.getPropertyAtAddress(pseudo, address);
			return Response.ok(Status.OK).entity(propertyDTO).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		} catch (PropertyNotFoundException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPropertiesFromOwner(@PathParam("token") String token) {
		try {
			String owner = sessionService.validate(token);
			List<PropertyDTO> properties = propertyService.getPropertiesFromOwner(owner);
			return Response.ok(Status.OK).entity(properties).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}

	@PUT
	@Path("/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProperty(@PathParam("token") String token, PropertyDTO propertyDTO) {
		try {
			String owner = sessionService.validate(token);
			propertyService.editPropertyFeatures(owner, propertyDTO);
			return Response.ok(Status.OK).build();
		} catch (InvalidPropertyInformationException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadProperty(@PathParam("token") String token, PropertyDTO propertyDTO) {
		try {
			String owner = sessionService.validate(token);
			propertyService.uploadProperty(owner, propertyDTO);
			return Response.ok(Status.OK).build();
		} catch (InvalidPropertyInformationException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}
}
