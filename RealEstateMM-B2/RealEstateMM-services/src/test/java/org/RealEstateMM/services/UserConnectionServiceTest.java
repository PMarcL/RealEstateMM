package org.RealEstateMM.services;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.DTO.UserCredentials;
import org.RealEstateMM.services.DTO.UserInformationsDTO;
import org.RealEstateMM.services.DTO.UserInformationsDTOAssembler;
import org.junit.Before;
import org.junit.Test;

public class UserConnectionServiceTest {

	private final String USER_PSEUDO = "UserExample90";
	private final String USER_PASSWORD = "password90";

	private UserConnectionService connectionService;

	private UserCredentials userCredentials;
	private UserInformations userInfo;
	private UserRepository userRepoMock;
	private UserInformationsDTOAssembler dtoAssemblerMock;
	private UserInformationsDTO userInfoDTO;

	@Before
	public void initialisation() {
		userRepoMock = mock(UserRepository.class);
		dtoAssemblerMock = mock(UserInformationsDTOAssembler.class);
		userInfo = new UserInformations();
		userInfoDTO = mock(UserInformationsDTO.class);
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

		UserInformationsDTO returnedDTO = connectionService.connectWithCredentials(userCredentials);

		assertEquals(userInfoDTO, returnedDTO);
	}
}
