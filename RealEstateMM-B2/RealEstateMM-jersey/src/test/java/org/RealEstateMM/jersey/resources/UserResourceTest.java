package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.RealEstateMM.services.dtos.account.AccountDTO;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.AccountDTOBuilder;
import org.RealEstateMM.services.helpers.DefaultUserDTOBuilder;
import org.junit.Before;

public class UserResourceTest {

	private final UserDTO A_USER_DTO = new DefaultUserDTOBuilder().build();
	private final AccountDTO AN_ACCOUNT_DTO = new AccountDTOBuilder().withOwner(A_USER_DTO).build();

	private UserResource userConnectionResource;

	@Before
	public void Setup() {
		userConnectionResource = new UserResource();
	}

	// FIXME ServiceLocator
	// @Test
	public void givenAValidUserWhenAUserIsRegisteredThenTheServerShouldReturnResponseStatusOK() {
		StatusType statusType = userConnectionResource.registerUser(AN_ACCOUNT_DTO).getStatusInfo();
		assertEquals(Status.OK, statusType);
	}

	// FIXME ServiceLocator
	// @Test
	public void givenNoUserInfoWhenAUserIsRegisteredTheServerShouldReturnReponseStatusBadRequest() {
		StatusType statusType = userConnectionResource.registerUser(AN_ACCOUNT_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

}
