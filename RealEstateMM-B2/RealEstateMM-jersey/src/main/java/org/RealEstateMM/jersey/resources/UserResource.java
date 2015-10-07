package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.Session;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.user.repository.UserWithPseudonymAlreadyStoredException;
import org.RealEstateMM.services.InvalidPasswordException;
import org.RealEstateMM.services.UserDoesNotExistException;
import org.RealEstateMM.services.UserService;
import org.RealEstateMM.services.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.anticorruption.UserInformationsValidator;
import org.RealEstateMM.services.anticorruption.UserServiceAntiCorruption;
import org.RealEstateMM.services.dtos.user.UserDTO;

@Path("/")
public class UserResource {

	private static final String AUTHORIZATION_HEADER_MISSING = "Authorization header missing";
	private UserServiceAntiCorruption userService;
	private SessionService sessionService;

	public UserResource() {
		this.userService = new UserServiceAntiCorruption(new UserService(), new UserInformationsValidator());
		this.sessionService = new SessionService();
	}

	public UserResource(UserServiceAntiCorruption userServiceAC, SessionService sessionService) {
		this.userService = userServiceAC;
		this.sessionService = sessionService;
	}

	@POST
	@Path("logout")
	public Response logout(@HeaderParam("Authorization") String token) {
		if (token == null) {
			return Response.status(Status.BAD_REQUEST).entity(AUTHORIZATION_HEADER_MISSING).build();
		}
		sessionService.close(token);
		return Response.ok(Status.OK).build();

	}

	@GET
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@QueryParam("username") String pseudonym, @QueryParam("password") String password) {
		try {
			UserDTO userDTO = userService.login(pseudonym, password);
			Session session = sessionService.open(userDTO);
			return generateLoginJson(userDTO, session);

		} catch (InvalidPasswordException | UserDoesNotExistException exception) {
			String errorMessage = "Authentication failed: Pseudonym or password invalid";
			return Response.status(Status.UNAUTHORIZED).entity(errorMessage).build();
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}

	@PUT
	@Path("user")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editUserProfile(UserDTO userProfile) {
		// TODO call service layer and manage exceptions
		return null;
	}

	@POST
	@Path("user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(UserDTO userDTO) {
		try {
			userService.createUser(userDTO);
			Session session = sessionService.open(userDTO);
			return generateLoginJson(userDTO, session);
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (UserWithPseudonymAlreadyStoredException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}

	private Response generateLoginJson(UserDTO userDTO, Session session) {
		String json = "{\"userType\":\"" + userDTO.getUserType() + "\", \"token\":\"" + session.token + "\"}";
		return Response.ok(Status.OK).entity(json).build();
	}
}
