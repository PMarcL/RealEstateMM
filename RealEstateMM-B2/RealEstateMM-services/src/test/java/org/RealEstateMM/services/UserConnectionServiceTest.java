package org.RealEstateMM.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserAssembler;
import org.RealEstateMM.services.dto.UserCredentials;
import org.RealEstateMM.services.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;

public class UserConnectionServiceTest {

	private final String USER_PSEUDO = "UserExample90";
	private final String USER_PASSWORD = "password90";

	private UserConnectionService connectionService;

	private UserCredentials userCredentials;
	private User userInfo;
	private UserRepository userRepoMock;
	private UserAssembler dtoAssemblerMock;
	private UserDTO userInfoDTO;

	@Before
	public void initialisation() {
		userRepoMock = mock(UserRepository.class);
		dtoAssemblerMock = mock(UserAssembler.class);
		userInfo = mock(User.class);
		userInfoDTO = mock(UserDTO.class);
		userCredentials = new UserCredentials();
		userCredentials.setPassword(USER_PASSWORD);
		userCredentials.setPseudo(USER_PSEUDO);

		connectionService = new UserConnectionService(userRepoMock, dtoAssemblerMock);
	}

	@Test
	public void whenConnectWithUserCredentialsIsCalledThenGetUserFromRepository() {
		connectionService.connectWithCredentials(userCredentials);
		verify(userRepoMock).getUserWithPseudoAndPassword(USER_PSEUDO, USER_PASSWORD);
	}

	@Test
	public void givenConnectWithUserCredentialsIsCalledWhenRepositorySuccessfullyReturnsUserThenUserInfoDTOAssemblerIsCalled() {
		given(userRepoMock.getUserWithPseudoAndPassword(USER_PSEUDO, USER_PASSWORD)).willReturn(userInfo);
		connectionService.connectWithCredentials(userCredentials);
		verify(dtoAssemblerMock).buildDTO(userInfo);
	}

	@Test
	public void givenConnectWithUserCredentialsIsCalledWhenDTOAssemblerReturnsUserInfoDTOThenReturnsDTO() {
		given(userRepoMock.getUserWithPseudoAndPassword(USER_PSEUDO, USER_PASSWORD)).willReturn(userInfo);
		given(dtoAssemblerMock.buildDTO(userInfo)).willReturn(userInfoDTO);

		UserDTO returnedDTO = connectionService.connectWithCredentials(userCredentials);

		assertEquals(userInfoDTO, returnedDTO);
	}
}
