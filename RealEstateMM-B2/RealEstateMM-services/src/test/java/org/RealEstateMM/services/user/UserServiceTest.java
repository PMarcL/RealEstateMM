package org.RealEstateMM.services.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.Users;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.user.dtos.UserAssembler;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {
	private final String PSEUDONYM = "bobby134";
	private final String PASSWORD = "myTooOriginalPassword12345";

	private UserDTO dto;
	private UserAssembler userAssembler;
	private User user;
	private Users users;
	private UserService service;

	@Before
	public void setup() {
		dto = mock(UserDTO.class);
		userAssembler = mock(UserAssembler.class);
		user = mock(User.class);
		given(userAssembler.fromDTO(dto)).willReturn(user);
		given(userAssembler.toDTO(user)).willReturn(dto);
		users = mock(Users.class);

		ServiceLocator.getInstance().registerService(Users.class, users);

		service = new UserService(userAssembler);
	}

	@After
	public void tearDown() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void givenUserDTOWhenCreateUserShouldAskUsersToCreateAssembledUser() throws Throwable {
		service.createUser(dto);
		verify(users).addUser(user);
	}

	@Test
	public void givenPseudonymAndPasswordWhenAuthenticateShouldReturnAuthenticatedUser() throws Throwable {
		service.authenticate(PSEUDONYM, PASSWORD);
		verify(users).authenticate(PSEUDONYM, PASSWORD);
	}

	@Test
	public void givenPseudonymAndPasswordWhenAuthenticateShouldReturnAssembledDTO() throws Throwable {
		given(users.authenticate(PSEUDONYM, PASSWORD)).willReturn(user);
		UserDTO returnedDto = service.authenticate(PSEUDONYM, PASSWORD);
		assertSame(dto, returnedDto);
	}

	@Test
	public void givenAConfirmationCodeWhenConfirmEmailAddressThenShouldAskUsers() throws Throwable {
		final String CONFIRMATION_CODE = "confirmMyEmailAddressPlease";
		service.confirmEmailAddress(CONFIRMATION_CODE);
		verify(users).confirmEmailAddress(CONFIRMATION_CODE);
	}

	@Test
	public void givenPseudonymWhenGetUserShouldReturnAssembledUser() throws Throwable {
		given(users.getUser(PSEUDONYM)).willReturn(user);
		UserDTO returnedDto = service.getUserProfile(PSEUDONYM);
		assertSame(dto, returnedDto);
	}

	@Test
	public void givenDTOWhenUpdateUserProfileThenShouldUpdateUserProfile() throws Throwable {
		UserInformations userInfos = mock(UserInformations.class);
		given(userAssembler.createUserInformations(dto)).willReturn(userInfos);

		service.updateUserProfile(PSEUDONYM, dto);

		verify(users).updateUserProfile(userInfos);
	}
}