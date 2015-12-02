package org.RealEstateMM.services.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class UserServiceSecurityTest {
	private final String PSEUDONYM = "bobby134";
	private final String PASSWORD = "myOriginalPassword12345";
	private UserServiceHandler serviceHandler;
	private UserDTO dto;
	private UserAuthorizations authorizations;
	private UserServiceSecurity userService;

	@Before
	public void setup() {
		serviceHandler = mock(UserServiceHandler.class);
		dto = mock(UserDTO.class);
		authorizations = mock(UserAuthorizations.class);

		userService = new UserServiceSecurity(serviceHandler, authorizations);
	}

	@Test
	public void givenUserDTOWhenCreateUserShouldAskServiceHandlerToCreateUser() throws Throwable {
		userService.createUser(dto);
		verify(serviceHandler).createUser(dto);
	}

	@Test
	public void givenPseudonymAndPasswordWhenAuthenticateShouldAskServiceToAuthenticateUser() throws Throwable {
		given(serviceHandler.authenticate(PSEUDONYM, PASSWORD)).willReturn(dto);
		UserDTO returnedDTO = userService.authenticate(PSEUDONYM, PASSWORD);
		assertSame(dto, returnedDTO);
	}

	@Test
	public void givenConfirmatinoCodeWhenConfirmEmailAddressShouldAskServiceToConfirmEmailAddress() throws Throwable {
		final String CONFIRMATION_CODE = "confirmMyEmailAddreess";
		userService.confirmEmailAddress(CONFIRMATION_CODE);
		verify(serviceHandler).confirmEmailAddress(CONFIRMATION_CODE);
	}

	@Test
	public void givenPseudonymAndUserDtoWhenUpdateUserProfileShouldVerifyUserAccessBeforeUpdatingProfile()
			throws Throwable {
		userService.updateUserProfile(PSEUDONYM, dto);

		InOrder inOrder = inOrder(authorizations, serviceHandler);
		inOrder.verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER, AccessLevel.SELLER);
		inOrder.verify(serviceHandler).updateUserProfile(PSEUDONYM, dto);
	}

	@Test
	public void givenPseudonymWhenGetUserProfileThenSholdAskServiceForProfile() throws Throwable {
		given(serviceHandler.getUserProfile(PSEUDONYM)).willReturn(dto);
		UserDTO returnedDTO = userService.getUserProfile(PSEUDONYM);
		assertSame(dto, returnedDTO);
	}
}
