package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import org.RealEstateMM.domain.user.UserWithPseudonymAlreadyStoredException;
import org.RealEstateMM.services.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.anticorruption.UserServiceAntiCorruption;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.DefaultUserDTOBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserResourceTest {

	private final UserDTO A_USER_DTO = new DefaultUserDTOBuilder().build();

	private UserResource userConnectionResource;

	private UserServiceAntiCorruption userServiceAC;

	@Before
	public void setup() {
		userServiceAC = mock(UserServiceAntiCorruption.class);
		userConnectionResource = new UserResource(userServiceAC);
	}

	@Test
	public void givenAUserWhenRegisterUserThenShouldCallServiceLayer() {
		userConnectionResource.registerUser(A_USER_DTO).getStatusInfo();
		verify(userServiceAC).createUser(A_USER_DTO);
	}

	@Test
	public void givenAValidUserWhenAUserIsRegisteredThenTheServerShouldReturnResponseStatusOK() {
		StatusType statusType = userConnectionResource.registerUser(A_USER_DTO).getStatusInfo();
		assertEquals(Status.OK, statusType);
	}

	@Test
	public void givenAUserWithInvalidInformationWhenCreateUserThenReturnsResponseBadRequest() {
		doThrow(InvalidUserInformationsException.class).when(userServiceAC).createUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.registerUser(A_USER_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

	@Test
	public void givenAUserWithValidInformationWhenUserWithSamePseudoAlreadyExistsThenReturnsResponseBadRequest() {
		doThrow(UserWithPseudonymAlreadyStoredException.class).when(userServiceAC).createUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.registerUser(A_USER_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

	@Test
	public void givenAUserWhenUnexpectedExceptionThenReturnsResponseServerError() {
		doThrow(Exception.class).when(userServiceAC).createUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.registerUser(A_USER_DTO).getStatusInfo();
		assertEquals(Status.INTERNAL_SERVER_ERROR, statusType);
	}

}
