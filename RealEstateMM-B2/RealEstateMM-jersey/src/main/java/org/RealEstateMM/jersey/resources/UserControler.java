package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.services.dto.UserDTO;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
public class UserControler {

	@POST
	public Response registerUser(UserDTO userDTO) {

		return Response.ok(Status.OK).build();
	}

}
