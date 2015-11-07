package org.RealEstateMM.jersey.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

@Path("/property")
public class PropertyResource {

	private PropertyServiceHandler propertyService;

	public PropertyResource() {
		propertyService = ServiceLocator.getInstance().getService(PropertyServiceHandler.class);
	}

	public PropertyResource(PropertyServiceHandler service) {
		this.propertyService = service;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProperties() {
		String json = getJsonFromPropertyDTOs(propertyService.getAllProperties());
		return Response.ok(Status.OK).entity(json).build();
	}

	@GET
	@Path("/{owner}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPropertiesFromOwner(@PathParam("owner") String owner) {
		String json = getJsonFromPropertyDTOs(propertyService.getPropertiesFromOwner(owner));
		return Response.ok(Status.OK).entity(json).build();
	}

	private String getJsonFromPropertyDTOs(ArrayList<PropertyDTO> propertyJSON) {
		Gson gson = new Gson();
		return gson.toJson(propertyJSON);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProperty(PropertyDTO propertyDTO) {
		try {
			propertyService.editPropertyFeatures(propertyDTO);
			return Response.ok(Status.OK).build();
		} catch (InvalidPropertyInformationException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadProperty(PropertyDTO propertyInfos) {
		try {
			propertyService.uploadProperty(propertyInfos);
			return Response.ok(Status.OK).build();
		} catch (InvalidPropertyInformationException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}
}
