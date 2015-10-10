package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.Session;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.user.emailconfirmation.AlreadyConfirmedEmailAddressException;
import org.RealEstateMM.domain.user.emailconfirmation.InvalidEmailConfirmationCodeException;
import org.RealEstateMM.domain.user.repository.UserWithPseudonymAlreadyStoredException;
import org.RealEstateMM.emailsender.CouldNotSendMailException;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.user.UserService;
import org.RealEstateMM.services.user.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.user.anticorruption.UserInformationsValidator;
import org.RealEstateMM.services.user.anticorruption.UserServiceAntiCorruption;
import org.RealEstateMM.services.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.services.user.exceptions.UserDoesNotExistException;

@Path("/")
public class UserResource {

	private static final String AUTHORIZATION_HEADER_MISSING = "Authorization header missing";
	private UserServiceAntiCorruption userServiceAC;
	private SessionService sessionService;

	public UserResource() {
		this.userServiceAC = new UserServiceAntiCorruption(new UserService(), new UserInformationsValidator());
		this.sessionService = new SessionService();
	}

	public UserResource(UserServiceAntiCorruption userServiceAC, SessionService sessionService) {
		this.userServiceAC = userServiceAC;
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
			UserDTO userDTO = userServiceAC.login(pseudonym, password);
			Session session = sessionService.open(userDTO);
			return generateLoginResponse(userDTO, session);

		} catch (InvalidPasswordException | UserDoesNotExistException exception) {
			return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).build();
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}

	@POST
	@Path("user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(UserDTO userDTO) {
		try {
			userServiceAC.createUser(userDTO);
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
		String json = "{\"userType\":\"" + userDTO.getUserType() + "\", \"token\":\"" + session.token + "\"}";
		return Response.ok(Status.OK).entity(json).build();
	}

	@GET
	@Path("user/emailConfirmation/{confirmationCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response confirmEmail(
			@PathParam("confirmationCode") String confirmationCode) {
		try {
			userServiceAC.confirmEmailAddress(confirmationCode);
			return Response.status(Status.OK).entity("Email Confirmed").build();
		} catch (InvalidEmailConfirmationCodeException | AlreadyConfirmedEmailAddressException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}
	}
}
