package org.RealEstateMM.restapi.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.authentication.session.Session;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.user.AuthenticationFailedException;
import org.RealEstateMM.domain.user.EmailAddressConfirmationException;
import org.RealEstateMM.domain.user.ExistingUserException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.restapi.responses.LoginResponse;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.user.InvalidUserInformationsException;
import org.RealEstateMM.services.user.UserServiceHandler;
import org.RealEstateMM.services.user.dtos.UserDTO;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private static final String AUTHORIZATION_HEADER_MISSING = "Authorization header missing";
	private UserServiceHandler userService;
	private SessionService sessionService;

	public UserResource() {
		this.userService = ServiceLocator.getInstance().getService(UserServiceHandler.class);
		this.sessionService = ServiceLocator.getInstance().getService(SessionService.class);
	}

	@POST
	@Path("logout/{token}")
	public Response logout(@PathParam("token") String token) {
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
			UserDTO userDTO = userService.authenticate(pseudonym, password);
			Session session = sessionService.open(userDTO);
			return generateLoginResponse(userDTO, session);
		} catch (AuthenticationFailedException exception) {
			return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).build();
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}

	@PUT
	@Path("user/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editUserProfile(@PathParam("token") String token, UserDTO userProfile) {
		try {
			String pseudo = sessionService.validate(token);
			userService.updateUserProfile(pseudo, userProfile);
			return Response.status(Status.OK).build();
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (ForbiddenAccessException e) {
			return Response.status(Status.FORBIDDEN).entity(e.getMessage()).build();
		} catch (EmailAddressConfirmationException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(UserDTO userDTO) {
		try {
			userService.createUser(userDTO);
			Session session = sessionService.open(userDTO);
			return generateLoginResponse(userDTO, session);
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (ExistingUserException exception) {
			return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).build();
		} catch (EmailAddressConfirmationException exception) {
			return Response.status(Status.CREATED).build();
		}
	}

	private Response generateLoginResponse(UserDTO userDTO, Session session) {
		LoginResponse response = new LoginResponse(userDTO.getUserRole(), session.token);
		return Response.ok(Status.OK).entity(response).build();
	}

	@GET
	@Path("user/emailConfirmation/{confirmationCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response confirmEmail(@PathParam("confirmationCode") String confirmationCode) {
		try {
			userService.confirmEmailAddress(confirmationCode);
			return Response.status(Status.OK).entity("Email Confirmed").build();
		} catch (EmailAddressConfirmationException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}

	@GET
	@Path("user/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserProfile(@PathParam("token") String token) {
		try {
			String pseudonym = sessionService.validate(token);
			UserDTO userProfile = userService.getUserProfile(pseudonym);
			return Response.status(Status.OK).entity(userProfile).build();
		} catch (UserNotFoundException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (InvalidSessionTokenException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		}
	}
}
