package org.RealEstateMM.services.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.emailconfirmation.InvalidEmailConfirmationCodeException;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.RealEstateMM.services.user.dtos.UserAssembler;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

public class UserServiceTest {

	private final String INVALID_CONFIRMATION_CODE = "anInvalidCode";
	private final String PSEUDONYM = "pseudo34";
	private final String PASSWORD = "pw1234";
	private final UserDTO USER_DTO = new UserDTOBuilder().withPseudonym(PSEUDONYM).withPassword(PASSWORD).build();

	private User user;
	private UserInformations userInfos;
	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private UserEmailAddressValidator emailValidator;

	private UserService userService;

	@Before
	public void setup() {
		setupMocks();
		registerServices();

		userService = new UserService();
	}

	@After
	public void tearDown() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void whenCreateUserThenAddNewUserToRepository() {
		userService.createUser(USER_DTO);
		verify(userRepository).addUser(user);
	}

	@Test
	public void whenCreateUserThenSendEmailConfirmationWithCreatedUserEmailConfirmationCode() {
		UserInformations userInfos = mock(UserInformations.class);
		given(user.getUserInformations()).willReturn(userInfos);

		userService.createUser(USER_DTO);

		verify(emailValidator).sendEmailConfirmationMessage(userInfos);
	}

	@Test
	public void givenAPseudonymWithRightPassWordWhenAuthenticateThenReturnTheUserDTO() throws Exception {
		given(user.hasPassword(PASSWORD)).willReturn(true);
		UserDTO actual = userService.authenticate(PSEUDONYM, PASSWORD);
		assertEquals(USER_DTO, actual);
	}

	@Test(expected = UserNotFoundException.class)
	public void givenNoUserWhenAuthenticateThenThrowUserNotFoundException() throws Exception {
		userDoesNotExists();
		userService.authenticate(PSEUDONYM, PASSWORD);
	}

	@Test(expected = ImpossibleToConfirmEmailAddressException.class)
	public void givenAnInvalidConfirmationCodeWhenConfirmEmailThenThrowAnImpossibleToConfirmEmailAddressException()
			throws Exception {

		confirmationCodeIsInvalid();
		userService.confirmEmailAddress(INVALID_CONFIRMATION_CODE);
	}

	@Test
	public void givenAnExistingUserWhenEditUserProfileShouldUpdateUserInformationsInUserWithProperInfos() {
		userService.updateUserProfile(USER_DTO);

		ArgumentCaptor<UserInformations> argument = ArgumentCaptor.forClass(UserInformations.class);
		verify(user).updateUserInformations(argument.capture());
		validateUserProfile(argument.getValue());
	}

	@Test
	public void givenAnExistingUserWhenEditUserProfileShouldPersistUserAfterUpdatingUserInformations() {
		userService.updateUserProfile(USER_DTO);

		InOrder inOrder = inOrder(user, userRepository);
		inOrder.verify(user).updateUserInformations(any(UserInformations.class));
		inOrder.verify(userRepository).replaceUser(user);
	}

	@Test(expected = UserNotFoundException.class)
	public void givenAnUnexistingUserWhenEditUserProfileShouldThrowException() {
		userDoesNotExists();
		userService.updateUserProfile(USER_DTO);
	}

	@Test
	public void givenNewEmailAddressDifferentFromUserEmailAddressWhenUpdateUserProfileShouldSendEmailAddressConfirmationEmail() {
		given(user.hasEmailAddress(anyString())).willReturn(false);
		userService.updateUserProfile(USER_DTO);
		verify(emailValidator).sendEmailConfirmationMessage(userInfos);
	}

	@Test
	public void givenNewEmailAddressDifferentFromEmailAddressWhenUpdateUserProfileShouldLockUserBeforeReplacingItInRepository() {
		given(user.hasEmailAddress(anyString())).willReturn(false);

		userService.updateUserProfile(USER_DTO);

		InOrder inOrder = inOrder(user, userRepository);
		inOrder.verify(user).lock();
		inOrder.verify(userRepository).replaceUser(user);
	}

	@Test
	public void givenEmailAddressIsSameAsCurrentEmailAddressWhenUdpateUserProfileShouldNotSendEmailAddressConfirmationEmail() {
		given(user.hasEmailAddress(UserBuilder.DEFAULT_EMAIL_ADDRESS)).willReturn(true);
		userService.updateUserProfile(USER_DTO);
		verify(emailValidator, never()).sendEmailConfirmationMessage(any(UserInformations.class));
	}

	@Test
	public void givenExistingUserWhenGetUserProfileShouldReturnAssembledUserInformations() {
		UserDTO result = userService.getUserProfile(PSEUDONYM);
		assertSame(USER_DTO, result);
	}

	@Test(expected = UserNotFoundException.class)
	public void givenUserDoesNotExistsWhenGetUserProfileShouldThrowException() {
		userDoesNotExists();
		userService.getUserProfile(PSEUDONYM);
	}

	private void confirmationCodeIsInvalid() {
		doThrow(InvalidEmailConfirmationCodeException.class).when(emailValidator).confirmEmailAddress(anyString(),
				any(UserRepository.class));
	}

	private void userDoesNotExists() {
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(Optional.empty());
	}

	private void validateUserProfile(UserInformations userInfos) {
		assertEquals(USER_DTO.getPseudonym(), userInfos.pseudonym);
		assertEquals(USER_DTO.getEmailAddress(), userInfos.emailAddress);
		assertEquals(USER_DTO.getFirstName(), userInfos.firstName);
		assertEquals(USER_DTO.getLastName(), userInfos.lastName);
		assertEquals(USER_DTO.getPassword(), userInfos.password);
		assertEquals(USER_DTO.getPhoneNumber(), userInfos.phoneNumber);
	}

	private void registerServices() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(UserAssembler.class, userAssembler);
		ServiceLocator.getInstance().registerService(UserEmailAddressValidator.class, emailValidator);
	}

	private void setupMocks() {
		user = mock(User.class);
		userInfos = mock(UserInformations.class);
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserAssembler.class);
		emailValidator = mock(UserEmailAddressValidator.class);

		given(userAssembler.fromDTO(USER_DTO)).willReturn(user);
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(Optional.of(user));
		given(user.getUserInformations()).willReturn(userInfos);
		given(userAssembler.toDTO(user)).willReturn(USER_DTO);
	}

}