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

import org.RealEstateMM.services.AccountService;
import org.RealEstateMM.services.anticorruption.AccountServiceAntiCorruption;
import org.RealEstateMM.services.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.anticorruption.UserInformationsValidator;
import org.RealEstateMM.services.dtos.account.AccountDTO;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	private AccountServiceAntiCorruption accountServiceAC;

	public UserResource() {
		accountServiceAC = new AccountServiceAntiCorruption(new AccountService(), new UserInformationsValidator());
	}

	@GET
	public Response getExistingUser(@QueryParam("username") String pseudonym) {
		try {
			if (accountServiceAC.userExists(pseudonym)) {
				return Response.ok(Status.OK).build();

			} else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(AccountDTO accountDTO) {
		try {
			accountServiceAC.createAccount(accountDTO);
			return Response.ok(Status.OK).header("isLoggedIn", "true").build();
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}
