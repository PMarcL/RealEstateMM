package org.RealEstateMM.restapi.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;

@Path("/search")
public class SearchResource {

	@GET
	@Path("{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearch(@PathParam("token") String token) {
		// TODO retourne une liste de PropertySearchParametersDTO
		return Response.ok(Status.OK).build();
	}

	@POST
	@Path("{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveSearch(@PathParam("token") String token, PropertySearchParametersDTO searchParams) {
		// TODO utilise le service pour persiter un PropertySearchParameters
		return Response.ok(Status.OK).build();
	}

	@PUT
	@Path("{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editSearch(@PathParam("token") String token, PropertySearchParametersDTO searchParams) {
		// TODO utilise le service pour mettre à jour un
		// PropertySearchParameters existant
		return Response.ok(Status.OK).build();
	}

	@DELETE
	@Path("{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteSearch(@PathParam("token") String token, PropertySearchParametersDTO searchParams) {
		// TODO utilise le servicce pour supprimer un PropertySearchParameters
		// existant
		return Response.ok(Status.OK).build();
	}
}
