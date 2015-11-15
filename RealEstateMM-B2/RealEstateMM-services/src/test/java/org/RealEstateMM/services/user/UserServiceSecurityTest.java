package org.RealEstateMM.services.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.junit.Before;
import org.junit.Test;

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
		given(authorizations.isUserAuthorized(anyString(), anyVararg())).willReturn(true);

		userService = new UserServiceSecurity(serviceHandler, authorizations);
	}

	@Test
	public void givenUserDTOWhenCreateUserShouldAskServiceHandlerToCreateUser() {
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
	public void givenPseudonymAndUserDtoWhenUpdateUserProfileShouldCheckIfUserIsAllowedWithBuyerAndSellerAccessLevel()
			throws Throwable {
		userService.updateUserProfile(PSEUDONYM, dto);
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.BUYER, AccessLevel.SELLER);
	}

	@Test
	public void givenUserIsAuthorizedWhenUpdateUserProfileShouldAskServiceToUpdateProfile() throws Throwable {
		userService.updateUserProfile(PSEUDONYM, dto);
		verify(serviceHandler).updateUserProfile(PSEUDONYM, dto);
	}

	@Test(expected = UnauthorizedAccessException.class)
	public void givenUserIsNotAuthorizedWhenUpdateUserProfileShouldThrowException() throws Throwable {
		given(authorizations.isUserAuthorized(anyString(), anyVararg())).willReturn(false);
		userService.updateUserProfile(PSEUDONYM, dto);
	}

	@Test
	public void givenPseudonymWhenGetUserProfileThenSholdAskServiceForProfile() {
		given(serviceHandler.getUserProfile(PSEUDONYM)).willReturn(dto);
		UserDTO returnedDTO = userService.getUserProfile(PSEUDONYM);
		assertSame(dto, returnedDTO);
	}
}
