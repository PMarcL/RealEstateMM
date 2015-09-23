package org.RealEstateMM.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.persistence.InMemoryUserRepository;
import org.RealEstateMM.services.AccountService;
import org.RealEstateMM.services.anticorruption.AccountServiceAntiCorruption;
import org.RealEstateMM.services.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.anticorruption.UserInformationsValidator;
import org.RealEstateMM.services.dtos.account.AccountAssembler;
import org.RealEstateMM.services.dtos.account.AccountDTO;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
public class UserConnectionResource {

	private AccountServiceAntiCorruption accountServiceAC;

	public UserConnectionResource() {
		InMemoryUserRepository userRepository = new InMemoryUserRepository();
		AccountAssembler accountAssembler = new AccountAssembler();

		AccountService accountService = new AccountService(userRepository, null, accountAssembler);
		accountServiceAC = new AccountServiceAntiCorruption(accountService, new UserInformationsValidator());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(AccountDTO accountDTO) {
		try {
			accountServiceAC.createAccount(accountDTO);
			return Response.ok(Status.OK).build();
		} catch (InvalidUserInformationsException exception) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

}
