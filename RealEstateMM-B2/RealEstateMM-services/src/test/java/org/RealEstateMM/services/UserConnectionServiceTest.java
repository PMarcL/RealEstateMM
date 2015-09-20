package org.RealEstateMM.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.UserAccount;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserAccountAssembler;
import org.RealEstateMM.services.dto.UserCredentials;
import org.RealEstateMM.services.dto.UserInformations;
import org.junit.Before;
import org.junit.Test;

public class UserConnectionServiceTest {

	private final String USER_PSEUDO = "UserExample90";
	private final String USER_PASSWORD = "password90";

	private UserConnectionService connectionService;

	private UserCredentials credentials;
	private UserAccount userAccount;
	private UserRepository userRepository;
	private UserAccountAssembler assembler;
	private UserInformations userInfos;

	@Before
	public void initialisation() {
		userRepository = mock(UserRepository.class);
		assembler = mock(UserAccountAssembler.class);
		userAccount = mock(UserAccount.class);
		userInfos = mock(UserInformations.class);
		credentials = new UserCredentials();
		credentials.setPassword(USER_PASSWORD);
		credentials.setPseudo(USER_PSEUDO);
		given(assembler.buildDTO(userAccount)).willReturn(userInfos);

		connectionService = new UserConnectionService(userRepository, assembler);
	}

	@Test
	public void givenValidCredentionWhenUserConnectShouldReturnUsersInformations() {
		credentialsAreValid();
		UserInformations returnedInfos = connectionService.connectWithCredentials(credentials);
		assertSame(userInfos, returnedInfos);
	}

	private void credentialsAreValid() {
		given(userRepository.getUserWithPseudonym(USER_PSEUDO)).willReturn(Optional.of(userAccount));
		given(userAccount.hasPassword(USER_PASSWORD)).willReturn(true);
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
		given(userRepository.getUserWithPseudonym(USER_PSEUDO)).willReturn(Optional.of(userAccount));
		given(userAccount.hasPassword(USER_PASSWORD)).willReturn(false);
	}
}
