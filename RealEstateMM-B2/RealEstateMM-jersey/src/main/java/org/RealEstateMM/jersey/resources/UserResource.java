package org.RealEstateMM.jersey.resources;

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

import org.RealEstateMM.authentication.session.Session;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.authentication.session.TokenInvalidException;
import org.RealEstateMM.domain.emailsender.CouldNotSendMailException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.emailconfirmation.ImpossibleToConfirmEmailAddressException;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.user.ForbiddenAccessException;
import org.RealEstateMM.services.user.UserServiceHandler;
import org.RealEstateMM.services.user.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.RealEstateMM.domain.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.domain.user.exceptions.UnconfirmedEmailException;
import org.RealEstateMM.domain.user.exceptions.UserWithPseudonymAlreadyStoredException;
import org.RealEstateMM.jersey.responses.LoginResponse;

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

	public UserResource(UserServiceHandler userService, SessionService sessionService) {
		this.userService = userService;
		this.sessionService = sessionService;
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
		} catch (InvalidPasswordException | UserNotFoundException | UnconfirmedEmailException exception) {
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
		} catch (TokenInvalidException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ForbiddenAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
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
		} catch (UserWithPseudonymAlreadyStoredException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (CouldNotSendMailException exception) {
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
		} catch (ImpossibleToConfirmEmailAddressException exception) {
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
		} catch (TokenInvalidException e) {
			return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
		}
	}
}
