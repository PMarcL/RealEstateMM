package org.RealEstateMM.services;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserInformations;
import org.RealEstateMM.services.dto.UserInformationsAssembler;
import org.junit.Before;
import org.junit.Test;

public class UserRegistrationServiceTest {
	private UserRepository userRepository;
	private UserInformationsAssembler userAssembler;
	private UserRegistrationService service;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserInformationsAssembler.class);

		service = new UserRegistrationService(userRepository, userAssembler);
	}

	@Test
	public void givenNewUserWhenRegisterShouldAddTheUser() {
		UserInformations userInfos = mock(UserInformations.class);
		User user = mock(User.class);
		given(userAssembler.fromDTO(userInfos)).willReturn(user);

		service.register(userInfos);

		verify(userRepository).addUser(user);
	}
}
