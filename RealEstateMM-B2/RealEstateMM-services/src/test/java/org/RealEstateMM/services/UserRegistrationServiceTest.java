package org.RealEstateMM.services;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserInformations;
import org.RealEstateMM.services.dto.UserInformationsAssembler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class UserRegistrationServiceTest {
	private UserRepository userRepository;
	private UserInformationsAssembler userAssembler;
	private UserRegistrationService service;
	private SessionService sessionService;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserInformationsAssembler.class);
		sessionService = mock(SessionService.class);

		service = new UserRegistrationService(userRepository, sessionService, userAssembler);
	}

	@Test
	public void givenNewUserWhenRegisterShouldAddTheUserAndCreateUserSession() {
		UserInformations userInfos = mock(UserInformations.class);
		User user = mock(User.class);
		given(userAssembler.fromDTO(userInfos)).willReturn(user);

		service.register(userInfos);

		InOrder inOrder = inOrder(userRepository, sessionService);
		inOrder.verify(userRepository).addUser(user);
		inOrder.verify(sessionService).createSession(user);
	}

	// TODO check this test --> weird
	// @Test
	// public void
	// givenANewUserWithAnUniquePseudoWhenRegisterThenReturnUserDTOWithSessionToken()
	// {
	// UserDTO actual = service.register(UNIQUE_PSEUDO_USER_DTO);
	//
	// UserDTO expected = new UserAssembler().buildDTO(UNIQUE_PSEUDO_USER);
	// expected.setToken(SOME_TOKEN);
	// assertEquals(expected, actual);
	// }
}
