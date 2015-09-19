package org.RealEstateMM.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

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

		connectionService = new UserConnectionService(userRepository, assembler);
	}

	@Test
	public void givenValidCredentionWhenUserConnectShouldReturnUsersInformations() {
		given(userRepository.getUserWithPseudoAndPassword(USER_PSEUDO, USER_PASSWORD)).willReturn(userAccount);
		given(assembler.buildDTO(userAccount)).willReturn(userInfos);

		UserInformations returnedInfos = connectionService.connectWithCredentials(credentials);

		assertSame(userInfos, returnedInfos);
	}

	@Test
	public void whenConnectWithUserCredentialsIsCalledThenGetUserFromRepository() {
		connectionService.connectWithCredentials(credentials);
		verify(userRepository).getUserWithPseudoAndPassword(USER_PSEUDO, USER_PASSWORD);
	}

	@Test
	public void givenConnectWithUserCredentialsIsCalledWhenRepositorySuccessfullyReturnsUserThenUserInfoDTOAssemblerIsCalled() {
		given(userRepository.getUserWithPseudoAndPassword(USER_PSEUDO, USER_PASSWORD)).willReturn(userAccount);
		connectionService.connectWithCredentials(credentials);
		verify(assembler).buildDTO(userAccount);
	}

	@Test
	public void givenConnectWithUserCredentialsIsCalledWhenDTOAssemblerReturnsUserInfoDTOThenReturnsDTO() {
		given(userRepository.getUserWithPseudoAndPassword(USER_PSEUDO, USER_PASSWORD)).willReturn(userAccount);
		given(assembler.buildDTO(userAccount)).willReturn(userInfos);

		UserInformations returnedDTO = connectionService.connectWithCredentials(credentials);

		assertEquals(userInfos, returnedDTO);
	}
}
