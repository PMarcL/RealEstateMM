package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.services.PropertyService;
import org.RealEstateMM.services.anticorruption.InvalidZipCodeFormatException;
import org.RealEstateMM.services.anticorruption.PropertyAddressValidator;
import org.RealEstateMM.services.anticorruption.PropertyUploadAntiCorruption;
import org.RealEstateMM.services.dtos.property.PropertyInformations;

@Path("/property")
public class PropertyUploadResource {

	private PropertyUploadAntiCorruption uploadAC;

	public PropertyUploadResource() {
		PropertyService uploadService = new PropertyService();
		uploadAC = new PropertyUploadAntiCorruption(uploadService, new PropertyAddressValidator());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadProperty(PropertyInformations propertyInfos) {
		try {
			uploadAC.upload(propertyInfos);
			return Response.ok(Status.OK).build();
		} catch (InvalidZipCodeFormatException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

}
