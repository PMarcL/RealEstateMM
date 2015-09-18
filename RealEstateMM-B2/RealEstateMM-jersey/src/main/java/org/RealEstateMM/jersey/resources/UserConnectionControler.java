package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.services.dto.UserInformations;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
public class UserConnectionControler {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response ajouterDemande(UserInformations userDTO) {
		return Response.ok(Status.OK).build();
	}

}
