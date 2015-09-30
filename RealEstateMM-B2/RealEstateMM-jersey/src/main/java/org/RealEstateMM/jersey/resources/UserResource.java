package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.user.repository.UserWithPseudonymAlreadyStoredException;
import org.RealEstateMM.services.InvalidPasswordException;
import org.RealEstateMM.services.UserDoesNotExistException;
import org.RealEstateMM.services.UserService;
import org.RealEstateMM.services.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.anticorruption.UserInformationsValidator;
import org.RealEstateMM.services.anticorruption.UserServiceAntiCorruption;
import org.RealEstateMM.services.dtos.user.UserDTO;

@Path("/user")
public class UserResource {

	private UserServiceAntiCorruption userServiceAC;
	private SessionService sessionService;

	public UserResource() {
		this.userServiceAC = new UserServiceAntiCorruption(new UserService(), new UserInformationsValidator());
	}

	public UserResource(UserServiceAntiCorruption userService, SessionService sessionService) {
		userServiceAC = userService;
		this.sessionService = sessionService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response logInUser(@QueryParam("username") String pseudonym, @QueryParam("password") String password) {

		try {
			UserDTO userDTO = userServiceAC.findUserType(pseudonym, password);
			sessionService.openSession(userDTO);
			return Response.ok(Status.OK).entity("{\"userType\":\"" + userDTO.getUserType() + "\"}").build();

		} catch (InvalidPasswordException | UserDoesNotExistException exception) {
			String errorMessage = "Authentication failed: Pseudonym or password invalid";
			return Response.status(Status.UNAUTHORIZED).entity(errorMessage).build();
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(UserDTO userDTO) {
		try {
			userServiceAC.createUser(userDTO);
			return Response.ok(Status.OK).build();
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (UserWithPseudonymAlreadyStoredException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}
}
