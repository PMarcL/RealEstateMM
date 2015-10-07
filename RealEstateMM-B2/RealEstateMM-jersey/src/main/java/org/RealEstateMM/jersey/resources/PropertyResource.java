package org.RealEstateMM.jersey.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import org.RealEstateMM.services.PropertyService;
import org.RealEstateMM.services.anticorruption.InvalidPropertyInformationException;
import org.RealEstateMM.services.anticorruption.PropertyInformationsValidator;
import org.RealEstateMM.services.anticorruption.PropertyServiceAntiCorruption;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;

@Path("/property")
public class PropertyResource {

	private PropertyServiceAntiCorruption serviceAC;
	private PropertyService propertyService;

	public PropertyResource() {
		propertyService = new PropertyService();
		serviceAC = new PropertyServiceAntiCorruption(propertyService, new PropertyInformationsValidator());
	}

	public PropertyResource(PropertyServiceAntiCorruption serviceAC, PropertyService service) {
		this.serviceAC = serviceAC;
		this.propertyService = service;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProperties() {
		try {
			String json = getJsonFromPropertyDTOs(propertyService.getAllProperties());
			return Response.ok(Status.OK).entity(json).build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private String getJsonFromPropertyDTOs(ArrayList<PropertyDTO> propertyJSON) {
		Gson gson = new Gson();
		return gson.toJson(propertyJSON);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProperty(PropertyFeaturesDTO features) {
		try {
			serviceAC.editProperty(features);
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
			serviceAC.upload(propertyInfos);
			return Response.ok(Status.OK).build();
		} catch (InvalidPropertyInformationException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}

}
