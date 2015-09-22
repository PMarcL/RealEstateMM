package org.RealEstateMM.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.helpers.DefaultUserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserAssembler;
import org.RealEstateMM.services.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class UserRegistrationServiceTest {

	private final User UNIQUE_PSEUDO_USER = new DefaultUserBuilder().build();
	private final UserDTO UNIQUE_PSEUDO_USER_DTO = new UserAssembler().toDTO(UNIQUE_PSEUDO_USER);

	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private UserRegistrationService service;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserAssembler.class);

		given(userAssembler.toDTO(UNIQUE_PSEUDO_USER)).willReturn(UNIQUE_PSEUDO_USER_DTO);
		given(userAssembler.fromDTO(UNIQUE_PSEUDO_USER_DTO)).willReturn(UNIQUE_PSEUDO_USER);
		given(userRepository.addUser(UNIQUE_PSEUDO_USER)).willReturn(UNIQUE_PSEUDO_USER);

		service = new UserRegistrationService(userRepository, userAssembler);
	}

	@Test
	public void givenANewUserWhenRegisterThenCreateTheUserOnce() {
		service.register(UNIQUE_PSEUDO_USER_DTO);

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(userRepository, times(1)).addUser(captor.capture());
		assertEquals(UNIQUE_PSEUDO_USER, captor.getValue());
	}

}