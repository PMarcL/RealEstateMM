package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyAddressValidator;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.PropertyServiceAntiCorruption;

@Path("/property")
public class PropertyResource {

	private PropertyServiceAntiCorruption uploadAC;
	private PropertyService propertyService;

	public PropertyResource() {
		propertyService = new PropertyService();
		uploadAC = new PropertyServiceAntiCorruption(propertyService, new PropertyAddressValidator());
	}

	public PropertyResource(PropertyServiceAntiCorruption serviceAC) {
		uploadAC = serviceAC;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProperties() {
		try {
			String propertyJSON = propertyService.getAllProperties();
			return Response.ok(Status.OK).entity(propertyJSON).build();

		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProperty() {
		return Response.ok(Status.OK).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadProperty(PropertyDTO propertyInfos) {
		try {
			uploadAC.upload(propertyInfos);
			return Response.ok(Status.OK).build();
		} catch (InvalidPropertyInformationException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

}
