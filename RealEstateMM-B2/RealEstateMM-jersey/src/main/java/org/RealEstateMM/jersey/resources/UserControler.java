package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.persistence.InMemoryUserRepository;
import org.RealEstateMM.services.UserRegistrationService;
import org.RealEstateMM.services.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.anticorruption.UserInformationsValidator;
import org.RealEstateMM.services.anticorruption.UserRegistrationAntiCorruption;
import org.RealEstateMM.services.dto.UserAssembler;
import org.RealEstateMM.services.dto.UserDTO;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
public class UserControler {

	private UserRegistrationAntiCorruption registrationAC;

	public UserControler() {
		registrationAC = new UserRegistrationAntiCorruption(new UserRegistrationService(new InMemoryUserRepository(),
				new UserAssembler()), new UserInformationsValidator());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(UserDTO userDTO) {
		try {
			registrationAC.register(userDTO);
			return Response.ok(Status.OK).build();
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

}
