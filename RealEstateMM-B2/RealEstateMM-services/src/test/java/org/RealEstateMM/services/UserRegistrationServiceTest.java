package org.RealEstateMM.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.RealEstateMM.domain.user.Email;
import org.RealEstateMM.domain.user.Name;
import org.RealEstateMM.domain.user.PhoneNumber;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserAssembler;
import org.RealEstateMM.services.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class UserRegistrationServiceTest {

	private final String UNIQUE_PSEUDO = "MyCoolName";
	private final Name A_NAME = new Name("John", "Doe");
	private final Email A_EMAIL = new Email("myEmail@something.ca");
	private final PhoneNumber A_PHONE_NUM = new PhoneNumber("418-656-7766");

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void givenANewUserWithUniquePseudonymWhenRegisterThenCreateTheUserOnce() {
		User user = aNewUserWithUniquePseudo();
		UserDTO userDTO = new UserAssembler().buildDTO(user);

		UserRepository userRepoMock = mock(UserRepository.class);
		UserAssembler userAssembler = mock(UserAssembler.class);
		when(userAssembler.assemble(userDTO)).thenReturn(user);
		UserRegistrationService userRegistrationService = new UserRegistrationService(userRepoMock, userAssembler);

		userRegistrationService.register(userDTO);

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(userRepoMock, times(1)).create(captor.capture());
		assertEquals(user, captor.getValue());
	}

	@Test
	public void givenANewUserWithAnAlreadyExistingPseudonymWhenRegisterThenThrowException() {

	}

	private User aNewUserWithUniquePseudo() {
		return new User(UNIQUE_PSEUDO, A_NAME, A_EMAIL, A_PHONE_NUM);
	}

}
