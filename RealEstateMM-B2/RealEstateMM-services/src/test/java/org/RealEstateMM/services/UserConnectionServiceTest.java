package org.RealEstateMM.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserCredentials;
import org.RealEstateMM.services.dto.UserInformations;
import org.RealEstateMM.services.dto.UserInformationsAssembler;
import org.junit.Before;
import org.junit.Test;

public class UserConnectionServiceTest {

	private final String USER_PSEUDO = "UserExample90";
	private final String USER_PASSWORD = "password90";

	private UserConnectionService connectionService;

	private UserCredentials credentials;
	private User user;
	private UserRepository userRepository;
	private UserInformationsAssembler assembler;
	private UserInformations userInfos;

	@Before
	public void initialisation() {
		userRepository = mock(UserRepository.class);
		assembler = mock(UserInformationsAssembler.class);
		user = mock(User.class);
		userInfos = mock(UserInformations.class);
		credentials = new UserCredentials();
		credentials.setPassword(USER_PASSWORD);
		credentials.setPseudo(USER_PSEUDO);
		given(assembler.toDTO(user)).willReturn(userInfos);

		connectionService = new UserConnectionService(userRepository, assembler);
	}

	@Test
	public void givenValidCredentionWhenUserConnectShouldReturnUsersInformations() {
		credentialsAreValid();
		UserInformations returnedInfos = connectionService.connectWithCredentials(credentials);
		assertSame(userInfos, returnedInfos);
	}

	private void credentialsAreValid() {
		given(userRepository.getUserWithPseudonym(USER_PSEUDO)).willReturn(Optional.of(user));
		given(user.hasPassword(USER_PASSWORD)).willReturn(true);
	}

	@Test(expected = UserNotFoundException.class)
	public void givenUserDoesNotHaveAnAccountWhenUserConnectShouldThrowException() {
		given(userRepository.getUserWithPseudonym(USER_PSEUDO)).willReturn(Optional.empty());
		connectionService.connectWithCredentials(credentials);
	}

	@Test(expected = ErronousPasswordException.class)
	public void givenUserConnectWithErronousPasswordWhenUserConnectShouldThrowException() {
		passwordIsErronous();
		connectionService.connectWithCredentials(credentials);
	}

	private void passwordIsErronous() {
		given(userRepository.getUserWithPseudonym(USER_PSEUDO)).willReturn(Optional.of(user));
		given(user.hasPassword(USER_PASSWORD)).willReturn(false);
	}
}
