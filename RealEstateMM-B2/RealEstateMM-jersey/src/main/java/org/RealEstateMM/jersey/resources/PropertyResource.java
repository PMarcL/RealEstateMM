package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.services.PropertyService;
import org.RealEstateMM.services.anticorruption.InvalidPropertyInformationException;
import org.RealEstateMM.services.anticorruption.PropertyAddressValidator;
import org.RealEstateMM.services.anticorruption.PropertyServiceAntiCorruption;
import org.RealEstateMM.services.dtos.property.PropertyInformations;

@Path("/property")
public class PropertyResource {

	private PropertyServiceAntiCorruption propertyServiceAC;

	public PropertyResource(PropertyServiceAntiCorruption propertyServiceAC) {
		this.propertyServiceAC = propertyServiceAC;
	}

	public PropertyResource() {
		PropertyService uploadService = new PropertyService();
		propertyServiceAC = new PropertyServiceAntiCorruption(uploadService, new PropertyAddressValidator());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadProperty(PropertyInformations propertyInfos) {
		try {
			propertyServiceAC.upload(propertyInfos);
			return Response.ok(Status.OK).build();
		} catch (InvalidPropertyInformationException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProperty() {
		return Response.ok(Status.OK).build();
	}

}
