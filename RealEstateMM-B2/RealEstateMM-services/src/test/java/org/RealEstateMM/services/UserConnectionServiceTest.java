package org.RealEstateMM.services;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.UserConnector;
import org.RealEstateMM.domain.user.UserInformation;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.DTO.UserCredentials;
import org.junit.Before;
import org.junit.Test;

public class UserConnectionServiceTest {

	private final String USER_PSEUDO = "UserExample90";
	private final String USER_EMAIL = "user@example.com";

	private UserConnectionService connectionService;

	private UserCredentials userCredentials;
	private UserInformation userInfo;
	private UserRepository userRepoMock;
	private UserConnector userConnectorMock;

	@Before
	public void initialisation() {
		userRepoMock = mock(UserRepository.class);
		userConnectorMock = mock(UserConnector.class);
		userInfo = new UserInformation();
		userCredentials = new UserCredentials();
		userCredentials.setEmail(USER_EMAIL);
		userCredentials.setPseudo(USER_PSEUDO);

		connectionService = new UserConnectionService(userRepoMock, userConnectorMock);
	}

	@Test
	public void whenConnectWithUserCredentialsIsCalledThenGetUserFromRepository() {
		connectionService.connectWithCredentials(userCredentials);
		verify(userRepoMock).getUserWithPseudoAndEmail(USER_PSEUDO, USER_EMAIL);
	}

	@Test
	public void whenConnectWithUserCredentialsIsCalledThenCallsUserConnector() {
		given(userRepoMock.getUserWithPseudoAndEmail(USER_PSEUDO, USER_EMAIL)).willReturn(userInfo);
		connectionService.connectWithCredentials(userCredentials);
		verify(userConnectorMock).ConnectUser(userInfo);
	}
}
